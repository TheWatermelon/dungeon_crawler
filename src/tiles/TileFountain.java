package tiles;

import java.awt.Color;

import engine.Resources;

public class TileFountain extends Tile {
	public TileFountain() {
		this.walkable = true;
		this.description = "Holy Fountain";
	}
	
	@Override
	public char getSymbol() {
		return 0xB1;
	}

	@Override
	public Color getColor() {
		return Resources.cyan;
	}
}
