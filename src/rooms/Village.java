package rooms;

import java.awt.Point;
import java.util.Vector;

import engine.Resources;
import objects.item.Item;
import tiles.Tile;
import tiles.TileFactory;

public class Village extends Room {
	private int[][] walls = {
			{0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0},
			{1,1,5,5,1,3,3,3,3,3,3,3,3,3,3,1,2,2,1,1},
			{1,5,5,2,2,4,4,4,4,4,4,4,4,4,4,2,2,2,6,1},
			{1,2,2,2,2,4,4,4,4,4,4,4,4,4,4,2,2,2,2,1},
			{1,1,2,2,1,3,3,3,4,4,4,4,3,3,3,1,2,2,1,1},
			{0,1,1,1,1,0,0,3,4,4,4,4,3,0,0,1,1,1,1,0},
			{0,0,0,0,0,0,0,3,4,4,4,4,3,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,3,4,4,4,4,3,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,3,4,4,4,4,3,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,3,4,4,4,4,3,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,3,4,4,4,4,3,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,3,4,4,4,4,3,3,3,1,1,1,0,0},
			{0,0,0,0,0,0,0,0,3,4,4,4,4,4,1,4,1,1,1,0},
			{0,0,0,0,0,0,0,0,0,3,4,4,4,4,4,1,4,4,1,0},
			{0,0,0,0,0,0,0,0,0,3,4,4,1,4,4,4,4,4,1,0},
			{0,0,0,0,0,0,0,0,0,3,4,1,1,4,4,4,4,4,1,0},
			{0,0,0,0,0,0,0,0,0,0,1,4,4,1,4,4,4,4,1,0},
			{0,0,0,0,0,0,0,0,0,0,1,4,1,1,4,4,4,1,1,0},
			{0,0,0,0,0,0,0,0,0,0,1,4,4,4,4,1,1,1,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0},
	};
	
	public Village() {
		this.description = "Village";
		int middleX = Resources.getInstance().resolution;
		int middleY = Resources.getInstance().resolution/2;
		this.p1 = new Point(middleX-10,middleY-10);
		this.p2 = new Point(middleX+9,middleY+9);
		this.door = new Vector<Door>();
		this.floor = TileFactory.getInstance().createTileStone();
		this.show = true;
	}

	@Override
	public void parsingFloor(Vector<Item> v) {
		
	}
	
	public void print(Tile[][] tab) {
		for(int i=0; i<this.walls.length; i++) {
			for(int j=0; j<this.walls[0].length; j++) {
				if(this.walls[i][j]==0) {
					tab[this.p1.y+i][this.p1.x+j] = TileFactory.getInstance().createTileVoid();
				}
				else if(this.walls[i][j]==1) {
					tab[this.p1.y+i][this.p1.x+j] = TileFactory.getInstance().createTileWall();
				}
				else if(this.walls[i][j]==2) {
					tab[this.p1.y+i][this.p1.x+j] = TileFactory.getInstance().createTileStone();
				}
				else if(this.walls[i][j]==3) {
					tab[this.p1.y+i][this.p1.x+j] = TileFactory.getInstance().createTileMoss();
				}
				else if(this.walls[i][j]==4) {
					tab[this.p1.y+i][this.p1.x+j] = TileFactory.getInstance().createTileDirt();
				}
				else if(this.walls[i][j]==5) {
					tab[this.p1.y+i][this.p1.x+j] = TileFactory.getInstance().createTileBarrel();
				}
				else if(this.walls[i][j]==6) {
					tab[this.p1.y+i][this.p1.x+j] = TileFactory.getInstance().createTileFountain();
				}
			}
		}
	}

}
