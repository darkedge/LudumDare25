package ld25;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ld25.Input.Button;

public class Player extends GameObject {
	private final World world;
	private float x;
	private float y;
	private int xx;
	private int yy;
	private SpriteSheet left;
	private SpriteSheet right;
	private SpriteSheet currentAnimation;
	private Looper animation;
	private static final float SPEED = 0.1f;
	private Movement movement = Movement.STILL;
	
	enum Movement {
		STILL, LEFT, RIGHT, UP, DOWN;
	}
	
	public Player(World world, int x, int y) {
		this.world = world;
		this.xx = x;
		this.yy = y;
		x = xx * world.getTileSize();
		y = yy * world.getTileSize();
		try {
			BufferedImage image = ImageIO.read(World.class.getResourceAsStream("/goat.png"));
			left = new SpriteSheet(image, image.getHeight());
			image = ImageIO.read(World.class.getResourceAsStream("/goat.png"));
			right = new SpriteSheet(image, image.getHeight());
			currentAnimation = right;
		} catch (IOException e) {
			e.printStackTrace();
		}
		animation = new Looper(0.0f, 1.0f, 1);
	}
	
	public void tick() {
		if(movement == Movement.STILL) {
			if(Input.getButtonDown(Button.LEFT)) {
				if(world.isClear(xx - 1, yy)) movement = Movement.LEFT;
			} else if(Input.getButtonDown(Button.RIGHT)) {
				if(world.isClear(xx + 1, yy)) movement = Movement.RIGHT;
			} else if(Input.getButtonDown(Button.UP)) {
				if(world.isClear(xx, yy - 1)) movement = Movement.UP;
			} else if(Input.getButtonDown(Button.DOWN)) {
				if(world.isClear(xx, yy + 1)) movement = Movement.DOWN;
			}
		}
		
		// Movement tweening
		switch (movement) {
			case DOWN:
				y += SPEED
				break;
			case LEFT:
				break;
			case RIGHT:
				break;
			case UP:
				break;
		}
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
