package ld25;

/**
 * Some simple math tools that I like to use
 * @author Marco Jonkers
 *
 */
public class GameMath {
	static float clamp(float a, float min, float max) {
		return a < min ? min : a > max ? max : a;
	}
	
	static long clamp(long a, long min, long max) {
		return a < min ? min : a > max ? max : a;
	}
	
	static float max(float... a) {
		float max = a[0];
		for(float b : a) {
			if (b > max) max = b;
		}
		return max;
	}
	
	static float min(float... a) {
		float min = a[0];
		for(float b : a) {
			if (b < min) min = b;
		}
		return min;
	}
}
