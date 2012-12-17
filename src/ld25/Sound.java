package ld25;

import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	private static HashMap<String, Sound> map = new HashMap<String, Sound>();
	
	public static Sound get(String name) {
		if(map.containsKey(name)) {
			return map.get(name);
		} else {
			Sound sound = null;
			try {
				AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(name));
				Clip clip = AudioSystem.getClip();
				clip.open(ais);
				sound = new Sound(clip);
				map.put(name, sound);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return sound;
		}
	}
	
	public Sound(Clip clip) {
		this.clip = clip;
	}
	
	private Clip clip;
	
	public void play() {
		clip.stop();
		clip.setFramePosition(0);
		new Thread() {
			public void run() {
				synchronized(clip) {
					clip.start();
				}
			};
		}.start();
	}
}
