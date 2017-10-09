import javax.swing.*; 

public class ArcadeCabinet{
  public staic void main(String[] args){
    //System.out.println("Hello World");
    cabinetWindow();
  }
  public static void cabinetWindow(){
    JFrame frame = new JFrame("Arcade Cabinet");  //Creates the window
    frame.setSize(224,288); //244 x 288 is the original resolution of Pacman
    frame.setLayout(NULL);
    frame.setVisible(TRUE): //Makes it so the window actually appears
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
