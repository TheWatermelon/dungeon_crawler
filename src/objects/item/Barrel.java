package objects.item;

import java.awt.Point;
import java.util.Random;

import tiles.Tile;
import tiles.TileFactory;

public class Barrel extends Item {
	public Barrel(int x, int y) {
		this.pos = new Point(x, y);
		this.description = "Barrel";
		this.val = 0;
	}
	
	public int open() {
		Random rnd = new Random();
		return rnd.nextInt(5);
		
	}
	
	public Tile getTile() {
		return TileFactory.getInstance().createTileBarrel();
	}
}
