package engine.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;

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
			// Commands Menu
		} else if(focusedItem == 1) {	// Cycle difficulty
			Resources.getInstance().difficulty=(Resources.getInstance().difficulty+1)%3;
		} else if(focusedItem == 2) {
			Resources.getInstance().theme = win.getDungeonPanel().pickTheme();
		} else {
		focusedItem=0;
			win.showPauseMenu();
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
		
		items[2] = "Theme : "+Resources.getInstance().theme.toString();
		
		items[3] = "Back";
	} 
	
	protected void refresh() {
		String difficulty="";
		if(Resources.getInstance().difficulty==0) { difficulty="Easy"; }
		else if(Resources.getInstance().difficulty==1) { difficulty="Normal"; }
		else if(Resources.getInstance().difficulty==2) { difficulty="Hard"; }
		items[1] = "Difficulty : "+difficulty;
		// colored square after text instead of color code
		items[2] = "Theme : ("+
				Resources.getInstance().theme.getRed()+","+
				Resources.getInstance().theme.getGreen()+","+
				Resources.getInstance().theme.getBlue()+
				")";
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		setBorder(BorderFactory.createLineBorder(Resources.white));
		
		refresh();
		
		int offsetY=getHeight()/2-37;
		for(int i=0; i<items.length; i++) {
			if(i == focusedItem) {
				char[] c = new char[1];
				c[0]='>';
				g.setColor(Resources.orange);
				g.drawChars(c, 0, 1, getWidth()/2-85, offsetY);
			} else {
				g.setColor(Resources.white);
			}
			g.drawString(items[i], getWidth()/2-65, offsetY);
			offsetY+=25;
		}
		
	}
}
