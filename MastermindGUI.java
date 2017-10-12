
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Mastermind
 *      
 * @author R. King
 * @version 2015.05.13
 */
public class MastermindGUI
{
    // constants:
    public static final String VERSION = "Copyright 2015 Ryan King"; //Random copyright.
    public static final int PEGS = 3; //how many pegs I will be using.
    public static final String[] COLORS = {"Yellow", "White", "Blue", "Red", "Green"}; //The colors for our game.
    
    // fields:
    private Engine engine; //My engine for Mastermind    
    private JFrame frame;
    private JPanel centerPanel; //Main panel
    private JPanel resultPanel; //This is where I put my textarea
    private JPanel exitPanel; //The area where I put my exit button
    private JPanel pegPanel; //First peg panel (west)
    private JPanel pegPanel2; //Second peg panel (center)
    private JPanel pegPanel3; //Third peg panel (east)
    private JPanel allPegs; //Putting all the peg panels together
    private JLabel statusLabel; //Copyright label
    private JButton answerButton; //Self-explanatory
    private JButton checkButton; //Self-explanatory
    private JButton resetButton; //Self-explanatory
    private JButton exitButton; //Self-explanatory
    JRadioButton yellowPeg; //My yellow peg
    JRadioButton bluePeg; //My blue peg 
    JRadioButton redPeg; //My red peg
    JRadioButton greenPeg; //My green peg
    JRadioButton whitePeg; //My white peg
    
    //now there is probably soem way to consolidate these into one thing, where I don't need 10 more JRadioButton objects, but I didn't feel comfortable
    //confident I could figure it out within the time constraints. So my apologies.
    JRadioButton yellowPeg2;
    JRadioButton bluePeg2;
    JRadioButton redPeg2;
    JRadioButton greenPeg2;
    JRadioButton whitePeg2;
    
    JRadioButton yellowPeg3;
    JRadioButton bluePeg3;
    JRadioButton redPeg3;
    JRadioButton greenPeg3;
    JRadioButton whitePeg3;
    
    //text area
    JTextArea textArea = null;
    
    //Random image if you want to use it
    Icon dialogImage;
    
    //more common fields.
    private String answer; //This is my answer to my Mastermind game.
    private ArrayList<JRadioButton> allButtons = new ArrayList<JRadioButton>(); //This stores all my jradiobuttons into a convienent location.
    private String[] selectedButtons = new String[PEGS]; //This is the array of my selected radio buttons.
    private int counter; //basic counter for guesses.
    
  
    /**
     * Create a mastermind GUI and display it. Also create a mastermind game.
     */
    public MastermindGUI()
    {
        makeFrame(); //make my GUI essentially
        randomize(); //put all pegs in random positon.
        engine = new Engine(getSelected()); //make an engine, and make sure that it doesn't have any correct pegs.
        counter = 1; //basic counter for guesses
        textArea.append("A new set of pegs has been made \n");
    }
    
    /**
     * Create the Swing frame and some buttons.
     */
    private void makeFrame()
    {
        frame = new JFrame("Mastermind");       
        JPanel contentPane = (JPanel)frame.getContentPane();
       
        makeMenuBar(frame);
        
        // Specify the layout manager with nice spacing
        contentPane.setLayout(new BorderLayout(3,3));
        
        // Create a JPanel in the center
        centerPanel = new JPanel();
        centerPanel.setBorder(new EtchedBorder());
        contentPane.add(centerPanel, BorderLayout.CENTER);
        
        contentPane.add(Box.createHorizontalStrut(10), BorderLayout.NORTH); //This creates some spacing so not everything is at the top
        
        /**
         * Make buttons, and our copyright signature.
         */
        // Create the toolbar with the buttons
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(0, 1));
        toolbar.add(Box.createHorizontalStrut(20), BorderLayout.NORTH);
        //toolbar.setBackground(Color.WHITE);
        
        // Create the "Answer" button
        answerButton = new JButton("Answer");
        answerButton.setSize(20, 20); //Make it a standard size
        answerButton.addActionListener(new ActionListener() 
                                            {
                                                public void actionPerformed(ActionEvent e) 
                                                { 
                                                   answer = engine.answer();
                                                   textArea.append("The answer is: " + answer + "\n");
                                                   counter = 1;           
                                                }
                                            });
        // Add the "answer" button to our toolbar
        toolbar.add(answerButton);
        toolbar.add(Box.createHorizontalStrut(5)); //This creates some spacing in between the buttons
        
        //Create the "Check" button
        checkButton = new JButton("Check");
        checkButton.setSize(20,20);
        checkButton.addActionListener(new ActionListener() 
                                           {
                                               public void actionPerformed(ActionEvent e) 
                                               { 
                                                   int[] temp = engine.check(getSelected()); //This is a two element array. The first element is my pegs correct, the second is my colors correct. 
                                                   if(temp[0] != PEGS)
                                                      textArea.append("Guess " + counter + ": Pegs correct: " + temp[0] + " Colors correct: " + temp[1] + "\n");
                                                   else if (counter > 1) //Multiple guesses
                                                           { 
                                                               textArea.append("Correct! You took " + counter + " guesses \n");
                                                               answer = engine.answer();
                                                               textArea.append("The answer is: " + answer + "\n");
                                                           }
                                                     else //First try, person is a true mastermind
                                                           {
                                                               textArea.append("Correct! You took 1 guess \n");
                                                               answer = engine.answer();
                                                               textArea.append("The answer is: " + answer + "\n");
                                                           }
                                                   counter++;
                                               }
                                           });
        // Add the "check" button to our toolbar
        toolbar.add(checkButton);
        toolbar.add(Box.createHorizontalStrut(5));
        
        //Create the reset button
        resetButton = new JButton("Reset");
        resetButton.setSize(20,20);
        resetButton.addActionListener(new ActionListener() 
                                           {
                                               public void actionPerformed(ActionEvent e) 
                                               { 
                                                   reset();
                                               }
                                           });
        //Add it to our toolbar
        toolbar.add(resetButton);
       
        //Add the toolbar to the center region
        JPanel center = new JPanel();
        center.add(toolbar);
        
        contentPane.add(center, BorderLayout.CENTER);
        
        //Make our exit button. This is in a different spot than all the others.
        exitPanel = new JPanel();
        exitPanel.setBorder(new EmptyBorder(0,450,5, 20)); 
        exitPanel.setLayout(new BorderLayout());
        exitButton = new JButton("Exit");
        exitButton.setSize(20,20);
        exitButton.addActionListener(new ActionListener() 
                                           {
                                               public void actionPerformed(ActionEvent e) 
                                               { 
                                                   System.exit(0);
                                               }
                                           });
        
        exitPanel.add(exitButton, BorderLayout.EAST);
        
        statusLabel = new JLabel(VERSION + "                     "); //This is our copyright, since it's going to be near our exit button, it should be in the same panel.
        exitPanel.add(statusLabel, BorderLayout.WEST);
        
        contentPane.add(exitPanel, BorderLayout.SOUTH); //Add the exit button, and copyright to the South region
        
        
        /**
         * Make Mastermind's pegs.
         */
        makePegs(contentPane); 
        
        
        /**
         * Make text area
         */
        
        
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new FlowLayout());
        textAreaPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Results"));
        
        
        // Create/configure text area.
        textArea = new JTextArea(10, 27); //10 lines, 35 character each.
        textArea.setEditable(false);  // don't allow user to edit text area
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 12)); // set the text area font style
        textArea.setLineWrap(true);  // enable the wrapping of lines

        // Put the text area into a scrollable pane and put in a panel.
        JScrollPane scrollPane = new JScrollPane(textArea);  // put text area into scrollable pane
        scrollPane.setVerticalScrollBarPolicy(scrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);  // display scrollbar only if needed
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());  // set the border style of the scrollable pane
        textAreaPanel.add(scrollPane); // sdd the scrollable pane to the text area panel
        
        contentPane.add(textAreaPanel, BorderLayout.WEST); //Add our panel to the west region.

        //Set the size of our frame.
        frame.setSize(800, 500);
        
        // place the frame at the center of the screen 
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        
        //pack and display
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Create Mastermind's menu bar
     * 
     * @param frame   The frame that the menu bar should be added to.
     */
    private void makeMenuBar(JFrame frame)
    {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        // create the File menu
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        
        //Create the Edit menu
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        
        //Create the Help menu
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        
        
        /**
         * File menu items
         */
       
        JMenuItem quitItem = new JMenuItem("Quit");
        // add an anonymous ActionListener for File: Quit
        quitItem.addActionListener(new ActionListener()
                                       {
                                           public void actionPerformed(ActionEvent event)
                                           {
                                               System.exit(0); //instant leave
                                           } 
                                            
                                       } );
        fileMenu.add(quitItem);
        
        
        /**
         * Edit menu items
         */
        
        JMenuItem resetItem = new JMenuItem("Reset");
        //add an anonymous ActionListener for Edit>Reset
        resetItem.addActionListener(new ActionListener()
                                   {
                                           public void actionPerformed(ActionEvent event)
                                           {
                                               reset();
                                           }
                                            
                                       } );
        editMenu.add(resetItem);
                                       
        /**
         * Help menu items
         */
        JMenuItem howToRunItem = new JMenuItem("Playing Mastermind");
        // add an anonymous ActionListener for Help>HowToRun
        howToRunItem.addActionListener(new ActionListener()
                                       {
                                           public void actionPerformed(ActionEvent event)
                                           {
                                              helpHowToPlay();
                                              
                                           }
                                            
                                       } );
        helpMenu.add(howToRunItem);
        
        
        JMenuItem aboutItem = new JMenuItem("About Mastermind");
        // add an anonymous ActionListener for File: Quit
        aboutItem.addActionListener(new ActionListener()
                                       {
                                           public void actionPerformed(ActionEvent event)
                                           {
                                              helpAbout(); //I copied it straight up, because it is such a good method name. 
                                           }
                                            
                                       } );
        helpMenu.add(aboutItem);    
    }

    
    /**
     * Make the Pegs buttons for mastermind
     * @param JPanel the JPanel the radio buttons will be added to
     */
    private void makePegs(JPanel contentPane)
    {     
        allPegs = new JPanel(); //Make a panel for all the pegs to be on.
        allPegs.setLayout(new FlowLayout());
        
       
        yellowPeg = new JRadioButton("Yellow"); 
        yellowPeg.setSelected(false); 
        allButtons.add(yellowPeg); //add the button to our arraylist of radiobuttons
        
        bluePeg = new JRadioButton("Blue");
        bluePeg.setSelected(true); //Set as default radio button for this group
        allButtons.add(bluePeg); 
        
        redPeg = new JRadioButton("Red");
        redPeg.setSelected(false);
        allButtons.add(redPeg);
        
        greenPeg = new JRadioButton("Green");        
        greenPeg.setSelected(false);
        allButtons.add(greenPeg);
       
        whitePeg = new JRadioButton("White");
        whitePeg.setSelected(false);
        allButtons.add(whitePeg);
        
        //Group the radio buttons
        ButtonGroup btnGroup1 = new ButtonGroup(); // create the button group
        btnGroup1.add(yellowPeg); // add the radio buttons to the group
        btnGroup1.add(bluePeg);
        btnGroup1.add(redPeg);
        btnGroup1.add(greenPeg);    
        btnGroup1.add(whitePeg);
        
        // Add radio buttons to a panel, so we can use them.
        pegPanel = new JPanel(new GridLayout(0, 1));
        pegPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder() , "Peg 1"));
        pegPanel.add(yellowPeg);
        pegPanel.add(bluePeg);
        pegPanel.add(redPeg);
        pegPanel.add(greenPeg);
        pegPanel.add(whitePeg);
        
        //Add the panel to our allPegs so we can create a nice flow layout with three pegs.
        allPegs.add(pegPanel, BorderLayout.WEST); 
        
        /**
         * Repeat above twice
         */
        
        yellowPeg2 = new JRadioButton("Yellow"); 
        yellowPeg2.setSelected(false); 
        allButtons.add(yellowPeg2);
        
        bluePeg2 = new JRadioButton("Blue");
        bluePeg2.setSelected(false);
        allButtons.add(bluePeg2);
        
        redPeg2 = new JRadioButton("Red");
        redPeg2.setSelected(true); //set as default selected button in this group.
        allButtons.add(redPeg2);
        
        greenPeg2 = new JRadioButton("Green");        
        greenPeg2.setSelected(false);
        allButtons.add(greenPeg2);
        
        whitePeg2 = new JRadioButton("White");
        whitePeg2.setSelected(false);
        allButtons.add(whitePeg2);
        
        // Group the radio buttons.
        ButtonGroup btnGroup2 = new ButtonGroup(); // create the button group
        btnGroup2.add(yellowPeg2); // add the radio buttons to the group
        btnGroup2.add(bluePeg2);
        btnGroup2.add(redPeg2);
        btnGroup2.add(greenPeg2);    
        btnGroup2.add(whitePeg2);
       
        //Add them to a panel.
        pegPanel2 = new JPanel(new GridLayout(0, 1));
        pegPanel2.setBorder(BorderFactory.createTitledBorder("Peg 2"));
        pegPanel2.add(yellowPeg2);
        pegPanel2.add(bluePeg2);
        pegPanel2.add(redPeg2);
        pegPanel2.add(greenPeg2);
        pegPanel2.add(whitePeg2);
       
        allPegs.add(pegPanel2, BorderLayout.CENTER); //Add panel.
       
        yellowPeg3 = new JRadioButton("Yellow");
        yellowPeg3.setSelected(false); 
        allButtons.add(yellowPeg3);
        
        bluePeg3 = new JRadioButton("Blue");
        bluePeg3.setSelected(false);
        allButtons.add(bluePeg3);
        
        redPeg3 = new JRadioButton("Red");
        redPeg3.setSelected(false);
        allButtons.add(redPeg3);
        
        greenPeg3 = new JRadioButton("Green");        
        greenPeg3.setSelected(true); //Set as default selected radio button.
        allButtons.add(greenPeg3);
        
        whitePeg3 = new JRadioButton("White");
        whitePeg3.setSelected(false);
        allButtons.add(whitePeg3);
        
        // Group the radio buttons. 
        ButtonGroup btnGroup3 = new ButtonGroup(); // create the button group
        btnGroup3.add(yellowPeg3); // add the radio buttons to the group
        btnGroup3.add(bluePeg3);
        btnGroup3.add(redPeg3);
        btnGroup3.add(greenPeg3);    
        btnGroup3.add(whitePeg3);
        
        //Group the buttons.
        pegPanel3 = new JPanel(new GridLayout(0, 1));
        pegPanel3.setBorder(BorderFactory.createTitledBorder("Peg 3"));
        pegPanel3.add(yellowPeg3);
        pegPanel3.add(bluePeg3);
        pegPanel3.add(redPeg3);
        pegPanel3.add(greenPeg3);
        pegPanel3.add(whitePeg3);
        
        allPegs.add(pegPanel3, BorderLayout.EAST);
        
        contentPane.add(allPegs, BorderLayout.EAST); //Add everything to content pane.
    }
    
    /**
     * This helper method tells me which RadioButtons have been selected, and instead of returning an array of radio buttons, only returns their text.
     * @return The text of the radio buttons that have been selected.
     */
    private String[] getSelected()
    {
        int k = 0;
        for (int i = 0; i < allButtons.size() && k < PEGS; i++)
            {
              if (allButtons.get(i).isSelected() == true)
                 {
                  selectedButtons[k] = allButtons.get(i).getText();
                  k++;
                 }
            }
        
        return selectedButtons;
    }
    
    /**
     * This method randommizes the selection of the radio buttons so that each game is different. 
     */
    private void randomize()
    {
       
        for (int i = 0; i < allButtons.size(); i++)
            {
                int num = new Random().nextInt(allButtons.size());
                String[] selected = getSelected();
                
                while(isFound(selected, allButtons.get(num).getText())) //Pegs can't be in right place at the start and no duplicates              
                     num = new Random().nextInt(allButtons.size());
                           
                allButtons.get(num).setSelected(true);

            }
        
        
    }
    
    /**
     * Processes the press of the Reset button, and Edit>Reset. Resets the game.
     */
    private void reset()
    {
        textArea.setText(null); //Clear results area
        randomize(); //Set up new radio buttons.
        engine = new Engine(getSelected()); //make a new game
        textArea.append("A new set of pegs has been made \n"); //Add some text so the user knows what's going on.
        counter = 1; //Reset my counter. 
    }
    
    /**
     * Process the Help>About menu item.
     */
    private void helpAbout()
    {
        dialogImage = createImageIcon("paper.jpg",      //This is a picture of Clippy, the famous Microsft assistant 
                                 "Microsoft Paperclip");
        JOptionPane.showMessageDialog(  frame,                  // frame in which to display
                                        "About Mastermind " +         // body of dialog
                                        VERSION +               // version number
                                        "\n\nName: Mastermind\n" + "Version: " + VERSION + "\n" + "Created: 5.15.2014" + "\n" + "Author: Ryan King",   // Clippy's phrase
                                        "About Mastermind",     // message dialog title
                                        JOptionPane.INFORMATION_MESSAGE, // receiver
                                        dialogImage);           // message's icon
    }
    /**
     * Process the Help>How to Play menu item.
     */
    private void helpHowToPlay()
    {
        dialogImage = createImageIcon("paper.jpg",
                                 "Microsoft Paperclip");
        JOptionPane.showMessageDialog(  frame,                  // frame in which to display
                                        "Mastermind " +         // body of dialog
                                        VERSION +               // version number
                                        "\n\nThe computer has chosen a pattern of three pegs. There are no duplicates. It may take you a while\n"
                                            + "to figure out the code. You, the user, tries to guess the pattern, in both order and color, with \n"
                                            + "as many guesses as it takes. However, it shouldn’t take forever since there are only three colors.\n"
                                            + "Each guess is made by pressing one of the buttons in it’s “peg” slot, then press \"Check\". Then, \n"
                                            + "computer provides feedback by saying if any of the colors chosen were correct, and if any of the \n"
                                            + "pegs guessed are correct. However, the computer won’t tell which you which peg or color is correct.\n"
                                            + "Guessing commences until the user has given up and presses “Answer”, or the user has answered correctly.\n"
                                            + "Press “Reset” to start a new game.",   //Clippy's description
                                        "Clippy Says...",     // message dialog title
                                        JOptionPane.INFORMATION_MESSAGE, // receiver
                                        dialogImage);           // message's icon
    }
    
    /** 
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected ImageIcon createImageIcon(String path, String description) 
    {
        java.net.URL imgURL = getClass().getResource(path);     // look for file in current path (project folder)
        if (imgURL != null) 
           {
               return new ImageIcon(imgURL, description);       // return the image as an anonymous Icon object
           } 
        else 
           {
               System.err.println("Couldn't find file: " + path);   // ruh-rho Shaggy!
               return null;
           }
    }
    
    /**
     * This a simple boolean method that just checks if a string is an array.
     * @param arr The string array we want to use
     * @param target The string we want to verify
     * @return boolean True if target is in array, false otherwise.
     */
    private static boolean isFound(String[] arr, String target)
    {
       int x = 0;
       while (x < arr.length && arr[x] != null)
             {
                 if(target.equals(arr[x]))
                    return true;
                 x++;
             }
       
       return false;
    }
}



