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
    static final int HEIGHT = BOUNDS_SIZE/OBJ_SIZE;
    static final int WIDTH = BOUNDS_SIZE/OBJ_SIZE;
    
    private Timer timer;			//starts the actionlistener
    private TetrisBlock block;		//current block that is falling
    private TetrisBlock[][] matrix;	//matrix of blocks
    
    private boolean falling = true;	//piece is falling or not
    private int lines = 0;		//number of cleared lines
    private int score = 0; 		//The player's score
    boolean gameOver;
    
    private int xBlock = 0;
    private int yBlock = 0;
    
    

    /**
     * Create a TetrisGame object.
     * Purely serves to do interaction stuff. Most of the hardwork is done in its methods.
     */
    public TetrisGame()
    {
    	matrix = new TetrisBlock[HEIGHT][WIDTH];
        
        //initialize the board
        resetMatrix();
        newBlock();
        
    	//sets the gui to read keystrokes and make it the focus
    	addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        //the following was grabbed off Oracle's swing tutorial online
        //essentially it just automatically starts the game with a delay and sets the speed of the game
        timer = new Timer(600, this);    
        timer.start();
    }
    
    /**
     * Create new TetrisBlock.
     * Place it at top and middle of matrix
     */
    private void newBlock()
    {
    	block = TetrisBlock.createRandomBlock();
        
        xBlock = (int) (WIDTH / 2);
        yBlock = HEIGHT - 1 + block.getMinYCoord();
        falling = true;
    }
    
    /**
     * Initalize matrix with null blocks
     */
    private void resetMatrix()
    {
    	for (int i = 0; i < HEIGHT; i++)
        {
        	for(int j = 0; j < WIDTH; j++)
        	{
           		matrix[j][i] = TetrisBlock.createNullBlock();
        	}
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
        
        G.setPaint(Color.WHITE); //set background to be white
        G.fillRect(0, 0, 512, 512);
        
        G.setPaint(Color.GRAY); //pieces paint to gray
        
        int[][] bCoords = block.getCoordinateArr(); //current block coordinates
        
        //Paint existing blocks first        
        for (int i = 0; i < HEIGHT; i++) 
        {
            for (int j = 0; j < WIDTH; j++) 
            {
                TetrisBlock t = getBlock(j, i);
                
                if(t != null && t.getBlockType() != "nullBlock")
                {
                	//draw the block
                    g.fillRect(j * OBJ_SIZE, (HEIGHT - 1 - i) * OBJ_SIZE, OBJ_SIZE, OBJ_SIZE);
                }
            }
        }

        //then paint our current block
        if (block != null && block.getBlockType() != "nullBlock")
        {
        	for(int k = 0; k < bCoords.length; k++)
        	{
                int x = xBlock + bCoords[k][0];
                int y = yBlock - bCoords[k][1];
                
                g.fillRect(x * OBJ_SIZE, (HEIGHT - 1 - y) * OBJ_SIZE, OBJ_SIZE, OBJ_SIZE);
        	}
        }
        
        G.dispose();
    }
    
    /**
     * If possible, move block to the left
     */
    private void moveLeft()
    {	
    	int xPos = xBlock - 1;
    	
    	int[][] bCoords = block.getCoordinateArr();
    	

    	for(int i = 0; i < bCoords.length; i++)
    	{
    		int x = xPos + bCoords[i][0]; 
    		int y = yBlock - bCoords[i][1];
    		
            if(!checkBounds(x, y))
            {
            	//out of bounds.  kill method
            	return;
            }
    	}
   
    	xBlock = xPos;
    	repaint();
    }
    
    /**
     * If possible, move block to the right
     */
    private void moveRight()
    {
    	int xPos = xBlock + 1;
    	
    	int[][] bCoords = block.getCoordinateArr();
    	
    	for(int i = 0; i < bCoords.length; i++)
    	{
    		int x = xPos + bCoords[i][0]; 
    		int y = yBlock - bCoords[i][1];
    		
            if(!checkBounds(x, y))
            {
            	//out of bounds. kill method
            	return;
            }
    	}
    	
    	xBlock = xPos;
    	
    	repaint();
    }
    
    /**
     * If possible, move block down a line
     */
    private void moveDown()
    {
    	int yPos = yBlock - 1;
    	
    	int[][] bCoords = block.getCoordinateArr();
    	
    	for(int i = 0; i < bCoords.length; i++)
    	{
    		int x = xBlock + bCoords[i][0];
    		int y = yPos - bCoords[i][1];
    		
    		
    		
            if(!checkBounds(x, y))
            {
            	//out of bounds, lock piece in and end method
            	lockPieceIn();
            	clearLines();
            	return;
            }
    	}
    	
    	yBlock = yPos;
        
    	repaint();
    
    }
    
    /**
     * After collision, piece is added permanently added to the matrix
     */
    private void lockPieceIn()
    {
    	falling = false;
    	
    	int[][] bCoords = block.getCoordinateArr();
    	
    	for(int i = 0; i < bCoords.length; i++)
    	{
            int x = xBlock + bCoords[i][0];
            int y = yBlock - bCoords[i][1];
          
            //check for game condition
        	if((yBlock - block.getMinYCoord()) >= HEIGHT - 1)
            {
        		gameOver = true;
        		falling = false;
            }
        	
            matrix[y][x] = block;
        }
        
    	repaint();
    }
    
    /**
     * Check for full lines and clear them.
     * Update score after lines are cleared
     */
    private void clearLines()
    {
    	int count = 0;
         
        int i = HEIGHT - 1;
        
        //go down entire array
        while(i >= 0)
        {
            boolean full = true;

            //check entire array for an empty line
            for(int j = 0; j < WIDTH; j++) 
            {
            	TetrisBlock t = getBlock(j, i);
            	
                if(t == null || t.getBlockType() == "nullBlock") 
                {
                    full = false;
                    break;
                }
            }

            //no empty line found
            if(full == true) 
            {   
            	//for all lines that are below a full line
            	//replace full line with line above
            	for(int k = i; k < HEIGHT - 1; k++)
                {
            		for(int m = 0; m < WIDTH; m++)
                    {
            			matrix[k][m] = getBlock(m, k + 1);
                    }
            	}
       
                count++;
            }
            
            i--;
        }

        //update score and repaint with new lines
        if (count > 0) 
        {
            lines += count;
            addScore(lines);
            
            //reset block to avoid confusion. 
            //Note: had a crazy bug occur when I cleared two lines, so this is necessary although seemingly redundant.
            block = TetrisBlock.createNullBlock();
            
            repaint();
        }
    }
    
    /**
     * Calculate new score based on amount of lines cleared.
     * @param num Amount of lines cleared
     */
    private void addScore(int num)
    {
    	score += Math.pow(2, num);
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
    	
    	if(gameOver)
    	{
    		timer.stop();
    		return;
    	}
    	else if(!falling) //piece is not falling, make a new one 
    	{
    		//check game over
    		falling = true;
    		newBlock();
    	} 
    	else //piece is falling, automatically move it down
    	{
    		moveDown();
    	}
    	
		repaint();
	
    }
    
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == 37)	//left arrow key
        {
			moveLeft();
		}
        else if(e.getKeyCode() == 38) //up arrow key
        {
        	//blocks can't rotate near edges.
        	//this causes the game to crash.
        	block.rotateLeft();
        }
        else if(e.getKeyCode() == 39) //right arrow key
        {
        	moveRight();
        }
        else if(e.getKeyCode() == 40) //down arrow key
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
	
	public void stop()
    {
    	timer.stop();
    }
	
	private boolean checkBounds(int xBlock, int yBlock)
	{
		//check boundaries
		if (xBlock < 0 || yBlock < 0 || xBlock >= WIDTH || yBlock >= HEIGHT)
        {
            return false;
            
        }
		
		//check for collision
		if (getBlock(xBlock, yBlock) != null && !getBlock(xBlock, yBlock).getBlockType().equals("nullBlock"))
		{
			return false;
		}
		
		return true;
	}
	
	/*
	 * Get block at certain board position
	 */
	private TetrisBlock getBlock(int x, int y) 
	{ 
		return matrix[y][x];
	}

    public static void main(String[] args) throws InterruptedException
    {
       	JFrame f = new JFrame("Paddle Game");
	
       	final int BORDER_WIDTH = 16; //This number allow the game itself to be 512 x 512
       	final int BORDER_HEIGHT = 39; //This number allow the game itself to be 512 x 512
       	f.setSize(BOUNDS_SIZE, BOUNDS_SIZE + 39); 
        
       	TetrisGame w = new TetrisGame();
       	f.setContentPane(w);
       	f.setResizable(false);
       	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       	f.setVisible(true);
    }


}
