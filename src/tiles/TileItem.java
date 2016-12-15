package tiles;

import java.awt.Color;

import engine.Resources;

public class TileItem extends Tile {
	@Override
	public char getSymbol() {
		return '$';
	}

	@Override
	public Color getColor() {
		return Resources.lightGray;
	}

}
