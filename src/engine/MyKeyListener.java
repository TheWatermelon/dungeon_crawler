package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {
	private Map map;
	private Window win;
	
	public MyKeyListener() {
	}
	
	public MyKeyListener(Map m, Window w) {
		this.map = m;
		this.win = w;
	}
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void keyTyped(KeyEvent ke) {
		if(ke.getKeyChar() == 'z') {
			this.map.movePlayerUp();
		} else if(ke.getKeyChar() == 's') {
			this.map.movePlayerDown();
		} else if(ke.getKeyChar() == 'q') {
			this.map.movePlayerLeft();
		} else if(ke.getKeyChar() == 'd') {
			this.map.movePlayerRight();
		} else if(ke.getKeyChar() == 'r') {
			this.map.generateDungeon();
		}
		// Refresh map on window
		this.win.setLabel(this.map.generateLabel());
		this.win.refresh();
	}
}
