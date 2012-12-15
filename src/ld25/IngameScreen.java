package ld25;

import java.awt.Graphics2D;

public class IngameScreen {
	/**
	 * TODO: HUD, minimap
	 */
	private Game game;
	private World world;
	private Hud hud;

	public IngameScreen(Game game) {
		this.game = game;
		
		world = new World();
		hud = new Hud(world);
	}
	
	public void tick() {
		world.tick();
		hud.tick();
	}
	
	public void render(Graphics2D g, double interpolation) {
		world.render(g, interpolation);
		hud.render(g);
	}
}
