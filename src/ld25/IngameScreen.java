package ld25;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import ld25.Input.Button;

public class IngameScreen {
	private Game game;
	private World world;
	private Hud hud;
	private BufferedImage gameover = GameImage.get("/img/gameover.png");
	private BufferedImage won = GameImage.get("/img/won.png");

	public IngameScreen(Game game) {
		this.game = game;
		
		world = new World(game);
		hud = new Hud(world);
	}
	
	public void tick() {
		if(world.isGameOver() || world.isWon()) {
			if(Input.getButtonDown(Button.ACCEPT)) {
				world = new World(game);
				hud = new Hud(world);
			}
		} else {
			world.tick();
			hud.tick();
		}
	}
	
	public void render(Graphics2D g, double interpolation) {
		world.render(g, interpolation);
		hud.render(g);
		if(world.isGameOver()) {
			g.drawImage(gameover, 120 - gameover.getWidth() / 2, 120 - gameover.getHeight() / 2, null);
		}
		if(world.isWon()) {
			g.drawImage(gameover, 120 - gameover.getWidth() / 2, 120 - gameover.getHeight() / 2, null);
		}
	}
}
