import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class WormGame extends JPanel{
    static int xWorm = 0;
    static int yWorm = 0;
    int prevXWorm = 0;
    int prevYWorm = 0;
    int xFood;
    int yFood;
    
    public void moveWormRight(){
        prevXWorm = xWorm;
        xWorm++;
    }
    
    public void moveWormLeft(){
        prevXWorm = xWorm;
        xWorm--;
    }
    
    public void moveWormUp(){
        prevYWorm = yWorm;
        yWorm--;
    }
    
    public void moveWormDown(){
        prevYWorm = yWorm;
        yWorm++;
    }
    
    public void moveFood(){
        Random ran = new Random();
        xFood = ran.nextInt(512);
        yFood = ran.nextInt(512);
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D G = (Graphics2D) g;
        G.setColor(Color.WHITE);
        G.fillRect(0, 0, 512, 512);
        G.setColor(Color.BLACK);
        G.fillRect(xWorm, yWorm, 10, 10);
        //G.clearRect(prevXWorm, prevYWorm, 10, 10);
        G.drawString("Use the arrow keys to move!", 156, 156);
        G.drawString("Collect the food!", 184, 166);
        G.dispose();
    }

    
    public static void main(String[] args) throws InterruptedException{
        JFrame f = new JFrame("Worm Game");
        final WormGame w = new WormGame();
        f.setSize(512, 512);
        f.add(w);
        //f.setBackground(Color.WHITE);
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
        
        while(true){
        	f.addKeyListener(new KeyAdapter(){
        		@Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyReleased(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
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
                	
                }
            });
            w.repaint();
            Thread.sleep(200);
        }
    }
}
