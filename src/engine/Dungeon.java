package engine;

public class Dungeon extends Observer {
	protected ArrayList<Map> levels;
	protected Window win;
	
	public Dungeon() {
		levels = new ArrayList<Map>();
		win = new Window();
	}
}