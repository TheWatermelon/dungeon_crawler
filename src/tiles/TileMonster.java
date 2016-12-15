package tiles;

import java.awt.Color;

import engine.Resources;

public class TileMonster extends TileMob {
	private char symbol;
	
	public TileMonster(char s) {
		this.walkable = false;
		this.symbol = s;
		this.description = "Monster";
	}
	
	@Override
	public char getSymbol() {
		return this.symbol;
	}

	@Override
	public Color getColor() {
		return Resources.coolRed;
	}
}
