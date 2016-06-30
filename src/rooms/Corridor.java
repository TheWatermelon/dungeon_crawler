package rooms;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import objects.Door;
import tiles.Tile;
import tiles.TileFactory;

public class Corridor extends Room {
	private Point gold;
	
	public Corridor(int x1, int y1, int x2, int y2, String s) {
		this.description = s;
		this.p1 = new Point(x1, y1);
		this.p2 = new Point(x2, y2);
		this.door = new Vector<Door>();
		this.gold = new Point();
		this.floor = TileFactory.getInstance().createTileStone();
		this.show = false;
		
		parsingGold();
	}
	
	public void isGold(int x, int y) {
		if(x == this.gold.x && y == this.gold.y) {
			this.gold = new Point();
		}
	}
	
	private void parsingGold() {
		Random rnd = new Random();
		int parsingChance = rnd.nextInt(4);
		
		if(parsingChance==0) {
			this.gold.y = rnd.nextInt(this.getHeight()-1)+1+this.p1.y;
			this.gold.x = rnd.nextInt(this.getWidth()-1)+1+this.p1.x;
		}
	}
	
	public void print(Tile[][] tab) {
		for(int i=0; i<=this.getHeight(); i++) {
			for(int j=0; j<=this.getWidth(); j++) {
				if(i == 0 || i == this.getHeight() || j == 0 || j == this.getWidth()) {
					tab[this.p1.y+i][this.p1.x+j] = TileFactory.getInstance().createTileWall();
				} else {
					tab[this.p1.y+i][this.p1.x+j] = this.floor;
				}
			}
		}
		printAdditionalFloor(tab);
	}
	
	private void printAdditionalFloor(Tile[][] tab) {
		if(this.gold.x != 0 && this.gold.y != 0) {
			tab[this.gold.y][this.gold.x] = TileFactory.getInstance().createTileGold();
		}
	}
}
