import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * Clone of the classic arcade game Snake with a few twists.
 * @author J. Capuzzo (lead) and R. King (partner)
 */
@SuppressWarnings("serial")
public class WormGame extends JPanel implements KeyListener, ActionListener
{
	public static final int OBJ_SIZE = 32; // The size of the worm and worm ford
    public static final int BOUNDS_SIZE = 512; //The size of the game boundaries
    public static final int winScore = 100;
    
    private static boolean pauseGame = true;
    
    private int xWorm = 0; 	// The x-position of the worm
    private int yWorm = 0; 	// The y-position of the worm
    private int xFood = 64; 	// The x-position of the worm food
    private int yFood = 64;	// The y-position of the worm food
    private int score = 0; 	// The player's score
    private static int time = 6000;	// time/60 is the number of seconds player has
    
    private Timer timer;
    
    /**
     * Constructor for the WormGame
     * Purely serves to do interaction stuff. Most of the hard work is done in its methods.
     */
    public WormGame()
    {	
    	newGame();
    	
    	//sets the gui to read keystrokes and make it the focus
    	addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        //the following was grabbed off Oracle's swing tutorial online
        //essentially it just automatically starts the game with a delay and sets the speed of the game
        timer = new Timer(15, this);
        timer.start();
    }
    
    private void newGame()
    {
    	xWorm = 0; 
    	yWorm = 0; 
    	xFood = 64;
    	yFood = 64;
    	score = 0;
    }
    
    private void moveWormRight()
    {
    	boolean inBounds = checkUpperBounds(xWorm);
    	if(inBounds == true){
    		xWorm += OBJ_SIZE;
    	}
    }
    
    private void moveWormLeft()
    {
    	boolean inBounds = checkLowerBounds(xWorm);
    	if(inBounds == true)
    	{
    		xWorm -= OBJ_SIZE;
    	}
    }
    
    private void moveWormUp()
    {
    	boolean inBounds = checkLowerBounds(yWorm);
    	if(inBounds == true)
    	{
    		yWorm -= OBJ_SIZE;
    	}
    }
    
    private void moveWormDown()
    {
    	boolean inBounds = checkUpperBounds(yWorm);
    	if(inBounds == true)
    	{
    		yWorm += OBJ_SIZE;
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
    
    private void checkFood(){
    	if(xWorm == xFood && yWorm == yFood)
    	{
    		score++;
    		moveFood();
    	}
    }
    
    private void moveFood(){
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
    
    /**
     * Paint the current state of the game. 
     * Using our timer, it repaints at a constant interval.
     */
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
	        	G.setFont(new Font("Arial", Font.BOLD, 20));
	        	G.drawString("Press the p key to play", 140, 222);
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
    		//do nothing
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
		if(e.getKeyCode() == 80) // p key
	    {
	       	pauseGame = !pauseGame;
	    }
		else if(pauseGame == true)
		{
			//do nothing until game is unpaused
		}
		else if(e.getKeyCode() == 37)
        {
			moveWormLeft();
        }
        else if(e.getKeyCode() == 38)
        {
        	moveWormUp();
        }
        else if(e.getKeyCode() == 39)
        {
           	moveWormRight();
        }
        else if(e.getKeyCode() == 40)
        {
            moveWormDown();
		}
    }
	
    /* Following methods do nothing but make the KeyListener interface happy */
    @Override
    public void keyReleased(KeyEvent e) {}
	
    @Override
    public void keyTyped(KeyEvent e) {}
    /* end of useless methods */
    
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
