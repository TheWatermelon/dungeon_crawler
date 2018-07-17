package engine.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.Resources;
import engine.Window;
import game.Main;

public class MainMenu extends Menu {
	private static final long serialVersionUID = 1L;

	public MainMenu(Window win) {
		super(win);
		initPanel();
	}
	
	@Override
	public void exitMenu() {
		Resources.playExitMenuSound();
		System.exit(0);
	}

	@Override
	public void selectFocusedItem() {
		Resources.playSelectMenuSound();
		if(focusedItem==0) {
			win.showOptionsMenuNewGame();
		} else if(focusedItem==1) {
			focusedItem=0;
			win.showLoadMenu();
		} else if(focusedItem==2) {
			focusedItem=0;
			win.showOptionsMenuInGame();
		} else {
			System.exit(0);
		}
	}

	@Override
	protected void initPanel() {
		focusedItem=0;
		items = new String[4];
		items[0] = "New game";
		items[1] = "Load";
		items[2] = "Options";
		items[3] = "Quit";
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		try {
			BufferedImage img = ImageIO.read(Main.class.getResourceAsStream("/title_banner.png"));
			g.drawImage(img, getWidth()/2-400, 0, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		g.drawString(win.getTitle(), getWidth()/2-(win.getTitle().length()*13/2), getHeight()/2-87);
		
		int offsetY=getHeight()/2-37;
		for(int i=0; i<items.length; i++) {
			if(i == focusedItem) { g.setColor(Resources.orange); } 
			else { g.setColor(Resources.white); }
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
