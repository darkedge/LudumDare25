package ld25;

public abstract class GameObject {
	protected final World world;
	protected float x;
	protected float y;
	protected int mapx;
	protected int mapy;
	protected int targetX;
	protected int targetY;
	
	public GameObject(World world, int mapx, int mapy) {
		this.world = world;
		this.mapx = mapx;
		this.mapy = mapy;
		x = mapx * world.getTileSize();
		y = mapy * world.getTileSize();
	}	
	
	public abstract void tick();

	public abstract void render(Camera camera, double interpolation);

	public int getMapX() {
		return mapx;
	}
	
	public int getMapY() {
		return mapy;
	}

	public int getTargetX() {
		return targetX;
	}
	
	public int getTargetY() {
		return targetY;
	}
}
