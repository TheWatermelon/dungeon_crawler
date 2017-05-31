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
        	((InventoryMenu)menu).dropItem();
		} else if(e.getKeyChar() == Resources.Commands.Right.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_RIGHT) {
        	((InventoryMenu)menu).dropItem();
		} else if(e.getKeyChar() == Resources.Commands.Up.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_UP) {
			menu.decFocusedItem();
		} else if(e.getKeyChar() == Resources.Commands.Down.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_DOWN) {
			menu.incFocusedItem();
		} else if(e.getKeyChar() == Resources.Commands.QuickAction1.getKey() ||
				e.getKeyChar() == Resources.Commands.QuickAction2.getKey()) {
			((InventoryMenuGrid)menu).repareItem();
		} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			menu.exitMenu();
		}
		menu.repaint();
	}
}
