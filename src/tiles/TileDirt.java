package tiles;

import java.awt.Color;

import engine.Resources;

public class TileDirt extends Tile {
	public TileDirt() {
		this.walkable=true;
		this.description="Dirt";
	}
	
	@Override
	public char getSymbol() {
		return ',';
	}

	@Override
	public Color getColor() {
		return Resources.darkBrown;
	}
}
