package ld25.gameobject;

import java.io.IOException;

import javax.imageio.ImageIO;

import ld25.Game;
import ld25.World;
import ld25.gameobject.GameObject.Direction;

public class Goat extends GameObject {
	private static final int MAX_HEALTH = 50;
	private static final int MIN_THINK_TICKS = Game.TICK_RATE;
	private static final float MOVE_CHANCE = 0.2f;
	private static final int VAR_THINK_TICKS = 30;

	public Goat(World world, int mapx, int mapy) {
		super(world, mapx, mapy);
		
		try {
			left = ImageIO.read(Goat.class.getResourceAsStream("/img/goatleft.png"));
			right = ImageIO.read(Goat.class.getResourceAsStream("/img/goatright.png"));
			currentImage = left;
		} catch (IOException e) {
			e.printStackTrace();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkFlashlight() {
		if(checkedFlashlight) return;
		checkedFlashlight = true;
	}
	
	@Override
	public Type getType() {
		return Type.GOAT;
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
		return 13371337;
	}
	
	@Override
	protected int getDamage() {
		return 9001;
	}
}
