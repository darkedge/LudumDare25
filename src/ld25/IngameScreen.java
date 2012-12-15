package ld25;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import ld25.Input.Button;

public class IngameScreen {
	/**
	 * TODO: HUD, minimap
	 */
	private Game game;
	private World world;
	private BufferedImage image;
	private float x = 0;
	private float y = 0;

	public IngameScreen(Game game) {
		this.game = game;
		
		image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.red);
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.dispose();
		
		world = new World();
	}
	
	public void tick() {
		world.tick();
	}
	
	public void render(Camera camera, double interpolation) {
		world.render(camera, interpolation);
	}
}
