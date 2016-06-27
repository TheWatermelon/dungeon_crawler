package tiles;

public class TileDoor extends Tile {
	public TileDoor() {
		this.walkable=false;
		this.description="Door";
	}

	public char getSymbol() {
		return '+';
	}
}
