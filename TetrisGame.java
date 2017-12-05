import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class TetrisGame extends JPanel implements KeyListener, ActionListener 
{
	static final int OBJ_SIZE = 16; 	// The size of the worm and worm ford
    static final int BOUNDS_SIZE = 512; 	//The size of the game boundaries
    static final int WIDTH = BOUNDS_SIZE + 16;	
    static final int HEIGHT = BOUNDS_SIZE + 39;
    
   
    private Timer timer;	//starts the actionlistener
    private TetrisBlock block;
    private int[][] board;
    
    int score = 0; 		//The player's score

    /**
     * Create a TetrisGame object.
     * Purely serves to do interaction stuff. Most of the hardwork is done in its methods.
     */
    public TetrisGame()
    {
    	//sets the gui to read keystrokes and make it the focus
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
     * Paint the current state of the game. Using our timer, it repaints at a constant interval.
     */
    public void paint(Graphics g)
    {
    	super.paint(g);
        Graphics2D G = (Graphics2D) g;
        
        G.setPaint(Color.WHITE); //set background to be white
        G.fillRect(0, 0, 512, 512);
        //user paddle
        G.setPaint(Color.BLACK);
        
        //G.fillRect(xPaddle, yPaddle, OBJ_SIZE, PADDLE_HEIGHT);
		
        //enemy paddle
        //G.fillRect(xEnemyPaddle, yEnemyPaddle, OBJ_SIZE, PADDLE_HEIGHT);
		
        //directions and scores
        G.setPaint(Color.BLACK);
        G.drawString("Use the arrow keys to move up and down!", 156, 156);
        G.drawString("Score: " + String.valueOf(score), 214, 166);
        G.dispose();
    }
    
    /**
     * Play the game. 
     */
    public void play()
    {
    	/**
    	 * Create piece
    	 * 		done
    	 * Move piece
    	 * 		Conditions for piece: 1. Can it be rotated (due to boundaries)? 2. Can it move (due to boundaries)
    	 * 			Framework made
    	 * Lock piece in.
    	 * 		Framework made
    	 * Check if line is full or if we topped out
    	 * 		Full line checked. Topped out can be implemented last
    	 * Re-loop
    	 */
    	
    	//create piece
    	block = TetrisBlock.createRandomBlock();
    	
    }
    
    private void moveLeft()
    {
    	boolean inBounds = true; //checkLowerBounds(xWorm);
    	
    	if(inBounds == true)
    	{
    		//move block to the left
    	}
    }
    
    private void moveRight()
    {
    	boolean inBounds = true; //checkLowerBounds(xWorm);
    	
    	if(inBounds == true)
    	{
    		//move block to the right
    	}
    }    
    
    private void moveDown()
    {
    	boolean inBounds = true; //checkLowerBounds(xWorm);
    	
    	if(inBounds == true)
    	{
    		//move block down a line
    	}
    }
    
    private void lockPieceIn(TetrisBlock t)
    {
    	/**
    	 * Initial board set up: Empty 2D array of ints (x-y coordinates)
    	 * Following a collision (i.e. this is called from another method)
    	 * 1. Get current board state
    	 * 2. Add 1's where block is placed given piece shape
    	 * 3. Redraw board (maybe respecting piece color idk)
    	 */
    }
    
    private void clearLine()
    {
    	/**
    	 * 1. Get current board state
    	 * 2. If row is all 1's then clear it.
    	 * 3. Shift entire array down a row.
    	 * 4. Repeat for all rows until a full row is not found
    	 * 4. Update score with benefits for multiple line clears 
    	 * 5. Redraw board
    	 */
    }
    
    private void addScore(int rows)
    {
    	score += Math.pow(2, rows);
    	//repaint
    }
    
	@Override
	/**
	 * Automatically begins with Timer instance.
	 * Only calls play() method for easier debugging.  
	 */
	public void actionPerformed(ActionEvent arg0) 
	{
		play();
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == 37)
        {
			//if checkLeftMove(position) == true
        		//moveLeft();
		}
        else if(e.getKeyCode() == 38)
        {
        	//if canRotate == true
        		//rotateRight
        	
        }
        else if(e.getKeyCode() == 39)
        {
        	//if checkRightMove(position) == true
        		//moveRight();
        }
        else if(e.getKeyCode() == 40)
        {
        	//moveDown
        }
		
		/*
		 * In real tetris, the space bar automatically pushes the piece to the bottom. 
		 * Also, maybe we should have a pause and resume feature
		 */
	}
	
	//Following methods do nothing and are purely to keep interface happy
	@Override
	public void keyReleased(KeyEvent arg0) { }

	@Override
	public void keyTyped(KeyEvent arg0) { }

}
