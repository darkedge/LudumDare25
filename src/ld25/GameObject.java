package ld25;

import java.awt.image.BufferedImage;

public abstract class GameObject {
	protected final World world;
	protected float x;
	protected float y;
	protected int mapx;
	protected int mapy;
	protected Direction direction = Direction.STILL;
	public static final int MOVEMENT_TICKS = 15;
	private int movementTicks;
	
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
	}
	
	protected boolean tryMove(Direction movement) {
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
}
