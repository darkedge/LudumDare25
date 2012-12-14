package ld25;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "TODO: Set name!";
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private long lastTime;
	private boolean running = false;
	
	public Game() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setIgnoreRepaint(true);
		addKeyListener(Input.INSTANCE);
	}
	
	public void start() {
		new Thread(this).start();
	}
	
	public void stop() {
		running = false;
	}

	@Override
	public void run() {
		running = true;
		long lastTime = System.currentTimeMillis();
		float delta = 0;
		
		while(running) {
			// Timing
			long now = System.currentTimeMillis();
			delta += GameMath.clamp(now - lastTime, 0, 50);
			lastTime = now;
			
			while (delta > 0) {
				delta -= 1000.0f / 60;
				tick();
			}
			
			Thread.yield();
		}
	}
	
	private void tick() {
		Input.INSTANCE.tick();
		for(int i : Input.INSTANCE.getKeysDown()) {
			System.out.print(KeyEvent.getKeyText(i));
		}
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		
	}
	
	public static void main(String[] args) {
		Game canvas = new Game();

		JFrame frame = new JFrame(TITLE);
		frame.add(canvas);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		canvas.start();
	}
}
