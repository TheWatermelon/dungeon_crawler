package engine.menus;

import java.awt.*;
import java.util.*;

import engine.*;
import engine.Window;

public class CommandsMenu extends Menu implements Observer {
	private static final long serialVersionUID = 1L;
	
	protected KeyLogger logger;
	
	protected int cmd;
	
	public CommandsMenu(Window win) {
		super(win);
		items = new String[11];
		focusedItem=0;
		logger = new KeyLogger(this);
		win.addKeyListener(logger);
		cmd=-1;
		initPanel();
	}
	
	@Override
	public void exitMenu() {
		focusedItem=0;
		win.showOptionsMenu();
	}

	@Override
	public void selectFocusedItem() {
		if(focusedItem<10) { // Commands
			cmd=(cmd==-1)?focusedItem:-1;
			if(cmd==-1) { initPanel(); return; }
			char key = items[focusedItem].substring(items[focusedItem].length()-1, items[focusedItem].length()).charAt(0);
			items[focusedItem] = items[focusedItem].substring(0, items[focusedItem].length()-1);
			items[focusedItem]+=">"+key+"<";
		} else {
			focusedItem=0;
			win.showOptionsMenu();
		}
	}

	@Override
	protected void initPanel() {
		items[0] = "Up : "+Character.toUpperCase(Resources.Commands.Up.getKey());
		items[1] = "Down : "+Character.toUpperCase(Resources.Commands.Down.getKey());
		items[2] = "Left : "+Character.toUpperCase(Resources.Commands.Left.getKey());
		items[3] = "Right : "+Character.toUpperCase(Resources.Commands.Right.getKey());
		items[4] = "Take/Use : "+Character.toUpperCase(Resources.Commands.Take.getKey());
		items[5] = "Inventory : "+Character.toUpperCase(Resources.Commands.Inventory.getKey());
		items[6] = "Repare Weapon : "+Character.toUpperCase(Resources.Commands.QuickAction1.getKey());
		items[7] = "Repare Shield : "+Character.toUpperCase(Resources.Commands.QuickAction2.getKey());
		items[8] = "Pause : "+Character.toUpperCase(Resources.Commands.Pause.getKey());
		items[9] = "Restart : "+Character.toUpperCase(Resources.Commands.Restart.getKey());
		items[10] = "Back";
	}

	@Override
	public void update(Observable o, Object arg) {
		if(cmd!=-1 && logger.getKey()!=0) {
			Resources.Commands[] commands = Resources.Commands.values();
			commands[cmd].setKey(logger.getKey());
			cmd=-1;
			initPanel();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		g.drawString("Commands", getWidth()/2-(8*13/2), getHeight()/2-87);
		
		int offsetY=getHeight()/2-37;
		for(int i=0; i<items.length; i++) {
			if(i == focusedItem) { g.setColor(Resources.orange); } 
			else { g.setColor(Resources.white); }
			int offsetX = items[i].length()*13/2;
			g.drawString(items[i], getWidth()/2-offsetX, offsetY);
			offsetY+=25;
		}
		
		String commands;
		if(cmd==-1) {
			commands=Character.toUpperCase(Resources.Commands.Up.getKey())+": Up, "+
					Character.toUpperCase(Resources.Commands.Down.getKey())+": Down, "+
							Character.toUpperCase(Resources.Commands.Take.getKey())+": Change";
		} else {
			commands="Type the new key or type Enter to cancel";
		}
				
		g.setColor(Resources.white);
		g.drawString(commands, getWidth()/2-(commands.length()*13/2), getHeight()-30);
	}
}
