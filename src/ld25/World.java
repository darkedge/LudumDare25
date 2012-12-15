package ld25;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * Game model
 * @author Marco Jonkers
 *
 */
public class World {
	private static final int WIDTH = 128;
	private static final int HEIGHT = 128;
	private int[] tiles;
	private SpriteSheet sheet;
	private static final int TILE_SIZE = 16;
	private Player player;
	private Overlay overlay;
	
	public World() {
		try {
			BufferedImage image = ImageIO.read(World.class.getResourceAsStream("/test.png"));
			sheet = new SpriteSheet(image, TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tiles = new int[WIDTH * HEIGHT];
		Arrays.fill(tiles, 0);
		player = new Player(0,0);
		overlay = new Overlay();
	}
	
	public void tick() {
		player.tick();
		overlay.tick();
	}
	
	public void render(Camera camera, double interpolation) {
		int px = player.getX() + player.getWidth() / 2 - camera.getWidth() / 2;
		int py = player.getY() + player.getHeight() / 2 - camera.getHeight() / 2;
		px = (int) GameMath.clamp(px, 0, WIDTH * TILE_SIZE - camera.getWidth());
		py = (int) GameMath.clamp(py, 0, HEIGHT * TILE_SIZE - camera.getHeight());
		camera.setPosition(px, py);
		
		int cx1 = camera.getX();
		int cy1 = camera.getY();
		int cx2 = cx1 + camera.getWidth();
		int cy2 = cy1 + camera.getHeight();
		int firstX = GameMath.clamp(cx1 / TILE_SIZE, 0, WIDTH - 1);
		int lastX = GameMath.clamp(cx2 / TILE_SIZE, 0, WIDTH - 1);
		int firstY = GameMath.clamp(cy1 / TILE_SIZE, 0, HEIGHT - 1);
		int lastY = GameMath.clamp(cy2 / TILE_SIZE, 0, HEIGHT - 1);
		
		for(int x = firstX; x <= lastX; x++) {
			for (int y = firstY; y <= lastY; y++) {
				camera.drawImage(sheet.getImage(tiles[y * WIDTH + x]), x * TILE_SIZE, y * TILE_SIZE);
			}
		}
		
		player.render(camera, interpolation);
		overlay.render(camera, interpolation);
	}
}
