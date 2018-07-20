package Login_s;

/**
 *Stopwatch class
 * Keeps track of elapsed time since it was started.
 */

public class Stopwatch {
	
	private long startTime;
	private long stopTime;
	private boolean running;
	
	/** Saves the current system time and goes into running mode. */
	public void start() {
		if (!running) {
			startTime = System.currentTimeMillis();
			running = true;
		}
	}
	
	/** If in running mode, saves the current system time
	 * and stops the running mode. */
	public void stop() {
		if (running) {
			stopTime = System.currentTimeMillis();
			running = false;
		}
	}
	
	/** Resets the stopwatch to its initialization state */
	public void reset() {
		startTime = stopTime = 0;
		running = false;
	}
	
	/** If stopwatch was started and stopped,
	 *  returns elapsed time in seconds in between the start and stop calls.
	 *  If the stopwatch is still in running,
	 *  returns elapsed time since the start call.
	 *  If start was not called, returns zero. */
	public int getElapsedTimeSecs() {
		return (int)((running ? System.currentTimeMillis() : stopTime) - startTime) / 1000;
	}
}
