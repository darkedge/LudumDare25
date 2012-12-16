package ld25;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ld25.util.Flipper;

public class Overlay {
	private Flipper alphaflipper;
	private Color color;
	private BufferedImage vignet;
	
	public Overlay() {
		alphaflipper = new Flipper(0.2f, 1.0f);
		try {
			vignet = ImageIO.read(Overlay.class.getResourceAsStream("/vignet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		alphaflipper.tick(1.0f / (Game.TICK_RATE * 10));
		color = new Color(0.0f, 0.0f, 0.0f, 1.0f - alphaflipper.getPosition());
	}
	
	public void render(Camera camera, double interpolation) {
		Graphics2D g = camera.getGraphics();
		g.setColor(color);
		//g.fillRect(0, 0, camera.getWidth(), camera.getHeight());
		g.drawImage(vignet, 0, 0, null);
	}
}
