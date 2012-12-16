package ld25.gameobject;

import java.awt.Color;
import java.awt.image.BufferedImage;

import ld25.Camera;
import ld25.Game;
import ld25.GameImage;
import ld25.World;
import ld25.util.Flipper;

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
	private static final int LOST_TICKS = Game.TICK_RATE;
	private int lostTicks;
	protected boolean checkedFlashlight = false;
	protected State state;
	private int ticks = getMinThinkTicks() + Game.RANDOM.nextInt(getVarThinkTicks());
	
	protected abstract int getMinThinkTicks();
	protected abstract int getVarThinkTicks();
	protected abstract float getMoveChance();
	
	private int laserTicks;
	private static final int LASER_TICKS = 6;
	protected BufferedImage currentImage;
	protected BufferedImage left;
	protected BufferedImage right;
	private BufferedImage alerted = GameImage.get("/img/shout.png");
	private BufferedImage lost = GameImage.get("/img/lost.png");
	protected Flipper moveFlipper;
	
	public enum Type {
		GUNNER, BANDIT, SNIPER, GOAT, PLAYER
	}
	
	public enum Direction {
		STILL, LEFT, RIGHT, UP, DOWN;
	}
	
	public enum State {
		IDLE, FLEEING, HOMING, LOST;
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
			state = State.HOMING;
			world.shout(this);
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
	
	private int shootTicks;
	
	public void shoot() {
		if(shootTicks == 0) {
			laserTicks = LASER_TICKS;
			world.getPlayer().hurt(getDamage());
			playAttackSound();
			shootTicks = getShootTicks();
		}
	}
	
	protected abstract int getDamage();
	protected abstract int getShootTicks();
	
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
			moveFlipper = new Flipper(0, world.getTileSize() / 4);
		}
		return success;
	}
	
	protected void dispose() {
		isDisposed = true;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void tick() {
		shootTicks--;
		if(shootTicks < 0) shootTicks = 0;
		checkedFlashlight = false;
		laserTicks--;
		if(laserTicks < 0) {
			laserTicks = 0;
		}
		if(direction == Direction.STILL) {
			ticks--;
			if(ticks < 0) {
				act();
			}
		}
		
		doMovement();
	}
	
	private void act() {
		switch(state) {
			case FLEEING:
				break;
			case HOMING:
				int dx = world.getPlayer().getMapX() - mapx;
				int dy = world.getPlayer().getMapY() - mapy;
				Direction dir;
				if(dx * dx > dy * dy) {
					if(dx > 0) {
						dir = Direction.RIGHT;
						currentImage = right;
					} else {
						dir = Direction.LEFT;
						currentImage = left;
					}
				} else {
					dir = dy > 0 ? Direction.DOWN : Direction.UP;
				}
				tryMove(dir);
				ticks = getMinThinkTicks();
				break;
			case IDLE:
				if(Game.RANDOM.nextFloat() < getMoveChance()) {
					switch(Game.RANDOM.nextInt(4)) {
						case 0:
							tryMove(Direction.UP);
							break;
						case 1:
							tryMove(Direction.DOWN);
							break;
						case 2:
							if (tryMove(Direction.LEFT)) currentImage = left;
							break;
						case 3:
							if (tryMove(Direction.RIGHT)) currentImage = right;
							break;
					}
				}
				ticks = getMinThinkTicks() + Game.RANDOM.nextInt(getVarThinkTicks());
				break;
			case LOST:
				break;
			
		}
	}
	
	public void doMovement() {
		if(direction != Direction.STILL) {
			if(movementTicks < MOVEMENT_TICKS) {
				switch (direction) {
					case DOWN:
						y += world.getTileSize() / MOVEMENT_TICKS;
						break;
					case LEFT:
						moveFlipper.tickPercentage(2.0f / MOVEMENT_TICKS);
						y = mapy * world.getTileSize() - moveFlipper.getPosition();
						x -= world.getTileSize() / MOVEMENT_TICKS;
						break;
					case RIGHT:
						moveFlipper.tickPercentage(2.0f / MOVEMENT_TICKS);
						y = mapy * world.getTileSize() - moveFlipper.getPosition();
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
		state = State.HOMING;
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
		if(state == State.HOMING) {
			camera.drawImage(alerted, x, y - 11);
		}
		if(state == State.LOST) {
			camera.drawImage(lost, x, y - 11);
		}
		if(laserTicks > 0) {
			int x1 = (int) x + world.getTileSize() / 2 - camera.getX();
			int y1 = (int) y + world.getTileSize() / 2 - camera.getY();
			int x2 = (int) world.getPlayer().getX() + world.getPlayer().getWidth() / 2 - camera.getX();
			int y2 = (int) world.getPlayer().getY() + world.getPlayer().getHeight() / 2 - camera.getY();
			camera.getGraphics().setColor(Color.yellow);
			camera.getGraphics().drawLine(x1, y1, x2, y2);
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

	public void relax() {
		state = State.IDLE;
	}
}
