package tiles;

import java.awt.Color;

import engine.Resources;

public class TileBow extends Tile {
	public TileBow() {
		super();
		this.walkable=true;
		this.description="Bow";
	}

	@Override
	public char getSymbol() {
		return '(';
	}

	@Override
	public Color getColor() {
		return Resources.lightGray;
	}

}
