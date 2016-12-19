package objects.item;

import java.awt.Color;
import java.awt.Point;

import engine.Resources;
import objects.effect.EffectNormal;
import tiles.Tile;
import tiles.TileFactory;

public class Bow extends Equipement {
	public Bow(int x, int y) {
		this.pos = new Point(x, y);
		this.val=3;
		this.maxDurability = 25;
		this.resetDurability();
		this.effect = new EffectNormal();
		this.description = "Bow";
	}

	@Override
	public Color getColor() {
		return Resources.brown;
	}

	@Override
	public Tile getTile() {
		return TileFactory.getInstance().createTileBow();
	}

	@Override
	public boolean isStackable() {
		return true;
	}

}
