package ld25;

import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class GameObject {
	private boolean isDisposed = false;
	protected final World world;
	protected float x;
	protected float y;
	protected int mapx;
	protected int mapy;
	protected Direction direction = Direction.STILL;
	public static final int MOVEMENT_TICKS = 15;
	private int movementTicks;
	protected int health;
	private boolean isColliding;
	private boolean showHealth = false;
	private boolean canMove = true;
	
	protected BufferedImage currentImage;
	protected BufferedImage left;
	protected BufferedImage right;
	
	enum Direction {
		STILL, LEFT, RIGHT, UP, DOWN;
	}
	
	public GameObject(World world, int mapx, int mapy) {
		this.world = world;
		this.mapx = mapx;
		this.mapy = mapy;
		x = mapx * world.getTileSize();
		y = mapy * world.getTileSize();
		health = getMaxHealth();
	}
	
	public void hurt(int damage) {
		health -= damage;
		if(health < getMaxHealth()) {
			showHealth = true;
		}
		if(health <= 0) {
			// TODO Add sound here
			playDeathSound();
			isColliding = false;
			showHealth = false;
		} else {
			playHurtSound();
		}
	}
	
	public void lock() {
		canMove = false;
	}
	
	public void release() {
		canMove = true;
	}
	
	protected abstract void playDeathSound();
	
	protected abstract void playHurtSound();
	protected abstract void playAttackSound();
	
	public abstract int getMaxHealth();
	
	protected boolean tryMove(Direction movement) {
		if(!canMove) {
			return false;
		}
		boolean success = false;
		switch(movement) {
			case LEFT:
				if(success = world.isClear(mapx - 1, mapy)) {
					direction = Direction.LEFT;
				}
				break;
			case RIGHT:
				if(success = world.isClear(mapx + 1, mapy)) {
					direction = Direction.RIGHT;
				}
				break;
			case UP:
				if(success = world.isClear(mapx, mapy - 1)) {
					direction = Direction.UP;
				}
				break;
			case DOWN:
				if(success = world.isClear(mapx, mapy + 1)) {
					direction = Direction.DOWN;
				}
				break;
		}
		if (success) {
			movementTicks = 0;
			world.move(this, direction);
		}
		return success;
	}
	
	protected void dispose() {
		isDisposed = true;
	}
	
	public abstract void tick();
	
	public void doMovement() {
		if(direction != Direction.STILL) {
			if(movementTicks < MOVEMENT_TICKS) {
				switch (direction) {
					case DOWN:
						y += world.getTileSize() / MOVEMENT_TICKS;
						break;
					case LEFT:
						x -= world.getTileSize() / MOVEMENT_TICKS;
						break;
					case RIGHT:
						x += world.getTileSize() / MOVEMENT_TICKS;
						break;
					case UP:
						y -= world.getTileSize() / MOVEMENT_TICKS;
						break;
				}
				movementTicks++;
			} else {
				direction = Direction.STILL;
				x = mapx * world.getTileSize();
				y = mapy * world.getTileSize();
			}
		}
	}

	public void render(Camera camera, double interpolation) {
		camera.drawImage(currentImage, x, y);
		if(showHealth) {
			final int height = 3;
			final int width = world.getTileSize();
			camera.getGraphics().setColor(Color.red);
			camera.fillRect(Math.round(x) + 1, Math.round(y - 5) + 1, (int) Math.ceil((float) health / getMaxHealth() * width - 2), height - 2); // Red HP bar
		}
	}

	public int getMapX() {
		return mapx;
	}
	
	public int getMapY() {
		return mapy;
	}
	public void setMapX(int mapx) {
		this.mapx = mapx;
	}
	
	public void setMapY(int mapy) {
		this.mapy = mapy;
	}

	public boolean isDisposed() {
		return isDisposed;
	}
}
