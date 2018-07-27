package engine.menus;

import java.awt.event.*;

import engine.Resources;

public class MenuKeyListener implements KeyListener {
	protected Menu menu;
	
	public MenuKeyListener(Menu m) {
		this.menu = m;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == Resources.Commands.Take.getKey() ||
				e.getKeyCode() == KeyEvent.VK_ENTER) {
            menu.selectFocusedItem();
        } else if(e.getKeyChar() == Resources.Commands.Up.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_UP) {
			menu.decFocusedItem();
		} else if(e.getKeyChar() == Resources.Commands.Down.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_DOWN) {
			menu.incFocusedItem();
		} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			menu.exitMenu();
		}
		menu.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub
	}
}
