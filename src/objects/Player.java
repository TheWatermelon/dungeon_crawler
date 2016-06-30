package objects;

import java.awt.Point;

import tiles.Tile;
import tiles.TileGold;
import tiles.TilePlayer;

public class Player {
	public Point pos;
	private char symbol;
	private Tile floor;
	
	private double hp;
	private int gold;
	private int level;
	
	public Player() {
		this.hp=100;
		this.gold=0;
		this.level=1;
		this.pos = new Point();
		this.symbol = '@';
	}
	
	public Player(int x, int y) {
		this.hp=100;
		this.gold=0;
		this.level=1;
		this.pos = new Point(x, y);
		this.symbol = '@';
	}
	
	public void placeOn(int x, int y) {
		this.pos.x = x;
		this.pos.y = y;
	}
	
	public void addGold(int amount) {
		this.gold+=amount;
	}
	
	public void setFloor(Tile t) {
		if(!(t instanceof TilePlayer || t instanceof TileGold)) {
			this.floor = t;
		}
	}
	
	public Tile getFloor() {
		return this.floor;
	}
	
	public char getSymbol() { return this.symbol; }
	
	public String getInfo() {
		return "  HP : "+Math.round(this.hp)+"\tLevel : "+this.level+"\n  Gold : "+this.gold;
	}
}
