package ld25.util;

public class Looper extends Picker {
	public Looper(float min, float max, float start, int steps, boolean forward) {
		super(min, max, start, steps, forward);
	}
	
	public Looper(float min, float max, int steps) {
		this(min, max, 0.0f, steps, true);
	}
	
	public Looper(float min, float max, boolean forward) {
		this(min, max, 0.0f, 0, forward);
	}
	
	public Looper(float min, float max) {
		this(min, max, 0.0f, 0, true);
	}
	
	public Looper(float min, float max, float start, boolean forward) {
		this(min, max, start, 0, forward);
	}

	@Override
	public void tick(float f) {
		if(forward) position += f;
		else position -= f;
		if(position > max) {
			position -= max;
		} else if (position < min) {
			position = max - (min - position);
		}
		if(var != 0) value = Math.round((position - min) * var);
	}
}
