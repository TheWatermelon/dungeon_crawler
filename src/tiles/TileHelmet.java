package tiles;

import java.awt.Color;

import engine.Resources;

public class TileHelmet extends TileItem {
	public TileHelmet() {
		this.walkable = true;
		this.description="Helmet";
	}
	
	@Override
	public char getSymbol() {
		return '^';
	}
	
	@Override
	public Color getColor() {
		return Resources.lightGray;
	}
}
