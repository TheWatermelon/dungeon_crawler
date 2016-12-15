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
	@Override
	public void keyPressed(KeyEvent e) {
		if(!this.map.isPlayerDead()) {
			if(e.getKeyChar() == Resources.Commands.Up.getKey() ||
					e.getKeyCode() == KeyEvent.VK_UP) {
				this.map.movePlayerUp();
			} else if(e.getKeyChar() == Resources.Commands.Down.getKey() ||
					e.getKeyCode() == KeyEvent.VK_DOWN) {
				this.map.movePlayerDown();
			} else if(e.getKeyChar() == Resources.Commands.Left.getKey() ||
					e.getKeyCode() == KeyEvent.VK_LEFT) {
				this.map.movePlayerLeft();
			} else if(e.getKeyChar() == Resources.Commands.Right.getKey() ||
					e.getKeyCode() == KeyEvent.VK_RIGHT) {
				this.map.movePlayerRight();
			} else if(e.getKeyChar() == Resources.Commands.RepareWeapon.getKey()) {
				this.map.getPlayer().repareWeapon();
			} else if(e.getKeyChar() == Resources.Commands.RepareShield.getKey()) {
				this.map.getPlayer().repareShield();
			} else if(e.getKeyChar() == Resources.Commands.Take.getKey() ||
					e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.map.checkPickableItem(map.getPlayer().pos.x, map.getPlayer().pos.y);
			} else if(e.getKeyChar() == Resources.Commands.Inventory.getKey()) {
				this.win.showInventoryMenu();
			} else if(e.getKeyChar() == Resources.Commands.Restart.getKey()) {
				this.map.generateDungeon();
			} else if(e.getKeyChar() == Resources.Commands.Pause.getKey()) {
				this.win.showPauseMenu();
				return;
			}
			// Refresh map on window
			this.win.refresh();
		} else {
			if(e.getKeyChar() == Resources.Commands.Restart.getKey()) {
				this.dungeon.newGame();
			} else if(e.getKeyChar() == Resources.Commands.Pause.getKey()) {
				this.win.showPauseMenu();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent ke) {
	}
}
