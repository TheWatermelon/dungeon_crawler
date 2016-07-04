package tiles;

public class TileMonster extends TileMob {
	private char symbol;
	
	public TileMonster(char s) {
		this.walkable = false;
		this.symbol = s;
		this.description = "Monster";
	}
	
	public char getSymbol() {
		return this.symbol;
	}
}
