import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * @author J. Capuzzo and R. King
 *
 */
@SuppressWarnings("serial")
public class PaddleGame extends JPanel implements KeyListener
{
    static final int OBJ_SIZE = 32; // The size of the worm and worm ford
    static final int PADDLE_HEIGHT = OBJ_SIZE * 2;
    static final int BOUNDS_SIZE = 512; //The size of the game boundaries
    static final int WIDTH = BOUNDS_SIZE + 16;
    static final int HEIGHT = BOUNDS_SIZE + 39;
    
    static int xPaddle = 0; // The x-position of the worm
    static int yPaddle = 0; // The y-position of the worm
    static int xEnemyPaddle = BOUNDS_SIZE; // The x-position of the worm
    static int yEnemyPaddle = 0; // The y-position of the worm
    
    private int xBall = BOUNDS_SIZE/2; // The x-position of the worm food
    private int yBall = BOUNDS_SIZE/2; // The y-position of the worm food
    private int xBallDirection = 4;         // velocity of ball
    private int yBallDirection = 8;
    private final int CHANGE = 16;
    
    int score = 0; // The player's score
    int enemyScore = 0;
    
    boolean resetBall = false;
    
    public void movePaddleUp()
    {
    	boolean inBounds = checkLowerBounds(yPaddle);
    	if(inBounds == true)
    	{
    		yPaddle -= CHANGE; 		
    	}
     }
    
    public void movePaddleDown()
    {
    	boolean inBounds = checkUpperBounds(yPaddle);
    	
    	if(inBounds == true)
    	{
    		yPaddle += CHANGE;
    	}
    }
    
    public void moveEnemyPaddleUp()
    {
    	boolean inBounds = checkLowerBounds(yEnemyPaddle);
    	if(inBounds == true)
    	{
    		yEnemyPaddle -= CHANGE/2;	//nerfing the ai paddle
    	}
    }
    
    public void moveEnemyPaddleDown()
    {
    	boolean inBounds = checkUpperBounds(yEnemyPaddle);
    	if(inBounds == true)
    	{
    		yEnemyPaddle += CHANGE/2;
    	}
    }
    
    public boolean checkLowerBounds(int position){
    	//int p = position;
    	if(position <= BOUNDS_SIZE - BOUNDS_SIZE)
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
    	if(position >= BOUNDS_SIZE - PADDLE_HEIGHT)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    public boolean checkBallUpperBounds(int position)
    {
    	//int p = position;
    	if(position >= BOUNDS_SIZE - OBJ_SIZE)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    public void moveEnemyPaddle()
    {
        if(yEnemyPaddle < yBall)
        {
        	moveEnemyPaddleDown();
        }
        
        else if(yEnemyPaddle > yBall)
        {
        	moveEnemyPaddleUp();
        }
    }
    
    public void moveBall()
    {	
    	//hit top or bottom
    	if(yBall < 0 || yBall > HEIGHT - OBJ_SIZE) 
    	{
    		yBallDirection = -yBallDirection;
    	}
    			
    	//hit left
    	if(xBall < 0) 
    	{
    		enemyScore++;
    		xBallDirection = -xBallDirection;
    	}
    		
    	//hit right
    	if(xBall + OBJ_SIZE > WIDTH) 
    	{	
    		score++;
    		xBallDirection = -xBallDirection;
    	}
    	
    	//hit paddles
    	if(xBall - OBJ_SIZE <= 0 && xBallDirection < 0) //check left paddle
    	{
    		if(yBall - OBJ_SIZE <= yPaddle && yBall >= yPaddle - OBJ_SIZE)
    		{
    			xBallDirection = -xBallDirection;
    		}
    	}
    		
    	if(xBall + OBJ_SIZE >= BOUNDS_SIZE && xBallDirection > 0) //check right paddle
    	{
    		if(yBall + OBJ_SIZE >= yEnemyPaddle && yBall <= yEnemyPaddle + OBJ_SIZE)
    		{
    			xBallDirection = -xBallDirection;
    		}
    	}
    	
    	xBall += xBallDirection;
    	yBall += yBallDirection;
    }
	
    //method currently not being used, but may have a purpose
    public void setYBall()
    {
    	int randNum = (int) (Math.random() * 101) / 2;
    	
	//coin-flip the direction
    	if(randNum < 50)
        {
             yBallDirection = -OBJ_SIZE;
        }
        else 
        {
             yBallDirection = OBJ_SIZE;
        }
    }
    
    public void resetBall()
    {
    	xBall = BOUNDS_SIZE/2;
	yBall = BOUNDS_SIZE/2;
	resetBall = false;
    }
    
    public void paint(Graphics g)
    {
    	super.paint(g);
        Graphics2D G = (Graphics2D) g;
        
        G.setPaint(Color.WHITE);
        G.fillRect(0, 0, 512, 512);
        
        if(resetBall)
        {
        	yPaddle = HEIGHT / 2;
		yEnemyPaddle = yPaddle;
	}
		
	//user paddle
	Rectangle2D ourPaddle = new Rectangle(xPaddle, yPaddle, OBJ_SIZE, PADDLE_HEIGHT);
	G.setPaint(Color.GREEN);
	G.fill(ourPaddle);
		
	//enemy paddle
	Rectangle2D enemyPaddle = new Rectangle(xEnemyPaddle, yEnemyPaddle, OBJ_SIZE, PADDLE_HEIGHT);
	G.setPaint(Color.RED);
	G.fill(enemyPaddle);
		
	//ball
	Ellipse2D ball = new Ellipse2D.Double(xBall, yBall, OBJ_SIZE, OBJ_SIZE);
	G.setPaint(Color.MAGENTA);
	G.fill(ball);
		
	//directions and scores
	G.setPaint(Color.BLACK);
        G.drawString("Use the arrow keys to move up and down!", 156, 156);
        G.drawString("Score: " + String.valueOf(score), 214, 166);
        G.drawString("Enemy Score: " + String.valueOf(enemyScore), 204, 176);
        G.dispose();
    }
    
    public PaddleGame()
    { 
    	addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    
    //may or may not need exception here, but i do not have ide open. 
    public static void main(String[] args) throws InterruptedException
    {
       	JFrame f = new JFrame("Paddle Game");
	
	/********
	* To do: need to figure out spacing, and how to get GUI integrated
	*/
	
       	final int BORDER_WIDTH = 16; //This number allow the game itself to be 512 x 512
       	final int BORDER_HEIGHT = 39; //This number allow the game itself to be 512 x 512
       	f.setSize(BOUNDS_SIZE + BORDER_WIDTH, BOUNDS_SIZE + BORDER_HEIGHT); 
        
       	PaddleGame w = new PaddleGame();
       	f.setContentPane(w);
	f.setResizable(false);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setVisible(true);
	w.play();
     }
	
     public void play() throws InterruptedException
     {
	while(true)
	{
	     moveBall(); 
	    	
	     if(Math.random() > .33) //some rng to make the game a bit more competitive
	     {
	    	moveEnemyPaddle();
	     }
	    	
	     if(resetBall == true)
	     {
	        resetBall();
	     }
	        
	     repaint();
	     Thread.sleep(20); // Affects movement speed
	}
     }

     @Override
     public void keyPressed(KeyEvent e) 
     {	
	if(e.getKeyCode() == 38)
        {
            movePaddleUp();
        }
        else if(e.getKeyCode() == 40)
        {
             movePaddleDown();
        }
     }
      
     //following methods do nothing
     @Override
     public void keyReleased(KeyEvent e) {}
	
     @Override
     public void keyTyped(KeyEvent e) {}
 }
