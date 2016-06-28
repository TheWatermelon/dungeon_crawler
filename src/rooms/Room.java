package rooms;

import java.awt.Point;
import java.util.Vector;

import tiles.Tile;
import tiles.TileFactory;

public abstract class Room {
	protected String description;
	public Point p1;
	public Point p2;
	public Vector<Point> door;
	protected boolean show;
	protected Tile floor;
	protected Tile[][] room;
	
	public final Tile getTile() { return this.floor; }
	public final int getWidth() { return (this.p2.x - this.p1.x); }
	public final int getHeight() { return (this.p2.y - this.p1.y); }
	public final String toString() { return this.description; }
	public final void show() { this.show=true; }
	public final void hide() { this.show=false; }
	public final void addDoor(Point p) { this.door.add(p); }
	public abstract void printOn(Tile[][] tab);
	protected abstract void printDoors(Tile[][] tab);
	
	public final void print(Tile[][] tab) {
		for(int i=0; i<=this.getHeight(); i++) {
			for(int j=0; j<=this.getWidth(); j++) {
				if(i == 0 || i == this.getHeight() || j == 0 || j == this.getWidth()) {
					tab[this.p1.y+i][this.p1.x+j] = TileFactory.getInstance().createTileWall();
				} else {
					tab[this.p1.y+i][this.p1.x+j] = this.floor;
				}
			}
		}
		printDoors(tab);
	}
}
