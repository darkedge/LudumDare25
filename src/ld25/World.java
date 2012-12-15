package ld25;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.imageio.ImageIO;

/**
 * Game model
 * 
 * @author Marco Jonkers
 * 
 */
public class World {
	private static final int WIDTH = 96;
	private static final int HEIGHT = 96;
	private int[] tiles;
	private GameObject[] map;
	private SpriteSheet sheet;
	private static final int TILE_SIZE = 16;
	private Player player;
	private Overlay overlay;
	private Camera camera;
	
	private int pixelWidth;
	private int pixelHeight;
	
	// Tile culling
	private int firstX;
	private int firstY;
	private int lastX;
	private int lastY;
	
	private Collection<GameObject> entities = new HashSet<GameObject>();

	public World(Game game) {
		try {
			BufferedImage image = ImageIO.read(World.class.getResourceAsStream("/test.png"));
			sheet = new SpriteSheet(image, TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}

		tiles = new int[WIDTH * HEIGHT];
		Arrays.fill(tiles, 0);
		player = new Player(this, 0, 0);
		overlay = new Overlay();
		camera = new Camera(0, 0, game.getHeight(), game.getHeight());
		pixelWidth = WIDTH * TILE_SIZE;
		pixelHeight = HEIGHT * TILE_SIZE;
	}

	public void tick() {
		player.tick();
		
		// Center around player
		int px = player.getX() + player.getWidth() / 2 - getCamera().getWidth() / 2;
		int py = player.getY() + player.getHeight() / 2 - getCamera().getHeight() / 2;
		px = (int) GameMath.clamp(px, 0, WIDTH * TILE_SIZE - getCamera().getWidth());
		py = (int) GameMath.clamp(py, 0, HEIGHT * TILE_SIZE
				- getCamera().getHeight());
		getCamera().setPosition(px, py);

		// Tile culling
		int cx1 = getCamera().getX();
		int cy1 = getCamera().getY();
		int cx2 = cx1 + getCamera().getWidth();
		int cy2 = cy1 + getCamera().getHeight();
		firstX = GameMath.clamp(cx1 / TILE_SIZE, 0, WIDTH - 1);
		lastX = GameMath.clamp(cx2 / TILE_SIZE, 0, WIDTH - 1);
		firstY = GameMath.clamp(cy1 / TILE_SIZE, 0, HEIGHT - 1);
		lastY = GameMath.clamp(cy2 / TILE_SIZE, 0, HEIGHT - 1);

		overlay.tick();
	}

	public void render(Graphics2D g, double interpolation) {
		getCamera().setGraphics(g);
		
		for (int x = firstX; x <= lastX; x++) {
			for (int y = firstY; y <= lastY; y++) {
				getCamera().drawImage(sheet.getImage(tiles[y * WIDTH + x]), x * TILE_SIZE, y * TILE_SIZE);
			}
		}

		player.render(getCamera(), interpolation);
		overlay.render(getCamera(), interpolation);
	}

	public Camera getCamera() {
		return camera;
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public int getTileSize() {
		return TILE_SIZE;
	}
	
	public int getPixelWidth() {
		return pixelWidth;
	}
	
	public int getPixelHeight() {
		return pixelHeight;
	}
}
