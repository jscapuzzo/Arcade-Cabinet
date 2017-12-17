import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A clone of the Tetris game created by Alexey Pajitnov in 1984.
 * @author Ryan King (lead) and Jerry Capuzzo (partner)
 *
 */
@SuppressWarnings("serial") //suppression has to be here for some reason?
public class TetrisGame extends JPanel implements KeyListener, ActionListener 
{
	public static final int OBJ_SIZE = 32; 		//The size of the block
    public static final int BOUNDS_SIZE = 512; 	//The size of the game boundaries
    public static final int HEIGHT = BOUNDS_SIZE/OBJ_SIZE; 	//matrix height
    public static final int WIDTH = BOUNDS_SIZE/OBJ_SIZE;	//matrix width
    
    private Timer timer;			//starts the actionlistener
    private TetrisBlock block;		//new block in the matrix
    private TetrisBlock[][] matrix;	//matrix of blocks
    
    private int linesCleared;	//number of cleared lines
    private int score; 			//The player's score
    
    private boolean gameOver;	//game over state
    private boolean pauseGame;	//game pause state
    
    private int xBlock;		//x-position of our block (in the matrix)
    private int yBlock;		//y-position of our block 
    
    /**
     * Create a TetrisGame object.
     * Purely serves to do interaction stuff. Most of the hard work is done in its methods.
     */
    public TetrisGame()
    {
    	//initialize game state
    	matrix = new TetrisBlock[WIDTH][HEIGHT];
        newBlock();
        
        linesCleared = 0;
        score = 0;
        gameOver = false;
        pauseGame = true;
        
    	//set the GUI to read keystrokes and make it the focus
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
        
        xBlock = (int) (WIDTH / 2);		//middle of matrix
        yBlock = HEIGHT - 1 + block.getMinYCoord();		//lowest y-value must be factored for game over or tall column situations
    }
    
    
    /**
     * Paint the current state of the game. 
     * Using our timer, it repaints at a constant interval.
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        
        Graphics2D G = (Graphics2D) g;
        
        if(gameOver == true)
        {
        	G.setColor(Color.BLACK); //The on-screen text color
	        G.drawString("GAME OVER", (BOUNDS_SIZE/2) - OBJ_SIZE, (BOUNDS_SIZE/2) - OBJ_SIZE);
	        
	        return;
        }
        
        G.setPaint(Color.WHITE);	 //set background to be white
        G.fillRect(0, 0, 512, 512);
        
        int[][] bCoords = block.getCoordinateArr(); //current block x-y coordinates
        
        //loops to paint blocks
        for(int i = 0; i < WIDTH; i++) 
        {
        	G.setColor(block.getBlockColor());
        	
        	//paint existing block first and don't go out of bounds
            if(i < bCoords.length && block != null)
            {
            	int x = xBlock - bCoords[i][0];
                int y = yBlock - bCoords[i][1];
                
                G.fillRect(x * (BOUNDS_SIZE/WIDTH), (HEIGHT - 1 - y) * (BOUNDS_SIZE/HEIGHT), OBJ_SIZE, OBJ_SIZE);  	
            }
            
            //paint the "locked in" blocks next
            for(int j = 0; j < HEIGHT; j++) 
            {
                TetrisBlock t = getBlock(i, j);
                
                if(t == null)	//null indices will just be white
                {
                	continue;
                }
                
                //draw the block
                G.setColor(t.getBlockColor());
                G.fillRect(i * (BOUNDS_SIZE/WIDTH), (HEIGHT - 1 - j) * (BOUNDS_SIZE/HEIGHT), OBJ_SIZE, OBJ_SIZE);
            }
        }
        
        //display controls
        //update scores and lines cleared
        G.setColor(Color.BLACK);
        G.drawString("Score: " + String.valueOf(score), 405, 10);
        G.drawString("Lines Cleared: " + String.valueOf(linesCleared), 405, 25);
        G.drawString("Use the arrow keys to move", 5, 10);
        G.drawString("Use the UP arrow key to rotate", 5, 25);
        
        if(pauseGame == true) 
        {
        	G.setColor(Color.RED);
        	G.setFont(new Font("Arial", Font.BOLD, 20));
        	G.drawString("Press the p key to play", 140, 206);
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
    	
    	//must test all x and y values due to rotations and variance in block shape.
    	for(int i = 0; i < bCoords.length; i++)
    	{
    		//test new block x-position. y values don't change
    		int x = xPos - bCoords[i][0]; 
    		int y = yBlock - bCoords[i][1];
    		
    		//check for bounds and collisions
            if(checkBounds(x, WIDTH) == false || checkBounds(y, HEIGHT) == false || checkCollision(x, y) == true)
            {
            	//out of bounds.  kill method
            	return;
            }
    	}
   
    	//able to be moved so update
    	xBlock = xPos;
    }
    
    /**
     * If possible, move block to the right
     */
    private void moveRight()
    {
    	int xPos = xBlock + 1;
    	
    	int[][] bCoords = block.getCoordinateArr();
    	
    	//must test all x and y values due to rotations and variance in block shape.
    	for(int i = 0; i < bCoords.length; i++)
    	{
    		//test new block x-position. y values don't change
    		int x = xPos - bCoords[i][0]; 
    		int y = yBlock - bCoords[i][1];
    		
    		if(checkBounds(x, WIDTH) == false || checkBounds(y, HEIGHT) == false || checkCollision(x, y) == true)
            {
            	//out of bounds. kill method
            	return;
            }
    	}
    	
    	//able to be moved, so update
    	xBlock = xPos;
    }
    
    /**
     * If possible, move block down a line
     */
    private void moveDown()
    {
    	int yPos = yBlock - 1;
    	
    	int[][] bCoords = block.getCoordinateArr();
    	
    	//must test all x and y values due to rotations and variance in block shape.
    	for(int i = 0; i < bCoords.length; i++)
    	{
    		//test new block y-position. x values don't change
    		int x = xBlock - bCoords[i][0];
    		int y = yPos - bCoords[i][1];
    		
    		if(checkBounds(x, WIDTH) == false || checkBounds(y, HEIGHT) == false || checkCollision(x, y) == true)
            {
            	//out of bounds, lock piece in and end method
            	lockPieceIn();
            	clearLines();
            	newBlock();
            	return;
            }
    	}
    	
    	yBlock = yPos;
    }
    
    /**
     * Rotate the current block if possible. 
     */
    private void rotate()
    {
    	//create deep copy of current block
    	TetrisBlock t = new TetrisBlock(block);
    	
    	//rotate the tester block
    	t.rotateLeft();
    	int[][] bCoords = t.getCoordinateArr();
    	
    	//check if tester block is in bounds
    	for(int i = 0; i < bCoords.length; i++)
    	{
    		int x = xBlock - bCoords[i][0];
    		int y = yBlock - bCoords[i][1];
    		
    		if(checkBounds(x, WIDTH) == false || checkBounds(y, HEIGHT) == false || checkCollision(x, y) == true)
    		{
    			return;
    		}
    	}
    	
    	//it passed so rotate it
    	block = t;
    }
    
    /**
     * After collision, piece is added permanently added to the matrix
     */
    private void lockPieceIn()
    {
    	int[][] bCoords = block.getCoordinateArr();
    	
    	//lock the pieces of the block in at their respective coordinates
    	for(int i = 0; i < bCoords.length; i++)
    	{
    		int x = xBlock - bCoords[i][0];
            int y = yBlock - bCoords[i][1];
          
            //check for game over condition
            int check = yBlock - block.getMinYCoord();
        	if(checkBounds(check, HEIGHT - 1) == false)
            {
        		gameOver = true;
            }
            
            matrix[x][y] = block;
        }
        
    	score++;
    	repaint();
    }
    
    /**
     * Check for full lines and clear them.
     * Update score after lines are cleared
     */
    private void clearLines()
    {
    	int count = 0;
    	
    	/*
    	 * Must check entire array due to split lines (i.e. line 3 and 1 are full, but 2 isn't)
    	 */
    	int i = HEIGHT - 1;
    	while(i >= 0)
    	{
    		int j = 0;
    		while(checkBounds(j, WIDTH))
    		{
    			TetrisBlock t = getBlock(j, i);
     
    			if(t == null) 
              	{
                  	break;
              	}
    			
    			j++;
    		}
    		   	 
        	if(j == WIDTH)
        	{
        		/*
        		 * Method call does 3 things:
        		 * 1. remove row i from matrix
        		 * 2. shift rows above down
        		 * 3. add empty space up top
        		 */
        		
        		matrix = removeLine(i);
        		linesCleared++;
        		count++;
        	}
        	
        	i--;
    	}
        	
        //update score and repaint with new lines
        if(count > 0)
        {
   	 		score += Math.pow(3, count);
   	 		repaint();
        }
   	 	
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
   	 	if(pauseGame == true)
   	 	{
   	 		//do nothing until game is unpaused 
   	 	}
   	 	else if(gameOver == true)
    	{
    		timer.stop();
    		return;
    	}
    	else
    	{
    		//not game over, automatically move piece
    		moveDown();
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
		else if(e.getKeyCode() == 37)	//left arrow key
        {
			moveLeft();
		}
        else if(e.getKeyCode() == 38) //up arrow key
        {
        	rotate();
        }
        else if(e.getKeyCode() == 39) //right arrow key
        {
        	moveRight();
        }
        else if(e.getKeyCode() == 40) //down arrow key
        {
        	moveDown();
        }
		
		repaint();
	}
	
	/* Following methods do nothing and are purely to keep interface happy */
	@Override
	public void keyReleased(KeyEvent arg0) { }

	@Override
	public void keyTyped(KeyEvent arg0) { }
	/* End of useless methods */
	
	/**
	 * Check if the integer is within bounds
	 * Combination of our checkLowerBounds and checkUpperBounds methods from previous games
	 * @param position
	 * @param bound
	 * @return
	 */
    public boolean checkBounds(int position, int bound)
    {
    	if(position < 0)	//out of bounds
    	{
    		return false;
    	}
    	else if(position >= bound)	//out of bounds
    	{
    		return false;
    	}
    	
    	return true;
    }
	
	/**
	 * Check if block is colliding with another block
	 * @param x	row location
	 * @param y	column location
	 * @return
	 */
	private boolean checkCollision(int x, int y)
	{
		if(getBlock(x, y) != null)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Get block at matrix location
	 * @param x row location
	 * @param y	column location
	 * @return
	 */
	private TetrisBlock getBlock(int x, int y) 
	{ 
		if(matrix[x][y] != null)
		{
			return matrix[x][y];
		}
		
		return null;
	}
	
	/**
	 * Removes a line from the matrix and replaces it with a null space.
	 * @param flag
	 * @return
	 */
	private TetrisBlock[][] removeLine(int flag)
    {
        TetrisBlock[][] copy= new TetrisBlock[WIDTH][HEIGHT];
        
        int count = 0;	//denotes which rows will not be null

        //traverse entire array.
        for(int i = 0; i < HEIGHT; i++)
        { 
           for(int j =  0; j < WIDTH; j++)
           {   
        	   //row to be removed
        	   if(i == flag)
               {
                   	i++;
               }
               
               if(i + 1 == HEIGHT)
               {
                    copy[j][i] = null;
               }
                             
               copy[j][count] = matrix[j][i];
           }
                
           count++;
        }
        
        return copy;
    }

	/**
	 * Stop the game
	 */
	public void stop()
	{
		timer.stop();
	}
	
	/**
	 * Main method for testing
	 * @param args
	 * @throws InterruptedException
	 */
    public static void main(String[] args)
    {
       	JFrame f = new JFrame("Tetris");
	
       	f.setSize(BOUNDS_SIZE, BOUNDS_SIZE + 39); 
        
       	TetrisGame w = new TetrisGame();
       	f.setContentPane(w);
       	f.setResizable(false);
       	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       	f.setVisible(true);
    }
}
