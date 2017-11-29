import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ArcadeGUI {

	private JDesktopPane desktop;
	private JFrame frame;
	private JInternalFrame gameFrame;
	private JPanel panel;
	private JToolBar themeToolBar;
	private JToolBar gameChoiceToolBar;
	private PaddleGame game1;
	private WormGame game2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArcadeGUI window = new ArcadeGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws InterruptedException 
	 */
	public ArcadeGUI() throws InterruptedException {
		
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws InterruptedException 
	 */
	private void initialize() throws InterruptedException 
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(50, 50));
		
		desktop = new JDesktopPane();
		desktop.setLayout(new BorderLayout(0,0));
		
		gameFrame = new JInternalFrame("Main Menu");
		desktop.add(gameFrame);
		//frame.getContentPane().add(gameFrame, BorderLayout.CENTER);
		
		themeToolBar = new JToolBar();
		themeToolBar.setFont(new Font("Arial", Font.PLAIN, 15));
		themeToolBar.setOrientation(SwingConstants.VERTICAL);
		//themeToolBar.setBounds(23, 60, 136, 223);
		
		
		gameChoiceToolBar = new JToolBar();
		gameChoiceToolBar.setOrientation(SwingConstants.VERTICAL);
		gameChoiceToolBar.setFont(new Font("Arial", Font.PLAIN, 15));
		//gameChoiceToolBar.setBounds(23, 276, 136, 240);
		
		initGameFrame();
		initToolBars();
		
		//frame.setMinimumSize(new Dimension(528, 551));
		frame.pack();
		frame.setLocationRelativeTo(null);
		//frame.setMinimumSize(null);
		frame.setVisible(true);
	}
	
	private void initGameFrame() throws InterruptedException
	{
		//gameFrame.setResizable(true);
		//desktop.add(gameFrame);
		//gameFrame.addKeyListener();
		gameFrame = new JInternalFrame();
        gameFrame.setFocusable(true);
        gameFrame.setFocusTraversalKeysEnabled(false);
		
		gameFrame.setPreferredSize(new Dimension(528, 551));
		gameFrame.setMaximumSize(new Dimension(528, 551));
		gameFrame.setMinimumSize(new Dimension(528, 551));
		gameFrame.setMaximizable(true);
		//gameFrame.add(panel);
		gameFrame.setVisible(true);
		frame.getContentPane().add(gameFrame, BorderLayout.CENTER);
	}
	
	private void initToolBars()
	{
		JLabel lblThemeLabel = new JLabel("Select a theme:");
		lblThemeLabel.setFont(new Font("Arial", Font.BOLD, 15));
		themeToolBar.add(lblThemeLabel);
		
		JRadioButton rdbtnClassicTheme = new JRadioButton("Classic");
		rdbtnClassicTheme.setFont(new Font("Tahoma", Font.PLAIN, 15));
		themeToolBar.add(rdbtnClassicTheme);
		
		JRadioButton rdbtnOceanTheme = new JRadioButton("Ocean");
		rdbtnOceanTheme.setFont(new Font("Arial", Font.PLAIN, 15));
		themeToolBar.addSeparator();
		themeToolBar.add(rdbtnOceanTheme);
		
		JRadioButton rdbtnSpaceTheme = new JRadioButton("Space");
		rdbtnSpaceTheme.setFont(new Font("Tahoma", Font.PLAIN, 15));
		themeToolBar.addSeparator();
		themeToolBar.add(rdbtnSpaceTheme);
		
		ButtonGroup themeGroup = new ButtonGroup();
		themeGroup.add(rdbtnSpaceTheme);
		themeGroup.add(rdbtnOceanTheme);
		themeGroup.add(rdbtnClassicTheme);
		
		JButton btnSubmitTheme = new JButton("Submit");
		btnSubmitTheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					SwingUtilities.updateComponentTreeUI(frame);  // update components
					frame.pack();
					//frame.setBounds(100, 100, 1130, 659);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		themeToolBar.addSeparator();
		themeToolBar.addSeparator();
		themeToolBar.add(btnSubmitTheme);
		btnSubmitTheme.setFont(new Font("Arial", Font.PLAIN, 15));
		
		JLabel lblSelectAGame = new JLabel("Select a game:");
		lblSelectAGame.setFont(new Font("Arial", Font.BOLD, 15));
		gameChoiceToolBar.add(lblSelectAGame);
		
		JRadioButton rdbtnGame1 = new JRadioButton("Paddle Game");
		rdbtnGame1.setToolTipText("Game1");
		rdbtnGame1.setFont(new Font("Arial", Font.PLAIN, 15));
		gameChoiceToolBar.addSeparator();
		gameChoiceToolBar.add(rdbtnGame1);
		
		JRadioButton rdbtnGame2 = new JRadioButton("Worm Game");
		rdbtnGame2.setFont(new Font("Arial", Font.PLAIN, 15));
		gameChoiceToolBar.addSeparator();
		gameChoiceToolBar.add(rdbtnGame2);
		
		JRadioButton rdbtnGame3 = new JRadioButton("Game3");
		rdbtnGame3.setFont(new Font("Arial", Font.PLAIN, 15));
		gameChoiceToolBar.addSeparator();
		gameChoiceToolBar.add(rdbtnGame3);
		
		JButton btnSubmitChoice = new JButton("Submit");
		
		btnSubmitChoice.setFont(new Font("Arial", Font.PLAIN, 15));
		gameChoiceToolBar.addSeparator();
		gameChoiceToolBar.addSeparator();
		gameChoiceToolBar.add(btnSubmitChoice);
		
		ButtonGroup choiceGroup = new ButtonGroup();
		choiceGroup.add(rdbtnGame1);
		choiceGroup.add(rdbtnGame2);
		choiceGroup.add(rdbtnGame3);
		
		btnSubmitChoice.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnGame1.isSelected())
				{
					register(rdbtnGame1);
				}
				else if(rdbtnGame2.isSelected())
				{
					register(rdbtnGame2);
				}
			}
		});

		frame.getContentPane().add(themeToolBar, BorderLayout.LINE_START);
		frame.getContentPane().add(gameChoiceToolBar, BorderLayout.LINE_END);
	}
	
	private void register(JRadioButton button)
	{
		//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		if(button.getText().equals("Paddle Game"))
		{
			try {
				game1 = new PaddleGame();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			gameFrame.setTitle("Paddle Game");
			gameFrame.setFocusable(true);
			gameFrame.requestFocus();
			gameFrame.setFocusTraversalKeysEnabled(false);
			
			if(game2 != null)
			{
				gameFrame.removeKeyListener(game2);
				gameFrame.remove(game2);
				game2.stop();
			}
			
			gameFrame.addKeyListener(game1);
			gameFrame.add(game1);
			gameFrame.revalidate();
			gameFrame.pack();
			gameFrame.setVisible(true);
		}	
		else if(button.getText().equals("Worm Game"))
		{
			game2 = new WormGame();
			//gameFrame.removeAll();
			gameFrame.setVisible(false);
			gameFrame.setTitle("Worm Game");
			gameFrame.setFocusable(true);
			gameFrame.requestFocusInWindow();
			gameFrame.setFocusTraversalKeysEnabled(false);
			
			if(game1 != null)
			{
				gameFrame.removeKeyListener(game1);
				gameFrame.remove(game1);
				game1.stop();
				//game1.removeAll();
			}
			
			gameFrame.addKeyListener(game2);
			gameFrame.add(game2);
			gameFrame.revalidate();
			gameFrame.pack();
			gameFrame.setVisible(true);
		}
		//SwingUtilities.updateComponentTreeUI(frame);  // update components
		//frame.pack();
		//frame.setBounds(100, 100, 1130, 659);
		//frame.setLocationRelativeTo(null);
		//frame.setVisible(true); 
	}

}
