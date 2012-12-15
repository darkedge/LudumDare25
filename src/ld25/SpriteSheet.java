package ld25;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SpriteSheet {
	private int tw;
	private int th;
	private int horTiles;
	private int verTiles;
	private int pw;
	private int ph;
	private BufferedImage image;
	private BufferedImage[] slices;

	public SpriteSheet(BufferedImage image, int tw, int th) {
		this.image = image;
		this.tw = tw;
		this.th = th;
		pw = image.getWidth();
		ph = image.getHeight();
		horTiles = pw / tw;
		verTiles = ph / th;
		slices = new BufferedImage[horTiles * verTiles];
		split();
	}
	
	private void split() {
		for(int i = 0; i < horTiles * verTiles; i++) {
			int x = i % horTiles;
			int y = i / horTiles;
			slices[i] = new BufferedImage(tw, th, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = slices[i].createGraphics();
			g.drawImage(image, -x * tw, -y * th, null);
			g.dispose();
		}
	}

	public SpriteSheet(BufferedImage image, int ts) {
		this(image, ts, ts);
	}
	
	public BufferedImage getImage(int i) {
		return slices[i];
	}
	
	public BufferedImage getImage(int x, int y) {
		return slices[y * horTiles + x];
	}

	public int getSpriteWidth() {
		return tw;
	}
	
	public int getSpriteHeight() {
		return th;
	}
}
