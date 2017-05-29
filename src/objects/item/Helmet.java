package objects.item;

import java.awt.Point;
import java.util.Random;

import engine.Resources;
import objects.effect.*;
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
		Random rnd = new Random();
		int effectChance=rnd.nextInt(4);
		
		if(effectChance==0) {
			return new EffectStrength();
		} else if(effectChance==1) {
			return new EffectToughness();
		}
		return new EffectNormal();
	}

}
