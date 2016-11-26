package objects.mob;

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
	protected Tile mobTile;
	
	public int hp;
	public int atk;
	public int def;
	public int vit;

	public abstract void murder();

	public abstract int getAtk();
	public abstract int getDef();
	
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
	
	public final Tile getMobTile() {
		return this.mobTile;
	}
	
	
	public final boolean isDead() { return this.dead; }
	
	public final char getSymbol() { return this.symbol; }
	
	public String toString() { return this.description; }
}
