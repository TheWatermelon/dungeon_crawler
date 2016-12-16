package engine.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import engine.*;

public class OptionsMenu extends Menu {
	private static final long serialVersionUID = 1L;
	
	public OptionsMenu(Window win) {
		super(win);
		focusedItem=0;
		items = new String[6];
		initPanel();
	}


	@Override
	public void selectFocusedItem() {
		if(focusedItem == 0) {
			win.getMap().getPlayer().changeName();
		} else if(focusedItem == 1) {
			win.showCommandsMenu();
		} else if(focusedItem == 2) {	// Cycle resolution
			Resources.getInstance().resolution=(Resources.getInstance().resolution==30)?60:30;
		} else if(focusedItem == 3) {	// Cycle difficulty
			Resources.getInstance().difficulty=(Resources.getInstance().difficulty+1)%3;
		} else if(focusedItem == 4) {
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
		items[0] = "Name : "+win.getMap().getPlayer();
		
		items[1] = "Commands";
		
		items[2] = "Resolution : "+Resources.getInstance().resolution*2+"x"+Resources.getInstance().resolution;
		
		String difficulty="";
		if(Resources.getInstance().difficulty==0) { difficulty="Easy"; }
		else if(Resources.getInstance().difficulty==1) { difficulty="Normal"; }
		else if(Resources.getInstance().difficulty==2) { difficulty="Hard"; }
		items[3] = "Difficulty : "+difficulty;
		
		items[4] = "Theme : ";
		
		items[5] = "Back";
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		initPanel();
		
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
		g.fillRect(getWidth()/2+60, getHeight()/2+47, 20, 20);
		
		String commands=Resources.Commands.Up.getKey()+": Up, "+
				Resources.Commands.Down.getKey()+": Down, "+
				Resources.Commands.Take.getKey()+": Select";
		g.setColor(Resources.white);
		g.drawString(commands, getWidth()/2-(commands.length()*13/2), getHeight()-30);
	}
}
