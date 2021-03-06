package engine.menus;

import java.awt.event.*;

import engine.Resources;

public class SaveMenuKeyListener implements KeyListener {
	protected Menu menu;
	
	public SaveMenuKeyListener(Menu m) {
		this.menu = m;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == Resources.Commands.Take.getKey() ||
				e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(((SaveMenu)menu).deleteWanted) { 
				((SaveMenu)menu).deleteWanted = false; 
			} else {
				menu.selectFocusedItem();
			}
        } else if(e.getKeyChar() == Resources.Commands.Left.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_LEFT) {
        	((SaveMenu)menu).deleteFocusedFile();
		} else if(e.getKeyChar() == Resources.Commands.Right.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_RIGHT) {
        	((SaveMenu)menu).deleteFocusedFile();
		} else if(e.getKeyChar() == Resources.Commands.Up.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_UP) {
			if(((SaveMenu)menu).deleteWanted) { 
				((SaveMenu)menu).deleteWanted = false; 
			} else {
				menu.decFocusedItem();
			}
		} else if(e.getKeyChar() == Resources.Commands.Down.getKey() ||
        		e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(((SaveMenu)menu).deleteWanted) { 
				((SaveMenu)menu).deleteWanted = false; 
			} else {
				menu.incFocusedItem();
			}
		} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(((SaveMenu)menu).deleteWanted) { 
				((SaveMenu)menu).deleteWanted = false; 
			} else {
				menu.exitMenu();
			}
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
