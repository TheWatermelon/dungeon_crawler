package tiles;

public class TileWall extends Tile {
	public TileWall() {
		this.walkable=false;
		this.description="Wall";
	}

	public char getSymbol() {
		return '#';
	}
}
