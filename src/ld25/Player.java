package ld25;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ld25.Input.Button;

public class Player extends GameObject {
	private BufferedImage left;
	private BufferedImage right;
	private BufferedImage currentSprite;
	
	public Player(World world, int mapx, int mapy) {
		super(world, mapx, mapy);
		try {
			left = ImageIO.read(World.class.getResourceAsStream("/dogleft.png"));
			right = ImageIO.read(World.class.getResourceAsStream("/dogright.png"));
			currentSprite = right;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		if(direction == Direction.STILL) {
			if (Input.getButton(Button.LEFT)) {
				if (tryMove(Direction.LEFT)) currentSprite = left;
			}
			else if (Input.getButton(Button.RIGHT)) {
				if (tryMove(Direction.RIGHT)) currentSprite = right;
			}
			else if (Input.getButton(Button.UP)) tryMove(Direction.UP);
			else if (Input.getButton(Button.DOWN)) tryMove(Direction.DOWN);
		}
		
		doMovement();
	}
	
	public void render(Camera camera, double interpolation) {
		camera.drawImage(currentSprite, x, y);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public int getMapX() {
		return mapx;
	}
	
	public int getMapY() {
		return mapy;
	}
	
	public int getWidth() {
		return currentSprite.getWidth();
	}
	
	public int getHeight() {
		return currentSprite.getHeight();
	}
}
