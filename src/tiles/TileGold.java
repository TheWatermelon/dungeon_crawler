package tiles;

public class TileGold extends TileItem {
	public TileGold() {
		this.walkable = true;
		this.description = "Gold";
	}
	
	@Override
	public char getSymbol() {
		return '*';
	}
}
