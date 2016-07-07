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
		if(!this.map.isPlayerDead()) {
			if(ke.getKeyChar() == 'z') {
				this.map.getLooker().hide();
				this.win.printOnScreen();
				this.map.movePlayerUp();
			} else if(ke.getKeyChar() == 's') {
				this.map.getLooker().hide();
				this.win.printOnScreen();
				this.map.movePlayerDown();
			} else if(ke.getKeyChar() == 'q') {
				this.map.getLooker().hide();
				this.win.printOnScreen();
				this.map.movePlayerLeft();
			} else if(ke.getKeyChar() == 'd') {
				this.map.getLooker().hide();
				this.win.printOnScreen();
				this.map.movePlayerRight();
			} else if(ke.getKeyChar() == 'w') {
				this.map.getPlayer().repareWeapon();
			} else if(ke.getKeyChar() == 'x') {
				this.map.getPlayer().repareShield();
			} else if(ke.getKeyChar() == 'r') {
				this.win.pickTheme();
				this.map.generateDungeon();
			} else if(ke.getKeyChar() == 'l') {
				this.map.getLooker().placeOn(this.map.getPlayer().pos.x, this.map.getPlayer().pos.y);
				this.map.getLooker().show();
			}
			// Refresh map on window
			this.win.refresh();
		} else {
			if(ke.getKeyChar()=='r') {
				this.map.newGame();
			}
		}
	}
}
