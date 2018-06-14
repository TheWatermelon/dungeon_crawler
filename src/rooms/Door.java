package rooms;

import java.awt.Point;

import engine.Resources;
import tiles.Tile;
import tiles.TileDoorClosed;
import tiles.TileFactory;

public class Door {
	private Point pos;
	private boolean open;
	private Tile floor;
	
	public Door(Point p) {
		this.pos = p;
		this.open = false;
		this.floor = TileFactory.getInstance().createTileDoorClosed();
	}
	
	public Door(Point p, Tile t) {
		this.pos = p;
		this.open = false;
		this.floor = t;
	}
	
	public Door(Point p, boolean opened) {
		this.pos = p;
		if(opened) {
			this.open();
		} else {
			this.open = false;
			this.floor = TileFactory.getInstance().createTileDoorClosed();
		}
	}
	
	public int getX() { return this.pos.x; }
	
	public int getY() { return this.pos.y; }
	
	public Tile getFloor() { return this.floor; }
	
	public boolean isOpen() { return this.open; }
	
	public void open() {
		this.open = true;
		if(this.floor instanceof TileDoorClosed) {
			this.floor = TileFactory.getInstance().createTileDoorOpened();
			Resources.playDoorSound();
		} else {
			this.floor = TileFactory.getInstance().createTileStone();
		}
	}
	
	public void close() {
		this.open = false;
	}
	
	@Override
	public String toString() {
		return "("+pos.x+","+pos.y+"):"+((open)?"opened":"closed");
	}
}
