package application;

import java.time.Duration;

public class StopWatch {
	private boolean running;
	private long pauseTime;
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public long getPauseTime() {
		return pauseTime;
	}

	public void setPauseTime(long pauseTime) {
		this.pauseTime = pauseTime;
	}
	
	public String stopwatch( long start ) {
		long runningTime = (System.currentTimeMillis() - start) - this.pauseTime;
        Duration duration = Duration.ofMillis(runningTime);
        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);
        long seconds = duration.toMillis() / 1000;
        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}
