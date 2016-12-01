package objects.item;

import java.awt.Point;
import java.util.Random;

import objects.effect.*;
import tiles.Tile;
import tiles.TileFactory;
import engine.Ressources;

public class Weapon extends Equipement {
	
	public Weapon() {
		this.val=0;
		this.maxDurability = -1;
		this.resetDurability();
		this.description = Ressources.getWeaponAt(0);
		this.effect = new EffectNormal();
	}
	
	public Weapon(int x, int y) {
		this.pos = new Point(x, y);
		this.val=pickVal(5);
		this.maxDurability = val*10;
		this.resetDurability();
		this.effect = pickEffect();
		this.description = this.effect.name()+" "+Ressources.getWeaponAt(val);
	}
	
	public Tile getTile() {
		return TileFactory.getInstance().createTileWeapon();
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
		}
		return new EffectNormal();
	}
}
