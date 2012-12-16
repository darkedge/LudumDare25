package ld25.gameobject;

import ld25.Camera;
import ld25.Game;
import ld25.GameImage;
import ld25.World;
import ld25.gameobject.GameObject.State;

public class Gunner extends GameObject {
	private static final int MAX_HEALTH = 30;
	private static final int MIN_THINK_TICKS = 50;
	private static final float MOVE_CHANCE = 0.4f;
	private static final int VAR_THINK_TICKS = 30;
	
	public Gunner(World world, int mapx, int mapy) {
		super(world, mapx, mapy);

		left = GameImage.get("/img/gunnerleft.png");
		right = GameImage.get("/img/gunnerright.png");
		currentImage = left;
	}
	
	@Override
	public void render(Camera camera, double interpolation) {
		super.render(camera, interpolation);
		camera.drawFlashlight((int) x + getWidth() / 2,
		                      (int) y + getHeight() / 2,
		                      (int) (2.5f * world.getTileSize()),
		                      3 * world.getTileSize(),
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
		if(checkedFlashlight) return;
		checkedFlashlight = true;
		if(currentImage == right) {
			for(int x = 1; x < 3; x++) {
				for(int y = -1; y < 2; y++) {
					if(world.getGameObjectAt(mapx + x, mapy + y) == world.getPlayer()
							&& world.getBush(mapx + x, mapy + y) == null) {
						state = State.HOMING;
						world.shout(this);
						shoot();
					}
				}
			}
		} else {
			for(int x = -2; x < 0; x++) {
				for(int y = -1; y < 2; y++) {
					if(world.getGameObjectAt(mapx + x, mapy + y) == world.getPlayer()
							&& world.getBush(mapx + x, mapy + y) == null) {
						state = State.HOMING;
						world.shout(this);
						shoot();
					}
				}
			}
		}
	}
	
	@Override
	public Type getType() {
		return Type.GUNNER;
	}

	@Override
	protected int getMinThinkTicks() {
		return MIN_THINK_TICKS;
	}

	@Override
	protected int getVarThinkTicks() {
		return VAR_THINK_TICKS;
	}

	@Override
	protected float getMoveChance() {
		return MOVE_CHANCE;
	}
	
	@Override
	protected int getShootTicks() {
		return Game.TICK_RATE / 2;
	}
	
	@Override
	protected int getDamage() {
		return 5;
	}
}
