package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {
	private Dungeon dungeon;
	private Map map;
	private Window win;
	
	public MyKeyListener() {
	}
	
	public MyKeyListener(Dungeon d, Map m, Window w) {
		this.dungeon = d;
		this.map = m;
		this.win = w;
	}
	
	public void refresh(Map m, Window w) {
		this.map = m;
		this.win = w;
	}
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	// French version (AZERTY keyboard)
	public void keyTyped(KeyEvent ke) {
		if(!this.map.isPlayerDead()) {
			if(ke.getKeyChar() == 'z') {
				this.map.getPlayer().getLooker().hide();
				this.map.movePlayerUp();
			} else if(ke.getKeyChar() == 's') {
				this.map.getPlayer().getLooker().hide();
				this.map.movePlayerDown();
			} else if(ke.getKeyChar() == 'q') {
				this.map.getPlayer().getLooker().hide();
				this.map.movePlayerLeft();
			} else if(ke.getKeyChar() == 'd') {
				this.map.getPlayer().getLooker().hide();
				this.map.movePlayerRight();
			} else if(ke.getKeyChar() == 'a') {
				this.map.getPlayer().repareWeapon();
			} else if(ke.getKeyChar() == 'e') {
				this.map.getPlayer().repareShield();
			} else if(ke.getKeyChar() == 'f') {
				this.map.checkPickableItem(map.getPlayer().pos.x, map.getPlayer().pos.y);
			} else if(ke.getKeyChar() == 'r') {
				this.map.generateDungeon();
			}
			// Refresh map on window
			this.win.refresh();
		} else {
			if(ke.getKeyChar()=='r') {
				this.dungeon.newGame();
			}
		}
	}
	
	// Canadian version (QWERTY keyboard)
	/*
	public void keyTyped(KeyEvent ke) {
		if(!this.map.isPlayerDead()) {
			if(ke.getKeyChar() == 'w') {
				this.map.getPlayer().getLooker().hide();
				this.win.printOnScreen();
				this.map.movePlayerUp();
			} else if(ke.getKeyChar() == 's') {
				this.map.getPlayer().getLooker().hide();
				this.win.printOnScreen();
				this.map.movePlayerDown();
			} else if(ke.getKeyChar() == 'a') {
				this.map.getPlayer().getLooker().hide();
				this.win.printOnScreen();
				this.map.movePlayerLeft();
			} else if(ke.getKeyChar() == 'd') {
				this.map.getPlayer().getLooker().hide();
				this.win.printOnScreen();
				this.map.movePlayerRight();
			} else if(ke.getKeyChar() == 'q') {
				this.map.getPlayer().repareWeapon();
			} else if(ke.getKeyChar() == 'e') {
				this.map.getPlayer().repareShield();
			} else if(ke.getKeyChar() == 'r') {
				this.win.pickTheme();
				this.map.generateDungeon();
			}
			// Refresh map on window
			this.win.refresh();
		} else {
			if(ke.getKeyChar()=='r') {
				this.map.newGame();
			}
		}
	}
	*/
}
