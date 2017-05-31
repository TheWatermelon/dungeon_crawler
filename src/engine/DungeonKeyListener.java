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

	@Override
	public void keyPressed(KeyEvent e) {
		if(!this.map.isPlayerDead()) {
			if(e.getKeyChar() == Resources.Commands.Up.getKey() ||
					e.getKeyCode() == KeyEvent.VK_UP) {
				this.map.moveTo(Direction.North);
			} else if(e.getKeyChar() == Resources.Commands.Down.getKey() ||
					e.getKeyCode() == KeyEvent.VK_DOWN) {
				this.map.moveTo(Direction.South);
			} else if(e.getKeyChar() == Resources.Commands.Left.getKey() ||
					e.getKeyCode() == KeyEvent.VK_LEFT) {
				this.map.moveTo(Direction.West);
			} else if(e.getKeyChar() == Resources.Commands.Right.getKey() ||
					e.getKeyCode() == KeyEvent.VK_RIGHT) {
				this.map.moveTo(Direction.East);
			} else if(e.getKeyChar() == Resources.Commands.QuickAction1.getKey()) {
				this.map.getPlayer().getInventory().use(this.map.getPlayer().getInventory().getQuickItem1());
			} else if(e.getKeyChar() == Resources.Commands.QuickAction2.getKey()) {
				this.map.getPlayer().getInventory().use(this.map.getPlayer().getInventory().getQuickItem2());
			} else if(e.getKeyChar() == Resources.Commands.Take.getKey() ||
					e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.map.checkAction(map.getPlayer().pos.x, map.getPlayer().pos.y);
			} else if(e.getKeyChar() == Resources.Commands.Inventory.getKey()) {
				this.win.showInventoryMenu();
			} else if(e.getKeyChar() == Resources.Commands.Restart.getKey()) {
				this.map.generateDungeon();
				this.win.getDungeonPanel().initPlayerRectangle();
			} else if(e.getKeyChar() == Resources.Commands.Pause.getKey()) {
				this.win.showPauseMenu();
				return;
			} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				if(this.win.getMap().isFireMode()) {
					this.win.getMap().fireMode = false;
				} else {
					this.win.showPauseMenu();
					return;	
				}
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
