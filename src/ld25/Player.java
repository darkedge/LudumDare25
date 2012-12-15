package ld25;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ld25.Input.Button;

public class Player extends GameObject {
	private BufferedImage left;
	private BufferedImage right;
	private BufferedImage currentSprite;
	private static final float SPEED = 1.5f;
	private Movement movement = Movement.STILL;
	
	enum Movement {
		STILL, LEFT, RIGHT, UP, DOWN;
	}
	
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
		if(movement == Movement.STILL) {
			if(Input.getButton(Button.LEFT)) {
				if(world.isClear(mapx - 1, mapy)) {
					targetX = mapx - 1;
					world.move(this);
					movement = Movement.LEFT;
					currentSprite = left;
				}
			} else if(Input.getButton(Button.RIGHT)) {
				if(world.isClear(mapx + 1, mapy)) {
					targetX = mapx + 1;
					world.move(this);
					movement = Movement.RIGHT;
					currentSprite = right;
				}
			} else if(Input.getButton(Button.UP)) {
				if(world.isClear(mapx, mapy - 1)) {
					targetY = mapy - 1;
					world.move(this);
					movement = Movement.UP;
				}
			} else if(Input.getButton(Button.DOWN)) {
				if(world.isClear(mapx, mapy + 1)) {
					targetY = mapy + 1;
					world.move(this);
					movement = Movement.DOWN;
				}
			}
		}
		
		// Movement tweening
		switch (movement) {
			case DOWN:
				y += SPEED;
				if((int) Math.floor(y) / world.getTileSize() == targetY) {
					y = (int) Math.floor(y);
					mapy = targetY;
					movement = Movement.STILL;
				}
				break;
			case LEFT:
				x -= SPEED;
				if(Math.floor(x) / world.getTileSize() < targetX) {
					x = (int) Math.floor(x);
					mapx = targetX;
					movement = Movement.STILL;
				}
				break;
			case RIGHT:
				x += SPEED;
				if((int) Math.floor(x) / world.getTileSize() == targetX) {
					x = (int) Math.floor(x);
					mapx = targetX;
					movement = Movement.STILL;
				}
				break;
			case UP:
				y -= SPEED;
				if(Math.floor(y) / world.getTileSize() < targetY) {
					y = (int) Math.floor(y);
					mapy = targetY;
					movement = Movement.STILL;
				}
				break;
		}
	}
	
	public void render(Camera camera, double interpolation) {
		camera.drawImage(currentSprite, x, y);
		Graphics2D g = camera.getGraphics();
		g.setColor(Color.white);
		g.drawString(String.valueOf(mapx) + " " + String.valueOf(mapy), 10, 10);
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
