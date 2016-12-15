package tiles;

import java.awt.Color;

import engine.Resources;

public class TileWall extends Tile {
	public TileWall() {
		this.walkable=false;
		this.description="Wall";
	}

	@Override
	public char getSymbol() {
		return '#';
	}

	@Override
	public Color getColor() {
		return Resources.getInstance().theme;
	}
}
