package engine;

import java.awt.event.KeyEvent;

import engine.menus.*;

public class InventoryMenuKeyListener extends MenuKeyListener {
	public InventoryMenuKeyListener(Menu m) {
		super(m);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == Resources.Commands.Take.getKey() ||
				e.getKeyCode() == KeyEvent.VK_ENTER) {
            menu.selectFocusedItem();
        } else if(e.getKeyChar() == Resources.Commands.Left.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_LEFT) {
        	((InventoryMenuGrid)menu).dropItem();
		} else if(e.getKeyChar() == Resources.Commands.Right.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_RIGHT) {
        	((InventoryMenuGrid)menu).dropItem();
		} else if(e.getKeyChar() == Resources.Commands.Up.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_UP) {
			((InventoryMenuGrid)menu).dropWanted = false;
			menu.decFocusedItem();
		} else if(e.getKeyChar() == Resources.Commands.Down.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_DOWN) {
			((InventoryMenuGrid)menu).dropWanted = false;
			menu.incFocusedItem();
		} else if(e.getKeyChar() == Resources.Commands.RepareWeapon.getKey() ||
				e.getKeyChar() == Resources.Commands.RepareShield.getKey()) {
			((InventoryMenuGrid)menu).dropWanted = false;
			((InventoryMenuGrid)menu).repareItem();
		} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			((InventoryMenuGrid)menu).dropWanted = false;
			menu.exitMenu();
		}
		menu.repaint();
	}
}
