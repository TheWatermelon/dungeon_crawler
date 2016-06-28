package tiles;

public class TileVoid extends Tile {
	public TileVoid() {
		this.walkable=false;
		this.description="Void";
	}
	
	public char getSymbol() {
		return ' ';
	}
}
