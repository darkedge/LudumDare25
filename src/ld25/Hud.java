package ld25;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hud {
	private static final int MINIMAP_X = 484;
	private static final int MINIMAP_Y = 4;
	private static final int MINIMAP_WIDTH = 152;
	private static final int MINIMAP_HEIGHT = 152;
	
	private BufferedImage image;
	private final World world;
	
	// Minimap rectangle
	private int rectX;
	private int rectY;
	private int rectWidth;
	private int rectHeight;
	
	public Hud(World world) {
		this.world = world;
		try {
			image = ImageIO.read(Hud.class.getResourceAsStream("/img/hud.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		Camera camera = world.getCamera();
		int pw = world.getPixelWidth();
		int ph = world.getPixelHeight();
		
		rectX = (int) ((float) camera.getX() / pw * MINIMAP_WIDTH) + MINIMAP_X;
		rectY = (int) ((float) camera.getY() / ph * MINIMAP_HEIGHT) + MINIMAP_Y;
		rectWidth = (int) ((float) camera.getWidth() / pw * MINIMAP_WIDTH);
		rectHeight = (int) ((float) camera.getHeight() / ph * MINIMAP_HEIGHT);
	}
	
	public void render(Graphics2D g) {
		g.drawImage(image, 0, 0, null);
		g.setColor(Color.white);
		g.drawRect(rectX, rectY, rectWidth, rectHeight);
	}
}
