import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;

public class TetrisGame extends JPanel implements KeyListener, ActionListener 
{
	
	static final int OBJ_SIZE = 32; // The size of the worm and worm ford
    static final int BOUNDS_SIZE = 512; //The size of the game boundaries
    static final int WIDTH = BOUNDS_SIZE + 16;
    static final int HEIGHT = BOUNDS_SIZE + 39;
   
    private Timer timer;
    
    int score = 0; // The player's score

    //constructor does some interaction stuff
    public TetrisGame()
    {
    	//reset();
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
    
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent arg0) { }

	@Override
	public void keyTyped(KeyEvent arg0) { }

}
