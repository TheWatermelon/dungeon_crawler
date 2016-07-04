package tiles;

public class TileCorpse extends Tile {
	public TileCorpse() {
		this.description = "Corpse";
		this.walkable = true;
	}
	
	public TileCorpse(String desc) {
		this.description = desc;
		this.walkable = true;
	}
	
	@Override
	public char getSymbol() {
		return '%';
	}

}
