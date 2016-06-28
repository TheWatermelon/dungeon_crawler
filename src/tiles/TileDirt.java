package tiles;

public class TileDirt extends Tile {
	public TileDirt() {
		this.walkable=false;
		this.description="Dirt";
	}
	
	public char getSymbol() {
		return ',';
	}
}
