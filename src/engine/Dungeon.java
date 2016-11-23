package engine;

public class Dungeon extends Observer {
	protected ArrayList<Map> levels;
	protected int currentLevel;
	protected Window win;
	
	public Dungeon() {
		levels = new ArrayList<Map>();
		currentLevel = 0;
		levels.add(new Map(54, 26));
		win = new Window();
	}
	
	public void start() {
		if(win!=null)	win.dispose();
		win = new Window(levels.get(currentLevel));
	}
}