package engine.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import engine.*;

public class OptionsMenu extends Menu {
	private static final long serialVersionUID = 1L;
	
	public OptionsMenu(Window win) {
		super(win);
		initPanel();
	}


	@Override
	public void selectFocusedItem() {
		if(focusedItem == 0) {
			win.showCommandsMenu();
		} else if(focusedItem == 1) {	// Cycle difficulty
			Resources.getInstance().difficulty=(Resources.getInstance().difficulty+1)%3;
		} else if(focusedItem == 2) {
			Resources.getInstance().theme = win.getDungeonPanel().pickTheme();
		} else {
			focusedItem=0;
			if(win.isMainMenu()) {
				win.showMainMenu();
			} else {
				win.showPauseMenu();
			}
		}
		repaint();
	}

	@Override
	protected void initPanel() {
		focusedItem=0;
		items = new String[4];
		
		items[0] = "Commands";
		
		String difficulty="";
		if(Resources.getInstance().difficulty==0) { difficulty="Easy"; }
		else if(Resources.getInstance().difficulty==1) { difficulty="Normal"; }
		else if(Resources.getInstance().difficulty==2) { difficulty="Hard"; }
		items[1] = "Difficulty : "+difficulty;
		
		items[2] = "Theme : ";
		
		items[3] = "Back";
	} 
	
	protected void refresh() {
		String difficulty="";
		if(Resources.getInstance().difficulty==0) { difficulty="Easy"; }
		else if(Resources.getInstance().difficulty==1) { difficulty="Normal"; }
		else if(Resources.getInstance().difficulty==2) { difficulty="Hard"; }
		items[1] = "Difficulty : "+difficulty;
		items[2] = "Theme : ";
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		refresh();
		
		g.drawString("Options", getWidth()/2-45, getHeight()/2-87);
		
		int offsetY=getHeight()/2-37;
		for(int i=0; i<items.length; i++) {
			if(i == focusedItem) { g.setColor(Resources.orange); } 
			else { g.setColor(Resources.white); }
			int offsetX = items[i].length()*13/2;
			g.drawString(items[i], getWidth()/2-offsetX, offsetY);
			offsetY+=25;
		}
		g.setColor(Resources.getInstance().theme);
		g.fillRect(getWidth()/2+60, getHeight()/2-3, 20, 20);
	}
}
