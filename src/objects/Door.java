package objects;

import java.awt.Point;

public class Door {
	private Point pos;
	private boolean open;
	
	public Door(Point p) {
		this.pos = p;
		this.open = false;
	}
	
	public int getX() { return this.pos.x; }
	
	public int getY() { return this.pos.y; }
	
	public boolean isOpen() { return this.open; }
	
	public void open() {
		this.open = true;
	}
	
	public void close() {
		this.open = false;
	}

}
