package objects.item;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import tiles.*;
import engine.Resources;
import objects.effect.*;

public class Shield extends Equipement {	
	public Shield() {
		this.val=0;
		this.maxDurability = -1;
		this.resetDurability();
		this.description = Resources.getShieldAt(0);
		this.effect = new EffectNormal();
	}
	
	public Shield(int x, int y) {
		this.pos = new Point(x, y);
		this.val=pickVal(5);
		this.maxDurability = val*10;
		this.resetDurability();
		this.effect = pickEffect();
		this.description = this.effect.name()+" "+Resources.getShieldAt(val);
	}
	
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
	
	public Tile getTile() {
		return TileFactory.getInstance().createTileShield();
	}
	
	protected Effect pickEffect() {
		Random rnd = new Random();
		int effectChance=rnd.nextInt(8);
		
		if(effectChance<2) {
			return new EffectWeak();
		} else if(effectChance==2) {
			return new EffectHeal();
		} else if(effectChance==3) {
			return new EffectHeavy();
		} else if(effectChance==4) {
			return new EffectStrong();
		}
		return new EffectNormal();
	}
}
