package tiles;

import java.awt.Color;

import engine.Resources;

public class TilePotion extends Tile {
	public TilePotion() {
		this.walkable = true;
		this.description = "Potion";
	}
	
	@Override
	public char getSymbol() {
		return '&';
	}

	@Override
	public Color getColor() {
		return Resources.lightGray;
	}

}
