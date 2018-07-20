package Login_s;

import Login_s.Minefield.MinefieldEvent;
import Login_s.Minefield.MinefieldListener;

import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;


@SuppressWarnings("serial")
public class Minesweeper extends JFrame implements ActionListener, MinefieldListener {
	
	/** Static main method. Creates an instance of Minesweeper. */
	//public static void main(String[] args) {
	//	new Minesweeper();
	//}

	private static final String
	WINDOW_TITLE = "Minesweeper";
	
	
	private enum GameDifficulty {
            BEGINNER(10,10,10), INTERMEDIATE(15,15,70), EXPERT(20,20,120);
            public final int rows, columns, mines;
            GameDifficulty(int r, int c, int m) { rows = r; columns = c; mines = m; }
	};
	//variables
	Minefield game;
	GameDifficulty	currentDifficulty;
	Highscores[] highscores;
	DisplayPanel display;
	JMenuItem menuReset, menuTopTen, menuClearTopTen, menuExit, menuHelp, menuAbout,
		  menuBeginner, menuIntermediate, menuExpert;
	
	
	/** Creates an instance of the minesweeper game, in its own window. */
	Minesweeper() {
		super(WINDOW_TITLE);
		game = new Minefield(this);
		initializeHighscores();
		setupGUI();
		startNewGame(GameDifficulty.BEGINNER);
	}
	
	/** Creates a Highscores object for each difficulty.
	 *  Each highscores list is loaded from a file, if it exists. */
	private void initializeHighscores() {
		highscores = new Highscores[GameDifficulty.values().length];
		for (GameDifficulty d : GameDifficulty.values()) {
			Highscores hs = new Highscores(10);
			hs.loadFromFile("highscores/" + d.toString() + ".txt");
			highscores[d.ordinal()] = hs;
			hs.setHighscoreFormat(hs.new HighscoreFormat() {
				public String format(Highscores.Highscore hs) {
					return String.format("%02d:%02d:%02d - %s", hs.SCORE / 3600, hs.SCORE / 60 % 60, hs.SCORE % 60, hs.NAME);
				}
			});
		}
	}
	
	/** Starts a new game of minesweeper with a given difficulty.
	 *  If new difficulty is different from the current one,
	 *  the window is resized to accomodate the new board dimensions.
	 *  The display panel clock and mine counter is also reset. */
	private void startNewGame(GameDifficulty difficulty) {
		if (difficulty == null)
			game.createNewGame();
		else {
			game.createNewGame(difficulty.rows, difficulty.columns, difficulty.mines);
			if (currentDifficulty != difficulty) {
				currentDifficulty = difficulty;
				pack();
				setLocationRelativeTo(null);
			}
		}
		display.resetClock();
		display.setMines(game.getMineCount());
	}
	
	
	/** Handles all of the user click events, except the minefield grid,
	 *  which handles the game play clicks separately. */
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		 if (source == menuBeginner) startNewGame(GameDifficulty.BEGINNER);
		else if (source == menuIntermediate) startNewGame(GameDifficulty.INTERMEDIATE);
		else if (source == menuExpert) startNewGame(GameDifficulty.EXPERT);
		else if (source == menuReset) startNewGame(null);
		else if (source == display.getResetButton()) startNewGame(null);
		else if (source == display.getGiveUpButton()) 
                {
                    game.giveUp();
                    display.stopClock();
		}
		else if (source == menuTopTen) {
			Highscores hs = highscores[currentDifficulty.ordinal()];
			JOptionPane.showMessageDialog(this,
			hs.size() > 0 ? hs : "No highscores yet!",
			currentDifficulty + " TOP TEN HIGHSCORES",
			JOptionPane.PLAIN_MESSAGE); 
		}
		else if (source == menuClearTopTen) {
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Are you sure?", "Warning", JOptionPane.YES_NO_OPTION)) {
				Highscores hs = highscores[currentDifficulty.ordinal()];
				hs.clear();
				hs.saveToFile("highscores/" + currentDifficulty.toString() + ".txt");
				JOptionPane.showMessageDialog(this,
				currentDifficulty + " highscores cleared!",
				currentDifficulty + " TOP TEN HIGHSCORES",
				JOptionPane.PLAIN_MESSAGE);
			}
		}
		else if (source == menuExit) {
			System.exit(0);
		}
		else if (source == menuHelp) {
			JOptionPane.showMessageDialog(this, 
				"How to play\n"
				+ "- Avoid all mines and win!\n"
				+ "- Numbers tell you how many mines surround that tile.\n"
				+ "- Set flags to mark a mine and ?'s if you're unsure.\n"
				+ "- Get the fastest time to be in the top ten!",
				"Help", JOptionPane.PLAIN_MESSAGE);			
		}
		else if (source == menuAbout) {
			JOptionPane.showMessageDialog(this,
				"Version 1.0 \n"
				+ "Minesweeper project \n"
				+ "by Team Aryabhatta\n",
				"About", JOptionPane.PLAIN_MESSAGE);			
		}
	}
	
	/** Handles all gameplay events such as: first click on the minefield;
	 *  flag count change; mine expoded; and game won. */
	@Override
	public void handleMinefieldEvent(MinefieldEvent event) {
		if (event == Minefield.MinefieldEvent.FIRST_CLICK)
			display.startClock();
		else if (event == Minefield.MinefieldEvent.FLAG_CHANGE)
			display.setMines(game.getMineCount() - game.getFlagCount());
		else if (event == Minefield.MinefieldEvent.MINE_CLICKED)
			display.stopClock();
		else if (event == Minefield.MinefieldEvent.MINEFIELD_CLEARED) {
			int score = display.stopClock();
			Highscores hs = highscores[currentDifficulty.ordinal()];
			if (hs.isHighscore(score)) {
				String name = JOptionPane.showInputDialog(
					"Congratulations!\n"
					+ "Your score of " + score + " made it into TOP TEN!\n"
					+ "Please enter your name:");
				if (name == null)
					name = "no name";
				hs.add(name, score);
				JOptionPane.showMessageDialog(this, hs, currentDifficulty + " TOP TEN", JOptionPane.PLAIN_MESSAGE);
				hs.saveToFile("highscores/" + currentDifficulty.toString() + ".txt");
			}
		}
	}
	
	
	/** Initializes and sets up all GUI components. This method is called once
	 *  during the creation of the instance from the constructor. */
	private void setupGUI() {
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		initializeMenuBar();
		display = new DisplayPanel();
		display.getResetButton().addActionListener(this);
		display.getGiveUpButton().addActionListener(this);
		getContentPane().add(game);
		getContentPane().add(display);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/** Initializes the MenuBar and its items. This method is called once
	 *  during the creation of the instance from the setupGUI() method. */
	private void initializeMenuBar() {
		// Initialize menu items
		JMenuBar menu = new JMenuBar();
			JMenu gameMenu = new JMenu( "Game" );
				menuReset = new JMenuItem( "Reset" );
				JMenu pickGameMenu = new JMenu( "Pick Game" );
				menuBeginner = new JMenuItem( "Play Beginner" );
				menuIntermediate = new JMenuItem( "Play Intermediate" );
				menuExpert = new JMenuItem( "Play Expert" );
				menuTopTen = new JMenuItem( "Show Highscores" );
				menuClearTopTen = new JMenuItem( "Clear Highscores" );
				menuExit = new JMenuItem( "Exit" );
                                JMenu helpMenu = new JMenu( "Help" );
				menuHelp = new JMenuItem( "Help" );
				menuAbout = new JMenuItem( "About" );
		
		gameMenu.setFont(new Font(gameMenu.getFont().getName(), Font.BOLD, 20));
		helpMenu.setFont(new Font(helpMenu.getFont().getName(), Font.BOLD, 20));
		menuReset.setFont(new Font(menuReset.getFont().getName(), Font.BOLD, 20));
		pickGameMenu.setFont(new Font(pickGameMenu.getFont().getName(), Font.BOLD, 20));
		menuBeginner.setFont(new Font(menuBeginner.getFont().getName(), Font.BOLD, 20));
		menuIntermediate.setFont(new Font(menuIntermediate.getFont().getName(), Font.BOLD, 20));
		menuExpert.setFont(new Font(menuExpert.getFont().getName(), Font.BOLD, 20));
		menuTopTen.setFont(new Font(menuTopTen.getFont().getName(), Font.BOLD, 20));
		menuClearTopTen.setFont(new Font(menuClearTopTen.getFont().getName(), Font.BOLD, 20));
		menuExit.setFont(new Font(menuExit.getFont().getName(), Font.BOLD, 20));
		menuHelp.setFont(new Font(menuHelp.getFont().getName(), Font.BOLD, 20));
		menuAbout.setFont(new Font(menuAbout.getFont().getName(), Font.BOLD, 20));
		
		// Set mnemonics
		gameMenu.setMnemonic( KeyEvent.VK_G );
		helpMenu.setMnemonic( KeyEvent.VK_L );
		menuReset.setMnemonic( KeyEvent.VK_R );
		pickGameMenu.setMnemonic( KeyEvent.VK_P );
		menuBeginner.setMnemonic( KeyEvent.VK_B );
		menuIntermediate.setMnemonic( KeyEvent.VK_A );
		menuExpert.setMnemonic( KeyEvent.VK_E );
		menuTopTen.setMnemonic( KeyEvent.VK_T );
		menuClearTopTen.setMnemonic( KeyEvent.VK_C );
		menuExit.setMnemonic( KeyEvent.VK_X );
		menuHelp.setMnemonic( KeyEvent.VK_L );
		menuAbout.setMnemonic( KeyEvent.VK_A );
		
		// Assemble the menu
		setJMenuBar(menu);
			menu.add(gameMenu);
				gameMenu.add(menuReset);
				gameMenu.add(pickGameMenu);
					pickGameMenu.add(menuBeginner);
					pickGameMenu.add(menuIntermediate);
					pickGameMenu.add(menuExpert);
				gameMenu.add(menuTopTen);
				gameMenu.add(menuClearTopTen);
				gameMenu.add(menuExit);
			menu.add(helpMenu);
				helpMenu.add(menuHelp);
				helpMenu.add(menuAbout);
		
		// Register as action listener
		menuReset.addActionListener(this);
		menuBeginner.addActionListener(this);
		menuIntermediate.addActionListener(this);
		menuExpert.addActionListener(this);
		menuTopTen.addActionListener(this);
		menuClearTopTen.addActionListener(this);
		menuExit.addActionListener(this);
		menuHelp.addActionListener(this);
		menuAbout.addActionListener(this);
	}
}
