package ld25.gameobject;

import java.io.IOException;

import javax.imageio.ImageIO;

import ld25.Game;
import ld25.World;
import ld25.gameobject.GameObject.Direction;

public class Bandit extends GameObject {
	private static final int MIN_THINK_TICKS = 20;
	private static final float MOVE_CHANCE = 1.0f;
	private static final int VAR_THINK_TICKS = 10;
	
	private int ticks = MIN_THINK_TICKS + Game.RANDOM.nextInt(VAR_THINK_TICKS);
	
	private static final int MAX_HEALTH = 30;

	public Bandit(World world, int mapx, int mapy) {
		super(world, mapx, mapy);
		try {
			left = ImageIO.read(Goat.class.getResourceAsStream("/banditleft.png"));
			right = ImageIO.read(Goat.class.getResourceAsStream("/banditright.png"));
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
