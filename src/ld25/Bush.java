package ld25;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ld25.util.Flipper;

public class Bush {
	private static final int SHAKE_TICKS = 10;
	private Flipper shakeFlipper;
	private BufferedImage image;
	private int x;
	private int y;
	
	public Bush(int x, int y) {
		this.x = x;
		this.y = y;
		try {
			image = ImageIO.read(Bush.class.getResourceAsStream("/img/bush.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		
	}
	
	public void shake() {
		
	}

	public void render(Camera camera, double interpolation) {
		camera.drawImage(image, x, y);
	}
}
