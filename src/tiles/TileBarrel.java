package tiles;

public class TileBarrel extends Tile {
	public TileBarrel() {
		this.walkable = false;
		this.description = "Barrel";
	}
	
	public char getSymbol() {
		return 0xD4;
	}
}
