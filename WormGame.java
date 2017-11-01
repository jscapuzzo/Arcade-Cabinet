import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class WormGame extends JPanel{
	static final int OBJ_SIZE = 32; // The size of the worm and worm ford
    static final int BOUNDS_SIZE = 512; //The size of the game boundaries
    static int xWorm = 0; // The x-position of the worm
    static int yWorm = 0; // The y-position of the worm
    int xFood = 64; // The x-position of the worm food
    int yFood = 64; // The y-position of the worm food
    int score = 0; // The player's score
    boolean canMove = true; // Ensure's an object does not move more than once per cycle
    
    public void moveWormRight(){
    	boolean inBounds = checkUpperBounds(xWorm);
    	if(inBounds == true){
    		xWorm += OBJ_SIZE;
    	}
    	else{
    		inBounds = true;
    	}
    }
    
    public void moveWormLeft(){
    	boolean inBounds = checkLowerBounds(xWorm);
    	if(inBounds == true){
    		xWorm -= OBJ_SIZE;
    	}
    	else{
    		inBounds = true;
    	}
    }
    
    public void moveWormUp(){
    	boolean inBounds = checkLowerBounds(yWorm);
    	if(inBounds == true){
    		yWorm -= OBJ_SIZE;
    	}
    	else{
    		inBounds = true;
    	}
    }
    
    public void moveWormDown(){
    	boolean inBounds = checkUpperBounds(yWorm);
    	if(inBounds == true){
    		yWorm += OBJ_SIZE;
    	}
    	else{
    		inBounds = true;
    	}
    }
    
    public boolean checkLowerBounds(int position){
    	int p = position;
    	if(p <= 0){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    public boolean checkUpperBounds(int position){
    	int p = position;
    	if(p >= BOUNDS_SIZE - OBJ_SIZE){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    public void checkFood(){
    	if(xWorm == xFood && yWorm == yFood){
    		score++;
    		moveFood();
    	}
    	else{
    		// Do nothing
    	}
    }
    
    public void moveFood(){
        Random ran = new Random();
        xFood = ran.nextInt(BOUNDS_SIZE - OBJ_SIZE);
        yFood = ran.nextInt(BOUNDS_SIZE - OBJ_SIZE);
        while(xFood % OBJ_SIZE != 0){
        	xFood = ran.nextInt(BOUNDS_SIZE - OBJ_SIZE);
        }
        while(yFood % OBJ_SIZE != 0){
        	yFood = ran.nextInt(BOUNDS_SIZE - OBJ_SIZE);
        }
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D G = (Graphics2D) g;
        G.setColor(Color.WHITE); // Background color
        G.fillRect(0, 0, 512, 512); // Places background over JPanel default none
        G.setColor(Color.BLACK); // Worm color
        G.fillRect(xWorm, yWorm, OBJ_SIZE, OBJ_SIZE); // Creates worm on screen
        G.setColor(Color.GREEN); // Worm food color
        G.fillRect(xFood, yFood, OBJ_SIZE, OBJ_SIZE); // Creates worm food on screen
        G.setColor(Color.BLUE); // The on-screen text color
        G.drawString("Use the arrow keys to move!", 156, 156);
        G.drawString("Score: " + String.valueOf(score), 214, 166);
        G.dispose();
    }

    public static void main(String[] args) throws InterruptedException{
        JFrame f = new JFrame("Worm Game");
        final WormGame w = new WormGame();
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
	                		w.moveWormLeft();
	                	}
	                	else if(e.getKeyCode() == 38){
	                		w.moveWormUp();
	                	}
	                	else if(e.getKeyCode() == 39){
	                		w.moveWormRight();
	                	}
	                	else if(e.getKeyCode() == 40){
	                		w.moveWormDown();
	                	}
	                	else{}
	                	w.canMove = false;
                	}
                	else{
                		// Do nothing
                	}
                }
            });
        	w.checkFood();
            w.repaint();
            Thread.sleep(100); // Affects movement speed
            w.canMove = true;
        }
    }
}
