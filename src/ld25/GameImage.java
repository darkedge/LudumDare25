package ld25;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class GameImage {
	private static HashMap<String, BufferedImage> map = new HashMap<String, BufferedImage>();
	
	public static BufferedImage get(String name) {
		if(map.containsKey(name)) {
			return map.get(name);
		} else {
			BufferedImage image = null;
			try {
				image = ImageIO.read(GameImage.class.getResourceAsStream(name));
				map.put(name, image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return image;
		}
	}
}
