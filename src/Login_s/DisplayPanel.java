package Login_s;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class DisplayPanel extends JPanel {
	
	private static final String
	CLOCK_IMAGE_FILEPATH =	"images/clock.png",
	MINE_IMAGE_FILEPATH =	"images/mine.png";
	
	private static Icon
	CLOCK_ICON,
	MINE_ICON;
	
	private static final Color
	BACKGROUND_COLOR = new Color(32, 64, 128),
	CLOCK_LABEL_COLOR = new Color(32, 64, 128),
	MINE_LABEL_COLOR = new Color(32, 64, 128),
	LABEL_TEXT_COLOR = new Color(255, 255, 255);
	
	private static final int
	HEIGHT = 50;
	
	private JLabel
	clockLabel,
	mineLabel;

	private JButton
	resetButton,
	giveUpButton;
	
	Stopwatch stopwatch = new Stopwatch();
	Timer timer;

	/** Creates a DisplayPanel instance and initializes all GUI components. */
	DisplayPanel() {
		super();
		setOpaque(true);
		setBackground(BACKGROUND_COLOR);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		try {
			CLOCK_ICON = new ImageIcon(ImageIO.read(new File(CLOCK_IMAGE_FILEPATH)).getScaledInstance(HEIGHT - 8, HEIGHT - 8, Image.SCALE_SMOOTH));
			MINE_ICON = new ImageIcon(ImageIO.read(new File(MINE_IMAGE_FILEPATH)).getScaledInstance(HEIGHT - 2, HEIGHT - 2, Image.SCALE_SMOOTH));
		} catch (IOException e) {}
		
		clockLabel = new JLabel(" 00:00:00");
		clockLabel.setIcon(CLOCK_ICON);
		clockLabel.setOpaque(true);
		clockLabel.setBackground(CLOCK_LABEL_COLOR);
		clockLabel.setForeground(LABEL_TEXT_COLOR);
		clockLabel.setPreferredSize(new Dimension(240, HEIGHT));
		clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		clockLabel.setFont(new Font(getFont().getName(), Font.BOLD, HEIGHT - 10));
			
		mineLabel = new JLabel("10");		
		mineLabel.setIcon(MINE_ICON);
		mineLabel.setOpaque(true);
		mineLabel.setBackground(MINE_LABEL_COLOR);
		mineLabel.setForeground(LABEL_TEXT_COLOR);
		mineLabel.setPreferredSize(new Dimension(120, HEIGHT));
		mineLabel.setFont(new Font(getFont().getName(), Font.BOLD, HEIGHT - 10));

		giveUpButton = new JButton("Give Up");
		giveUpButton.setFont(new Font(getFont().getName(), Font.BOLD, 24));

		resetButton = new JButton("Reset");
		resetButton.setFont(new Font(getFont().getName(), Font.BOLD, 24));

		add(clockLabel);
		add(mineLabel);
		add(Box.createHorizontalGlue());
		add(giveUpButton);
		add(Box.createHorizontalStrut(5));
		add(resetButton);
		add(Box.createHorizontalStrut(5));
	}
	
	/** Begins incrementing the clock and updates the display every second. */
	public void startClock() {
		stopwatch.start();
		setClock(0);
		timer = new Timer();
		timer.schedule(new UpdateClockTask(), 0, 100);
	}
	
	/** Stops the clock display at its current time. */
	public int stopClock() {
		stopwatch.stop();
		int seconds = stopwatch.getElapsedTimeSecs();
		setClock(seconds);
		if (timer != null)
			timer.cancel();
		timer = null;
		return seconds;
	}
	
	/** Resets the clock display to zero. */
	public void resetClock() {
		stopwatch.reset();
		setClock(0);
		if (timer != null)
			timer.cancel();
		timer = null;
	}
	
	/** Updates the mine counter display to the new given value. */
	public void setMines(int newNumber) {
		mineLabel.setText(String.format("%01d", newNumber));
	}
	
	/** Updates the clock display to show the new given time,
	 *  specified in seconds. */
	public void setClock(int seconds) {
		clockLabel.setText(String.format(" %02d:%02d:%02d", seconds / 3600, seconds / 60 % 60, seconds % 60));
	}
	
	/** Returns the reset JButton for purposes of adding event listeners. */
	public JButton getResetButton() {
		return resetButton;
	}
	
	/** Returns the give up JButton for purposes of adding event listeners. */
	public JButton getGiveUpButton() {
		return giveUpButton;
	}
	
	/**
	 * UpdateClockTask class
	 *  Extended TimerTask class for the purpose
	 * of updating the display clock of the panel.
	 */
	private class UpdateClockTask extends TimerTask {
	    public void run() {
	    	setClock(stopwatch.getElapsedTimeSecs());
	    }
	}
}
