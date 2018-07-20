package Login_s;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Highscores {
	
	/** Number of highscores to keep */
	public final int MAX_SIZE;
	
	/** List of stored highscores */
	private final ArrayList<Highscore> highscores;
	
	/** Highscore formatting object used when toString() is invoked */
	private HighscoreFormat highscoreFormat;
	
	/** Creates a Highscores instance with a given maximum number of
	 *  highscores to keep. Uses a default HighscoreFormat instance (used
	 *  to format highscores in the toString() method). */
	Highscores(int size) {
		MAX_SIZE = size;
		highscores = new ArrayList<Highscore>(MAX_SIZE);
		highscoreFormat = new HighscoreFormat();
	}
	
	/** Adds a new highscore entry to the list,
	 *  using the given name and score, if isHighscore() returns true.
	 *  Returns true if a new highscore was added, false otherwise. */
	public boolean add(String name, int score) {
		if (isHighscore(score)) {
			highscores.add(new Highscore(name, score));
			Collections.sort(highscores);
			if (highscores.size() > MAX_SIZE)
				highscores.remove(MAX_SIZE);
			return true;
		}
		else return false;
	}
	
	/** Removes all highscore entries from the list. */
	public void clear() {
		highscores.clear();
	}
	
	/** Returns false if the highscores list is already full and the given score
	 *  is worse (numerically higher) than the worst highscore in the list;
	 *  otherwise, returns true. */
	public boolean isHighscore(int score) {
		return size() < MAX_SIZE || highscores.get(MAX_SIZE-1).SCORE > score;
	}
	
	/** Returns the current number of highscore entries in the list. */
	public int size() {
		return highscores.size();
	}
	
	/** Sets a user-provided HighscoreFormat instance to be used when
	 *  creating a string representation of highscore entries.
	 *  Currently this formatter is used only in the toString() method. */
	public void setHighscoreFormat(HighscoreFormat hsf) {
		highscoreFormat = hsf;
	}
	
	/** Loads highscores from a file located in the given filepath.
	 *  Loads up to MAX_SIZE highscore entries.
	 *  MAX_SIZE is specified in the constructor of this object. */
	public boolean loadFromFile(String filepath) {
		Scanner file = null;
		try { file = new Scanner(new FileReader(filepath));
		} catch (FileNotFoundException e) {	return false; }
		
		highscores.clear();
		String name = null;
		int score;
		while (file.hasNext() && highscores.size() < MAX_SIZE) {
			String next = file.next();
			try {
				score = Integer.parseInt(next);
				if (name != null) {
					highscores.add(new Highscore(name, score));
					name = null;
				}
			}
			catch (Exception e) {
				if (name == null)
					name = next;
				else
					name = name + next;
			}
		}
		file.close();
		Collections.sort(highscores);
		while (highscores.size() > MAX_SIZE)
			highscores.remove(MAX_SIZE);
		return true;
	}
	
	/** Saves highscores to a file located in the specified filepath.
	 *  Creates any necessary directories in the filepath that do not exist. */
	public boolean saveToFile(String filepath) {
		File dir = (new File(filepath)).getParentFile();
		if (dir != null)
			dir.mkdirs();
		PrintWriter file = null;
		try { file = new PrintWriter(filepath); }
		catch (Exception e) { return false; }
		
		StringBuilder sb = new StringBuilder();
		for (Highscore hs : highscores) {
			sb.append(hs.NAME);
			sb.append(' ');
			sb.append(hs.SCORE);
			sb.append(System.getProperty("line.separator"));
		}
		file.write(sb.toString());
		file.close();
		return true;
	}
	
	/** Returns a string that contains the list of all highscore entries.
	 *  Each highscore entry is formatted using the currently set
	 *  HighscoreFormat instance. If no format was set by the user,
	 *  the default HighscoreFormat instance is used. */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Highscore hs : highscores) {
			sb.append(highscoreFormat.format(hs));
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	
	/**
	 *Highscore class
	 *Represents one highscore entry.
	 *Stores a name and a score.
	 *Implements comparable interface for sorting purposes.
	 */
	public final class Highscore implements Comparable<Highscore> {
		
		public final String NAME;
		public final int SCORE;
		
		Highscore(String name, int score) {
			NAME = name;
			SCORE = score;
		}
		
		@Override
		public int compareTo(Highscore other) {
			return SCORE - other.SCORE;
		}
	}
	
	/**
	 *HighscoreFormat class
	 *Given a Highscore instance, uses the name and score information
	 * to format a desired string representation of the highscore entry.
	 */
	public class HighscoreFormat {
		public String format(Highscore hs) {
			return hs.NAME + ' ' + hs.SCORE;
		}
	}
}
