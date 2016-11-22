package tiles;

public class TileStairsUp extends Tile {
	public TileStairsUp() {
		this.walkable=true;
		this.description="Stairs Up";
	}
	
	public char getSymbol() {
		return '<';
	}
}
