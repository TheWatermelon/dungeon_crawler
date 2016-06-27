package rooms;

import java.awt.Point;

import tiles.*;

public class CorridorNorth extends Corridor {
	public CorridorNorth(int x1, int y1, int x2, int y2, String s) {
		this.description = s;
		this.p1 = new Point(x1, y1);
		this.p2 = new Point(x2, y2);
		this.floor = TileFactory.getInstance().createTileStone();
	}
	
	public void printOn(Tile[][] tab) {
		for(int i=0; i<=this.getHeight(); i++) {
			for(int j=0; j<=this.getWidth(); j++) {
				if(i == 0 || i == this.getHeight() || j == 0 || j == this.getWidth()) {
					tab[this.p1.y+i][this.p1.x+j] = TileFactory.getInstance().createTileWall();
				} else {
					tab[this.p1.y+i][this.p1.x+j] = this.floor;
				}
			}
		}
		tab[this.p2.y][this.p1.x+1] = TileFactory.getInstance().createTileDoor();
	}

}
