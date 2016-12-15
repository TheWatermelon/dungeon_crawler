package tiles;

import java.awt.Color;

import engine.Resources;

public class TileMoss extends Tile {
	public TileMoss() {
		this.walkable=false;
		this.description="Moss";
	}
	
	@Override
	public char getSymbol() {
		return ';';
	}

	@Override
	public Color getColor() {
		return Resources.darkGreen;
	}

}
