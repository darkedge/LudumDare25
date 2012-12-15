package ld25;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	public static Sound test = new Sound("/snd/test.wav");
	
	private Clip clip;
	
	public Sound(String string) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(string));
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
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
