package ld25;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import ld25.Input.Button;

public class Screen {
	private Game game;
	private BufferedImage image;
	private float x = 0;
	private float y = 0;

	public Screen(Game game) {
		this.game = game;
		
		image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.red);
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.dispose();
	}
	
	public void tick() {
		if(Input.getButton(Button.RIGHT)) {
			x += 3;
		}
		if(Input.getButton(Button.LEFT)) {
			x -= 3;
		}
		if(Input.getButton(Button.DOWN)) {
			y += 3;
		}
		if(Input.getButton(Button.UP)) {
			y -= 3;
		}
	}
	
	public void render(Camera camera, double interpolation) {
		float xx = x;
		float yy = y;
		if(Input.getButton(Button.RIGHT)) {
			xx += 3 * (float) interpolation;
		}
		if(Input.getButton(Button.LEFT)) {
			xx -= 3 * (float) interpolation;
		}
		if(Input.getButton(Button.DOWN)) {
			yy += 3 * (float) interpolation;
		}
		if(Input.getButton(Button.UP)) {
			yy -= 3 * (float) interpolation;
		}
		camera.drawImage(image, xx, yy);
	}
}
