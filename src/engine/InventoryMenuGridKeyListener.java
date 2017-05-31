package engine;

import java.awt.event.KeyEvent;

import engine.menus.InventoryMenuGrid;
import engine.menus.Menu;

public class InventoryMenuGridKeyListener extends MenuKeyListener {
	public InventoryMenuGridKeyListener(Menu m) {
		super(m);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == Resources.Commands.Take.getKey() ||
				e.getKeyCode() == KeyEvent.VK_ENTER) {
            menu.selectFocusedItem();
        } else if(e.getKeyChar() == Resources.Commands.Left.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_LEFT) {
        	if(((InventoryMenuGrid)menu).dropWanted) {
				((InventoryMenuGrid)menu).dropWanted = false;
			} else {
				menu.decFocusedItem();
			}
		} else if(e.getKeyChar() == Resources.Commands.Right.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(((InventoryMenuGrid)menu).dropWanted) {
				((InventoryMenuGrid)menu).dropWanted = false;
			} else {
				menu.incFocusedItem();
			}
		} else if(e.getKeyChar() == Resources.Commands.Up.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_UP) {
			if(((InventoryMenuGrid)menu).dropWanted) {
				((InventoryMenuGrid)menu).dropWanted = false;
			} else {
				((InventoryMenuGrid)menu).decLineFocusedItem();
			}
		} else if(e.getKeyChar() == Resources.Commands.Down.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(((InventoryMenuGrid)menu).dropWanted) {
				((InventoryMenuGrid)menu).dropWanted = false;
			} else {
				((InventoryMenuGrid)menu).incLineFocusedItem();
			}
		} else if(e.getKeyChar() == Resources.Commands.QuickAction1.getKey()) {
			if(((InventoryMenuGrid)menu).dropWanted) {
				((InventoryMenuGrid)menu).dropWanted = false;
			} else {
				((InventoryMenuGrid)menu).focusedItemToQuickAction1();
			}
		} else if(e.getKeyChar() == Resources.Commands.QuickAction2.getKey()) {
			if(((InventoryMenuGrid)menu).dropWanted) {
				((InventoryMenuGrid)menu).dropWanted = false;
			} else {
				((InventoryMenuGrid)menu).focusedItemToQuickAction2();
			}
		} else if(e.getKeyChar() == Resources.Commands.Inventory.getKey()) {
			if(((InventoryMenuGrid)menu).dropWanted) {
				((InventoryMenuGrid)menu).dropWanted = false;
			} else {
				((InventoryMenuGrid)menu).repareItem();
			}
		} else if(e.getKeyChar() == Resources.Commands.Pause.getKey()) {
			((InventoryMenuGrid)menu).dropItem();
		} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(((InventoryMenuGrid)menu).dropWanted) {
				((InventoryMenuGrid)menu).dropWanted = false;
			} else {
				menu.exitMenu();
			}
		}
		menu.repaint();
	}
}
