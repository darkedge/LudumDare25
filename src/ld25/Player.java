package ld25;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ld25.Input.Button;

public class Player {
	private World world;
	private int x;
	private int y;
	private SpriteSheet left;
	private SpriteSheet right;
	private SpriteSheet currentAnimation;
	private Looper animation;
	private static final int SPEED = 4;
	
	public Player(World world, int x, int y) {
		this.world = world;
		this.x = x;
		this.y = y;
		try {
			BufferedImage image = ImageIO.read(World.class.getResourceAsStream("/dog.png"));
			left = new SpriteSheet(image, image.getHeight());
			image = ImageIO.read(World.class.getResourceAsStream("/dog.png"));
			right = new SpriteSheet(image, image.getHeight());
			currentAnimation = right;
		} catch (IOException e) {
			e.printStackTrace();
		}
		animation = new Looper(0.0f, 1.0f, 1);
	}
	
	public void tick() {
		if(Input.getButton(Button.LEFT)) {
			x-= SPEED;
			currentAnimation = left;
		}
		if(Input.getButton(Button.RIGHT)) {
			x+= SPEED;
			currentAnimation = right;
		}
		if(Input.getButton(Button.UP)) {
			y-= SPEED;
		}
		if(Input.getButton(Button.DOWN)) {
			y+= SPEED;
		}
		if(Input.getButton(Button.LEFT) ||
				Input.getButton(Button.RIGHT) ||
				Input.getButton(Button.DOWN) ||
				Input.getButton(Button.UP)) {
			animation.tick(1.0f / (Game.TICK_RATE / 4));
		}
		
		x = GameMath.clamp(x, 0, world.getPixelWidth() - getWidth());
		y = GameMath.clamp(y, 0, world.getPixelHeight() - getHeight());
	}
	
	public void render(Camera camera, double interpolation) {
		camera.drawImage(currentAnimation.getImage(animation.getValue()), x, y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return currentAnimation.getSpriteWidth();
	}
	
	public int getHeight() {
		return currentAnimation.getSpriteHeight();
	}
}
