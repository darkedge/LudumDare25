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
    
    private HashSet<Integer> pressed;
    private HashSet<Integer> released;
    private HashSet<Integer> held;
    private HashSet<Integer> newreleased;
    
    private Input() {
    	pressed 	= new HashSet<Integer>(RANGE);
    	released	= new HashSet<Integer>(RANGE);
    	held 		= new HashSet<Integer>(RANGE);
    	newreleased = new HashSet<Integer>(RANGE);
    }
    
    public void tick() {
    	pressed.removeAll(held);
    	held.addAll(pressed);
    	held.removeAll(released);
    	
    	released.clear();
    	released.addAll(newreleased);
    	newreleased.clear();
    }
    
    public boolean getKey(int key) {
    	return held.contains(key);
    }
    
    public boolean getKeyDown(int key) {
    	return pressed.contains(key);
    }
    
    public boolean getKeyUp(int key) {
    	return released.contains(key);
    }
    
    public Collection<Integer> getKeys() {
    	return held;
    }
    
    public Collection<Integer> getKeysDown() {
    	return pressed;
    }
    
    public Collection<Integer> getKeysUp() {
    	return released;
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
