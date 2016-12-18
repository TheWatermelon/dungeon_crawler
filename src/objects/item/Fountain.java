package objects.item;

import java.awt.Color;
import java.awt.Point;

import engine.Resources;
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

	@Override
	public Color getColor() {
		return Resources.cyan;
	}
}
