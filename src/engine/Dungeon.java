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
		player = new Player(27, 13, log);
		levels.add(new Map(54, 26, this));
		win = new Window("Dungeon Crawler", this);
		win.setVisible(true);
	}
	
	public Player getPlayer() { return player; }
	public Map getMap() { return levels.get(currentLevel); }
	public Window getWin() { return win; }
	public int getLevel() { return currentLevel; }
	public MessageLog getLog() { return log; }
	
	public void start() {
		win.setMap(getMap());
		win.refreshListener();
		win.refresh();
	}
	
	public void levelUp() {
		log.appendMessage("Going up...");
		currentLevel--;
		if(currentLevel%5==0 && currentLevel!=0) { 
			win.getDungeonPanel().pickTheme();
			win.getDungeonPanel().hideLight(); 
		} else { win.getDungeonPanel().showLight(); }
		levels.get(currentLevel).placePlayerStairsDown();
		start();
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
			start();
		} else {
			newLevel();
		}
	}
	
	public void newLevel() {
		currentLevel++;
		levels.add(new Map(54, 29, this));
		levels.get(currentLevel).generateDungeon();
		if(currentLevel%5==0) {
			win.getDungeonPanel().pickTheme();
			win.getDungeonPanel().hideLight();
		} else {
			win.getDungeonPanel().showLight();
		}
		start();
	}
	
	public void newGame() {
		log.clear();
		levels.clear();
		currentLevel=0;
		player.reset();
		levels.add(new Map(54, 26, this));
		levels.get(currentLevel).generateDungeon();
		start();
	}
}