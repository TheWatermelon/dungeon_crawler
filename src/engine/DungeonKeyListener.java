package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DungeonKeyListener implements KeyListener {
	private Dungeon dungeon;
	private Map map;
	private Window win;
	
	public DungeonKeyListener() {
	}
	
	public DungeonKeyListener(Dungeon d, Map m, Window w) {
		this.dungeon = d;
		this.map = m;
		this.win = w;
	}
	
	public void refresh(Map m, Window w) {
		this.map = m;
		this.win = w;
	}

	// French version (AZERTY keyboard)
	public void keyPressed(KeyEvent e) {
		if(!this.map.isPlayerDead()) {
			if(e.getKeyChar() == 'z' ||
					e.getKeyCode() == KeyEvent.VK_UP) {
				this.map.movePlayerUp();
			} else if(e.getKeyChar() == 's' ||
					e.getKeyCode() == KeyEvent.VK_DOWN) {
				this.map.movePlayerDown();
			} else if(e.getKeyChar() == 'q' ||
					e.getKeyCode() == KeyEvent.VK_LEFT) {
				this.map.movePlayerLeft();
			} else if(e.getKeyChar() == 'd' ||
					e.getKeyCode() == KeyEvent.VK_RIGHT) {
				this.map.movePlayerRight();
			} else if(e.getKeyChar() == 'a') {
				this.map.getPlayer().repareWeapon();
			} else if(e.getKeyChar() == 'e') {
				this.map.getPlayer().repareShield();
			} else if(e.getKeyChar() == 'f' ||
					e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.map.checkPickableItem(map.getPlayer().pos.x, map.getPlayer().pos.y);
			} else if(e.getKeyChar() == 'r') {
				this.map.generateDungeon();
			} else if(e.getKeyChar() == 'p') {
				this.win.showPauseMenu();
				return;
			}
			// Refresh map on window
			this.win.refresh();
		} else {
			if(e.getKeyChar()=='r') {
				this.dungeon.newGame();
			} else if(e.getKeyChar() == 'p') {
				this.win.showPauseMenu();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void keyTyped(KeyEvent ke) {
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
