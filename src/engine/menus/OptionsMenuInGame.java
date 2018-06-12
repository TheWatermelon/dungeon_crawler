package engine.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import engine.*;

public class OptionsMenuInGame extends Menu {
	private static final long serialVersionUID = 1L;
	
	public OptionsMenuInGame(Window win) {
		super(win);
		focusedItem=0;
		items = new String[5];
		initPanel();
	}
	
	@Override
	public void exitMenu() {
		Resources.playExitMenuSound();
		focusedItem=0;
		win.showPauseMenu();
	}

	@Override
	public void selectFocusedItem() {
		Resources.playSelectMenuSound();
		if(focusedItem == 0) {			// Show commands menu
			win.showCommandsMenu();
		} else if(focusedItem == 1) { 	// Toggle commands help in game
			Resources.getInstance().commandsHelp = !Resources.getInstance().commandsHelp;
		} else if(focusedItem == 2) { 	// Toggle sounds
			Resources.getInstance().music = !Resources.getInstance().music;
		} else if(focusedItem == 3) {	// Cycle theme
			Resources.getInstance().theme = win.getDungeonPanel().pickTheme();
		} else {						// Exit game
			this.exitMenu();
		}
		repaint();
	}

	@Override
	protected void initPanel() {
		items[0] = "Commands";
		
		items[1] = "Commands help in game : "+((Resources.getInstance().commandsHelp)?"Yes":"No");
		
		items[2] = "Sounds : "+((Resources.getInstance().music)?"Yes":"No");
		
		items[3] = "Theme : ";
		
		items[4] = "Back";
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		initPanel();
		
		g.drawString("Options", getWidth()/2-45, getHeight()/2-95);
		
		int offsetY=getHeight()/2-57;
		for(int i=0; i<items.length; i++) {
			if(i == focusedItem) { g.setColor(Resources.orange); } 
			else { g.setColor(Resources.white); }
			int offsetX = items[i].length()*13/2;
			g.drawString(items[i], getWidth()/2-offsetX, offsetY);
			offsetY+=25;
		}
		g.setColor(Resources.getInstance().theme);
		g.fillRect(getWidth()/2+55, getHeight()/2+2, 20, 20);
		
		String commands=Character.toUpperCase(Resources.Commands.Up.getKey())+": Up, "+
				Character.toUpperCase(Resources.Commands.Down.getKey())+": Down, "+
				Character.toUpperCase(Resources.Commands.Take.getKey())+": Select";
		g.setColor(Resources.white);
		g.drawString(commands, getWidth()/2-(commands.length()*13/2), getHeight()-30);
	}
}