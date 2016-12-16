package objects.item;

import java.awt.Point;

import tiles.*;

public class Fountain extends Item {
	public Fountain(int x, int y) {
		this.pos = new Point(x, y);
		this.description="Holy Fountain";
		this.val = 5;
	}
	
	@Override
	public Tile getTile() {
		return TileFactory.getInstance().createTileFountain();
	}
	
	@Override
	public boolean isStackable() {
		return false;
	}
}
