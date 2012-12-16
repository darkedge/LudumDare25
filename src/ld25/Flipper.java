package ld25;

/**
 * TODO: add a delegate for customized interpolation
 * @author Marco Jonkers
 *
 */
public class Flipper extends Picker {	
	public Flipper(float min, float max, float start, int steps, boolean forward) {
		super(min, max, start, steps, forward);
	}
	
	public Flipper(float min, float max, int steps) {
		this(min, max, 0.0f, steps, true);
	}
	
	public Flipper(float min, float max, boolean forward) {
		this(min, max, 0.0f, 0, forward);
	}
	
	public Flipper(float min, float max) {
		this(min, max, 0.0f, 0, true);
	}
	
	public Flipper(float min, float max, float start, boolean forward) {
		this(min, max, start, 0, forward);
	}
	
	/**
	 * Updates the position with f.
	 */
	public void tick(float f) {
		if(forward) position += f;
		else position -= f;
		if(position > max) {
			position = max - (position - max);
			forward = !forward;
		} else if (position < min) {
			position = min + (min - position);
			forward = !forward;
		}
		if(var != 0) value = Math.round((position - min) * var);
	}
	
	public void tickPercentage(float p) {
		tick(p * (max - min));
	}
}
