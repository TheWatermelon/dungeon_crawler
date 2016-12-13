package engine;

import java.util.*;

import objects.mob.Player;

public class Dungeon {
	protected ArrayList<Map> levels;
	protected int currentLevel;
	protected Window win;
	protected MessageLog log;
	protected Player player;
	
	public Dungeon() {
		log = new MessageLog();
		levels = new ArrayList<Map>();
		currentLevel = 0;
		player = new Player(27, 13, log);
		levels.add(new Map(54, 29, this));
		win = new Window("Dungeon Crawler", this);
	}
	
	public Player getPlayer() { return player; }
	public Map getMap() { return levels.get(currentLevel); }
	public Window getWin() { return win; }
	public int getLevel() { return currentLevel; }
	public MessageLog getLog() { return log; }
	
	public void start() {
		levels.get(currentLevel).generateDungeon();
		win.setVisible(true);
		win.setMap(levels.get(currentLevel));
		win.refresh();
	}
	
	public void levelUp() {
		if(currentLevel-1>=0) {
			log.appendMessage("Going up...");
			currentLevel--;
			if(currentLevel%5==0 && currentLevel!=0) { 
				win.getDungeonPanel().pickTheme();
				win.getDungeonPanel().hideLight(); 
			} else { win.getDungeonPanel().showLight(); }
			levels.get(currentLevel).placePlayerStairsDown();
			win.setMap(levels.get(currentLevel));
			win.refresh();
		} else {
			log.appendMessage("There is no escape !");
		}
	}
	
	public void levelDown() {
		
		if(currentLevel+1<levels.size()) {
			log.appendMessage("Going down...");
			currentLevel++;
			if(currentLevel%5==0 && currentLevel!=0) { 
				win.getDungeonPanel().pickTheme();
				win.getDungeonPanel().hideLight(); 
			} else { win.getDungeonPanel().showLight(); }
			levels.get(currentLevel).placePlayerStairsUp();
			win.setMap(levels.get(currentLevel));
			win.refresh();
		} else {
			newLevel();
		}
	}
	
	public void newLevel() {
		currentLevel++;
		levels.add(new Map(54, 29, this));
		if(currentLevel%5==0) {
			win.getDungeonPanel().pickTheme();
			win.getDungeonPanel().hideLight();
		} else {
			win.getDungeonPanel().showLight();
		}
		start();
	}
	
	public void newGame() {
		log = new MessageLog();
		levels.clear();
		currentLevel=0;
		player.reset();
		levels.add(new Map(54, 26, this));
		start();
	}
}