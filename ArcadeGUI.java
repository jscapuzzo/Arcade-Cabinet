import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import java.awt.BorderLayout;

import java.awt.Dimension;

import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class ArcadeGUI 
{
	private static BorderLayout layoutDesk;
	private static BorderLayout layoutFrame;
	private JDesktopPane desktop;
	private JFrame frame;
	private JInternalFrame gameFrame;
	private JMenuBar menuBar;
	private JToolBar themeToolBar;
	private JToolBar gameChoiceToolBar;
	private PaddleGame game1;
	private WormGame game2;
	private SpaceGame game3;
	private TetrisGame game4;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					ArcadeGUI window = new ArcadeGUI();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws InterruptedException 
	 */
	public ArcadeGUI() throws InterruptedException 
	{	
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws InterruptedException 
	 */
	private void initialize() throws InterruptedException 
	{
		layoutFrame = new BorderLayout(50, 50);
		layoutDesk = new BorderLayout(0, 0);
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(layoutFrame);
		
		desktop = new JDesktopPane();
		desktop.setLayout(layoutDesk);
		
		gameFrame = new JInternalFrame("Main Menu");
		//desktop.add(gameFrame);
		frame.getContentPane().add(gameFrame, layoutFrame.CENTER);
		
		themeToolBar = new JToolBar();
		themeToolBar.setFont(new Font("Arial", Font.PLAIN, 15));
		themeToolBar.setOrientation(SwingConstants.VERTICAL);
		//themeToolBar.setBounds(23, 60, 136, 223);
		
		gameChoiceToolBar = new JToolBar();
		gameChoiceToolBar.setOrientation(SwingConstants.VERTICAL);
		gameChoiceToolBar.setFont(new Font("Arial", Font.PLAIN, 15));
		//gameChoiceToolBar.setBounds(23, 276, 136, 240);
		
		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial", Font.BOLD, 12));
		
		initGameFrame();
		initToolBars();
		initMenuBar();
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * Initialize the menu bar that allows users to see scores and instructions.
	 */
	private void initMenuBar() 
	{
		JMenu fileMenu = new JMenu("Help");
		 
		JMenuItem help = new JMenuItem("Instructions");
		help.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("b");
				JFrame helpBox = new JFrame("How to Play");
				helpBox.setSize(300, 300);
				helpBox.setLocationRelativeTo(null);
				helpBox.setResizable(false);
				helpBox.setVisible(true);
			}
		});
		
		fileMenu.add(help);
		fileMenu.setVisible(true);
		
		menuBar.add(fileMenu);
		menuBar.setMaximumSize(new Dimension(551, 200));
		layoutFrame.setVgap(10);
		frame.getContentPane().add(menuBar, layoutFrame.NORTH);
		
	}

	/**
	 * Initialize the frame where games are played.
	 * @throws InterruptedException
	 */
	private void initGameFrame() throws InterruptedException
	{
		gameFrame.setFocusable(true);
        gameFrame.setFocusTraversalKeysEnabled(false);
		
		gameFrame.setPreferredSize(new Dimension(528, 551));
		gameFrame.setMaximumSize(new Dimension(528, 551));
		gameFrame.setMinimumSize(new Dimension(528, 551));
		gameFrame.setMaximizable(true);
		gameFrame.setVisible(true);
		frame.getContentPane().add(gameFrame, layoutFrame.CENTER);
	}
	
	/**
	 * Initialize the toolbars that allow users to switch themes and games
	 */
	private void initToolBars()
	{
		
		JLabel lblSelectAGame = new JLabel("Select a game:");
		lblSelectAGame.setFont(new Font("Arial", Font.BOLD, 15));
		gameChoiceToolBar.add(lblSelectAGame);
		
		final JRadioButton rdbtnGame1 = new JRadioButton("Paddle Game");
		rdbtnGame1.setToolTipText("Game1");
		rdbtnGame1.setFont(new Font("Arial", Font.PLAIN, 15));
		gameChoiceToolBar.addSeparator();
		gameChoiceToolBar.add(rdbtnGame1);
		
		final JRadioButton rdbtnGame2 = new JRadioButton("Worm Game");
		rdbtnGame2.setFont(new Font("Arial", Font.PLAIN, 15));
		gameChoiceToolBar.addSeparator();
		gameChoiceToolBar.add(rdbtnGame2);
		
		final JRadioButton rdbtnGame3 = new JRadioButton("Space Game");
		rdbtnGame3.setFont(new Font("Arial", Font.PLAIN, 15));
		gameChoiceToolBar.addSeparator();
		gameChoiceToolBar.add(rdbtnGame3);
		
		final JRadioButton rdbtnGame4 = new JRadioButton("Tetris Game");
		rdbtnGame4.setFont(new Font("Arial", Font.PLAIN, 15));
		gameChoiceToolBar.addSeparator();
		gameChoiceToolBar.add(rdbtnGame4);
		
		JButton btnSubmitChoice = new JButton("Submit");
		
		btnSubmitChoice.setFont(new Font("Arial", Font.PLAIN, 15));
		gameChoiceToolBar.addSeparator();
		gameChoiceToolBar.addSeparator();
		gameChoiceToolBar.add(btnSubmitChoice);
		
		ButtonGroup choiceGroup = new ButtonGroup();
		choiceGroup.add(rdbtnGame1);
		choiceGroup.add(rdbtnGame2);
		choiceGroup.add(rdbtnGame3);
		choiceGroup.add(rdbtnGame4);
		
		btnSubmitChoice.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) 
			{
				if(rdbtnGame1.isSelected())
				{
					register(rdbtnGame1);
				}
				else if(rdbtnGame2.isSelected())
				{
					register(rdbtnGame2);
				}
				else if(rdbtnGame3.isSelected())
				{
					register(rdbtnGame3);
				}
				else if(rdbtnGame4.isSelected())
				{
					register(rdbtnGame4);
				}
			}
		});

		themeToolBar.setBorder(new EmptyBorder(0, 16, 0, 16));
		gameChoiceToolBar.setBorder(new EmptyBorder(0, 16, 0, 16));
		frame.getContentPane().add(themeToolBar, layoutFrame.WEST);
		frame.getContentPane().add(gameChoiceToolBar, layoutFrame.EAST);
	}
	
	/**
	 * Helper method that switches games.
	 * Created to create a cleaner initToolBars() method 
	 * @param button
	 */
	private void register(JRadioButton button)
	{
		if(button.getText().equals("Paddle Game"))
		{
			try 
			{
				game1 = new PaddleGame();
			} 
			catch (InterruptedException e) 
			{
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
			
			if(game3 != null)
			{
				gameFrame.removeKeyListener(game3);
				gameFrame.remove(game3);
				game3.stop();
			}
			
			if(game4 != null)
			{
				gameFrame.removeKeyListener(game4);
				gameFrame.remove(game4);
				game4.stop();
			}
			
			gameFrame.addKeyListener(game1);
			gameFrame.getContentPane().add(game1);
			gameFrame.revalidate();
			gameFrame.pack();
			gameFrame.setVisible(true);
		}
		else if(button.getText().equals("Worm Game"))
		{
			game2 = new WormGame();
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
			}
			
			if(game3 != null)
			{
				gameFrame.removeKeyListener(game3);
				gameFrame.remove(game3);
				game3.stop();
			}
			
			if(game4 != null)
			{
				gameFrame.removeKeyListener(game4);
				gameFrame.remove(game4);
				game4.stop();
			}
			
			gameFrame.addKeyListener(game2);
			gameFrame.getContentPane().add(game2);
			gameFrame.revalidate();
			gameFrame.pack();
			gameFrame.setVisible(true);
		}
		else if(button.getText().equals("Space Game"))
		{
			game3 = new SpaceGame();
			gameFrame.setVisible(false);
			gameFrame.setTitle("Space Game");
			gameFrame.setFocusable(true);
			gameFrame.requestFocusInWindow();
			gameFrame.setFocusTraversalKeysEnabled(false);
			
			if(game1 != null)
			{
				gameFrame.removeKeyListener(game1);
				gameFrame.remove(game1);
				game1.stop();
			}
			
			if(game2 != null)
			{
				gameFrame.removeKeyListener(game2);
				gameFrame.remove(game2);
				game2.stop();
			}
			
			if(game4 != null)
			{
				gameFrame.removeKeyListener(game4);
				gameFrame.remove(game4);
				game4.stop();
			}
			
			gameFrame.addKeyListener(game3);
			gameFrame.getContentPane().add(game3);
			gameFrame.revalidate();
			gameFrame.pack();
			gameFrame.setVisible(true);
		}
		else if(button.getText().equals("Tetris Game"))
		{
			game4 = new TetrisGame();
			gameFrame.setVisible(false);
			gameFrame.setTitle("Tetris Game");
			gameFrame.setFocusable(true);
			gameFrame.requestFocusInWindow();
			gameFrame.setFocusTraversalKeysEnabled(false);
			
			if(game1 != null)
			{
				gameFrame.removeKeyListener(game1);
				gameFrame.remove(game1);
				game1.stop();
			}
			
			if(game2 != null)
			{
				gameFrame.removeKeyListener(game2);
				gameFrame.remove(game2);
				game2.stop();
			}
			
			if(game3 != null)
			{
				gameFrame.removeKeyListener(game3);
				gameFrame.remove(game3);
				game3.stop();
			}
			
			gameFrame.addKeyListener(game4);
			gameFrame.getContentPane().add(game4);
			gameFrame.revalidate();
			gameFrame.pack();
			gameFrame.setVisible(true);
		}
	}

}
