package ld25;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ld25.Input.Button;

public class Player {
	private World world;
	private int x;
	private int y;
	private BufferedImage image;
	private static final int SPEED = 4;
	
	public Player(World world, int x, int y) {
		this.world = world;
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
			x-= SPEED;
		}
		if(Input.getButton(Button.RIGHT)) {
			x+= SPEED;
		}
		if(Input.getButton(Button.UP)) {
			y-= SPEED;
		}
		if(Input.getButton(Button.DOWN)) {
			y+= SPEED;
		}
		
		x = GameMath.clamp(x, 0, world.getPixelWidth() - getWidth());
		y = GameMath.clamp(y, 0, world.getPixelHeight() - getHeight());
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
