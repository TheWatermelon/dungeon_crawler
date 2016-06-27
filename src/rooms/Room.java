package rooms;

import java.awt.Point;

import tiles.Tile;

public abstract class Room {
	protected String description;
	public Point p1;
	public Point p2;
	protected Tile floor;
	protected Tile[][] room;
	
	public final Tile getTile() { return this.floor; }
	public final int getWidth() { return (this.p2.x - this.p1.x); }
	public final int getHeight() { return (this.p2.y - this.p1.y); }
	public final String toString() { return this.description; }
	
	public abstract void printOn(Tile[][] tab);
}
