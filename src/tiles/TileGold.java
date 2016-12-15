package tiles;

import java.awt.Color;

import engine.Resources;

public class TileGold extends TileItem {
	public TileGold() {
		this.walkable = true;
		this.description = "Gold";
	}
	
	@Override
	public char getSymbol() {
		return '*';
	}
	
	@Override
	public Color getColor() {
		return Resources.yellow;
	}
}
