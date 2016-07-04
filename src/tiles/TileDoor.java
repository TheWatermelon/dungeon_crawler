package tiles;

public class TileDoor extends Tile {
	private boolean opened;
	
	public TileDoor(boolean o) {
		this.walkable=true;
		this.opened = o;
		this.description="Door";
	}

	public void open() {
		this.opened=true;
	}
	
	public boolean isOpen() {
		return this.opened;
	}
	
	public char getSymbol() {
		if(this.opened) {
			return ':';
		} else {
			return '+';
		}
	}
}
