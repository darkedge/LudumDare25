package ld25;

import java.awt.Canvas;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private static final String NAME = "TODO: Set name!";
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private long lastTime;
	private boolean running = false;

	/**
	 * Entry point
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void start() {
		
	}
	
	public void stop() {
		
	}

	@Override
	public void run() {
		// LETS GOOOOOOOOOOOOOOOOOOOO
		long lastTime = System.nanoTime();
		while(running) {
			// Timing
			long now = System.nanoTime();
			long delta = now - lastTime;
			lastTime = now;
			
			
		}
	}
}
