package objects;

import java.awt.Point;
import java.util.Random;

import tiles.*;

public class Potion extends Item {
	public Potion(int x, int y) {
		this.pos = new Point(x, y);
		this.val = pickVal();
		this.description="Potion";
	}
	
	protected int pickVal() {
		Random rnd = new Random();
		int val = pickVal(10);
		if(rnd.nextInt(2)==0) {
			// Pas de chance, celle la va faire mal!
			return val*-1;
		}
		return val;
	}

	@Override
	public Tile getTile() {
		return TileFactory.getInstance().createTilePotion();
	}

}
