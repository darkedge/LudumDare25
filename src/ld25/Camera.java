package ld25;

import java.awt.Color;
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
	
	public void drawFlashlight(int x, int y, int width, int height, boolean right) {
		Rectangle2D rectt = new Rectangle2D.Float(right ? x : x - width, y - height / 2, width, height);
		if(rect.intersects(rectt)) {
			int r = height / 2;
			x -= rect.getX();
			y -= rect.getY();
			int[] xp = {x,
					x + (right ? width - r : -(width - r)),
					x + (right ? width - r : -(width - r))};
			int[] yp = {y,
					y + r ,
					y - r};
			g.setColor(new Color(1.0f, 1.0f, 0.2f, 0.2f));
			g.fillPolygon(xp, yp, 3);
			g.fillArc(right ? x + width - height : x - width,
			          y - r, height, height,
			          right ? -90 : 90,
			          180);
		}
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
