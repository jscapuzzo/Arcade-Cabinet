import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

@SuppressWarnings("serial")
public class SpaceGame extends JPanel implements KeyListener, ActionListener
{
	static final int OBJ_SIZE = 32; // The size of the ship and ship food
    static final int BOUNDS_SIZE = 512; //The size of the game boundaries
    static final int BULLET_SIZE = OBJ_SIZE/4;
    static final int eBulletPos = OBJ_SIZE - BULLET_SIZE;
    static final int numEnemies = 8;
    int enemyDirection = OBJ_SIZE/32;
    static int xShip = BOUNDS_SIZE/2; // The x-position of the ship
    static int yShip = BOUNDS_SIZE - OBJ_SIZE; // The y-position of the ship
    int score = 0; // The player's score
    int lives = 3;
    private Timer timer;
    boolean canMove = true; // Ensure's an object does not move more than once per cycle
    SpaceObject pBullet = new SpaceObject(1, 2, xShip + BULLET_SIZE, yShip);
    SpaceObject player = new SpaceObject(1, 1, xShip, yShip);
    SpaceObject barrier1;
    SpaceObject barrier2;
    SpaceObject barrier3;
    SpaceObject barrier4;
    SpaceObject enemy1 = new SpaceObject(2, 1, OBJ_SIZE, 0);
    SpaceObject eBullet = new SpaceObject(2, 2, enemy1.xPos + BULLET_SIZE, eBulletPos);
    boolean pBulletMoving = false;
    boolean eBulletMoving = false;

    public void moveShipRight()
    {
    	boolean inBounds = checkUpperBounds(player.xPos);
    	if(inBounds == true){
    		player.xPos += OBJ_SIZE;
    	}
    	
    	if(player.yPos == pBullet.yPos) {
    		pBullet.xPos = player.xPos + BULLET_SIZE;
    		pBullet.yPos = player.yPos;
    	}
    }
    
    public void moveShipLeft()
    {
    	boolean inBounds = checkLowerBounds(player.xPos);
    	if(inBounds == true)
    	{
    		player.xPos -= OBJ_SIZE;
    	}
    	
    	if(player.yPos == pBullet.yPos) {
    		pBullet.xPos = player.xPos + BULLET_SIZE;
    		pBullet.yPos = player.yPos;
    	}
    }
    
    public void fireBullet() 
    {
    	pBullet.yPos -= OBJ_SIZE/4;
    	
    	if(pBullet.yPos < 0) {
    		pBullet.xPos = player.xPos + BULLET_SIZE;
    		pBullet.yPos = player.yPos;
    		pBulletMoving = false;
    	}
    	
    }
    
    public void fireEBullet() 
    {
    	eBullet.yPos += OBJ_SIZE/4;
    	
    	if(eBullet.yPos > BOUNDS_SIZE) {
    		eBullet.xPos = enemy1.xPos + BULLET_SIZE;
    		eBullet.yPos = enemy1.yPos;
    		eBulletMoving = false;
    	}
    	
    }
    
    public void moveBullet()
    {
    	if(pBulletMoving == true) {
    		pBullet.yPos -= OBJ_SIZE/4;
    	}
    	
    	if(eBulletMoving == true) {
    		eBullet.yPos += OBJ_SIZE/4;
    	}
    	
    	if(eBulletMoving == false && eBullet.xPos != enemy1.xPos + BULLET_SIZE) {
    		eBullet.xPos = enemy1.xPos + BULLET_SIZE;
    		eBullet.yPos = enemy1.yPos;
    		eBulletMoving = false;
    	}
    }
    
    public boolean checkLowerBounds(int position)
    {
    	if(position <= 0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    public boolean checkUpperBounds(int position)
    {
    	if(position >= BOUNDS_SIZE - OBJ_SIZE)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    public void checkEnemy(){
    	if(pBullet.xPos <= enemy1.xPos + OBJ_SIZE && pBullet.xPos >= enemy1.xPos)
    	{
    		if(pBullet.yPos <= enemy1.yPos + OBJ_SIZE && pBullet.yPos >= enemy1.yPos) 
    		{
    			score++;
        		moveEnemy();
        		pBullet.xPos = player.xPos;
        		pBullet.yPos = player.yPos;
        		pBulletMoving = false;
    		}
    	}
    }
    
    public void checkPlayer(){
    	if(eBullet.xPos <= player.xPos + OBJ_SIZE && eBullet.xPos >= player.xPos)
    	{
    		if(eBullet.yPos <= player.yPos + OBJ_SIZE && eBullet.yPos >= player.yPos) 
    		{
    			lives--;
        		eBullet.xPos = enemy1.xPos;
        		eBullet.yPos = enemy1.yPos;
        		eBulletMoving = false;
    		}
    	}
    }
    
    public void moveEnemy(){
    	moveBullet();
        if(checkUpperBounds(enemy1.xPos) == false) {
        	enemyDirection = -enemyDirection;
        	enemy1.xPos += enemyDirection;
        	enemy1.yPos += OBJ_SIZE;
        }
        else if(checkLowerBounds(enemy1.xPos) == false) {
        	enemyDirection = -enemyDirection;
        	enemy1.xPos += enemyDirection;
        	enemy1.yPos += OBJ_SIZE;
        }
        else {
        	enemy1.xPos += enemyDirection;
        }
        
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D G = (Graphics2D) g;
        
        G.setColor(Color.BLACK); // Background color
        G.fillRect(0, 0, 512, 512); // Places background over JPanel default none
        
        if(lives > 0)
        {
	        
	        for(int i = 0; i < 4; i++){
	        	G.setColor(Color.BLUE); 
	            G.fillRect(i * 128 + OBJ_SIZE, BOUNDS_SIZE - OBJ_SIZE * 4, 64, 64);
	        }
	        
	        G.setColor(Color.YELLOW); // Bullet color
	        G.fillRect(pBullet.xPos, pBullet.yPos, OBJ_SIZE/4, OBJ_SIZE/4); // Creates player bullet on screen
	        
	        G.setColor(Color.MAGENTA); // Ship color
	        G.fillRect(player.xPos, player.yPos, player.size, player.size); // Creates worm on screen
	        
	        G.setColor(Color.ORANGE); // Enemy bullet color
	        G.fillRect(eBullet.xPos, eBullet.yPos, OBJ_SIZE/4, OBJ_SIZE/4); // Creates enemy bullet on screen
	        
	        G.setColor(Color.RED); 
	        G.fillRect(enemy1.xPos, enemy1.yPos, OBJ_SIZE, OBJ_SIZE);
	        
	        G.setColor(Color.WHITE); // The on-screen text color
	        G.drawString("Use the arrow keys to move!", 156, 156);
	        G.drawString("Score: " + String.valueOf(score), 214, 166);
	        G.drawString("Lives: " + String.valueOf(lives), 214, 176);
	        
        }
        else
        {
        	G.setColor(Color.WHITE); // The on-screen text color
	        G.drawString("GAME OVER", 156, 156);
        }
        
        G.dispose();
    }

    public SpaceGame()
    {
    	//sets the gui to read keystrokes and make it the focus
    	addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        //the following was grabbed off Oracle's swing tutorial online
        //essentially it just automatically starts the game with a delay and sets the speed of the game
        timer = new Timer(15, this);
        //timer.setInitialDelay(3000);
        timer.start();
    }
    
    public static void main(String[] args) throws InterruptedException
    {
        JFrame f = new JFrame("Space Game");
        final SpaceGame w = new SpaceGame();
        final int BORDER_WIDTH = 16; //This number allow the game itself to be 512 x 512
        final int BORDER_HEIGHT = 39; //This number allow the game itself to be 512 x 512
        f.setSize(BOUNDS_SIZE + BORDER_WIDTH, BOUNDS_SIZE + BORDER_HEIGHT); 
        f.add(w);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void play()
    {
    	checkEnemy();
    	moveBullet();
    	checkPlayer();
    	moveEnemy();
        repaint();
    }
    
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == 37)
        {
        	moveShipLeft();
        }
        else if(e.getKeyCode() == 38)
        {
        	fireBullet();
        	pBulletMoving = true;
        }
        else if(e.getKeyCode() == 39)
        {
        	moveShipRight();
        }
        else if(e.getKeyCode() == 40)
        {
        	//moveDown
        	fireEBullet();
        	eBulletMoving = true;
        }
    }
	
    //following methods do nothing
    //need to be here for interface implementation 
    @Override
    public void keyReleased(KeyEvent e) {}
	
    @Override
    public void keyTyped(KeyEvent e) {}
    
    //allows game to be played in separate GUI
    @Override
    public void actionPerformed(ActionEvent e) 
    {
    	play();
    }
    
    public void stop()
    {
    	timer.stop();
    }
}
