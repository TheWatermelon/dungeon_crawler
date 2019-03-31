package engine.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyListener;

import engine.Resources;
import engine.Window;

public abstract class PopupMenu extends Menu {
	private static final long serialVersionUID = 1L;
	protected String[] msg;
	
	public PopupMenu(Window w, String[] message, String action, String cancel) {
		super(w);
		this.msg = message;
		items = new String[2];
		items[0] = action;
		items[1] = cancel;
		initPanel();
	}
	
	public abstract void doAction();
	
	@Override
	public final void selectFocusedItem() {
		Resources.playSelectMenuSound();
		if(focusedItem==0) { doAction(); }
		else { exitMenu(); }
	}
	
	@Override
	public final void initPanel() {}
	
	public final KeyListener getKeyListener() { return new MenuKeyListener(this); }
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Resources.getInstance().background);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Resources.getInstance().foreground);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		// DEBUG : draw a cross on the panel
		//g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
		//g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
		
		initPanel();
		
		int offsetY = getHeight()/2-(this.msg.length*30)/2;
		int offsetX = 0;
		
		for(int i=0; i<this.msg.length; i++) {
			offsetX = getWidth()/2-(this.msg[i].length()*12/2);
			g.drawString(this.msg[i], offsetX, offsetY);
			offsetY += 20; 
		}
		
		offsetY+=30;
		offsetX = getWidth()/2-(items[0].length()*12)-30;
		for(int i=0; i<items.length; i++) {
			if(i == focusedItem) { g.setColor(Resources.orange); } 
			else { g.setColor(Resources.getInstance().foreground); }
			g.drawString(items[i], offsetX, offsetY);
			offsetX = getWidth()/2+30;
		}
		
		String commands=Character.toUpperCase(Resources.Commands.Left.getKey())+": Left, "+
				Character.toUpperCase(Resources.Commands.Right.getKey())+": Right, "+
				Character.toUpperCase(Resources.Commands.Take.getKey())+": Select";
		g.setColor(Resources.getInstance().foreground);
		g.drawString(commands, getWidth()/2-(commands.length()*12/2), getHeight()-30);
	}
}
