package tiles;

import java.awt.Color;

import engine.Resources;

public class TileShield extends TileItem {
	public TileShield() {
		this.walkable=true;
		this.description="Shield";
	}
	
	@Override
	public char getSymbol() {
		return ']';
	}
	
	@Override
	public Color getColor() {
		return Resources.lightGray;
	}
}
