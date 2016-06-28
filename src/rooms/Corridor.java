package rooms;

import java.awt.Point;
import java.util.Vector;

import tiles.Tile;
import tiles.TileFactory;

public class Corridor extends Room {
	public Corridor(int x1, int y1, int x2, int y2, String s) {
		this.description = s;
		this.p1 = new Point(x1, y1);
		this.p2 = new Point(x2, y2);
		this.door = new Vector<Point>();
		this.floor = TileFactory.getInstance().createTileStone();
		this.show = false;
	}
	
	public void printOn(Tile[][] tab) {
		if(show) {
			for(int i=0; i<=this.getHeight(); i++) {
				for(int j=0; j<=this.getWidth(); j++) {
					if(i == 0 || i == this.getHeight() || j == 0 || j == this.getWidth()) {
						tab[this.p1.y+i][this.p1.x+j] = TileFactory.getInstance().createTileWall();
					} else {
						tab[this.p1.y+i][this.p1.x+j] = this.floor;
					}
				}
			}
			printDoors(tab);
		}
	}
	
	protected void printDoors(Tile[][] tab) {
		for(int i=0; i<this.door.size(); i++) {
			tab[this.door.get(i).y][this.door.get(i).x] = TileFactory.getInstance().createTileDoor();
		}
	}
}
