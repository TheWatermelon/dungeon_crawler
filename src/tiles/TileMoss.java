package tiles;

public class TileMoss extends Tile {
	public TileMoss() {
		this.walkable=true;
		this.description="Moss";
	}
	@Override
	public char getSymbol() {
		return ';';
	}

}
