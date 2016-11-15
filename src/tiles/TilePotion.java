package tiles;

public class TilePotion extends Tile {
	public TilePotion() {
		this.walkable = true;
		this.description = "Potion";
	}
	
	@Override
	public char getSymbol() {
		return '&';
	}

}
