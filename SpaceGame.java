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
    static final int numEnemies = 8;
    static int xShip = BOUNDS_SIZE/2; // The x-position of the ship
    static int yShip = BOUNDS_SIZE - OBJ_SIZE; // The y-position of the ship
    int xFood = BOUNDS_SIZE/2; // The x-position of the worm food
    int yFood = BOUNDS_SIZE/2; // The y-position of the worm food
    int score = 0; // The player's score
    private Timer timer;
    boolean canMove = true; // Ensure's an object does not move more than once per cycle
    SpaceObject bullet1;
    SpaceObject bullet2;
    SpaceObject bullet3;
    SpaceObject bullet4;
    SpaceObject player = new SpaceObject(1, 1, xShip, yShip);
    SpaceObject barrier1;
    SpaceObject barrier2;
    SpaceObject barrier3;
    SpaceObject barrier4;

    public void moveShipRight()
    {
    	boolean inBounds = checkUpperBounds(player.xPos);
    	if(inBounds == true){
    		player.xPos += OBJ_SIZE;
    	}
    }
    
    public void moveShipLeft()
    {
    	boolean inBounds = checkLowerBounds(player.xPos);
    	if(inBounds == true)
    	{
    		player.xPos -= OBJ_SIZE;
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
    
    public void checkFood(){
    	if(xShip == xFood && yShip == yFood)
    	{
    		score++;
    		moveFood();
    	}
    }
    
    public void fireBullet(int bulletNum, int xPos, int yPos){
    	
    }
    
    
    public void moveFood(){
        Random ran = new Random();
        xFood = ran.nextInt(BOUNDS_SIZE - OBJ_SIZE);
        yFood = ran.nextInt(BOUNDS_SIZE - OBJ_SIZE);
        while(xFood % OBJ_SIZE != 0)
        {
        	xFood = ran.nextInt(BOUNDS_SIZE - OBJ_SIZE);
        }
        while(yFood % OBJ_SIZE != 0)
        {
        	yFood = ran.nextInt(BOUNDS_SIZE - OBJ_SIZE);
        }
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D G = (Graphics2D) g;
        
        G.setColor(Color.BLACK); // Background color
        G.fillRect(0, 0, 512, 512); // Places background over JPanel default none
        
        G.setColor(Color.MAGENTA); // Ship color
        G.fillRect(player.xPos, player.yPos, player.size, player.size); // Creates worm on screen
        
        for(int i = 0; i < numEnemies; i++){
        	G.setColor(Color.RED); 
            G.fillRect(0, 0, OBJ_SIZE, OBJ_SIZE);
        }
        
        G.setColor(Color.YELLOW); // Bullet color
        G.fillRect(xFood, yFood, OBJ_SIZE/4, OBJ_SIZE/4); // Creates bullet on screen
        
        for(int i = 0; i < 4; i++){
        	G.setColor(Color.BLUE); 
            G.fillRect(i * 128 + OBJ_SIZE, BOUNDS_SIZE - OBJ_SIZE * 4, 64, 64);
        }
        
        G.setColor(Color.WHITE); // The on-screen text color
        G.drawString("Use the arrow keys to move!", 156, 156);
        G.drawString("Score: " + String.valueOf(score), 214, 166);
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
    	checkFood();
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
        	//moveUp
        }
        else if(e.getKeyCode() == 39)
        {
        	moveShipRight();
        }
        else if(e.getKeyCode() == 40)
        {
        	//moveDown
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
