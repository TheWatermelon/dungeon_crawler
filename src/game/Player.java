package game;

import java.awt.Point;
import tiles.*;

public class Player {
	public Point pos;
	private char symbol;
	private Tile floor;
	
	public Player() {
		this.pos = new Point();
		this.symbol = '@';
	}
	
	public Player(int x, int y) {
		this.pos = new Point(x, y);
		this.symbol = '@';
	}
	
	public void placeOn(int x, int y) {
		this.pos.x = x;
		this.pos.y = y;
	}
	
	public void setFloor(Tile t) {
		this.floor = t;
	}
	
	public Tile getFloor() {
		return this.floor;
	}
	
	public char getSymbol() { return this.symbol; }
}
