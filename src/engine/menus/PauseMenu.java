package engine.menus;

import java.awt.*;

import engine.Resources;
import engine.Window;

public class PauseMenu extends Menu {
	private static final long serialVersionUID = 1L;
	
	public PauseMenu(Window w) { 
		super(w); 
		initPanel();
	}
	
	@Override
	public void exitMenu() {
		focusedItem=0;
		win.showDungeon();
	}
	
	@Override
	public void selectFocusedItem() {
		if(focusedItem==0) {
			win.showDungeon();
		} else if(focusedItem==1) {
			focusedItem=0;
			win.showOptionsMenuInGame();
		} else {
			focusedItem=0;
			win.showMainMenu();
		}
	}
	
	@Override
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
		String name = win.getMap().getPlayer().toString(); 
		String healthBar = win.getMap().getPlayer().drawHealthBar();
		//String stuff = "Gold : "+win.getMap().getPlayer().getGold()+" "+win.getMap().getPlayer().getWeaponInfo();
		g.drawString(name, getWidth()/2-(name.length()*13/2), 60);
		g.drawString(healthBar, getWidth()/2-(healthBar.length()*12/2), 80);
		//g.drawString(stuff, getWidth()/2-(stuff.length()*12/2), 100);

		g.setFont(new Font("Monospaced", Font.PLAIN, 50));
		g.setColor(win.getMap().getPlayer().getLooker().getLeftColor());
		g.drawString(""+win.getMap().getPlayer().getLooker().getLeft(), getWidth()/2-45, 150);
		g.setColor(win.getMap().getPlayer().getColor());
		g.drawString(""+win.getMap().getPlayer().getSymbol(), getWidth()/2-15, 150);
		g.setColor(win.getMap().getPlayer().getLooker().getRightColor());
		g.drawString(""+win.getMap().getPlayer().getLooker().getRight(), getWidth()/2+15, 150);
		if(win.getMap().getPlayer().getHelmet().getMaxDurability()!=-1) {
			g.setColor(win.getMap().getPlayer().getHelmet().getColor());
			g.drawString(""+win.getMap().getPlayer().getHelmet().getTile().getSymbol(), getWidth()/2-13, 140);
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		g.drawString("Pause", getWidth()/2-32, getHeight()/2-87);
		
		int offsetY=getHeight()/2-37;
		for(int i=0; i<items.length; i++) {
			if(i == focusedItem) { g.setColor(Resources.orange); } 
			else { g.setColor(Resources.white);	}
			int offsetX = items[i].length()*13/2;
			g.drawString(items[i], getWidth()/2-offsetX, offsetY);
			offsetY+=25;
		}
		
		String commands=Character.toUpperCase(Resources.Commands.Up.getKey())+": Up, "+
				Character.toUpperCase(Resources.Commands.Down.getKey())+": Down, "+
				Character.toUpperCase(Resources.Commands.Take.getKey())+": Select";
		g.setColor(Resources.white);
		g.drawString(commands, getWidth()/2-(commands.length()*13/2), getHeight()-30);
	}
}
