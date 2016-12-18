package objects.item;

import java.awt.Point;

import tiles.*;

public abstract class Potion extends Item {
	public Potion(int x, int y) {
		this.pos = new Point(x, y);
		this.val = 1;
		this.description="Potion";
	}

	@Override
	public Tile getTile() {
		return TileFactory.getInstance().createTilePotion();
	}

	@Override
	public boolean isStackable() {
		return true;
	}
}
