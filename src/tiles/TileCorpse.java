package tiles;

import java.awt.Color;

import engine.Resources;

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

	@Override
	public Color getColor() {
		return Resources.darkGray;
	}

}
