package engine.builders;

import java.awt.Point;
import java.util.Vector;

import rooms.Door;
import rooms.RectangleRoom;

public class RectangleRoomBuilder {
	protected Point p1;
	protected Point p2;
	protected String description;
	protected boolean show;
	protected Vector<Door> doors;
	protected Vector<Point> moss;
	protected Vector<Point> dirt;
	
	public RectangleRoomBuilder withPoints(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
		return this;
	}
	
	public RectangleRoomBuilder withDescription(String desc) {
		this.description = desc;
		return this;
	}
	
	public RectangleRoomBuilder withShowBool(boolean s) {
		this.show = s;
		return this;
	}
	
	public RectangleRoomBuilder withDoors(Vector<Door> d) {
		this.doors = d;
		return this;
	}
	
	public RectangleRoomBuilder withMoss(Vector<Point> m) {
		this.moss = m;
		return this;
	}
	
	public RectangleRoomBuilder withDirt(Vector<Point> d) {
		this.dirt = d;
		return this;
	}
	
	public RectangleRoom build() {
		return new RectangleRoom(this.p1, this.p2, this.description, this.show, this.doors, this.moss, this.dirt);
	}
}
