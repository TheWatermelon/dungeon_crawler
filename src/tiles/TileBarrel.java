package tiles;

import java.awt.Color;

import engine.Resources;

public class TileBarrel extends Tile {
	public TileBarrel() {
		this.walkable = false;
		this.description = "Barrel";
	}
	
	@Override
	public char getSymbol() {
		//return 0xD4;
		return '0';
	}

	@Override
	public Color getColor() {
		return Resources.brown;
	}
}
