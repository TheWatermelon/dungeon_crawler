package rooms;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import objects.item.*;
import tiles.*;

public class RectangleRoom extends Room {
	private Vector<Point> additionalFloor;
	
	public RectangleRoom(int x1, int y1, int x2, int y2, String s) {
		this.description = s;
		this.p1 = new Point(x1, y1);
		this.p2 = new Point(x2, y2);
		this.door = new Vector<Door>();
		this.additionalFloor = new Vector<Point>();
		this.floor = TileFactory.getInstance().createTileStone();
		this.show = false;
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
		for(int i=0; i<this.additionalFloor.size(); i++) {
			tab[this.additionalFloor.get(i).y][this.additionalFloor.get(i).x] = TileFactory.getInstance().createTileMoss();
		}
	}
	
	public void parsingFloor(Vector<Item> v) {
		Random rnd = new Random();
		int placingChance = rnd.nextInt(6), floorChance;
		
		if(placingChance == 0) {
			// Moss + 1 gold + 1 holy fountain
			for(int i=1; i<this.getHeight(); i++) {
				for(int j=1; j<this.getWidth(); j++) {
					floorChance = rnd.nextInt(100);
					if(floorChance<20) this.additionalFloor.add(new Point(this.p1.x+j,this.p1.y+i));
				}
			}
			v.add(new Gold(rnd.nextInt(this.getWidth()-1)+1+this.p1.x, rnd.nextInt(this.getHeight()-1)+1+this.p1.y));
			v.add(new Fountain(rnd.nextInt(this.getWidth()-1)+1+this.p1.x, rnd.nextInt(this.getHeight()-1)+1+this.p1.y));
		} else if(placingChance<4) {
			// Barrels (1 to 4)
			int height=rnd.nextInt(getHeight()-2)+1+this.p1.y, width=rnd.nextInt(getWidth()-2)+1+this.p1.x, gapBarrel=rnd.nextInt(4);
			floorChance = rnd.nextInt(4)+1;
			
			if(floorChance<3) {
				v.add(new Barrel(width, height));
				if(rnd.nextInt(2)==0) {
					v.add(new Barrel(width+1, height));
				} else {
					v.add(new Barrel(width, height+1));
				}
			} else if(floorChance==3) {
				if(gapBarrel==0) {
					v.add(new Barrel(width+1, height));
					v.add(new Barrel(width+1, height+1));
					v.add(new Barrel(width, height+1));
				} else if(gapBarrel==1) {
					v.add(new Barrel(width, height));
					v.add(new Barrel(width, height+1));
					v.add(new Barrel(width+1, height+1));
				} else if(gapBarrel==2) {
					v.add(new Barrel(width+1, height));
					v.add(new Barrel(width, height));
					v.add(new Barrel(width, height+1));
				} else {
					v.add(new Barrel(width, height));
					v.add(new Barrel(width+1, height));
					v.add(new Barrel(width+1, height+1));
				}
			} else {
				v.add(new Barrel(width, height));
				v.add(new Barrel(width+1, height));
				v.add(new Barrel(width+1, height+1));
				v.add(new Barrel(width, height+1));
			}
			
		}
	}
	
	
}
