package ld25.gameobject;

import ld25.Camera;
import ld25.Game;
import ld25.GameImage;
import ld25.World;
import ld25.gameobject.GameObject.State;

public class Bandit extends GameObject {
	private static final int MIN_THINK_TICKS = 20;
	private static final float MOVE_CHANCE = 1.0f;
	private static final int VAR_THINK_TICKS = 10;
	
	private int ticks = MIN_THINK_TICKS + Game.RANDOM.nextInt(VAR_THINK_TICKS);
	
	private static final int MAX_HEALTH = 30;

	public Bandit(World world, int mapx, int mapy) {
		super(world, mapx, mapy);
		left = GameImage.get("/img/banditleft.png");
		right = GameImage.get("/img/banditright.png");
		currentImage = left;
	}

	@Override
	public void tick() {
		if(direction == Direction.STILL) {
			ticks--;
			if(ticks < 0) {
				if(Game.RANDOM.nextFloat() < MOVE_CHANCE) {
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
				ticks = MIN_THINK_TICKS + Game.RANDOM.nextInt(VAR_THINK_TICKS);
			}
		}
		
		doMovement();
	}
	
	@Override
	public void render(Camera camera, double interpolation) {
		super.render(camera, interpolation);
		camera.drawFlashlight((int) x + getWidth() / 2,
		                      (int) y + getHeight() / 2,
		                      (int) (2.5f * world.getTileSize()),
		                      world.getTileSize(),
		                      currentImage == right);
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

	@Override
	public void checkFlashlight() {
		if(currentImage == right) {
			for(int x = 1; x < 3; x++) {
				if(world.getGameObjectAt(mapx + x, mapy) == world.getPlayer()
						&& world.getBush(mapx + x, mapy) == null) {
					state = State.ALERTED;
					world.shout(this);
				}
			}
		} else {
			for(int x = -2; x < 0; x++) {
				if(world.getGameObjectAt(mapx + x, mapy) == world.getPlayer()
						&& world.getBush(mapx + x, mapy) == null) {
					state = State.ALERTED;
					world.shout(this);
				}
			}
		}
	}

	
	@Override
	public Type getType() {
		return Type.BANDIT;
	}
}
