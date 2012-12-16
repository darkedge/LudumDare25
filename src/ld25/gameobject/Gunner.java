package ld25.gameobject;

import ld25.Camera;
import ld25.Game;
import ld25.GameImage;
import ld25.World;

public class Gunner extends GameObject {
	private static final int MAX_HEALTH = 30;
	private static final int MIN_THINK_TICKS = 50;
	private static final float MOVE_CHANCE = 0.4f;
	private static final int VAR_THINK_TICKS = 30;
	
	private int ticks = MIN_THINK_TICKS + Game.RANDOM.nextInt(VAR_THINK_TICKS);
	
	public Gunner(World world, int mapx, int mapy) {
		super(world, mapx, mapy);

		left = GameImage.get("/img/gunnerleft.png");
		right = GameImage.get("/img/gunnerright.png");
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
}
