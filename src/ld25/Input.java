package ld25;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public enum Input implements KeyListener, MouseListener, MouseMotionListener{
    INSTANCE;
    private static final int RANGE = 256;
    
    private static HashSet<Integer> pressed = new HashSet<Integer>(RANGE);
    private static HashSet<Integer> released = new HashSet<Integer>(RANGE);
    private static HashSet<Integer> held = new HashSet<Integer>(RANGE);
    private static HashSet<Integer> newreleased = new HashSet<Integer>(RANGE);
    
    public static enum Button {
		QUIT, ACCEPT, LEFT, RIGHT, UP, DOWN, FIRE;
	}
    
    private static HashMap<Integer, Button> MAP = new HashMap<Integer, Button>();
    
    // Mouse
    private static int x = 0;
    private static int y = 0;
    private static int dx = 0;
    private static int dy = 0;
    private static int newx = 0;
    private static int newy = 0;
    
    static {
    	MAP.put(KeyEvent.VK_LEFT, Button.LEFT);
		MAP.put(KeyEvent.VK_RIGHT, Button.RIGHT);
		MAP.put(KeyEvent.VK_UP, Button.UP);
		MAP.put(KeyEvent.VK_DOWN, Button.DOWN);
    	MAP.put(KeyEvent.VK_A, Button.LEFT);
		MAP.put(KeyEvent.VK_D, Button.RIGHT);
		MAP.put(KeyEvent.VK_W, Button.UP);
		MAP.put(KeyEvent.VK_S, Button.DOWN);
		MAP.put(KeyEvent.VK_SPACE, Button.FIRE);
		MAP.put(KeyEvent.VK_CONTROL, Button.FIRE);
		MAP.put(KeyEvent.VK_Z, Button.FIRE);
		MAP.put(KeyEvent.VK_X, Button.FIRE);
		MAP.put(KeyEvent.VK_ENTER, Button.ACCEPT);
    }
    
    public static void tick() {
    	Input.pressed.removeAll(Input.held);
    	Input.held.addAll(Input.pressed);
    	Input.held.removeAll(Input.released);
    	
    	Input.released.clear();
    	Input.released.addAll(Input.newreleased);
    	Input.newreleased.clear();
    	
    	dx = newx - x;
    	dy = newy - y;
    	x = newx;
    	y = newy;
    }
    
    public static int getMouseX() {
    	return Input.x;
    }
    
    public static int getMouseY() {
    	return Input.y;
    }
    
    public static int getMouseDX() {
    	return Input.dx;
    }
    
    public static int getMouseDY() {
    	return Input.dy;
    }
    
    public static boolean getButton(Button button) {
    	for(int i : Input.MAP.keySet()) {
    		if(Input.MAP.get(i) == button && Input.held.contains(i)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean getButtonDown(Button button) {
    	for(int i : Input.MAP.keySet()) {
    		if(Input.MAP.get(i) == button && Input.pressed.contains(i)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean getButtonUp(Button button) {
    	for(int i : Input.MAP.keySet()) {
    		if(Input.MAP.get(i) == button && Input.released.contains(i)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean getKey(int key) {
    	return Input.held.contains(key);
    }
    
    public static boolean getKeyDown(int key) {
    	return Input.pressed.contains(key);
    }
    
    public static boolean getKeyUp(int key) {
    	return Input.released.contains(key);
    }
    
    public static Collection<Integer> getKeys() {
    	return Input.held;
    }
    
    public static Collection<Integer> getKeysDown() {
    	return Input.pressed;
    }
    
    public static Collection<Integer> getKeysUp() {
    	return Input.released;
    }
    
	@Override
	public void keyPressed(KeyEvent e) {
		if(!pressed.contains(e.getKeyCode()))
			pressed.add(e.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(!newreleased.contains(e.getKeyCode()))
			newreleased.add(e.getKeyCode());
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		newx = e.getX();
		newy = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Mouse entered game area
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Mouse left game area
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
