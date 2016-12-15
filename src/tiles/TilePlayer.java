package tiles;

import java.awt.Color;

import engine.Resources;

public class TilePlayer extends TileMob {
	public TilePlayer() {
		this.walkable = false;
		this.description = "Player";
	}
	
	@Override
	public char getSymbol() {
		return '@';
	}

	@Override
	public Color getColor() {
		return Resources.green;
	}

}
