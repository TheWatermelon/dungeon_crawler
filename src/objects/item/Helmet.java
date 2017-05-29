package objects.item;

import java.awt.Point;

import engine.Resources;
import objects.effect.Effect;
import objects.effect.EffectNormal;
import tiles.Tile;
import tiles.TileFactory;

public class Helmet extends Equipement {
	private String type;
	
	public Helmet() {
		this.val = 0;
		this.maxDurability = -1;
		this.resetDurability();
		this.description = "Stub Helmet";
		this.effect = new EffectNormal();
		this.type = "";
	}
	
	public Helmet(int x, int y) {
		this.pos = new Point(x, y);
		this.val = pickVal(5);
		this.maxDurability = val*10;
		this.resetDurability();
		this.effect = pickEffect();
		this.type = Resources.getHelmetType();
		this.description = "";
		if(!(effect instanceof EffectNormal))
		{ this.description += effect.name() + " "; }
		this.description += Resources.getHelmetNameAt(val) + " " + this.type;
	}
	
	public String getType() {
		return this.type;
	}

	@Override
	public Tile getTile() {
		return TileFactory.getInstance().createTileHelmet();
	}

	@Override
	public boolean isStackable() {
		return false;
	}

	@Override
	public Effect pickEffect() {
		return new EffectNormal();
	}

}
