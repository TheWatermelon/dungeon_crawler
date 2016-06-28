package tiles;

public class TileDoor extends Tile {
	private boolean opened;
	
	public TileDoor() {
		this.walkable=true;
		this.opened = false;
		this.description="Door";
	}

	public void open() {
		this.opened=true;
	}
	
	public char getSymbol() {
		if(this.opened) {
			return ':';
		} else {
			return '+';
		}
	}
}
