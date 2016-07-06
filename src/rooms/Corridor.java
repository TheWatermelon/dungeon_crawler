package rooms;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import objects.Door;
import tiles.Tile;
import tiles.TileFactory;

public class Corridor extends Room {
	private Tile additionalFloor;
	
	public Corridor(int x1, int y1, int x2, int y2, String s) {
		
		this.description = s;
		this.p1 = new Point(x1, y1);
		this.p2 = new Point(x2, y2);
		this.door = new Vector<Door>();
		this.item = new Point();
		this.floor = TileFactory.getInstance().createTileStone();
		this.show = false;
		
		parsingFloor();
	}
	
	protected void parsingFloor() {
		Random rnd = new Random();
		int parsingChance = rnd.nextInt(5), floorType = rnd.nextInt(3);
		
		if(parsingChance==0) {
			if(floorType==0) {
				this.additionalFloor = TileFactory.getInstance().createTileGold();
			} else if(floorType==1) {
				this.additionalFloor = TileFactory.getInstance().createTileWeapon();
			} else if(floorType==2) {
				this.additionalFloor = TileFactory.getInstance().createTileShield();
			}
			this.item.y = rnd.nextInt(this.getHeight()-1)+1+this.p1.y;
			this.item.x = rnd.nextInt(this.getWidth()-1)+1+this.p1.x;
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
		if(this.item.x != 0 && this.item.y != 0) {
			tab[this.item.y][this.item.x] = this.additionalFloor;
		}
	}
}
