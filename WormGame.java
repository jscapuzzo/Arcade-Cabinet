import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

@SuppressWarnings("serial")
public class WormGame extends JPanel implements KeyListener, ActionListener
{
	static final int OBJ_SIZE = 32; // The size of the worm and worm ford
    static final int BOUNDS_SIZE = 512; //The size of the game boundaries
    static final int winScore = 100;
    static int xWorm = 0; // The x-position of the worm
    static int yWorm = 0; // The y-position of the worm
    int time = 6000;
    int xFood = 64; // The x-position of the worm food
    int yFood = 64; // The y-position of the worm food
    int score = 0; // The player's score
    private Timer timer;
    boolean canMove = true; // Ensure's an object does not move more than once per cycle
    boolean pauseGame = true;
    
    public void moveWormRight()
    {
    	boolean inBounds = checkUpperBounds(xWorm);
    	if(inBounds == true){
    		xWorm += OBJ_SIZE;
    	}
    }
    
    public void moveWormLeft()
    {
    	boolean inBounds = checkLowerBounds(xWorm);
    	if(inBounds == true)
    	{
    		xWorm -= OBJ_SIZE;
    	}
    }
    
    public void moveWormUp()
    {
    	boolean inBounds = checkLowerBounds(yWorm);
    	if(inBounds == true)
    	{
    		yWorm -= OBJ_SIZE;
    	}
    }
    
    public void moveWormDown()
    {
    	boolean inBounds = checkUpperBounds(yWorm);
    	if(inBounds == true)
    	{
    		yWorm += OBJ_SIZE;
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
    	if(xWorm == xFood && yWorm == yFood)
    	{
    		score++;
    		moveFood();
    	}
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
        
        G.setColor(Color.WHITE); // Background color
        G.fillRect(0, 0, 512, 512); // Places background over JPanel default none
        
        if(time / 60 > 0)
        {
	        G.setColor(Color.BLACK); // Worm color
	        G.fillRect(xWorm, yWorm, OBJ_SIZE, OBJ_SIZE); // Creates worm on screen
	        
	        G.setColor(Color.GREEN); // Worm food color
	        G.fillRect(xFood, yFood, OBJ_SIZE, OBJ_SIZE); // Creates worm food on screen
	        
	        G.setColor(Color.BLUE); // The on-screen text color
	        G.drawString("Use the arrow keys to move!", 166, 156);
	        G.drawString("Get " + winScore + " to win!", 200, 166);
	        G.drawString("Score: " + String.valueOf(score), 214, 186);
	        G.drawString("Time left: " + String.valueOf(time / 60), 200, 196);
	        if(pauseGame == true) {
	        	G.setColor(Color.RED);
	        	G.drawString("Press the p key to play", 182, 216);
	        }
        }
        else
        {
        	G.setColor(Color.BLACK); // The on-screen text color
	        G.drawString("GAME OVER", BOUNDS_SIZE/2 - OBJ_SIZE, BOUNDS_SIZE/2 - OBJ_SIZE);
	        G.drawString("Final Score = " + String.valueOf(score), BOUNDS_SIZE/2 - OBJ_SIZE - 10, BOUNDS_SIZE/2 - OBJ_SIZE + 10);
        }
        G.dispose();
    }

    public WormGame()
    {
    	try        
    	{
    	    Thread.sleep(500);
    	} 
    	catch(InterruptedException ex) 
    	{
    	    Thread.currentThread().interrupt();
    	}
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
        JFrame f = new JFrame("Worm Game");
        final WormGame w = new WormGame();
        final int BORDER_WIDTH = 16; //This number allow the game itself to be 512 x 512
        final int BORDER_HEIGHT = 39; //This number allow the game itself to be 512 x 512
        f.setSize(BOUNDS_SIZE + BORDER_WIDTH, BOUNDS_SIZE + BORDER_HEIGHT); 
        f.add(w);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void play()
    {
    	if(pauseGame == true)
    	{
    		
    	}
    	else
    	{
    		checkFood();
    		time--;
    	}
    	repaint();
    }
    
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == 37)
        {
			if(pauseGame == false)
			{
				moveWormLeft();
			}
			else
			{
				// Wait for game to unpause
			}
        }
        else if(e.getKeyCode() == 38)
        {
        	if(pauseGame == false)
			{
            	moveWormUp();
			}
			else
			{
				// Wait for game to unpause
			}
        }
        else if(e.getKeyCode() == 39)
        {
        	if(pauseGame == false)
			{
            	moveWormRight();
			}
			else
			{
				// Wait for game to unpause
			}
        }
        else if(e.getKeyCode() == 40)
        {
        	if(pauseGame == false)
			{
            	moveWormDown();
			}
			else
			{
				// Wait for game to unpause
			}
        }
        else if(e.getKeyCode() == 80) // p key
        {
        	pauseGame = !pauseGame;
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
