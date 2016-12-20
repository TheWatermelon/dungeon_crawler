package objects.item;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import engine.Resources;
import objects.effect.Effect;
import objects.effect.EffectFire;
import objects.effect.EffectHeavy;
import objects.effect.EffectNormal;
import objects.effect.EffectParalyze;
import objects.effect.EffectPoison;
import objects.effect.EffectSleep;
import objects.effect.EffectStrong;
import objects.effect.EffectWeak;
import tiles.Tile;
import tiles.TileFactory;

public class Bow extends Equipement {
	public Bow(int x, int y) {
		this.pos = new Point(x, y);
		this.val=pickVal(5);
		this.maxDurability = 25;
		this.resetDurability();
		this.effect = pickEffect();
		if(effect instanceof EffectNormal) 
		{ this.description = Resources.getEquipementAt(val)+" Bow"; } 
		else { this.description = effect.name()+" "+Resources.getEquipementAt(val)+" Bow"; }
	}

	@Override
	public Color getColor() {
		if(!(effect instanceof EffectNormal)) {
			return effect.getColor();
		}
		
		switch(val) {
			case 1:
				return Resources.brown;
			case 2:
				return Resources.orange;
			case 3:
				return Resources.lightGray;
			case 4:
				return Resources.pink;
			case 5:
				return Resources.cyan;
			default:
				return Resources.lightGray;
		}
	}
	
	public Effect pickEffect() {
		Random rnd = new Random();
		int effectChance=rnd.nextInt(15);
		
		if(effectChance==0) {
			return new EffectPoison();
		} else if(effectChance==1) {
			return new EffectSleep();
		} else if(effectChance==2) {
			return new EffectParalyze();
		} else if(effectChance==3) {
			return new EffectWeak();
		} else if(effectChance==4) {
			return new EffectHeavy();
		} else if(effectChance==5) {
			return new EffectFire();
		} else if(effectChance==6) {
			return new EffectStrong();
		}
		return new EffectNormal();
	}

	@Override
	public Tile getTile() {
		return TileFactory.getInstance().createTileBow();
	}

	@Override
	public boolean isStackable() {
		return false;
	}

}
