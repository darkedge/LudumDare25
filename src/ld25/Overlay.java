package ld25;

import java.awt.Color;
import java.awt.Graphics2D;

public class Overlay {
	private Flipper alphaflipper;
	private Color color;
	
	public Overlay() {
		alphaflipper = new Flipper(0.2f, 1.0f);
	}
	
	public void tick() {
		alphaflipper.tick(1.0f / (Game.TICK_RATE * 10));
		color = new Color(0.0f, 0.0f, 0.0f, 1.0f - alphaflipper.getPosition());
	}
	
	public void render(Camera camera, double interpolation) {
		Graphics2D g = camera.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, camera.getWidth(), camera.getHeight());
	}
}
