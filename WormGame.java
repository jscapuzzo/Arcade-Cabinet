import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class WormGame extends JPanel{
    int xWorm = 0;
    int yWorm = 0;
    int prevXWorm = 0;
    int prevYWorm = 0;
    int xFood;
    int yFood;
    //Graphics gr = getGraphics();
    
    public void moveWorm(){
        prevXWorm = xWorm;
        prevYWorm = yWorm;
        xWorm++;
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
        G.drawString("(Not Implemented Yet)", 166, 166);
        G.dispose();
    }

    
    public static void main(String[] args) throws InterruptedException{
        JFrame f = new JFrame("Worm Game");
        WormGame w = new WormGame();
        f.setSize(512, 512);
        f.add(w);
        //f.setBackground(Color.WHITE);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        while(true){
            w.moveWorm();
            w.repaint();
            Thread.sleep(10);
        }
        //System.out.println(w.xWorm);
    }
}
