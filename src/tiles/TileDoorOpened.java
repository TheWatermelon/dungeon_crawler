package tiles;

import java.awt.Color;

import engine.Resources;

public class TileDoorOpened extends Tile {	
	public TileDoorOpened() {
		this.walkable = true;
		this.description="Door";
	}
	
	@Override
	public char getSymbol() {
		return ':';
	}

	@Override
	public Color getColor() {
		return Resources.brown;
	}
}
