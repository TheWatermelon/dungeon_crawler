package objects;

import java.awt.Point;
import java.util.Random;

import tiles.Tile;

public abstract class Item {
	protected int val;
	protected String description;
	public Point pos;

	protected final int pickVal(int limit) { Random rnd = new Random(); return rnd.nextInt(limit)+1; }
	public final int getVal() { return this.val; }
	public final void setVal(int v) { this.val = v; }
	public String toString() { return this.description; }
	
	public abstract Tile getTile();
}
