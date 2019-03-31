package engine.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyListener;

import engine.*;

public class OptionsMenuInGame extends Menu {
	private static final long serialVersionUID = 1L;
	
	private String[] volumeSettings = {"No", "100%", "80%", "60%", "40%", "20%"};
	private int musicIndex, soundIndex;
	
	public OptionsMenuInGame(Window win) {
		super(win);
		focusedItem=0;
		items = new String[6];
		musicIndex = 1;
		soundIndex = 1;
		initPanel();
	}
	
	@Override
	public void exitMenu() {
		Resources.playExitMenuSound();
		focusedItem=0;
		if(this.win.isMainMenu()) {
			this.win.showMainMenu();
		} else {
			this.win.showPauseMenu();
		}
	}

	@Override
	public void selectFocusedItem() {
		Resources.playSelectMenuSound();
		if(focusedItem == 0) {			// Show commands menu
			win.showCommandsMenu();
		} else if(focusedItem == 1) { 	// Toggle commands help in game
			Resources.getInstance().commandsHelp = !Resources.getInstance().commandsHelp;
		} else if(focusedItem == 2) { 	// Cycle music settings
			musicIndex = (musicIndex+1) % 6;
			if(musicIndex==0) {
				Resources.getInstance().music = false;
				Resources.getInstance().musicVolume = 0.0f;
			} else if(musicIndex==1) {
				Resources.getInstance().music = true;
			} else {
				Resources.getInstance().musicVolume-=16.0f;
			}
			Resources.pauseMenuMusic();
			Resources.playMenuMusic();
		} else if(focusedItem == 3) { 	// Cycle sounds settings
			soundIndex = (soundIndex+1) % 6;
			if(soundIndex==0) {
				Resources.getInstance().sound = false;
				Resources.getInstance().soundVolume = 0.0f;
			} else if(soundIndex==1) {
				Resources.getInstance().sound = true;
			} else {
				Resources.getInstance().soundVolume-=16.0f;
			}
		} else if(focusedItem == 4) {	// Cycle theme
			Resources.getInstance().theme = Resources.pickTheme();
		} else {						// Exit game
			this.exitMenu();
		}
		repaint();
	}

	@Override
	protected void initPanel() {
		items[0] = "Commands";
		
		items[1] = "Commands help in game : "+((Resources.getInstance().commandsHelp)?"Yes":"No");

		items[2] = "Music : "+volumeSettings[musicIndex];

		items[3] = "Sounds : "+volumeSettings[soundIndex];
		
		items[4] = "Theme : ";
		
		items[5] = "Back";
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Resources.getInstance().background);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Resources.getInstance().foreground);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		initPanel();
		
		g.drawString("Options", getWidth()/2-(7*12/2), getHeight()/2-95);
		
		int offsetY=getHeight()/2-57;
		for(int i=0; i<items.length; i++) {
			if(i == focusedItem) { g.setColor(Resources.orange); } 
			else { g.setColor(Resources.getInstance().foreground); }
			int offsetX = items[i].length()*12/2;
			g.drawString(items[i], getWidth()/2-offsetX, offsetY);
			offsetY+=25;
		}
		g.setColor(Resources.getInstance().theme);
		g.fillRect(getWidth()/2+55, getHeight()/2+27, 20, 20);
		
		String commands=Character.toUpperCase(Resources.Commands.Up.getKey())+": Up, "+
				Character.toUpperCase(Resources.Commands.Down.getKey())+": Down, "+
				Character.toUpperCase(Resources.Commands.Take.getKey())+": Select";
		g.setColor(Resources.getInstance().foreground);
		g.drawString(commands, getWidth()/2-(commands.length()*12/2), getHeight()-30);
	}

	@Override
	public KeyListener getKeyListener() {
		return new MenuKeyListener(this);
	}
}
