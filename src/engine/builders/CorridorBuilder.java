package engine.builders;

import java.awt.Point;
import java.util.Vector;

import rooms.Corridor;
import rooms.Door;

public class CorridorBuilder {
	protected Point p1;
	protected Point p2;
	protected String description;
	protected boolean show;
	protected Vector<Door> doors;
	
	public CorridorBuilder withPoints(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
		return this;
	}
	
	public CorridorBuilder withDescription(String desc) {
		this.description = desc;
		return this;
	}
	
	public CorridorBuilder withShowBool(boolean s) {
		this.show = s;
		return this;
	}
	
	public CorridorBuilder withDoors(Vector<Door> d) {
		this.doors = d;
		return this;
	}
	
	public Corridor build() {
		return new Corridor(this.p1, this.p2, this.description, this.show, this.doors);
	}
}
