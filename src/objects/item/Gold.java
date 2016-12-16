package objects.item;

import java.awt.Point;

import tiles.Tile;
import tiles.TileFactory;

public class Gold extends Item {
	public Gold(int x, int y) {
		this.pos = new Point(x, y);
		this.val = pickVal(5);
		this.description="Gold";
	}
	
	public Gold(int x, int y, int v) {
		this.pos = new Point(x, y);
		this.val = pickVal(v);
		this.description="Gold";
	}

	@Override
	public Tile getTile() {
		return TileFactory.getInstance().createTileGold();
	}
	
	@Override
	public boolean isStackable() {
		return true;
	}
}
