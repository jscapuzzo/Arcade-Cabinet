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
    	 * Move piece
    	 * 		Conditions for piece: 1. Can it be rotated (due to boundaries)? 2. Can it move (due to boundaries)
    	 * Lock piece in.
    	 * Check if line is full or if we topped out
    	 * Re-loop
    	 */
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
        	//moveLeft();
        }
        else if(e.getKeyCode() == 38)
        {
        	//rotateRight
        }
        else if(e.getKeyCode() == 39)
        {
        	//moveRight();
        }
        else if(e.getKeyCode() == 40)
        {
        	//rotateLeft();
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
