package engine;

import java.util.*;

public class Dungeon implements Observer {
	protected ArrayList<Map> levels;
	protected int currentLevel;
	protected Window win;
	protected MessageLog log;
	
	public Dungeon() {
		log = new MessageLog();
		levels = new ArrayList<Map>();
		currentLevel = 0;
		levels.add(new Map(54, 26, this));
		win = new Window("Dungeon Crawler", this);
	}
	
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
			win.setMap(levels.get(currentLevel));
			win.firstPrint();
			win.refresh();
		} else {
			log.appendMessage("There is no escape !");
		}
	}
	
	public void levelDown() {
		// check if the sub-level exists else create a new one
		if(currentLevel+1<levels.size()) {
			log.appendMessage("Going down...");
			currentLevel++;
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
		start();
	}
	
	public void newGame() {
		levels.clear();
		currentLevel=0;
		levels.add(new Map(54, 26, this));
		start();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		try {
			currentLevel = Integer.parseInt((String)arg1);
			win.setMap(levels.get(currentLevel));
		} catch(NumberFormatException e) {}
		win.refresh();
	}
}