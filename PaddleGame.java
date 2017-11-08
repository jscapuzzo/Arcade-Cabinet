import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class PaddleGame extends JPanel{
	static final int OBJ_SIZE = 32; // The size of the worm and worm ford
	static final int PADDLE_HEIGHT = OBJ_SIZE * 2;
    static final int BOUNDS_SIZE = 512; //The size of the game boundaries
    static int xPaddle = 0; // The x-position of the worm
    static int yPaddle = 0; // The y-position of the worm
    static int xEnemyPaddle = BOUNDS_SIZE - OBJ_SIZE; // The x-position of the worm
    static int yEnemyPaddle = 0; // The y-position of the worm
    int xBall = BOUNDS_SIZE/2; // The x-position of the worm food
    int yBall = BOUNDS_SIZE/2; // The y-position of the worm food
    int ballDirection = -OBJ_SIZE;
    int ballYDirection = -OBJ_SIZE;
    int score = 0; // The player's score
    int enemyScore = 0;
    boolean canMove = true; // Ensure's an object does not move more than once per cycle
    boolean resetBall = false;
    boolean waitBallMove = false;
	
    public void movePaddleUp(){
    	boolean inBounds = checkLowerBounds(yPaddle);
    	if(inBounds == true){
    		yPaddle -= OBJ_SIZE;
    	}
    	else{
    		inBounds = true;
    	}
    }
    
    public void movePaddleDown(){
    	boolean inBounds = checkUpperBounds(yPaddle);
    	if(inBounds == true){
    		yPaddle += OBJ_SIZE;
    	}
    	else{
    		inBounds = true;
    	}
    }
    
    public void moveEnemyPaddleUp(){
    	boolean inBounds = checkLowerBounds(yEnemyPaddle);
    	if(inBounds == true){
    		yEnemyPaddle -= OBJ_SIZE;
    	}
    	else{
    		inBounds = true;
    	}
    }
    
    public void moveEnemyPaddleDown(){
    	boolean inBounds = checkUpperBounds(yEnemyPaddle);
    	if(inBounds == true){
    		yEnemyPaddle += OBJ_SIZE;
    	}
    	else{
    		inBounds = true;
    	}
    }
    
    public boolean checkLowerBounds(int position){
    	int p = position;
    	if(p <= BOUNDS_SIZE - BOUNDS_SIZE){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    public boolean checkUpperBounds(int position){
    	int p = position;
    	if(p >= BOUNDS_SIZE - PADDLE_HEIGHT){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    public boolean checkBallUpperBounds(int position){
    	int p = position;
    	if(p >= BOUNDS_SIZE - OBJ_SIZE){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    public void moveEnemyPaddle(){
        if(yEnemyPaddle < yBall){
        	moveEnemyPaddleDown();
        }
        else if(yEnemyPaddle > yBall){
        	moveEnemyPaddleUp();
        }
        else{
        	// Do nothing
        }
    }
    
    public void moveBall(){
    	xBall += ballDirection;
    	boolean upperBound = checkBallUpperBounds(yBall);
    	boolean lowerBound = checkLowerBounds(yBall);
    	if(waitBallMove == false){
	    	if(upperBound == false){
	    		ballYDirection = -OBJ_SIZE;
	    		waitBallMove = true;
	    	}
	    	else if(lowerBound == false){
	    		ballYDirection = OBJ_SIZE;
	    		waitBallMove = true;
	    	}
	    	else{
	    		// Do nothing
	    	}
    	}
    	else{
    		waitBallMove = false;
    	}
    	yBall += ballYDirection;
    	
    }
    
    public void setYBall(){
    	Random ran = new Random();
        int randNum = ran.nextInt(3);
        System.out.println(randNum);
        if(randNum == 0){
        	ballYDirection = -OBJ_SIZE;
        }
        else if(randNum == 1){
        	ballYDirection = OBJ_SIZE;
        }
        else{
        	ballYDirection = 0;
        }
    }
    
    public void checkBall(){
    	if(xBall == BOUNDS_SIZE - OBJ_SIZE){
    		score++;
    		resetBall = true;
    	}
    	else if(xBall == BOUNDS_SIZE - BOUNDS_SIZE){
    		enemyScore++;
    		resetBall = true;
    	}
    	else{
    		// Do nothing
    	}
    	if(xBall == xPaddle + OBJ_SIZE){
    		if(yBall == yPaddle || yBall == yPaddle + OBJ_SIZE){
    			ballDirection = -ballDirection;
    			System.out.println("Hit");
    			setYBall();
    		}
    		else{
    			// Do nothing
    		}
    	}
    	else if(xBall == xEnemyPaddle - OBJ_SIZE){
    		if(yBall == yEnemyPaddle || yBall == yEnemyPaddle + OBJ_SIZE){
    			ballDirection = -ballDirection;
    			System.out.println("Enemy Hit");
    			setYBall();
    		}
    		else{
    			// Do nothing
    		}
    	}
    	else{
    		// Do nothing
    	}
    }
    
    public void resetBall(){
    	xBall = BOUNDS_SIZE/2;
		yBall = BOUNDS_SIZE/2;
		resetBall = false;
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D G = (Graphics2D) g;
        G.setColor(Color.WHITE); // Background color
        G.fillRect(0, 0, 512, 512); // Places background over JPanel default none
        G.setColor(Color.BLACK); // Paddle color
        G.fillRect(xPaddle, yPaddle, OBJ_SIZE, PADDLE_HEIGHT); // Creates enemy paddle on screen
        G.setColor(Color.RED); // Paddle color
        G.fillRect(xEnemyPaddle, yEnemyPaddle, OBJ_SIZE, PADDLE_HEIGHT); // Creates enemy paddle on screen
        G.setColor(Color.GREEN); // Ball color
        G.fillRect(xBall, yBall, OBJ_SIZE, OBJ_SIZE); // Creates ball on screen
        G.setColor(Color.BLUE); // The on-screen text color
        G.drawString("Use the arrow keys to move up and down!", 156, 156);
        G.drawString("Score: " + String.valueOf(score), 214, 166);
        G.drawString("Enemy Score: " + String.valueOf(enemyScore), 204, 176);
        G.dispose();
    }
    
	public static void main(String[] args) throws InterruptedException{
        JFrame f = new JFrame("Paddle Game");
        final PaddleGame w = new PaddleGame();
        final int BORDER_WIDTH = 16; //This number allow the game itself to be 512 x 512
        final int BORDER_HEIGHT = 39; //This number allow the game itself to be 512 x 512
        f.setSize(BOUNDS_SIZE + BORDER_WIDTH, BOUNDS_SIZE + BORDER_HEIGHT); 
        f.add(w);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //System.out.println(f.getContentPane().getSize());
        while(true){
        	f.addKeyListener(new KeyAdapter(){
                @Override
                public void keyPressed(KeyEvent e) {
                	if(w.canMove == true){
	                	if(e.getKeyCode() == 37){
	                		//w.moveWormLeft();
	                	}
	                	else if(e.getKeyCode() == 38){
	                		w.movePaddleUp();
	                	}
	                	else if(e.getKeyCode() == 39){
	                		//w.moveWormRight();
	                	}
	                	else if(e.getKeyCode() == 40){
	                		w.movePaddleDown();
	                	}
	                	else{}
	                	w.canMove = false;
                	}
                	else{
                		// Do nothing
                	}
                }
            });
        	w.moveEnemyPaddle();
        	w.moveBall();
        	if(w.resetBall == true){
        		w.resetBall();
        	}
        	else{}
        	w.checkBall();
            w.repaint();
            //w.checkBall();
            Thread.sleep(100); // Affects movement speed
            w.canMove = true;
        }
    }
}
