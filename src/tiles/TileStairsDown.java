package tiles;

public class TileStairsDown extends Tile {
	public TileStairsDown() {
		this.walkable=true;
		this.description="Stairs Down";
	}

	public char getSymbol() {
		return '>';
	}
}
