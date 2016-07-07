package objects;

import java.awt.Point;
import java.util.Random;

import tiles.Tile;
import tiles.TileFactory;

public class Gold extends Item {
	public Gold(int x, int y) {
		this.pos = new Point(x, y);
		this.val = pickVal();
		this.description="Gold";
	}
	
	public Gold(int x, int y, int v) {
		this.pos = new Point(x, y);
		this.val = pickVal(v);
		this.description="Gold";
	}
	
	private int pickVal() {
		Random rnd = new Random();
		
		return rnd.nextInt(5)+1;
	}

	public Tile getTile() {
		return TileFactory.getInstance().createTileGold();
	}
}
