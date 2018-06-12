package engine;

import java.awt.event.KeyEvent;

import engine.menus.*;

public class InventoryMenuListKeyListener extends MenuKeyListener {
	public InventoryMenuListKeyListener(Menu m) {
		super(m);
	}


/**
 * keyPressed : keys and corresponding actions
 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == Resources.Commands.Take.getKey() ||
				e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(((InventoryMenuList)menu).dropWanted) { 
				((InventoryMenuList)menu).dropWanted = false; 
			} else {
				menu.selectFocusedItem();
			}
        } else if(e.getKeyChar() == Resources.Commands.Left.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_LEFT) {
        	((InventoryMenuList)menu).dropItem();
		} else if(e.getKeyChar() == Resources.Commands.Right.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_RIGHT) {
        	((InventoryMenuList)menu).dropItem();
		} else if(e.getKeyChar() == Resources.Commands.Up.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_UP) {
			if(((InventoryMenuList)menu).dropWanted) { 
				((InventoryMenuList)menu).dropWanted = false; 
			} else {
				menu.decFocusedItem();
			}
		} else if(e.getKeyChar() == Resources.Commands.Down.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(((InventoryMenuList)menu).dropWanted) { 
				((InventoryMenuList)menu).dropWanted = false; 
			} else {
				menu.incFocusedItem();
			}
		} else if(e.getKeyChar() == Resources.Commands.QuickAction1.getKey()) {
			if(((InventoryMenuList)menu).dropWanted) {
				((InventoryMenuList)menu).dropWanted = false;
			} else {
				((InventoryMenuList)menu).focusedItemToQuickAction1();
			}
		} else if(e.getKeyChar() == Resources.Commands.QuickAction2.getKey()) {
			if(((InventoryMenuList)menu).dropWanted) {
				((InventoryMenuList)menu).dropWanted = false;
			} else {
				((InventoryMenuList)menu).focusedItemToQuickAction2();
			}
		} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE ||
				e.getKeyChar() == Resources.Commands.Inventory.getKey()) {
			if(((InventoryMenuList)menu).dropWanted) { 
				((InventoryMenuList)menu).dropWanted = false; 
			} else {
				menu.exitMenu();
			}
		}
		menu.repaint();
	}
}
