package tiles;

import java.awt.Color;

import engine.Resources;

public class TileStone extends Tile {
	public TileStone() {
		this.walkable=true;
		this.description="Stone";
	}

	@Override
	public char getSymbol() {
		return '.';
	}

	@Override
	public Color getColor() {
		return Resources.darkerGray;
	}
}
