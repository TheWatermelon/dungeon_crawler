package engine.menus;

import java.awt.*;

import javax.swing.BorderFactory;

import engine.Resources;
import engine.Window;

public class PauseMenu extends Menu {
	private static final long serialVersionUID = 1L;
	
	protected String[] items;

	public PauseMenu(Window w) { 
		super(w); 
		initPanel();
	}
	
	public void selectFocusedItem() {
		if(focusedItem==0) {
			win.showDungeon();
		} else if(focusedItem==1) {
			
		} else {
			System.exit(0);
		}
	}
	
	protected void initPanel() {
		focusedItem = 0;
		items = new String[3];
		items[0] = "Resume";
		items[1] = "Options";
		items[2] = "Quit";
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		setBorder(BorderFactory.createLineBorder(Resources.white));
		
		int offsetY=getHeight()/2-37;
		for(int i=0; i<items.length; i++) {
			if(i == focusedItem) {
				char[] c = new char[1];
				c[0]='>';
				g.setColor(Resources.orange);
				g.drawChars(c, 0, 1, getWidth()/2-55, offsetY);
			} else {
				g.setColor(Resources.white);
			}
			g.drawString(items[i], getWidth()/2-35, offsetY);
			offsetY+=25;
		}
		
	}
}
