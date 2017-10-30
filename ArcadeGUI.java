import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JInternalFrame;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ButtonGroup;

public class ArcadeGUI {

	private JFrame frame;

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
	 */
	public ArcadeGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1130, 659);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	    //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    frame.setUndecorated(true);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(1005, 572, 113, 40);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		frame.getContentPane().setLayout(null);
		
		JToolBar themeToolBar = new JToolBar();
		themeToolBar.setFont(new Font("Arial", Font.PLAIN, 15));
		themeToolBar.setOrientation(SwingConstants.VERTICAL);
		themeToolBar.setBounds(23, 60, 136, 223);
		frame.getContentPane().add(themeToolBar);
		
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
		themeToolBar.addSeparator();
		themeToolBar.addSeparator();
		themeToolBar.add(btnSubmitTheme);
		btnSubmitTheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSubmitTheme.setFont(new Font("Arial", Font.PLAIN, 15));
		
		JInternalFrame gameFrame = new JInternalFrame("Main Menu");
		gameFrame.setBounds(318, 60, 507, 507);
		gameFrame.getContentPane().setForeground(new Color(0, 255, 255));
		gameFrame.setForeground(new Color(0, 255, 0));
		frame.getContentPane().add(gameFrame);
		gameFrame.setVisible(true);
		frame.getContentPane().add(btnExit);
		
		JToolBar gameChoiceToolBar = new JToolBar();
		gameChoiceToolBar.setOrientation(SwingConstants.VERTICAL);
		gameChoiceToolBar.setFont(new Font("Arial", Font.PLAIN, 15));
		gameChoiceToolBar.setBounds(23, 276, 136, 240);
		frame.getContentPane().add(gameChoiceToolBar);
		
		JLabel lblSelectAGame = new JLabel("Select a game:");
		lblSelectAGame.setFont(new Font("Arial", Font.BOLD, 15));
		gameChoiceToolBar.add(lblSelectAGame);
		
		JRadioButton rdbtnGame1 = new JRadioButton("Game1");
		rdbtnGame1.setToolTipText("Game1");
		rdbtnGame1.setFont(new Font("Arial", Font.PLAIN, 15));
		gameChoiceToolBar.addSeparator();
		gameChoiceToolBar.add(rdbtnGame1);
		
		JRadioButton rdbtnGame2 = new JRadioButton("Game2");
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
	}
}
