package objects;

import java.awt.Point;

import tiles.Tile;
import tiles.TileGold;
import tiles.TileMob;

public abstract class Mob {
	public Point pos;
	protected boolean dead;
	protected char symbol;
	protected String description;
	protected Tile floor;
	
	public int hp;
	public int atk;
	public int def;

	public abstract void murder();

	
	
	public final void placeOn(int x, int y) {
		this.pos = new Point(x, y);
	}
	
	public final void setFloor(Tile t) {
		if(!(t instanceof TileMob || t instanceof TileGold)) {
			this.floor = t;
		}
	}
	
	public final Tile getFloor() {
		return this.floor;
	}
	
	
	public final boolean isDead() { return this.dead; }
	
	public final char getSymbol() { return this.symbol; }
	
	public String toString() { return this.description; }
}
