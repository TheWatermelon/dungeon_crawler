package tiles;

public class TileGold extends TileItem {
	public TileGold() {
		this.walkable = true;
		this.description = "Gold";
	}
	
	public char getSymbol() {
		return '*';
	}
}
