package tiles;

public class TileStone extends Tile {
	public TileStone() {
		this.walkable=true;
		this.description="Stone";
	}

	public char getSymbol() {
		return '.';
	}
}
