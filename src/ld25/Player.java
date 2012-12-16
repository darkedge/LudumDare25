package ld25;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ld25.Input.Button;

public class Player extends GameObject {
	private BufferedImage left;
	private BufferedImage right;
	private BufferedImage currentSprite;
	private static final int MAX_HEALTH = 100;
	private static final int DAMAGE = 10;
	private Attack attack = Attack.NONE;
	private static final int ATTACK_TICKS = 10;
	private static final int COOLDOWN_TICKS = 15;
	private GameObject target;
	private int cooldownTicks;
	private int attackTicks;
	private Flipper attackFlipper;
	
	private enum Attack {
		NONE, UP, DOWN, LEFT, RIGHT;
	}
	
	public Player(World world, int mapx, int mapy) {
		super(world, mapx, mapy);
		health = MAX_HEALTH;
		try {
			left = ImageIO.read(World.class.getResourceAsStream("/dogleft.png"));
			right = ImageIO.read(World.class.getResourceAsStream("/dogright.png"));
			currentSprite = right;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		GameObject other = null;
		if(direction == Direction.STILL && attack == Attack.NONE) {
			cooldownTicks--;
			if(cooldownTicks < 0) cooldownTicks = 0;
			if (Input.getButton(Button.LEFT)) {
				currentSprite = left;
				if (!tryMove(Direction.LEFT)) {
					other = world.getGameObjectAt(mapx - 1, mapy);
					if(other != null) {
						attack = Attack.LEFT;
					}
				}
			}
			else if (Input.getButton(Button.RIGHT)) {
				currentSprite = right;
				if (!tryMove(Direction.RIGHT)) {
					other = world.getGameObjectAt(mapx + 1, mapy);
					if(other != null) {
						attack = Attack.RIGHT;
					}
				}
			}
			else if (Input.getButton(Button.UP)) {
				if (!tryMove(Direction.UP)) {
					other = world.getGameObjectAt(mapx, mapy - 1);
					if(other != null) {
						attack = Attack.UP;
					}
				}
			}
			else if (Input.getButton(Button.DOWN)) {
				if (!tryMove(Direction.DOWN)) {
					other = world.getGameObjectAt(mapx, mapy + 1);
					if(other != null) {
						attack = Attack.DOWN;
					}
				}
			}
		}
		
		if(cooldownTicks == 0) {
			if(other != null) {
				playAttackSound();
				target = other;
				target.lock();
				attackFlipper = new Flipper(0, world.getTileSize() / 2);
				attackTicks = 0;
			}
		} else {
			attack = Attack.NONE;
		}
		
		doMovement();
		doAttack();
	}
	
	@Override
	public void hurt(int damage) {
		health -= damage;
	}
	
	public int getHealth() {
		return health;
	}
	
	private void doAttack() {
		if(attack != Attack.NONE) {
			if(attackTicks < ATTACK_TICKS) {
				attackFlipper.tickPercentage(2.0f / ATTACK_TICKS);
				switch(attack) {
					case DOWN:
						y = mapy * world.getTileSize() + attackFlipper.getPosition();
						break;
					case LEFT:
						x = mapx * world.getTileSize() - attackFlipper.getPosition();
						break;
					case RIGHT:
						x = mapx * world.getTileSize() + attackFlipper.getPosition();
						break;
					case UP:
						y = mapy * world.getTileSize() - attackFlipper.getPosition();
						break;
				}
				if(attackTicks == ATTACK_TICKS / 2) {
					target.hurt(DAMAGE);
					target.playHurtSound();
				}
				attackTicks++;
			} else {
				cooldownTicks = COOLDOWN_TICKS;
				target.release();
				target = null;
				attack = Attack.NONE;
				x = mapx * world.getTileSize();
				y = mapy * world.getTileSize();
			}
		}
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

	@Override
	protected void playDeathSound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void playHurtSound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMaxHealth() {
		return MAX_HEALTH;
	}

	@Override
	protected void playAttackSound() {
		// TODO Auto-generated method stub
		
	}
}
