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
		player = new Player(Resources.getInstance().resolution, Resources.getInstance().resolution/2, log, this);
		levels.add(new Map(Resources.getInstance().resolution*2, Resources.getInstance().resolution, this));
		win = new Window("Dungeon Crawler", this);
		win.setVisible(true);
	}
	
	public Dungeon(ArrayList<Map> l, Player p, int currentL) {
		this.levels = l;
		this.player = p;
		this.currentLevel = currentL;
		this.log = new MessageLog();
		this.win = new Window("Dungeon Crawler", this);
		this.win.setVisible(true);
	}
	
	public Player getPlayer() { return player; }
	public Map getMap() { return levels.get(currentLevel); }
	public Window getWin() { return win; }
	public int getLevel() { return currentLevel; }
	public MessageLog getLog() { return log; }
	
	public void start() {
		win.setMap(getMap());
		win.getDungeonPanel().initPlayerRectangle();
		win.refreshListener();
		win.refresh();
	}
	
	/**
	 * levelUp : ascend one level
	 */
	public void levelUp() {
		log.appendMessage("Going up...");
		currentLevel--;
		if(currentLevel%5==0 && currentLevel!=0) { 
			win.getDungeonPanel().hideLight(); 
		} else { win.getDungeonPanel().showLight(); }
		levels.get(currentLevel).placePlayerStairsDown();
		start();
	}
	
	/**
	 * levelDown : descend one level
	 */
	public void levelDown() {
		if(currentLevel+1<levels.size()) {
			log.appendMessage("Going down...");
			currentLevel++;
			if(currentLevel%5==0 && currentLevel!=0) { 
				win.getDungeonPanel().hideLight(); 
			} else { win.getDungeonPanel().showLight(); }
			levels.get(currentLevel).placePlayerStairsUp();
			start();
		} else {
			newLevel();
		}
	}
	
	/**
	 * newLevel : create a level and add it to the dungeon
	 */
	public void newLevel() {
		currentLevel++;
		levels.add(new Map(Resources.getInstance().resolution*2, Resources.getInstance().resolution, this));
		levels.get(currentLevel).generateDungeon();
		if(currentLevel%5==0) {
			win.getDungeonPanel().hideLight();
		} else { win.getDungeonPanel().showLight(); }
		start();
	}
	
	/**
	 * newGame : start a new game
	 */
	public void newGame() {
		log.clear();
		levels.clear();
		currentLevel=0;
		player.reset();
		levels.add(new Map(Resources.getInstance().resolution*2, Resources.getInstance().resolution, this));
		levels.get(currentLevel).generateDungeon();
		start();
	}
	
	/**
	 * save : save the game to <player_name>.save
	 */
	public void save() {
		Saver s = new Saver(this);
		s.save(this.player.description+".save");
	}
}