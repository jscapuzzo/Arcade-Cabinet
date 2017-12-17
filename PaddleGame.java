import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Clone of the classic arcade game Pong
 * @author J. Capuzzo (lead) and R. King (partner)
 *
 */
@SuppressWarnings("serial")
public class PaddleGame extends JPanel implements KeyListener, ActionListener
{
	public static final int BOUNDS_SIZE = 512; //The size of the game boundaries
    public static final int WIDTH = BOUNDS_SIZE + 16;
    public static final int HEIGHT = BOUNDS_SIZE + 39;
    public static final int OBJ_SIZE = 32; 	// The size of the worm and worm ford
    public static final int PADDLE_HEIGHT = OBJ_SIZE * 2;
        
    private int xPaddle; // The x-position of the paddle
    private int yPaddle; // The y-position of the paddle
    private int xEnemyPaddle; // The x-position of the enemy paddle
    private int yEnemyPaddle; // The y-position of the enemy paddle
    
    private int xBall; // The x-position of the ball
    private int yBall; // The y-position of the ball
    private int xBallDirection;    // velocity of ball
    private int yBallDirection;
    
    public static final int CHANGE = 16;
    private Timer timer;
    
    private int score; // The player's score
    private int enemyScore;
    
    public static final int winScore = 25;
    
    private boolean resetBall;
    
    private static boolean secondPlayer = false;
    private static boolean pauseGame = true;
    
    /**
     * Constructor for PaddleGame Object. 
     * Resets all instance variables and adds interactive components.
     * @throws InterruptedException
     */
    public PaddleGame() throws InterruptedException
    {
    	newGame();
    	reset();
    	
    	//sets the GUI to read keystrokes and make it the focus
    	addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        //the following was grabbed off Oracle's swing tutorial online
        //essentially it just automatically starts the game with a delay and sets the speed of the game
        timer = new Timer(15, this);
        timer.setInitialDelay(1500);
        timer.start();
    }
    
    /**
     * Reset the game state
     */
    private void newGame()
    {
        xPaddle = 0;
        yPaddle = 0;
        xEnemyPaddle = WIDTH - OBJ_SIZE;
        yEnemyPaddle = 0;
        
        xBall = BOUNDS_SIZE/2;
        yBall = BOUNDS_SIZE/2;
        xBallDirection = 3;
        yBallDirection = 6;
        
        score = 0;
        enemyScore = 0;
        
        resetBall = false;
    }
    
    private void movePaddleUp()
    {
    	boolean inBounds = checkLowerBounds(yPaddle);
    	if(inBounds == true)
    	{
    		yPaddle -= 2 * CHANGE; 		
    	}
     }
    
    private void movePaddleDown()
    {
    	boolean inBounds = checkUpperBounds(yPaddle);
    	
    	if(inBounds == true)
    	{
    		yPaddle += 2 * CHANGE;
    	}
    }
    
    private void moveEnemyPaddleUp()
    {
    	boolean inBounds = checkLowerBounds(yEnemyPaddle);
    	if(inBounds == true)
    	{
    		if(secondPlayer == true)
    		{
    			yEnemyPaddle -= 2 * CHANGE;
    		}
    		else
    		{
    			yEnemyPaddle -= CHANGE/2;	//nerfing the ai paddle
    		}
    		
    	}
    }
    
    private void moveEnemyPaddleDown()
    {
    	boolean inBounds = checkUpperBounds(yEnemyPaddle);
    	if(inBounds == true)
    	{
    		if(secondPlayer == true)
    		{
    			yEnemyPaddle += 2 * CHANGE;
    		}
    		else
    		{
    			yEnemyPaddle += CHANGE/2;	//nerfing the ai paddle
    		}
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
    	if(position >= BOUNDS_SIZE - PADDLE_HEIGHT)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    private static boolean checkBallUpperBounds(int position)
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
    
    private void moveEnemyPaddle()
    {
    	if(secondPlayer == true)
    	{
	        
    	}
    	else
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
    }
    
    public void moveBall()
    {	
    	//hit top or bottom
    	if(checkBallUpperBounds(yBall) == false || checkLowerBounds(yBall) == false) 
    	{
    		yBallDirection = -yBallDirection;
    	}
    			
    	//hit left
    	if(xBall < 0) 
    	{
    		enemyScore++;
    		resetBall = true;
    	}
    		
    	//hit right
    	if(xBall + OBJ_SIZE > WIDTH) 
    	{	
    		score++;
    		resetBall = true;
    	}
    	
    	//hit paddles
    	if(xBallDirection < 0 && xBall <= OBJ_SIZE) //check left paddle
    	{
    		if(yBall + OBJ_SIZE>= yPaddle && yBall <= yPaddle + PADDLE_HEIGHT) //ball is 
    		{
    			xBallDirection = -xBallDirection;
    		}
    	}
    		
    	if(xBallDirection > 0 && xBall + OBJ_SIZE >= WIDTH - OBJ_SIZE) //check right paddle
    	{
    		if(yBall + OBJ_SIZE >= yEnemyPaddle && yBall <= yEnemyPaddle + PADDLE_HEIGHT)
    		{
    			xBallDirection = -xBallDirection;
    		}
    		//xBallDirection = -xBallDirection;
    	}
    	
    	xBall += xBallDirection;
    	yBall += yBallDirection;
    }
	
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
    	yPaddle = BOUNDS_SIZE/2 - OBJ_SIZE; // The y-position of the paddle
 	    yEnemyPaddle = BOUNDS_SIZE/2 - OBJ_SIZE; // The y-position of the enemy paddle
    	
 	    try  	//delay ball moving
    	{
    	    Thread.sleep(500);
    	} 
    	catch(InterruptedException ex) 
    	{
    	    Thread.currentThread().interrupt();
    	}
    }
    
    public void paint(Graphics g)
    {
    	super.paint(g);
        Graphics2D G = (Graphics2D) g;
        
        G.setPaint(Color.WHITE); //set background to be white
        G.fillRect(0, 0, BOUNDS_SIZE + 16, BOUNDS_SIZE + 39);
        
        if(score >= winScore)
        {
        	G.setColor(Color.BLACK); // The on-screen text color
	        G.drawString("YOU WIN!", BOUNDS_SIZE/2 - OBJ_SIZE, BOUNDS_SIZE/2 - OBJ_SIZE);
        }
        else if(enemyScore >= winScore)
        {
        	G.setColor(Color.BLACK); // The on-screen text color
	        G.drawString("GAME OVER", BOUNDS_SIZE/2 - OBJ_SIZE, BOUNDS_SIZE/2 - OBJ_SIZE);
	        G.drawString("Enemy Won!", BOUNDS_SIZE/2 - OBJ_SIZE, BOUNDS_SIZE/2 - OBJ_SIZE + 20);
        }
        else
        {
        	if(resetBall == true) //beginning of game
	        {
	        	yPaddle = HEIGHT / 2;
	        	yEnemyPaddle = yPaddle;
	        }
			
	        //ball or whatever sphere we want
	        Ellipse2D ball = new Ellipse2D.Double(xBall, yBall, OBJ_SIZE, OBJ_SIZE);
	        G.setPaint(Color.MAGENTA);
	        G.fill(ball);
	        
	        //user paddle
	        G.setPaint(Color.BLACK);
	        G.fillRect(xPaddle, yPaddle, OBJ_SIZE, PADDLE_HEIGHT);
			
	        //enemy paddle
	        G.setPaint(Color.RED);
	        G.fillRect(xEnemyPaddle, yEnemyPaddle, OBJ_SIZE, PADDLE_HEIGHT);
			
	        //directions and scores
	        G.setPaint(Color.BLACK);
	        G.drawString("Use the arrow keys to move up and down", 156, 116);
	        G.drawString("Press r key toggle 2-player", 186, 136);
	        G.drawString("Score " + winScore + " to win!", 214, 156);
	        G.drawString("Score: " + String.valueOf(score), 230, 176);
	        G.drawString("Enemy Score: " + String.valueOf(enemyScore), 210, 186);
	        
	        if(pauseGame == true) {
	        	G.setColor(Color.RED);
	        	G.setFont(new Font("Arial", Font.BOLD, 20));
	        	G.drawString("Press the p key to play", 160, 206);
	        }
        }
        
        G.dispose();
    }
    
    /**
     * Main method used for testing.
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
       	JFrame f = new JFrame("Paddle Game");
	
       	final int BORDER_WIDTH = 16; //This number allow the game itself to be 512 x 512
       	final int BORDER_HEIGHT = 39; //This number allow the game itself to be 512 x 512
       	f.setSize(BOUNDS_SIZE + BORDER_WIDTH, BOUNDS_SIZE + BORDER_HEIGHT); 
        
       	PaddleGame w = new PaddleGame();
       	f.setContentPane(w);
       	f.setResizable(false);
       	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       	f.setVisible(true);
     }
	
    /**
     * Play the game
     */
    public void play()
    {
    	if(pauseGame == true)
    	{
    	 
    	}
    	else
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
    		 
    	}
    	repaint();
     }

     @Override
     public void keyPressed(KeyEvent e) 
     {	
    	 if(e.getKeyCode() == 38) //'up' arrow key
    	 {
            if(pauseGame == false)
			{
            	movePaddleUp();
			}
			else
			{
				// Wait for game to unpause
			}
    	 }
    	 else if(e.getKeyCode() == 40) //'down' arrow key
    	 {
    		if(pauseGame == false)
 			{
    			 movePaddleDown();
 			}
 			else
 			{
 				// Wait for game to unpause
 			}
    	 }
    	 
    	 if(e.getKeyCode() == 87) //w key
    	 {
    		if(pauseGame == false)
 			{
    			 moveEnemyPaddleUp();
 			}
 			else
 			{
 				// Wait for game to unpause
 			}
    	 }
    	 else if(e.getKeyCode() == 83) //s key
    	 {
    		if(pauseGame == false)
 			{
    			 moveEnemyPaddleDown();
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
    	 
    	 if(e.getKeyCode() == 82) //r key
    	 {
    		 reset();
    		 secondPlayer = !secondPlayer;
    	 }
     }
     
     /**
      * Reset ball and scores
      */
     private void reset()
     {
    	 score = 0;
    	 enemyScore = 0;
    	 resetBall();
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
