package game;

import java.awt.Point;

public class Player {
	public Point pos;
	private char symbol;
	
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
	
	public char getSymbol() { return this.symbol; }
}
