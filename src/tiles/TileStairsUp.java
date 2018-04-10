package tiles;

import java.awt.Color;

import engine.Resources;

public class TileStairsUp extends Tile {
	public TileStairsUp() {
		this.walkable=true;
		this.description="Stairs Up";
	}
	
	@Override
	public char getSymbol() {
		return '<';
	}

	@Override
	public Color getColor() {
		return Resources.orange;
	}
}
