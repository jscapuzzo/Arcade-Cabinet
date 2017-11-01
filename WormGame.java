import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class WormGame extends JPanel{
    static int xWorm = 8;
    static int yWorm = 8;
    int xFood = 64;
    int yFood = 64;
    boolean foodConsumed = false;
    int score = 0;
    boolean canMove = true;
    
    public void moveWormRight(){
    	boolean inBounds = checkInBounds(xWorm);
    	boolean letMove = checkMove(xWorm);
    	if(inBounds == true){
    		if(letMove == true){
        		xWorm += 8;
        	}
        	else{
        		letMove = true;
        	}
    	}
    	else{
    		inBounds = true;
    	}
    }
    
    public void moveWormLeft(){
    	boolean inBounds = checkInBounds(xWorm);
    	boolean letMove = checkMove(xWorm);
    	if(inBounds == true){
    		if(letMove == true){
        		xWorm -= 8;
        	}
        	else{
        		letMove = true;
        	}
    	}
    	else{
    		inBounds = true;
    	}
    }
    
    public void moveWormUp(){
    	boolean inBounds = checkInBounds(yWorm);
    	boolean letMove = checkMove(yWorm);
    	if(inBounds == true){
    		if(letMove == true){
        		yWorm -= 8;
        	}
        	else{
        		letMove = true;
        	}
    	}
    	else{
    		inBounds = true;
    	}
    }
    
    public void moveWormDown(){
    	boolean inBounds = checkInBounds(yWorm);
    	boolean letMove = checkMove(yWorm);
    	if(inBounds == true){
    		if(letMove == true){
        		yWorm += 8;
        	}
        	else{
        		letMove = true;
        	}
    	}
    	else{
    		inBounds = true;
    	}
    }
    
    public boolean checkInBounds(int position){
    	int p = position;
    	if(p >= 400){
    		return false;
    	}
    	else if(p < 0){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    public boolean checkMove(int position){
    	int p = position;
    	if(p + 1 >= 400){
    		return false;
    	}
    	else if(p - 1 < 0){
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
        xFood = ran.nextInt(512);
        yFood = ran.nextInt(512);
        while(xFood % 8 != 0){
        	xFood = ran.nextInt(512);
        }
        while(yFood % 8 != 0){
        	yFood = ran.nextInt(512);
        }
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D G = (Graphics2D) g;
        G.setColor(Color.WHITE);
        G.fillRect(0, 0, 512, 512);
        G.setColor(Color.BLACK);
        G.fillRect(xWorm, yWorm, 8, 8);
        G.setColor(Color.GREEN);
        G.fillRect(xFood, yFood, 8, 8);
        G.setColor(Color.BLUE);
        G.drawString("Use the arrow keys to move!", 156, 156);
        G.drawString("Score: " + String.valueOf(score), 184, 166);
        G.dispose();
    }

    public static void main(String[] args) throws InterruptedException{
        JFrame f = new JFrame("Worm Game");
        final WormGame w = new WormGame();
        f.setSize(528, 551); //These numbers allow the game itself to be 512 x 512
        f.add(w);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        f.addKeyListener(new KeyAdapter(){
        	public void key(KeyEvent key){
        		int k = key.getKeyCode();
        		if(k == KeyEvent.VK_DOWN){
        			xWorm++;
        		}
        		else{
        		}
        	}
        });
        System.out.println(f.getContentPane().getSize());
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
            Thread.sleep(200);
            w.canMove = true;
        }
    }
}
