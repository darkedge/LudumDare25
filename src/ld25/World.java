package ld25;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Random;

import ld25.gameobject.Bandit;
import ld25.gameobject.GameObject;
import ld25.gameobject.GameObject.Direction;
import ld25.gameobject.Goat;
import ld25.gameobject.Gunner;
import ld25.gameobject.Player;
import ld25.gameobject.Sniper;
import ld25.util.GameMath;

/**
 * Game model
 * 
 * @author Marco Jonkers
 * 
 */
public class World {
	private static final int WIDTH = 32;
	private static final int HEIGHT = 32;
	private Cell[] map = new Cell[WIDTH * HEIGHT];
	private SpriteSheet sheet;
	private static final int TILE_SIZE = 16;
	private Player player;
	private Camera camera;
	private BufferedImage blood;
	private static final int ALERTNESS_TICKS = 5 * Game.TICK_RATE;
	public int alertness = 0;
	
	private int pixelWidth;
	private int pixelHeight;
	
	// Tile culling
	private int firstX;
	private int firstY;
	private int lastX;
	private int lastY;
	
	private int gunnerCount = 0;
	private int sniperCount = 0;
	private int goatCount = 0;
	private int banditCount = 0;

	private int totalGunnerCount = 0;
	private int totalSniperCount = 0;
	private int totalGoatCount = 0;
	private int totalBanditCount = 0;
	
	private boolean won = false;
	private boolean gameOver = false;
	
	private HashSet<GameObject> gameObjects = new HashSet<GameObject>();
	private HashSet<GameObject> disposed = new HashSet<GameObject>();
	
	public HashSet<GameObject> getGameObjects() {
		return gameObjects;
	}
	
	public int getTotalBanditCount() {
		return totalBanditCount;
	}
	public int getTotalGoatCount() {
		return totalGoatCount;
	}
	public int getTotalGunnerCount() {
		return totalGunnerCount;
	}
	public int getTotalSniperCount() {
		return totalSniperCount;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getGunnerCount() {
		return gunnerCount;
	}
	public int getSniperCount() {
		return sniperCount;
	}
	public int getGoatCount() {
		return goatCount;
	}
	public int getBanditCount() {
		return banditCount;
	}

	public World(Game game) {
		BufferedImage image = GameImage.get("/img/test.png");
		sheet = new SpriteSheet(image, TILE_SIZE);
		blood = GameImage.get("/img/blood.png");

		for(int i = 0; i < WIDTH * HEIGHT; i++) {
			map[i] = new Cell();
			map[i].tile = 0;
		}
		player = new Player(this, WIDTH / 2, HEIGHT / 2);
		insert(player);
		
		int dangerZone = 8;
		
		// Random goats
		Random r = Game.RANDOM;
		for(int i = 0; i < 50; i++) {
			int x, y;
			if(r.nextBoolean()) { // Left or right
				x = r.nextBoolean() ? r.nextInt(dangerZone) + WIDTH - dangerZone : r.nextInt(dangerZone);
				y = r.nextInt(HEIGHT);
			} else { // Up or down
				x = r.nextInt(WIDTH);
				y = r.nextBoolean() ? r.nextInt(dangerZone) + HEIGHT - dangerZone : r.nextInt(dangerZone);
			}
			if(isClear(x, y)) {
				int type = r.nextInt(4);
				switch(type) {
					case 0:
						goatCount++;
						insert(new Goat(this, x, y));
						break;
					case 1:
						gunnerCount++;
						insert(new Gunner(this, x, y));
						break;
					case 2:
						banditCount++;
						insert(new Bandit(this, x, y));
						break;
					case 3:
						sniperCount++;
						insert(new Sniper(this, x, y));
						break;
				}
			}
		}
		
		totalGoatCount = goatCount;
		totalGunnerCount = gunnerCount;
		totalBanditCount = banditCount;
		totalSniperCount = sniperCount;
		
		for(int i = 0; i < 50; i++) {
			int j = r.nextInt(WIDTH * HEIGHT);
			if(map[j].bush == null) {
				int x = (j % WIDTH) * getTileSize();
				int y = (j / WIDTH) * getTileSize();
				map[j].bush = new Bush(x, y);
			}
		}
		
		camera = new Camera(0, 0, game.getGameHeight(), game.getGameHeight());
		pixelWidth = WIDTH * TILE_SIZE;
		pixelHeight = HEIGHT * TILE_SIZE;
	}
	
	public Cell[] getMap() {
		return map;
	}

	private void insert(GameObject object) {
		gameObjects.add(object);
		int x = object.getMapX();
		int y = object.getMapY();
		map[y * WIDTH + x].gameObject = object;
	}

	public void tick() {
		if(alertness > 0) {
			checkLights();
		}
		if(getBush(player.getMapX(), player.getMapY()) != null) {
			alertness -= 3;
		} else {
			alertness--;
		}
		if(alertness < 0) {
			alertness = 0;
			relax();
		}
		
		for(GameObject o : gameObjects) {
			o.tick();
			if(o.isDisposed()) {
				switch(o.getType()) {
					case BANDIT:
						banditCount--;
						break;
					case GOAT:
						goatCount--;
						break;
					case GUNNER:
						gunnerCount--;
						break;
					case PLAYER:
						break;
					case SNIPER:
						sniperCount--;
						break;
				}
				disposed.add(o);
			}
		}
		for(GameObject o : disposed) {
			int x = o.getMapX();
			int y = o.getMapY();
			map[y * WIDTH + x].gameObject = null;
		}
		gameObjects.removeAll(disposed);
		disposed.clear();
		
		
		// Center around player
		if(player.getAttack() == Player.Attack.NONE) {
			int px = Math.round(player.getX()) + player.getWidth() / 2 - camera.getWidth() / 2;
			int py = Math.round(player.getY()) + player.getHeight() / 2 - camera.getHeight() / 2;
			px = (int) GameMath.clamp(px, 0, WIDTH * TILE_SIZE - camera.getWidth());
			py = (int) GameMath.clamp(py, 0, HEIGHT * TILE_SIZE
					- camera.getHeight());
			camera.setPosition(px, py);
		}

		// Tile culling
		int cx1 = camera.getX();
		int cy1 = camera.getY();
		int cx2 = cx1 + camera.getWidth();
		int cy2 = cy1 + camera.getHeight();
		firstX = GameMath.clamp(cx1 / TILE_SIZE, 0, WIDTH - 1);
		lastX = GameMath.clamp(cx2 / TILE_SIZE, 0, WIDTH - 1);
		firstY = GameMath.clamp(cy1 / TILE_SIZE, 0, HEIGHT - 1);
		lastY = GameMath.clamp(cy2 / TILE_SIZE, 0, HEIGHT - 1);
	}

	public void render(Graphics2D g, double interpolation) {
		camera.setGraphics(g);
		
		for (int x = firstX; x <= lastX; x++) {
			for (int y = firstY; y <= lastY; y++) {
				camera.drawImage(sheet.getImage(map[y * WIDTH + x].tile), x * TILE_SIZE, y * TILE_SIZE);
				if(map[y * WIDTH + x].blood) {
					camera.drawImage(blood, x * TILE_SIZE, y * TILE_SIZE);
				}
			}
		}

		for(GameObject o : gameObjects) {
			o.render(camera, interpolation);
		}
		
		// Bushes
		for (int x = firstX; x <= lastX; x++) {
			for (int y = firstY; y <= lastY; y++) {
				if(map[y * WIDTH + x].bush != null) {
					map[y * WIDTH + x].bush.render(camera, interpolation);
				}
			}
		}
	}
	
	public boolean isClear(int x, int y) {
		return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT && map[y * WIDTH + x].gameObject == null;
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

	public void move(GameObject object, Direction direction) {
		int x = object.getMapX();
		int y = object.getMapY();
		map[y * WIDTH + x].gameObject = null;
		switch(direction) {
			case DOWN:
				y++;
				break;
			case LEFT:
				x--;
				break;
			case RIGHT:
				x++;
				break;
			case UP:
				y--;
				break;
		}
		map[y * WIDTH + x].gameObject = object;
		object.setMapX(x);
		object.setMapY(y);
	}

	public GameObject getGameObjectAt(int x, int y) {
		if(x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
			return map[y * WIDTH + x].gameObject;
		}
		return null;
	}
	
	public void checkLights() {
		for(GameObject o : gameObjects) {
			if(o != player) {
				o.checkFlashlight();
			}
		}
	}

	public void shout(GameObject shouter) {
		alertness = ALERTNESS_TICKS;
		int rangeSq = 4 * 4;
		for(GameObject o : gameObjects) {
			int dx = o.getMapX() - shouter.getMapX();
			int dy = o.getMapY() - shouter.getMapY();
			if(dx * dx + dy * dy <= rangeSq) {
				o.alert();
			}
		}
	}

	public int getMaxAlertness() {
		return ALERTNESS_TICKS;
	}

	public void relax() {
		for(GameObject o : gameObjects) {
			o.relax();
		}
	}

	public Bush getBush(int x, int y) {
		return map[y * getWidth() + x].bush;
	}

	public void gameOver() {
		gameOver = true;
	}

	public boolean isGameOver() {
		return gameOver;
	}
	
	public boolean isWon() {
		return won;
	}
}
