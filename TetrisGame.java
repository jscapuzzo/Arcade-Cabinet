import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class TetrisGame extends JPanel implements KeyListener, ActionListener 
{
	static final int OBJ_SIZE = 32; 	// The size of the worm and worm ford
    static final int BOUNDS_SIZE = 512; 	//The size of the game boundaries
    static final int WIDTH = BOUNDS_SIZE + 16;	
    static final int HEIGHT = BOUNDS_SIZE + 39;
    static final int stackHeight = BOUNDS_SIZE/OBJ_SIZE;
    static final int stackWidth = BOUNDS_SIZE/OBJ_SIZE;
    
   
    private Timer timer;	//starts the actionlistener
    private TetrisBlock block;
    private TetrisBlock[] board;
    
    boolean falling = false;
    
    int lines = 0;
    
    int xPos = 0;
    int yPos = 0;
    
    TetrisBlock curBlock;
    
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
    	 * 			Done
    	 * Lock piece in.
    	 * 		Done
    	 * Check if line is full or if we topped out
    	 * 		Full line checked. Topped out can be implemented last
    	 * Re-loop
    	 */
    	
    	//create piece
    	block = TetrisBlock.createRandomBlock();
    	while(true)
    	{
    		if (!falling) 
    		{
    	        falling = false;
    	        //removeFullLines();
    	        block = TetrisBlock.createRandomBlock();
    	    } 
    		else 
    		{
    			moveDown();
    		}
    	
    	}
    }
    
    private void moveLeft()
    {
    	int xPos = this.xPos - OBJ_SIZE;
    	
    	int[][] arr = block.getCoordinateArr();
    	
    	int i = 0;	
    	while(i < 4)
    	{
    		int x = xPos + arr[i][0]; 
    		
            if(!checkBounds(x, yPos))
            {
            	//out of bounds. kill method
            	return;
            }
            
            i++;
    	}
    	
    	this.xPos = xPos;
    	
    	//repaint();
    }
    
    private void moveRight()
    {
    	int xPos = this.xPos + OBJ_SIZE;
    	
    	int[][] arr = block.getCoordinateArr();
    	
    	int i = 0;	
    	while(i < 4)
    	{
    		int x = xPos + arr[i][0]; 
    		
            if(!checkBounds(x, yPos))
            {
            	//out of bounds. kill method
            	return;
            }
            
            i++;
    	}
    	
    	this.xPos = xPos;
    	
    	//repaint();
    }    
    
    private void moveDown()
    {
    	int yPos = this.yPos - OBJ_SIZE;
    	
    	int[][] arr = block.getCoordinateArr();
    	
    	int i = 0;	
    	while(i < 4)
    	{
    		int y = yPos + arr[i][1]; 
    		
            if(!checkBounds(xPos, y))
            {
            	//out of bounds, lock piece in
            	lockPieceIn(block);
            }
            
            i++;
    	}
    	
    	this.yPos = yPos;
        
    	//repaint();
        
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
    	
    	int[][] arr = t.getCoordinateArr();
    	
        for(int i = 0; i < arr.length * 2; i++) 
        {
            int x = xPos + arr[i][0];
            int y = yPos - arr[i][1];
            
            board[(y * OBJ_SIZE/2) + x] = t;
        }

        falling = false;
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
			moveLeft();
		}
        else if(e.getKeyCode() == 38) //up arrow key
        {
        	block.rotateLeft(block);
        }
        else if(e.getKeyCode() == 39)
        {
        	moveRight();
        }
        else if(e.getKeyCode() == 40)
        {
        	moveDown();
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
	
	private boolean checkBounds(int xPos, int yPos)
	{
		//check for collision
		if (!getBlockType(xPos, yPos).equals("nullBlock"))
		{
			return false;
		}
		
		//check boundaries
		if (xPos < 0 || yPos < 0 || xPos >= BOUNDS_SIZE || yPos >= BOUNDS_SIZE)
        {
            return false;
        }
		
		return true;
	}
	
	private String getBlockType(int x, int y) 
	{ 
		return board[(y * OBJ_SIZE/2) + x].getBlockType();
	}

}
