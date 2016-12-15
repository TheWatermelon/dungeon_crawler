package engine;

import java.awt.event.*;
import java.util.*;

public class KeyLogger extends Observable implements KeyListener {
	protected char key;
	
	public KeyLogger(Observer o) {
		this.key = 0;
		addObserver(o);
	}
	
	public char getKey() { return this.key; }
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyChar()>32 && e.getKeyChar()<127) {
			this.key = e.getKeyChar();
			setChanged();
			notifyObservers();
		} else {
			this.key = 0;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
