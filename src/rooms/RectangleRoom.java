package rooms;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import objects.Door;
import tiles.Tile;
import tiles.TileFactory;

public class RectangleRoom extends Room {
	private Vector<Point> moss;
	
	public RectangleRoom(int x1, int y1, int x2, int y2, String s) {
		this.description = s;
		this.p1 = new Point(x1, y1);
		this.p2 = new Point(x2, y2);
		this.door = new Vector<Door>();
		this.moss = new Vector<Point>();
		this.item = new Point();
		this.floor = TileFactory.getInstance().createTileStone();
		this.room = new Tile[this.getHeight()][this.getWidth()];
		this.show = false;
		
		parsingFloor();
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
		for(int i=0; i<this.moss.size(); i++) {
			tab[this.moss.get(i).y][this.moss.get(i).x] = TileFactory.getInstance().createTileMoss();
		}
		if(this.item.x != 0 && this.item.y != 0) {
			tab[this.item.y][this.item.x] = TileFactory.getInstance().createTileGold();
		}
	}
	
	protected void parsingFloor() {
		Random rnd = new Random();
		int placingChance = rnd.nextInt(3), floorChance;
		
		if(placingChance == 0) {
			for(int i=1; i<this.getHeight(); i++) {
				for(int j=1; j<this.getWidth(); j++) {
					floorChance = rnd.nextInt(100);
					if(floorChance<20) this.moss.add(new Point(this.p1.x+j,this.p1.y+i));
				}
			}
			this.item.y = rnd.nextInt(this.getHeight()-1)+1+this.p1.y;
			this.item.x = rnd.nextInt(this.getWidth()-1)+1+this.p1.x;
		}
		
	}
}
