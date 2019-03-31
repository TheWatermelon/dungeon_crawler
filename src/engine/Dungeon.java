package engine;

import java.util.*;

import objects.mob.Player;

public class Dungeon{
	protected ArrayList<Map> levels;
	protected int currentLevel;
	protected Window win;
	protected MessageLog log;
	protected Player player;
	protected Map village;
	
	public Dungeon() {
		log = new MessageLog();
		levels = new ArrayList<Map>();
		player = new Player(Resources.getInstance().resolution, Resources.getInstance().resolution/2, log, this);
		village = Resources.createVillage(this);
		levels.add(village);
		win = new Window("Dungeon Crawler", this);
		win.setVisible(true);
	}
	
	public Player getPlayer() { return player; }
	public Map getMap() { return levels.get(currentLevel); }
	public Window getWin() { return win; }
	public int getLevel() { return currentLevel; }
	public MessageLog getLog() { return log; }
	
	protected void setLevels(ArrayList<Map> l) { this.levels = l; }
	protected void setCurrentLevel(int currLvl) { this.currentLevel = currLvl; }
	
	public void start() {
		win.showDungeon();
		win.getDungeonPanel().initPlayerRectangle();
		win.refreshListener();
		win.refresh();
	}
	
	/**
	 * levelUp : ascend one level
	 */
	public void levelUp() {
		if(currentLevel-1<0) { log.appendMessage("You can't escape...", Message.Type.Normal); return; }
		Resources.playStairUpSound();
		log.appendMessage("Going up...", Message.Type.Normal);
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
		Resources.playStairDownSound();
		log.appendMessage("Going down...", Message.Type.Normal);
		if(currentLevel+1<levels.size()) {
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
		levels.add(village);
		currentLevel=0;
		player.reset();
		player.pos.x = Resources.getInstance().resolution;
		player.pos.y = Resources.getInstance().resolution/2;
		win.initGamePanel();
		start();
	}
	
	/**
	 * save : save the game to <player_name>.save
	 */
	public void save() {
		Saver s = new Saver(this);
		s.save(this.player.description+".save");
	}
	
	/**
	 * load : load the game saved in <filename> file
	 * @param filename
	 */
	public void load(String filename) {
		this.log.clear();
		Loader l = new Loader(this);
		l.load(filename);
		this.win.initGamePanel();
		this.win.getDungeonPanel().initPlayerRectangle();
		this.win.getQuickActionPanel().setInventory(this.player.getInventory());
		this.win.refresh();
		this.log.appendMessage("Welcome back "+this.getPlayer()+" !", Message.Type.Normal);
	}
}