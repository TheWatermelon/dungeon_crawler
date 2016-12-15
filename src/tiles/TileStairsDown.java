package tiles;

import java.awt.Color;

import engine.Resources;

public class TileStairsDown extends Tile {
	public TileStairsDown() {
		this.walkable=true;
		this.description="Stairs Down";
	}

	@Override
	public char getSymbol() {
		return '>';
	}

	@Override
	public Color getColor() {
		return Resources.orange;
	}
}
