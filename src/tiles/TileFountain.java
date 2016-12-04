package tiles;

public class TileFountain extends Tile {
	public TileFountain() {
		this.walkable = true;
		this.description = "Holy Fountain";
	}
	
	public char getSymbol() {
		return 0xB1;
	}
}
