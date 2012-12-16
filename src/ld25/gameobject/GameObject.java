package ld25.gameobject;

import java.awt.Color;
import java.awt.image.BufferedImage;

import ld25.Camera;
import ld25.GameImage;
import ld25.World;

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
	protected State state;
	
	protected BufferedImage currentImage;
	protected BufferedImage left;
	protected BufferedImage right;
	private BufferedImage alerted = GameImage.get("/img/shout.png");
	
	public enum Type {
		GUNNER, BANDIT, SNIPER, GOAT, PLAYER
	}
	
	public enum Direction {
		STILL, LEFT, RIGHT, UP, DOWN;
	}
	
	public enum State {
		IDLE, FLEEING, ALERTED, LOST;
	}
	
	public GameObject(World world, int mapx, int mapy) {
		this.world = world;
		this.mapx = mapx;
		this.mapy = mapy;
		state = State.IDLE;
		x = mapx * world.getTileSize();
		y = mapy * world.getTileSize();
		health = getMaxHealth();
	}
	
	public abstract Type getType();
	
	public void hurt(int damage) {
		health -= damage;
		if(health < getMaxHealth()) {
			state = State.ALERTED;
			world.getMap()[mapy * world.getWidth() + mapx].blood = true;
			showHealth = true;
		}
		if(health <= 0) {
			// TODO Add sound here
			playDeathSound();
			isColliding = false;
			showHealth = false;
			dispose();
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
	
	public boolean isBleeding() {
		return health < getMaxHealth();
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
				if(isBleeding()) {
					world.getMap()[mapy * world.getWidth() + mapx].blood = true;
				}
				direction = Direction.STILL;
				x = mapx * world.getTileSize();
				y = mapy * world.getTileSize();
				checkFlashlight();
			}
		}
	}
	
	public void alert() {
		state = State.ALERTED;
	}
	
	public abstract void checkFlashlight();

	public void render(Camera camera, double interpolation) {
		camera.drawImage(currentImage, x, y);
		if(showHealth) {
			final int height = 3;
			final int width = world.getTileSize();
			camera.getGraphics().setColor(Color.red);
			camera.fillRect(Math.round(x) + 1, Math.round(y - 5) + 1, (int) Math.ceil((float) health / getMaxHealth() * width - 2), height - 2); // Red HP bar
		}
		if(state == State.ALERTED) {
			camera.drawImage(alerted, x, y - 11);
		}
	}
	
	public int getWidth() {
		return currentImage.getWidth();
	}
	
	public int getHeight() {
		return currentImage.getHeight();
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
