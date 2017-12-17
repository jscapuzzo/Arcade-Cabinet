import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * Clone of classic arcade game Space Invaders with our unique take on it.
 * @author J. Capuzzo (lead) and R. King (partner)
 */
@SuppressWarnings("serial")
public class SpaceGame extends JPanel implements KeyListener, ActionListener
{
	public static final int OBJ_SIZE = 32; // The size of the ships
    public static final int BOUNDS_SIZE = 512; //The size of the game boundaries
    public static final int BULLET_SIZE = OBJ_SIZE/4; // The size of the bullet used by both ships
    private static final int eBulletPos = OBJ_SIZE - BULLET_SIZE; // Used to align the enemy bullet correctly
    private static final int enemyFireDelayValue = 180; // The delay between firing bullets for the enemy
    
    private int enemySpeed; // Determines how fast the enemy moves across the screen
    private int enemyDirection; // Determines the x-direction the enemy ship moves
    
    private int xShip; // The x-position of the ship
    private int yShip; // The y-position of the ship
    
    private int lives; // The player's number of lives left
    private int enemyFireDelay; // A counter that when it reaches zero, the enemy ships fires 
    private int barrier1Hit; // Value that determines damage value of the first barrier
    private int barrier2Hit; // Value that determines damage value of the second barrier
    private int barrier3Hit; // Value that determines damage value of the third barrier
    private int barrier4Hit; // Value that determines damage value of the fourth barrier
    
    private Timer timer;
    
    private boolean pBulletMoving; // Ensures the player bullet only moves when fired
    private boolean eBulletMoving; // Ensures the enemy bullet only moves when fired
    
    private boolean resetEnemy; 
    private static boolean pauseGame = true;
    
    private SpaceObject player;
    private SpaceObject pBullet; // Player bullet
    private SpaceObject barrier1;
    private SpaceObject barrier2;
    private SpaceObject barrier3;
    private SpaceObject barrier4;
    private SpaceObject enemy;
    private SpaceObject eBullet; // Enemy bullet

    /**
     * Constructor for Space Game.
     * Mostly adds interaction to the object
     */
    public SpaceGame()
    {
    	newGame();
    	
    	//sets the GUI to read keystrokes and make it the focus
    	addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        //the following was grabbed off Oracle's swing tutorial online
        //essentially it just automatically starts the game with a delay and sets the speed of the game
        timer = new Timer(15, this);
        //timer.setInitialDelay(3000);
        timer.start();
    }
    
    private void newGame()
    {
    	enemySpeed = 32;
    	enemyDirection = OBJ_SIZE;
        
    	xShip = BOUNDS_SIZE/2;
    	yShip = BOUNDS_SIZE - OBJ_SIZE;
        
    	lives = 3;
    	enemyFireDelay = 240;
    	barrier1Hit = 2;
    	barrier2Hit = 2;
    	barrier3Hit = 2;
    	barrier4Hit = 2;
        
    	pBulletMoving = false;
        eBulletMoving = false;
        
        resetEnemy = false; 
        
        player = new SpaceObject(1, 1, xShip, yShip);
        pBullet = new SpaceObject(1, 2, xShip + BULLET_SIZE, yShip);
        barrier1 = new SpaceObject(0, 0, 0 * 128 + OBJ_SIZE, BOUNDS_SIZE - OBJ_SIZE * 4);
        barrier2 = new SpaceObject(0, 0, 1 * 128 + OBJ_SIZE, BOUNDS_SIZE - OBJ_SIZE * 4);
        barrier3 = new SpaceObject(0, 0, 2 * 128 + OBJ_SIZE, BOUNDS_SIZE - OBJ_SIZE * 4);
        barrier4 = new SpaceObject(0, 0, 3 * 128 + OBJ_SIZE, BOUNDS_SIZE - OBJ_SIZE * 4);
        enemy = new SpaceObject(2, 1, OBJ_SIZE, 0);
        eBullet = new SpaceObject(2, 2, enemy.xPos + BULLET_SIZE, eBulletPos); // Enemy bullet
    }
    
    private void moveShipRight()
    {
    	boolean inBounds = checkUpperBounds(player.xPos);
    	if(inBounds == true){
    		player.xPos += OBJ_SIZE;
    	}
    	
    	if(player.yPos == pBullet.yPos) {
    		pBullet.xPos = player.xPos + pBullet.size;
    		pBullet.yPos = player.yPos;
    	}
    }
    
    private void moveShipLeft()
    {
    	boolean inBounds = checkLowerBounds(player.xPos);
    	if(inBounds == true)
    	{
    		player.xPos -= OBJ_SIZE;
    	}
    	
    	if(player.yPos == pBullet.yPos) {
    		pBullet.xPos = player.xPos + pBullet.size;
    		pBullet.yPos = player.yPos;
    	}
    }
    
    private void fireBullet() 
    {
    	pBullet.yPos -= OBJ_SIZE/4;
    }
    
    private void fireEBullet() 
    {
    	eBullet.yPos += OBJ_SIZE/4;
    }
    
    private void enemyShoot() 
    {
    	Random ran = new Random();
    	boolean shoot = ran.nextBoolean();
    	if(shoot == true && enemyFireDelay < 1) {
    		fireEBullet();
    		eBulletMoving = true;
    		enemyFireDelay = enemyFireDelayValue;
    	}
    	else {
    		enemyFireDelay--;
    	}
    }
    
    private void moveBullet()
    {
    	if(pBulletMoving == true) {
    		pBullet.yPos -= OBJ_SIZE/4;
    	}
    	
    	if(pBullet.yPos < 0) {
    		pBullet.xPos = player.xPos + pBullet.size;
    		pBullet.yPos = player.yPos;
    		pBulletMoving = false;
    	}
    	
    	if(eBulletMoving == true) {
    		eBullet.yPos += OBJ_SIZE/4;
    	}
    	
    	if(eBullet.yPos > BOUNDS_SIZE) {
    		eBullet.xPos = enemy.xPos + eBullet.size;
    		eBullet.yPos = enemy.yPos;
    		eBulletMoving = false;
    	}
    	
    	if(eBulletMoving == false && eBullet.xPos != enemy.xPos + eBullet.size) {
    		eBullet.xPos = enemy.xPos + eBullet.size;
    		eBullet.yPos = enemy.yPos;
    		eBulletMoving = false;
    	}
    }
    
    private static boolean checkLowerBounds(int position)
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
    
    private static boolean checkUpperBounds(int position)
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
    
    private void checkBarrier()
    {
    	if(eBullet.xPos <= barrier1.xPos + barrier1.size && eBullet.xPos >= barrier1.xPos)
    	{
    		if(eBullet.yPos <= barrier1.yPos + barrier1.size && eBullet.yPos >= barrier1.yPos) 
    		{
    			barrier1Hit--;
    			if(barrier1Hit > 0)
    			{
            		eBullet.xPos = enemy.xPos;
            		eBullet.yPos = enemy.yPos;
            		eBulletMoving = false;
    			}
    			
    		}
    	}
    	else if(eBullet.xPos <= barrier2.xPos + barrier2.size && eBullet.xPos >= barrier2.xPos)
    	{
    		if(eBullet.yPos <= barrier2.yPos + barrier2.size && eBullet.yPos >= barrier2.yPos) 
    		{
    			barrier2Hit--;
    			if(barrier2Hit > 0)
    			{
            		eBullet.xPos = enemy.xPos;
            		eBullet.yPos = enemy.yPos;
            		eBulletMoving = false;
    			}
    		}
    	}
    	else if(eBullet.xPos <= barrier3.xPos + barrier3.size && eBullet.xPos >= barrier3.xPos)
    	{
    		if(eBullet.yPos <= barrier3.yPos + barrier3.size && eBullet.yPos >= barrier3.yPos) 
    		{
    			barrier3Hit--;
    			if(barrier3Hit > 0)
    			{
            		eBullet.xPos = enemy.xPos;
            		eBullet.yPos = enemy.yPos;
            		eBulletMoving = false;
    			}
    		}
    	}
    	else if(eBullet.xPos <= barrier4.xPos + barrier4.size && eBullet.xPos >= barrier4.xPos)
    	{
    		if(eBullet.yPos <= barrier4.yPos + barrier4.size && eBullet.yPos >= barrier4.yPos) 
    		{
    			barrier4Hit--;
    			if(barrier4Hit > 0)
    			{
            		eBullet.xPos = enemy.xPos;
            		eBullet.yPos = enemy.yPos;
            		eBulletMoving = false;
    			}
    		}
    	}
    }
    
    private void checkEnemy()
    {
    	if(pBullet.xPos <= enemy.xPos + OBJ_SIZE && pBullet.xPos >= enemy.xPos)
    	{
    		if(pBullet.yPos <= enemy.yPos + OBJ_SIZE && pBullet.yPos >= enemy.yPos) 
    		{
    			resetEnemy = true;
    			enemySpeed--;
        		moveEnemy();
        		pBullet.xPos = player.xPos;
        		pBullet.yPos = player.yPos;
        		pBulletMoving = false;
    		}
    	}
    }
    
    private void checkPlayer()
    {
    	if(eBullet.xPos <= player.xPos + OBJ_SIZE && eBullet.xPos >= player.xPos)
    	{
    		if(eBullet.yPos <= player.yPos + OBJ_SIZE && eBullet.yPos >= player.yPos) 
    		{
    			lives--;
        		eBullet.xPos = enemy.xPos;
        		eBullet.yPos = enemy.yPos;
        		eBulletMoving = false;
    		}
    	}
    }
    
    private void moveEnemy()
    {
    	moveBullet();
        if(checkUpperBounds(enemy.xPos) == false) 
        {
        	enemyDirection = -enemyDirection;
        	enemy.xPos += enemyDirection/enemySpeed;
        	enemy.yPos += OBJ_SIZE;
        }
        else if(checkLowerBounds(enemy.xPos) == false) 
        {
        	enemyDirection = -enemyDirection;
        	enemy.xPos += enemyDirection/enemySpeed;
        	enemy.yPos += OBJ_SIZE;
        }
        else if(resetEnemy == true)
        {
        	enemy.xPos = OBJ_SIZE;
        	enemy.yPos = 0;
        	resetEnemy = false;
        	//eBulletMoving = false;
        }
        else {
        	enemy.xPos += enemyDirection/enemySpeed;
        }
    }
    
    /**
     * Paint the current state of the game. 
     * Using our timer, it repaints at a constant interval.
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D G = (Graphics2D) g;
        
        G.setColor(Color.BLACK); // Background color
        G.fillRect(0, 0, 512, 512); // Places background over JPanel default none
        
        if(enemySpeed < 3)
        {
        	G.setColor(Color.WHITE); // The on-screen text color
	        G.drawString("YOU WIN!", BOUNDS_SIZE/2 - OBJ_SIZE, BOUNDS_SIZE/2 - OBJ_SIZE);
        }
        else if(lives > 0)
        {
        	if(barrier1Hit == 2)
        	{
        		G.setColor(Color.BLUE); 
	            G.fillRect(barrier1.xPos, barrier1.yPos, barrier1.size, barrier1.size);
        	}
        	else if(barrier1Hit == 1)
        	{
        		G.setColor(Color.CYAN); 
	            G.fillRect(barrier1.xPos, barrier1.yPos, barrier1.size, barrier1.size);
        	}
            
        	if(barrier2Hit == 2)
        	{
        		G.setColor(Color.BLUE); 
	            G.fillRect(barrier2.xPos, barrier2.yPos, barrier2.size, barrier2.size);
        	}
        	else if(barrier2Hit == 1)
        	{
        		G.setColor(Color.CYAN); 
	            G.fillRect(barrier2.xPos, barrier2.yPos, barrier2.size, barrier2.size);
        	}
            
        	if(barrier3Hit == 2)
        	{
        		G.setColor(Color.BLUE); 
	            G.fillRect(barrier3.xPos, barrier3.yPos, barrier3.size, barrier3.size);
        	}
        	else if(barrier3Hit == 1)
        	{
        		G.setColor(Color.CYAN); 
	            G.fillRect(barrier3.xPos, barrier3.yPos, barrier3.size, barrier3.size);
        	}

        	if(barrier4Hit == 2)
        	{
        		G.setColor(Color.BLUE); 
	            G.fillRect(barrier4.xPos, barrier4.yPos, barrier4.size, barrier4.size);
        	}
        	else if(barrier4Hit == 1)
        	{
        		G.setColor(Color.CYAN); 
	            G.fillRect(barrier4.xPos, barrier4.yPos, barrier4.size, barrier4.size);
        	}
	        
	        G.setColor(Color.YELLOW); // Bullet color
	        G.fillRect(pBullet.xPos, pBullet.yPos, OBJ_SIZE/4, OBJ_SIZE/4); // Creates player bullet on screen
	        
	        G.setColor(Color.MAGENTA); // Ship color
	        G.fillRect(player.xPos, player.yPos, player.size, player.size); // Creates worm on screen
	        
	        G.setColor(Color.ORANGE); // Enemy bullet color
	        G.fillRect(eBullet.xPos, eBullet.yPos, OBJ_SIZE/4, OBJ_SIZE/4); // Creates enemy bullet on screen
	        
	        G.setColor(Color.RED); 
	        G.fillRect(enemy.xPos, enemy.yPos, OBJ_SIZE, OBJ_SIZE);
	        
	        G.setColor(Color.WHITE); // The on-screen text color
	        G.drawString("Lives: " + String.valueOf(lives), 214, 196);
	        G.drawString("Enemies left: " + String.valueOf(enemySpeed - 3), 214 - OBJ_SIZE/2, 206);
	        G.drawString("Press Left & Right Arrows to Move", 214 - 2 * OBJ_SIZE, 166);
	        G.drawString("Press Up Arrow or Space to Fire", 214 - 2 * OBJ_SIZE, 176);
	        if(pauseGame == true) {
	        	G.setColor(Color.WHITE);
	        	G.setFont(new Font("Arial", Font.BOLD, 20));
	        	G.drawString("Press the p key to play", 140, 232);
	        }
	        
        }
        else
        {
        	G.setColor(Color.WHITE); // The on-screen text color
	        G.drawString("GAME OVER", BOUNDS_SIZE/2 - OBJ_SIZE, BOUNDS_SIZE/2 - OBJ_SIZE);
	        G.drawString("OUT OF LIVES", BOUNDS_SIZE/2 - OBJ_SIZE - 5, BOUNDS_SIZE/2 - OBJ_SIZE + 20);
        }
        
        G.dispose();
    }
    
    /**
     * Main method used for testing
     * @param args
     * @throws InterruptedException
     */
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
    	if(pauseGame == true) {
    		//do nothing
    	}
    	else {
	    	checkEnemy();
	    	checkPlayer();
	    	moveEnemy();
	    	moveBullet();
	    	enemyShoot();
	    	checkBarrier();
    	}
    	repaint();
    }
    
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == 80) // p key
	    {
	       	pauseGame = !pauseGame;
	    }
		else if(pauseGame == true)
		{
			//do nothing until game is unpaused
		}
		if(e.getKeyCode() == 37) // Left arrow key
        {
			moveShipLeft();
		}
        else if(e.getKeyCode() == 32) // Space key
        {
        	fireBullet();
            pBulletMoving = true;    	
        }
        else if(e.getKeyCode() == 38) // Up arrow key
        {
        	fireBullet();
            pBulletMoving = true;
		}
        else if(e.getKeyCode() == 39) // Right arrow key
        {
        	moveShipRight();
		}
    }
	
    /* Following methods do nothing but make the KeyListener interface happy */
    @Override
    public void keyReleased(KeyEvent e) {}
	
    @Override
    public void keyTyped(KeyEvent e) {}
    /* End of useless methods */
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
    	play();
    }
    
    /**
     * Stop the game
     */
    public void stop()
    {
    	timer.stop();
    }
}
