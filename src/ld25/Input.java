package ld25;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.HashSet;

public enum Input implements KeyListener, MouseListener, MouseMotionListener{
    INSTANCE;
    private static final int RANGE = 256;
    
    private HashSet<Integer> pressed = new HashSet<Integer>(RANGE);
    private HashSet<Integer> released = new HashSet<Integer>(RANGE);
    private HashSet<Integer> held = new HashSet<Integer>(RANGE);
    private HashSet<Integer> newreleased = new HashSet<Integer>(RANGE);
    
    public static void tick() {
    	INSTANCE.pressed.removeAll(INSTANCE.held);
    	INSTANCE.held.addAll(INSTANCE.pressed);
    	INSTANCE.held.removeAll(INSTANCE.released);
    	
    	INSTANCE.released.clear();
    	INSTANCE.released.addAll(INSTANCE.newreleased);
    	INSTANCE.newreleased.clear();
    }
    
    public static boolean getKey(int key) {
    	return INSTANCE.held.contains(key);
    }
    
    public static boolean getKeyDown(int key) {
    	return INSTANCE.pressed.contains(key);
    }
    
    public static boolean getKeyUp(int key) {
    	return INSTANCE.released.contains(key);
    }
    
    public static Collection<Integer> getKeys() {
    	return INSTANCE.held;
    }
    
    public static Collection<Integer> getKeysDown() {
    	return INSTANCE.pressed;
    }
    
    public static Collection<Integer> getKeysUp() {
    	return INSTANCE.released;
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
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
