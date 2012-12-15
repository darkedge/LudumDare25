package ld25;

public abstract class Picker {
	protected float min;
	protected float max;
	protected float position;
	protected float var;
	protected boolean forward;
	protected int value;
	
	public Picker(float min, float max, float start, int steps, boolean forward) {
		this.min = min;
		this.max = max;
		this.forward = forward;
		position = start;
		if(steps == 0) {
			var = 0;
		} else {
			var = (steps - 1) / (max - min);
		}
	}
	
	public Picker(float min, float max, int steps) {
		this(min, max, 0.0f, steps, true);
	}
	
	public Picker(float min, float max, boolean forward) {
		this(min, max, 0.0f, 0, forward);
	}
	
	public Picker(float min, float max) {
		this(min, max, 0.0f, 0, true);
	}
	
	public Picker(float min, float max, float start, boolean forward) {
		this(min, max, start, 0, forward);
	}
	
	public abstract void tick(float f);
	
	public int getValue() {
		return value;
	}
	
	public float getPosition() {
		return position;
	}
}
