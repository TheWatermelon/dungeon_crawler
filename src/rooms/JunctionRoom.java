package rooms;

import tiles.*;

public abstract class JunctionRoom extends RectangleRoom {
	protected int door;
	
	public JunctionRoom(int x1, int y1, int x2, int y2, int doorPos, String s) {
		super(x1, y1, x2, y2, s);
		this.door = doorPos;
	}
	
	public abstract void printOn(Tile[][] tab);
}
