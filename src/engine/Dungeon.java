package engine;

import java.util.*;

import objects.Player;

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
		levels.add(new Map(54, 26, this));
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
			levels.get(currentLevel).placePlayerStairsDown();
			win.setMap(levels.get(currentLevel));
			win.firstPrint();
			win.refresh();
		} else {
			log.appendMessage("There is no escape !");
		}
	}
	
	public void levelDown() {
		if(currentLevel+1<levels.size()) {
			log.appendMessage("Going down...");
			currentLevel++;
			levels.get(currentLevel).placePlayerStairsUp();
			win.setMap(levels.get(currentLevel));
			win.firstPrint();
			win.refresh();
		} else {
			newLevel();
		}
	}
	
	public void newLevel() {
		currentLevel++;
		levels.add(new Map(54, 26, this));
		if(currentLevel%5==0) {
			win.pickTheme();
		}
		start();
	}
	
	public void newGame() {
		levels.clear();
		currentLevel=0;
		player.reset();
		levels.add(new Map(54, 26, this));
		start();
	}
}