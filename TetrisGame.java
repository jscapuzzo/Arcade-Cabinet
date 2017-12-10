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
    
    int score = 0; 		//The player's score

    /**
     * Create a TetrisGame object.
     * Purely serves to do interaction stuff. Most of the hardwork is done in its methods.
     */
    public TetrisGame()
    {
    	//System.out.print("a");
    	System.out.println("Stack height: " + stackHeight);
        board = new TetrisBlock[stackHeight * stackWidth];
        clearBoard();
    	//sets the gui to read keystrokes and make it the focus
    	addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        //the following was grabbed off Oracle's swing tutorial online
        //essentially it just automatically starts the game with a delay and sets the speed of the game
        timer = new Timer(400, this);
        //timer.start();
    }
    
    //works
    private void newPiece()
    {
    	//System.out.print("b");
        block = TetrisBlock.createTBlock();
        
        //System.out.println(block.getBlockType());
        
        xPos = (int) (stackWidth / 2) + 1;
        yPos = stackHeight - 1 + block.getMinYCoord();
        //System.out.println(yPos);
    }
    
    //works
    private void clearBoard()
    {
    	//System.out.print("c");
        for (int i = 0; i < stackHeight * stackWidth; i++)
        {
            board[i] = TetrisBlock.createNullBlock();
        }
    }

    
    /**
     * Paint the current state of the game. Using our timer, it repaints at a constant interval.
     */
    public void paint(Graphics g)
    {
    	//System.out.println("d");
        super.paint(g);
        
        Graphics2D G = (Graphics2D) g;
        
        G.setPaint(Color.WHITE); //set background to be white
        G.fillRect(0, 0, 512, 512);

        int top = 0;
        
        int[][] arr = block.getCoordinateArr();
        
        for (int i = 0; i < stackHeight; i++) 
        {
            for (int j = 0; j < stackWidth; j++) 
            {
            	//works
                TetrisBlock t = getBlock(j, i);
                
                if(t != null && t.getBlockType() != "nullBlock")
                {
                	//System.out.println("j times stackwidth: " + j * stackWidth);
                	//System.out.println("top + i times stackheight: " + (top + i * stackHeight));
                	
                    drawSquare(g, j * OBJ_SIZE, (stackHeight - i- 1) * OBJ_SIZE, block);
                }
            }
        }

        if (block != null && block.getBlockType() != "nullBlock")
        {
        	for (int i = 0; i < 4; i++) 
        	{
                int x = xPos + arr[i][0];
                int y = yPos - arr[i][1];
                //System.out.println("x: " + x);
                //System.out.println("y: " + y);
                drawSquare(g, x * OBJ_SIZE, (stackHeight - y - 1) * OBJ_SIZE, block);
        	}
        }
        
        G.dispose();
    }
    
    private void drawSquare(Graphics g, int x, int y, TetrisBlock t)
    {
    	//System.out.print("f");
        Color color = Color.BLUE;
        g.setColor(color);
        g.fillRect(x, y, OBJ_SIZE, OBJ_SIZE);
    }
    

    private void moveLeft()
    {
    	//System.out.print("g");
    	int xPos = this.xPos - 1;
    	
    	int[][] arr = block.getCoordinateArr();
    	
    	int i = 0;	
    	while(i < 4)
    	{
    		int x = xPos + arr[i][0]; 
    		int y = yPos - arr[i][1];
    		
            if(!checkBounds(x, y))
            {
            	//out of bounds.  kill method
            	return;
            }
            
            i++;
    	}
   
    	this.xPos = xPos;
    	
    	repaint();
    }
    
    private void moveRight()
    {
    	//System.out.print("h");
    	int xPos = this.xPos + 1;
    	
    	int[][] arr = block.getCoordinateArr();
    	
    	int i = 0;	
    	while(i < 4)
    	{
    		int x = xPos + arr[i][0]; 
    		int y = yPos - arr[i][1];
    		
            if(!checkBounds(x, y))
            {
            	//out of bounds. kill method
            	return;
            }
            
            i++;
    	}
    	
    	this.xPos = xPos;
    	
    	repaint();
    }    
    
    private void moveDown()
    {
    	//System.out.print("I");
    	moveLeft();
    	moveRight();
    	int yPos = this.yPos - 1;
    	
    	int[][] arr = block.getCoordinateArr();
    	
    	int i = 0;	
    	
    	while(i < 4)
    	{
    		int x = xPos + arr[i][0];
    		int y = yPos - arr[i][1];
    		
    		//System.out.println("Try moved output begin\n");
    		//System.out.println(y);
    		//System.out.println("\nTrymoved output end\n");
    		//System.out.println(checkBounds(xPos, y));
    		
            if(!checkBounds(x, y))
            {
            	//out of bounds, lock piece in
            	lockPieceIn();
            	return;
            }
           
            i++;
    	}
    	
    	this.yPos = yPos;
        
    	repaint();
        
    }
    
    private void lockPieceIn()
    {
    	//System.out.print("j\n");
    	/**
    	 * Initial board set up: Empty 2D array of ints (x-y coordinates)
    	 * Following a collision (i.e. this is called from another method)
    	 * 1. Get current board state
    	 * 2. Add 1's where block is placed given piece shape
    	 * 3. Redraw board (maybe respecting piece color idk)
    	 */
    	
    	int[][] arr = block.getCoordinateArr();
    	
        for (int i = 0; i < 4; i++) 
        {
            int x = xPos + arr[i][0];
            int y = yPos - arr[i][1];
            
            //System.out.println("Piece dropped output begin\n");
            //System.out.println(xPos + "\n");
            //System.out.println(yPos + "\n");
            //System.out.println(arr[i][0] + "\n");
            //System.out.println(arr[i][1] + "\n");
            //System.out.println("Position: " + ((y * stackWidth) + x) + "\n");
            //System.out.println("Piece dropped output end\n");
            board[(y * stackWidth) + x] = block;
        }
        
        repaint();

        if (!falling)
        {
            newPiece();
        }
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
		repaint();
		//.out.print("k");
    	if(falling) 
    	{
    		falling = false;
    	    newPiece();
    	} 
    	else 
    	{
    		moveDown();
    	}
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
    }
    
	@Override
	public void keyPressed(KeyEvent e) 
	{
		//System.out.print("L");
		if(e.getKeyCode() == 37)
        {
			moveLeft();
		}
        else if(e.getKeyCode() == 38) //up arrow key
        {
        	block.rotateLeft();
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
		//System.out.print("M");
		
		//check boundaries
		if (xPos < 0 || yPos < 0 || xPos >= stackWidth || yPos >= stackHeight)
        {
            return false;
            
        }
		
		//check for collision
		if (getBlock(xPos, yPos) != null && !getBlock(xPos, yPos).getBlockType().equals("nullBlock"))
		{
			return false;
		}
		
		return true;
	}
	
	private TetrisBlock getBlock(int x, int y) 
	{ 
		//System.out.print("N\n");
		//System.out.println((y * stackWidth) + x + "\n");
		return board[(y * stackWidth) + x];
	}

	public void start()
    {
		//System.out.println("O");
		
		falling = false;
        lines = 0;
        
        clearBoard();
        newPiece();
        
        timer.start();
    }
	
    public static void main(String[] args) throws InterruptedException
    {
       	JFrame f = new JFrame("Paddle Game");
	
       	final int BORDER_WIDTH = 16; //This number allow the game itself to be 512 x 512
       	final int BORDER_HEIGHT = 39; //This number allow the game itself to be 512 x 512
       	f.setSize(BOUNDS_SIZE + BORDER_WIDTH, BOUNDS_SIZE + BORDER_HEIGHT); 
        
       	TetrisGame w = new TetrisGame();
       	f.setContentPane(w);
       	f.setResizable(false);
       	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       	f.setVisible(true);
       	w.start();
     }


}
