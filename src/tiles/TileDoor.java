package tiles;

import java.awt.Color;

import engine.Resources;

public class TileDoor extends Tile {
	private boolean opened;
	
	public TileDoor(boolean o) {
		if(o) {
			this.walkable=true;
		} else {
			this.walkable=false;
		}
		this.opened = o;
		this.description="Door";
	}

	public void open() {
		this.opened=true;
	}
	
	public boolean isOpen() {
		return this.opened;
	}
	
	@Override
	public char getSymbol() {
		if(this.opened) {
			return ':';
		} else {
			return '+';
		}
	}

	@Override
	public Color getColor() {
		return Resources.brown;
	}
}
