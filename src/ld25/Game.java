package ld25;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	// Constants
	private static final String TITLE = "TODO: Set name!";
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	private static final int TICK_RATE = 60;
	
	// Game loop variables
	private long lastTime;
	private boolean running = false;
	private int fps;
	private int fpsTimer;
	
	// Game objects
	private Camera camera;
	private Screen screen;
	
	public Game() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setIgnoreRepaint(true);
		addKeyListener(Input.INSTANCE);
		addMouseMotionListener(Input.INSTANCE);
		addMouseListener(Input.INSTANCE);
		
		setFocusable(true);
		setScreen(new Screen(this));
		camera = new Camera(0,0, WIDTH, HEIGHT);
	}
	
	public void setScreen(Screen screen) {
		this.screen = screen;
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
		double accumulator = 0;
		final double TICK_TIME = 1000000000d / TICK_RATE;
		long lastTime = System.nanoTime();
		
		while (running) {
			long now = System.nanoTime();
			long delta = now - lastTime;
			delta = GameMath.clamp(delta, 0, 100000000); // TODO: check this value
			accumulator += delta;
			fpsTimer += delta;
			
			while (accumulator > TICK_TIME) {
				tick();
				accumulator -= TICK_TIME;
			}

			render(accumulator);
			fps++;
			
			if (fpsTimer > 1000000000) {
				fpsTimer -= 1000000000;
				System.out.println(fps + " fps");
				fps = 0;
			}
			
			Thread.yield();
		}
	}
	
	private void tick() {
		Input.tick();
		for(int i : Input.getKeysDown()) {
			System.out.print(KeyEvent.getKeyText(i));
		}
	}
	
	private void render(double interpolation) {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		screen.render(camera, interpolation);
		g.dispose();
		bs.show();
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
