package ld25;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ld25.gameobject.GameObject;

public class Hud {
	private static final int MINIMAP_X = 242;
	private static final int MINIMAP_Y = 2;
	private static final int MINIMAP_WIDTH = 76;
	private static final int MINIMAP_HEIGHT = 76;
	
	private BufferedImage image;
	private final World world;
	private final int pw;
	private final int ph;
	private final int w;
	private final int h;
	private BufferedImage vignet = GameImage.get("/img/vignet.png");
	
	// Minimap rectangle
	private int rectX;
	private int rectY;
	private int rectWidth;
	private int rectHeight;
	
	public Hud(World world) {
		this.world = world;
		pw = world.getPixelWidth();
		ph = world.getPixelHeight();
		w = world.getWidth();
		h = world.getHeight();
		try {
			image = ImageIO.read(Hud.class.getResourceAsStream("/img/hud.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		Camera camera = world.getCamera();
		
		rectX = (int) ((float) camera.getX() / pw * MINIMAP_WIDTH) + MINIMAP_X;
		rectY = (int) ((float) camera.getY() / ph * MINIMAP_HEIGHT) + MINIMAP_Y;
		rectWidth = (int) ((float) camera.getWidth() / pw * MINIMAP_WIDTH);
		rectHeight = (int) ((float) camera.getHeight() / ph * MINIMAP_HEIGHT);
	}
	
	public void render(Graphics2D g) {
		g.drawImage(vignet, 0, 0, null);
		g.drawImage(image, 240, 0, null);
		
		// minimap dots
		g.setColor(Color.white);
		for(GameObject o : world.getGameObjects()) {
			int x = (int) ((float) o.getMapX() / w * MINIMAP_WIDTH) + MINIMAP_X;
			int y = (int) ((float) o.getMapY() / h * MINIMAP_HEIGHT) + MINIMAP_Y;
			g.drawLine(x, y, x, y);
		}
		
		// minimap camera rect
		g.setColor(Color.white);
		g.drawRect(rectX, rectY, rectWidth, rectHeight);
		
		// enemy counts
		int x = 265;
		int y = 109;
		g.drawString(String.valueOf(world.getSniperCount()), x, y);
		y += 19;
		g.drawString(String.valueOf(world.getGunnerCount()), x, y);
		y += 19;
		g.drawString(String.valueOf(world.getBanditCount()), x, y);
		y += 19;
		g.drawString(String.valueOf(world.getGoatCount()), x, y);
		y += 19;
		
		x = 294;
		y = 109;
		g.drawString(String.valueOf(world.getTotalSniperCount() - world.getSniperCount()), x, y);
		y += 19;
		g.drawString(String.valueOf(world.getTotalGunnerCount() - world.getGunnerCount()), x, y);
		y += 19;
		g.drawString(String.valueOf(world.getTotalBanditCount() - world.getBanditCount()), x, y);
		y += 19;
		g.drawString(String.valueOf(world.getTotalGoatCount() - world.getGoatCount()), x, y);
		y += 19;
		
		// player hitpoints
		g.setColor(new Color(130, 0, 0));
		int width = (int) ((float) world.getPlayer().getHealth() / world.getPlayer().getMaxHealth() * 66);
		g.fillRect(247, 195, width, 8);
	}
}
