package ld25;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Goat extends GameObject {
	private static final int MAX_HEALTH = 30;
	private static final int MIN_THINK_TICKS = Game.TICK_RATE;
	private static final float MOVE_CHANCE = 0.2f;
	private static final int VAR_THINK_TICKS = 30;
	
	private int ticks = MIN_THINK_TICKS + Game.RANDOM.nextInt(VAR_THINK_TICKS);

	public Goat(World world, int mapx, int mapy) {
		super(world, mapx, mapy);
		
		try {
			left = ImageIO.read(Goat.class.getResourceAsStream("/goatleft.png"));
			right = ImageIO.read(Goat.class.getResourceAsStream("/goatright.png"));
			currentImage = left;
		} catch (IOException e) {
			e.printStackTrace();
		}
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
