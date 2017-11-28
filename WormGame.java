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
    static int xWorm = 0; // The x-position of the worm
    static int yWorm = 0; // The y-position of the worm
    int xFood = 64; // The x-position of the worm food
    int yFood = 64; // The y-position of the worm food
    int score = 0; // The player's score
    boolean canMove = true; // Ensure's an object does not move more than once per cycle
    
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
        
        G.setColor(Color.BLACK); // Worm color
        G.fillRect(xWorm, yWorm, OBJ_SIZE, OBJ_SIZE); // Creates worm on screen
        
        G.setColor(Color.GREEN); // Worm food color
        G.fillRect(xFood, yFood, OBJ_SIZE, OBJ_SIZE); // Creates worm food on screen
        
        G.setColor(Color.BLUE); // The on-screen text color
        G.drawString("Use the arrow keys to move!", 156, 156);
        G.drawString("Score: " + String.valueOf(score), 214, 166);
        G.dispose();
    }

    public WormGame()
    {
    	//sets the gui to read keystrokes and make it the focus
    	addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        //the following was grabbed off Oracle's swing tutorial online
        //essentially it just automatically starts the game with a delay and sets the speed of the game
        Timer timer = new Timer(15, this);
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
    	checkFood();
        repaint();
    }
    
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == 37)
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
}