package tiles;

import java.awt.Color;

import engine.Resources;

public class TileDoorClosed extends Tile {
	public TileDoorClosed() {
		this.walkable = false;
		this.description="Door";
	}

	@Override
	public char getSymbol() {
		return '+';
	}

	@Override
	public Color getColor() {
		return Resources.brown;
	}
}
