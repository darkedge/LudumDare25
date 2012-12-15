package ld25;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Camera {
	private Rectangle2D rect;
	
	public Camera(int x, int y, int width, int height) {
		rect = new Rectangle2D.Float(x, y, width, height);
	}
	
	public void setPosition(int x, int y) {
		rect.setRect(x, y, rect.getWidth(), rect.getHeight());
	}
	
	public void drawImage(BufferedImage image, int x, int y) {
		x -= rect.getX();
		y -= rect.getY();
		Rectangle2D rectt = new Rectangle2D.Float(x, y, image.getWidth(), image.getHeight());
		if(rect.intersects(rectt)) {
			
		}
	}
}
