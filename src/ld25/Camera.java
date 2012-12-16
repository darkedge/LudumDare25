package ld25;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Camera {
	private Rectangle2D rect;
	private Graphics2D g;
	
	public Camera(int x, int y, int width, int height) {
		rect = new Rectangle2D.Float(x, y, width, height);
	}
	
	public void setPosition(int x, int y) {
		rect.setRect(x, y, rect.getWidth(), rect.getHeight());
	}
	
	public void drawImage(BufferedImage image, float x, float y) {
		Rectangle2D rectt = new Rectangle2D.Float(x, y, image.getWidth(), image.getHeight());
		if(rect.intersects(rectt)) {
			x -= rect.getX();
			y -= rect.getY();
			g.drawImage(image, Math.round(x), Math.round(y), null);
		}
	}

	public void setGraphics(Graphics2D g) {
		this.g = g;
	}
	
	public int getX() {
		return (int) Math.floor(rect.getX());
	}
	
	public int getY() {
		return (int) Math.floor(rect.getY());
	}
	
	public int getHeight() {
		return (int) Math.floor(rect.getHeight());
	}
	
	public int getWidth() {
		return (int) Math.floor(rect.getWidth());
	}
	
	public Graphics2D getGraphics() {
		return g;
	}

	public void drawRect(int x, int y, int width, int height) {
		Rectangle2D rectt = new Rectangle2D.Float(x, y, width, height);
		if(rect.intersects(rectt)) {
			x -= rect.getX();
			y -= rect.getY();
			g.drawRect(x, y, width, height);
		}
	}

	public void fillRect(int x, int y, int width, int height) {
		Rectangle2D rectt = new Rectangle2D.Float(x, y, width, height);
		if(rect.intersects(rectt)) {
			x -= rect.getX();
			y -= rect.getY();
			g.fillRect(x, y, width, height);
		}
	}
}
