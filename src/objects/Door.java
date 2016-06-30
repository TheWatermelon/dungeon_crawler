package objects;

import java.awt.Point;

import tiles.Tile;
import tiles.TileDoor;
import tiles.TileFactory;

public class Door {
	private Point pos;
	private boolean open;
	private Tile floor;
	
	public Door(Point p) {
		this.pos = p;
		this.open = false;
		this.floor = TileFactory.getInstance().createTileDoor(false);
	}
	
	public Door(Point p, Tile t) {
		this.pos = p;
		this.open = false;
		this.floor = t;
	}
	
	public int getX() { return this.pos.x; }
	
	public int getY() { return this.pos.y; }
	
	public Tile getFloor() { return this.floor; }
	
	public boolean isOpen() { return this.open; }
	
	public void open() {
		this.open = true;
		if(this.floor instanceof TileDoor) {
			this.floor = TileFactory.getInstance().createTileDoor(true);
		}
	}
	
	public void close() {
		this.open = false;
	}

}
