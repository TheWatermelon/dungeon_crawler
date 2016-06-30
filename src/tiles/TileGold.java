package tiles;

public class TileGold extends Tile {
	public TileGold() {
		this.walkable = true;
		this.description = "Gold";
	}
	
	public char getSymbol() {
		return '*';
	}
}
