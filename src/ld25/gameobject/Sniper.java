package ld25.gameobject;

import ld25.Camera;
import ld25.Game;
import ld25.GameImage;
import ld25.Sound;
import ld25.World;

public class Sniper extends GameObject {
	private static final int MAX_HEALTH = 30;
	private static final int MIN_THINK_TICKS = 120;
	private static final float MOVE_CHANCE = 0.5f;
	private static final int VAR_THINK_TICKS = 60;
	private static final Sound ATTACK = Sound.get("/snd/sniperattack.wav");

	public Sniper(World world, int mapx, int mapy) {
		super(world, mapx, mapy);

		left = GameImage.get("/img/sniperleft.png");
		right = GameImage.get("/img/sniperright.png");
		currentImage = left;
	}
	
	@Override
	public void render(Camera camera, double interpolation) {
		super.render(camera, interpolation);
		camera.drawFlashlight((int) x + getWidth() / 2,
		                      (int) y + getHeight() / 2,
		                      (int) (7.5f * world.getTileSize()),
		                      world.getTileSize(),
		                      currentImage == right);
	}
	
	@Override
	public void checkFlashlight() {
		if(checkedFlashlight) return;
		checkedFlashlight = true;
		if(currentImage == right) {
			for(int x = 1; x < 8; x++) {
				if(world.getGameObjectAt(mapx + x, mapy) == world.getPlayer()
						&& world.getBush(mapx + x, mapy) == null) {
					state = State.HOMING;
					world.shout(this);
					shoot();
				}
			}
		} else {
			for(int x = -7; x < 0; x++) {
				if(world.getGameObjectAt(mapx + x, mapy) == world.getPlayer()
						&& world.getBush(mapx + x, mapy) == null) {
					state = State.HOMING;
					world.shout(this);
					shoot();
				}
			}
		}
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
		ATTACK.play();
	}
	
	@Override
	public Type getType() {
		return Type.SNIPER;
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
		return 2 * Game.TICK_RATE;
	}
	
	@Override
	protected int getDamage() {
		return 30;
	}
}
