package rooms;

import tiles.*;

public class JunctionRoomNorth extends JunctionRoom {
	public JunctionRoomNorth(int x1, int y1, int x2, int y2, int doorPos, String s) {
		super(x1, y1, x2, y2, doorPos, s);
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
		// Door
		tab[this.p2.y][this.door] = TileFactory.getInstance().createTileDoor();
	}
}
