package tiles;

import java.awt.Color;

public class TileVoid extends Tile {
	public TileVoid() {
		this.walkable=false;
		this.description="Void";
	}
	
	@Override
	public char getSymbol() {
		return ' ';
	}

	@Override
	public Color getColor() {
		return Color.black;
	}
}
