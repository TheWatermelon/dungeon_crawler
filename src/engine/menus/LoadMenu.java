package engine.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

import engine.Resources;
import engine.Window;

public class LoadMenu extends Menu {
	private static final long serialVersionUID = 1L;
	protected String[] shortFilenames;
	public boolean deleteWanted;
	private static final String deleteText = "Press again to delete, other key to cancel";
	
	public LoadMenu(Window win) {
		super(win);
		this.focusedItem = 0;
		this.items = new String[6];
		this.shortFilenames = new String[5];
		initPanel();
	}
	
	public void deleteFocusedFile() {
		if(!deleteWanted) {
			deleteWanted=true;
		} else {
			deleteWanted=false;
			File f = new File("saves/"+this.shortFilenames[this.focusedItem]+".save");
			f.delete();
		}
	}

	@Override
	public void selectFocusedItem() {
		Resources.playSelectMenuSound();
		if(focusedItem<5) {
			if(!this.shortFilenames[this.focusedItem].equals("empty")) {
				this.win.getDungeon().load(this.shortFilenames[this.focusedItem]+".save");
				this.win.showDungeon();
			}
		} else {
			this.exitMenu();
		}
		this.focusedItem = 0;
	}

	@Override
	public void exitMenu() {
		Resources.playExitMenuSound();
		this.win.showMainMenu();
	}

	@Override
	protected void initPanel() {
		File saveDir = new File("saves");
		if(!saveDir.exists()) { saveDir.mkdir(); }
		File[] tempFilesList = saveDir.listFiles();
		ArrayList<String> fList = new ArrayList<String>();
		
		for(File f : tempFilesList) { if(f.getName().endsWith(".save")) fList.add(f.getName()); }
		
		for(int i=0; i<5; i++) {
			if(fList.size() > i) {
				shortFilenames[i] = fList.get(i).substring(0, fList.get(i).length()-5);
			} else {
				shortFilenames[i] = "empty";
			}
			this.items[i] = "Save "+(i+1)+" : "+shortFilenames[i];
		}
		this.items[this.items.length-1] = "Back";
		
		if(deleteWanted && focusedItem != this.items.length-1) { this.items[focusedItem] = deleteText; }
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Resources.getInstance().background);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Resources.getInstance().foreground);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		initPanel();
		
		g.drawString("Load game", getWidth()/2-(9*12/2), getHeight()/2-95);
		
		int offsetY=getHeight()/2-57;
		for(int i=0; i<items.length; i++) {
			if(i == focusedItem) { g.setColor(Resources.orange); } 
			else if(i<this.shortFilenames.length && this.shortFilenames[i].equals("empty")) { g.setColor(Resources.gray); }
			else { g.setColor(Resources.getInstance().foreground); }
			int offsetX = items[i].length()*12/2;
			g.drawString(items[i], getWidth()/2-offsetX, offsetY);
			offsetY+=25;
		}
				
		String commands=Character.toUpperCase(Resources.Commands.Up.getKey())+": Up, "+
				Character.toUpperCase(Resources.Commands.Down.getKey())+": Down, "+
				Character.toUpperCase(Resources.Commands.Left.getKey())+"/"+
				Character.toUpperCase(Resources.Commands.Right.getKey())+": Delete save, "+
				Character.toUpperCase(Resources.Commands.Take.getKey())+": Select";
		g.setColor(Resources.getInstance().foreground);
		g.drawString(commands, getWidth()/2-(commands.length()*12/2), getHeight()-30);
	}

	@Override
	public KeyListener getKeyListener() {
		return new LoadMenuKeyListener(this);
	}
}
