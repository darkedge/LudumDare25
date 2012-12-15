package ld25;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ld25.Input.Button;

public class Player {
	private int x;
	private int y;
	private BufferedImage image;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		try {
			image = ImageIO.read(World.class.getResourceAsStream("/p1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void tick() {
		if(Input.getButton(Button.LEFT)) {
			x--;
		}
		if(Input.getButton(Button.RIGHT)) {
			x++;
		}
		if(Input.getButton(Button.UP)) {
			y--;
		}
		if(Input.getButton(Button.DOWN)) {
			y++;
		}
	}
	
	public void render(Camera camera, double interpolation) {
		camera.drawImage(image, x, y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}
}
