package Login_s;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


//@SuppressWarnings("serial")
public class Minefield extends JPanel {
	
	
	private static final int
	MAX_WIDTH			= 1000,
	MAX_HEIGHT			=  600,
	BORDER_SIZE			=    0,
	CELL_SPACING		=    0,
	CELL_BORDER_SIZE	=    0;
		
	private static final Color
	BORDER_COLOR					= new Color( 16, 32, 64),
	CELL_SPACING_COLOR				= new Color(  0,  0,  0),
	CELL_BORDER_COLOR				= new Color(112,184,255),
	CELL_TEXT_COLOR					= new Color( 64,128,255),
	CELL_COLOR                                      = new Color(128,192,255),
	CELL_MOUSEOVER_COLOR                            = new Color(144,200,255),
	CELL_SELECTED_COLOR				= new Color(160,208,255),
	CELL_REVEALED_COLOR				= new Color(192,224,255),
	CELL_REVEALED_MOUSEOVER_COLOR                   = new Color(208,232,255),
	CELL_REVEALED_SELECTED_COLOR                    = new Color(224,240,255),
	CELL_FAILURE_COLOR				= new Color(128,  0,  0);
	
	private static final int
	EMPTY_CELL		=  0,
	FLAGGED_CELL		=  9,
	BROKEN_FLAG_CELL	= 10,
	MARKED_CELL		= 11,
	MINE_CELL		= 12,
	ACTIVE_MINE_CELL	= 13;
	
	private static final String[]
	CELL_IMAGE_FILEPATHS = {"images/emptyCell.png",
				"images/near1Cell.png",
				"images/near2Cell.png",
				"images/near3Cell.png",
				"images/near4Cell.png",
				"images/near5Cell.png",
				"images/near6Cell.png",
				"images/near7Cell.png",
				"images/near8Cell.png",
				"images/flaggedCell.png",
				"images/brokenFlagCell.png",
				"images/markedCell.png",
				"images/mineCell.png",
				"images/activeMineCell.png"	};
	
	private static final String[]
	CELL_NAMES = {	 "", "1", "2", "3", "4", "5", "6", "7", "8",
					"!", "!", "?", "M", "M"	};
	
	private static final Icon[]
	CELL_ICONS = new Icon[CELL_IMAGE_FILEPATHS.length];
	
	
	/** A simple interface to allow receiving game events.
	 *  There are four enumerated game events of type MinefieldEvent. */
	public interface MinefieldListener {
		void handleMinefieldEvent(MinefieldEvent event);
	}
	
	
	/** Enumeration representing a minefield event
	 *  used by the MinefieldListener interface. */
	public enum MinefieldEvent {
		FIRST_CLICK, FLAG_CHANGE, MINE_CLICKED, MINEFIELD_CLEARED;
	}
	
		
	private MinefieldListener listener; 
	private Cell[][] cells;
	private int cellSize;
	private boolean gameStarted;
	private boolean gameOver;
	private int flagCount;
	private int mineCount;
	private int clearedCellCount;
	private Cell firstRevealEmptyCell;
	
	/** Creates and initializes a Minefield instance.
	 *  Requires a MinefieldListener to be provided. */
	Minefield(MinefieldListener theListener) {
		super();
		listener = theListener;
		setBorder(BorderFactory.createLineBorder(BORDER_COLOR, BORDER_SIZE));
		setBackground(CELL_SPACING_COLOR);
	}
	
	
	/** Creates a new game. If this is the first game,
	 *  since no size and mine number is specified, default values are used.
	 *  Default size is 10 rows and 10 columns with 10 mines.
	 *  If this is not the first game,
	 *  then the same dimensions and mine number is used. */
	public void createNewGame() {
		if (cells == null)
			createNewGame(10, 10, 10);
		else
			createNewGame(cells.length, cells[0].length, mineCount);
	}
	
	/** Creates a new game with given number of rows, colums, and mines.
	 *  If current minefield has the same parameters, cells are simply reset;
	 *  otherwise, old cells are removed, new cells are created and
	 *  sized properly, and minefield JPanel itself is resized. */
	public void createNewGame(int rows, int columns, int mines) {
		if (rows <= 0 || columns <= 0 || mines <= 0)
			return;
		gameStarted = false;
		gameOver = false;
		flagCount = 0;
		clearedCellCount = 0;
		if (cells != null && cells.length == rows && cells[0].length == columns)
			resetCells();
		else {
			removeAll();
			setLayout(new GridLayout(rows, columns, CELL_SPACING,CELL_SPACING));
			setProperSizing(rows, columns);
			loadProperlySizedImages(cellSize);
			createCells(rows, columns);
		}
		addMinesRandomly(mines);
		mineCount = mines;
	}
	
	/** All of the cells are revealed and the game is over. */
	public void giveUp() {
		if (!gameOver) {
			gameOver = true;
			updateAllCells();
		}
	}
	
	/** Returns the curent number of flags in the minefield. */
	public int getFlagCount() {
		return flagCount;
	}
	
	/** Returns the current number of mines in the minefield. */
	public int getMineCount() {
		return mineCount;
	}
	
	/** When a flag is removed from a cell, that cell calls this method.
	 *  The flag count is decremented and the MinefieldListener is notified. */
	private void flagRemoved() {
		flagCount--;
		listener.handleMinefieldEvent(MinefieldEvent.FLAG_CHANGE);
	}
	
	/** When a flag is added to a cell, that cell calls this method.
	 *  The flag count is incremented and the MinefieldListener is notified.
	 *  If the game has not yet started,
	 *  then the game is stared and the MinefieldListener is notified. */
	private void flagAdded() {
		if (!gameStarted && !gameOver) {
			gameStarted = true;
			listener.handleMinefieldEvent(MinefieldEvent.FIRST_CLICK);
		}
		flagCount++;
		listener.handleMinefieldEvent(MinefieldEvent.FLAG_CHANGE);
	}
	
	/** When a cell is revealed, that cell calls this method.
	 *  The cleared cell count is incremented and the game is checked
	 *  for a winning condition.
	 *  If the game has not yet started,
	 *  then the game is stared and the MinefieldListener is notified. */
	private void cellCleared() {
		if (!gameStarted && !gameOver) {
			gameStarted = true;
			listener.handleMinefieldEvent(MinefieldEvent.FIRST_CLICK);
		}
		clearedCellCount++;
		checkForWin();
	}
	
	/** When a cell is revealed and contains a mine,
	 *  that cell calls this method. The game is set to be over.
	 *  All Cells are revealed, and the MinefieldListener is notified. */
	private void kaboom() {
		gameOver = true;
		updateAllCells();		
		listener.handleMinefieldEvent(MinefieldEvent.MINE_CLICKED);
	}
	
	/** If the game is not yet over, a check for a winning condition is done.
	 *  The winning condition is met when the number of uncleared
	 *  equals the total number of mines.
	 *  If the winning condition is met, the game is set to be over,
	 *  all cells are revealed, and the MinefieldListener is notified. */
	private void checkForWin() {
		if (!gameOver && cells.length * cells[0].length - clearedCellCount == mineCount) {
			gameOver = true;
			updateAllCells();
			listener.handleMinefieldEvent(MinefieldEvent.MINEFIELD_CLEARED);
		}
	}
	
	/** If the game is over, each cell is revealed; otherwise,
	 *  each cell's color and icon is updated based on its current state. */
	private void updateAllCells() {
		for (int r = 0; r < cells.length; r++)
			for (int c = 0; c < cells[0].length; c++) {
				if (gameOver)
					cells[r][c].gameOverReveal();
				else {
					cells[r][c].updateColor();
					cells[r][c].updateIcon();
				}
			}
	}
	
	/** Calculates the preferred size of the minefield's JPanel
	 *  and the size of each cell, according to the total number of rows
	 *  and columns, and according to the defined MAX_WIDTH and MAX_HEIGHT
	 *  of the JPanel. */
	private void setProperSizing(int rows, int columns) {
		int nonCellWidth = (2 * BORDER_SIZE) + (columns - 1) * CELL_SPACING;
		int nonCellHeight = (2 * BORDER_SIZE) + (rows - 1) * CELL_SPACING;
		int cellSizeA = (MAX_WIDTH - nonCellWidth) / columns;
		int cellSizeB = (MAX_HEIGHT - nonCellHeight) / rows;
		cellSize = Math.min(cellSizeA, cellSizeB);
		int width = cellSize * columns + nonCellWidth;
		int height = cellSize * rows + nonCellHeight;
		setPreferredSize(new Dimension(width, height));
	}
	
	/** Loads all icons used by cells
	 *  and resizes the icons to the given cell size. */
	private void loadProperlySizedImages(int cellSize) {
		for (int i = 0; i < CELL_ICONS.length; i++)
			try {
				CELL_ICONS[i] = null;
				CELL_ICONS[i] = new ImageIcon(
					ImageIO.read(new File(CELL_IMAGE_FILEPATHS[i]))
					.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
			} catch (IOException e) { }
	}
	
	/** Populates the minefield's JPanel with new cells
	 * based on a given number of rows and columns. */
	private void createCells(int rows, int columns) {
		cells = new Cell[rows][columns];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < columns; c++) {
				cells[r][c] = new Cell(r, c);
				add(cells[r][c]);
			}
	}
	
	/** Adds a given number of mines to current cells randomly. */
	private void addMinesRandomly(int mines) {
		int rows = cells.length;
		int cols = cells[0].length;
		Integer[] index = new Integer[rows * cols];
		for (int i = 0; i < index.length; i++)
			index[i] = i;
		Collections.shuffle(Arrays.asList(index));
		for (int m = 0; m < mines; m++)
			cells[index[m] / cols][index[m] % cols].mine = true;
		firstRevealEmptyCell = cells[index[mines] / cols][index[mines] % cols];
	}
	
	/** Iterates through all of the current cells
	 * and resets them to a new game state. */
	private void resetCells() {
		for (int r = 0; r < cells.length; r++)
			for (int c = 0; c < cells[0].length; c++)
				cells[r][c].reset();
	}	
	
	/** Given a row and a column, returns a list of neighboring cells. */
	private ArrayList<Cell> getNeighbors(int row, int col) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>(8);
		for (int r = row - 1; r <= row + 1; r++)
			for (int c = col - 1; c <= col + 1; c++)
				if (isValidCellLocation(r, c) && (r != row || c != col))
					neighbors.add(cells[r][c]);
		return neighbors;
	}
	
	/** Returns true if a given row and column is a valid location
	 *  of the minefield, false otherwise. */
	private boolean isValidCellLocation(int r, int c) {
		return (r >= 0 && r < cells.length && c >= 0 && c < cells[0].length);
	}
		
	
	
	private class Cell extends JLabel implements MouseListener {
		
	
		private final int R, C;
		private boolean mouseOver, selected, revealed, flagged, marked;
		private boolean mine, exploded;
		private boolean neighborsSelected;
		private int nearbyMines;
		
		
		Cell(int r, int c) {
			super();
			R = r;
			C = c;
			addMouseListener(this);
			setHorizontalAlignment(JLabel.CENTER);
			setOpaque(true);
			setForeground(CELL_TEXT_COLOR);
			setFont(new Font(getFont().getName(), Font.BOLD, cellSize));
			reset();
		}
		
		
		private void reset() {
			setBorder(BorderFactory.createLineBorder(
				CELL_BORDER_COLOR, CELL_BORDER_SIZE));
			mouseOver = selected = revealed = flagged = marked = false;
			mine = exploded = false;
			neighborsSelected = false;
			nearbyMines = -1;
			updateColor();
			updateIcon();
		}
		// Clear all possible GameSquare objects up to the borders or until the GameSquare has 1 or more mines
		private void reveal() {
			if (revealed || flagged || marked)
				return;
			revealed = true;
			if  (!gameStarted && mine) {
				mine = false;
				firstRevealEmptyCell.mine = true;
			}
			if (mine) {
				exploded = true;
				kaboom();
			} else {
				cellCleared();
				if (getNearbyMines() == 0) {
					mouseOver = false;
					revealNeighbors();
				}
				updateColor();
				updateIcon();
			}
		}
		
		private int getNearbyMines() {
			if (nearbyMines < 0) {
				nearbyMines = 0;
				for (Cell neighbor : getNeighbors(R, C))
					if (neighbor.mine)
						nearbyMines++;
			}
			return nearbyMines;
		}
		
		private void revealNeighbors() {
			for (Cell neighbor : getNeighbors(R, C))
				neighbor.reveal();
		}
		
		private void forceRevealNeighbors() {
			int flaggedNeighbors = 0;
			ArrayList<Cell> neighbors = getNeighbors(R, C);
			for (Cell neighbor : neighbors) {
				if (!neighbor.revealed) {
					neighbor.selected = false;
					neighbor.updateColor();
				}
				if ((!neighbor.revealed && neighbor.flagged) || (neighbor.revealed && neighbor.mine))
					flaggedNeighbors++;
			}
			if (getNearbyMines() == flaggedNeighbors) {
				for (Cell neighbor : neighbors) {
					neighbor.marked = false;
					if (!neighbor.revealed && neighbor.mine && !neighbor.flagged) {
						neighbor.exploded = true;
						kaboom();
					}
				}
				for (Cell neighbor : neighbors)
					neighbor.reveal();
			}
		}
		
		private void gameOverReveal() {
			if (!revealed && (mine || flagged)) {
				marked = false;
				revealed = true;
				if (mine && !flagged)
					flagAdded();
				cellCleared();
			}
			updateColor();
			updateIcon();
		}

		private void updateColor() {
			if (exploded || (revealed && !mine && flagged))
				setBackground(CELL_FAILURE_COLOR);
			else if (selected)
				if (revealed)	setBackground(CELL_REVEALED_SELECTED_COLOR);
				else		setBackground(CELL_SELECTED_COLOR);
			else if (mouseOver)
				if (revealed)	setBackground(CELL_REVEALED_MOUSEOVER_COLOR);
				else		setBackground(CELL_MOUSEOVER_COLOR);
			else if (!revealed || mine && !flagged)
						setBackground(CELL_COLOR);
			else			setBackground(CELL_REVEALED_COLOR);
		}
		
		private void updateIcon() {
			int cellType;
			if (revealed)
				if (exploded)		cellType = ACTIVE_MINE_CELL;
				else if (mine)		cellType = MINE_CELL;
				else if (flagged)	cellType = BROKEN_FLAG_CELL;
				else if (getNearbyMines() > 0)	cellType = nearbyMines;
				else							cellType = EMPTY_CELL;
			else if (flagged)	cellType = FLAGGED_CELL;
			else if (marked)	cellType = MARKED_CELL;
			else				cellType = EMPTY_CELL;
			
			boolean textMode = CELL_ICONS[cellType] == null;
			setText(textMode ? CELL_NAMES[cellType] : null);
			setIcon(textMode ? null : CELL_ICONS[cellType]);
			setBorder(textMode && !revealed ? BorderFactory.createRaisedBevelBorder() : null);
		}
				
		
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {
			if (!gameOver && (mouseOver || !gameStarted)) {
				selected = true;
				updateColor();
				if (e.getButton() != MouseEvent.BUTTON1)
					if (!revealed) {
						if (flagged) flagRemoved();
						flagged = !flagged && !marked;
						marked = !flagged && !marked;
						if (flagged) flagAdded();
						updateIcon();
					}
					else {
						neighborsSelected = true;
						for (Cell neighbor : getNeighbors(R, C))
							if (!neighbor.revealed) {
								neighbor.selected = true;
								neighbor.updateColor();
							}
					}
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (!gameOver && (mouseOver || !gameStarted)) {
				selected = false;
				updateColor();
				if (e.getButton() == MouseEvent.BUTTON1)
					reveal();
				else if (revealed)
					forceRevealNeighbors();
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			if (!gameOver && (!revealed || (!mine && getNearbyMines() > 0)))
			mouseOver = true;
			updateColor();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			mouseOver = false;
			selected = false;
			if (neighborsSelected) {
				neighborsSelected = false;
				for (Cell neighbor : getNeighbors(R, C))
					if (!neighbor.revealed) {
						neighbor.selected = false;
						neighbor.updateColor();
					}
			}
			updateColor();
		}
	}
}
