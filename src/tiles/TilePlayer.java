package tiles;

public class TilePlayer extends Tile {
	public TilePlayer() {
		this.walkable = false;
		this.description = "Player";
	}
	
	@Override
	public char getSymbol() {
		return '@';
	}

}
