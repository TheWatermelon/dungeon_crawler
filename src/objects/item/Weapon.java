package objects.item;

import java.awt.Point;
import java.util.Random;

import objects.effect.*;
import tiles.Tile;
import tiles.TileFactory;
import engine.Resources;

public class Weapon extends Equipement {
	public Weapon() {
		this.val=0;
		this.maxDurability = -1;
		this.resetDurability();
		this.description = Resources.getEquipementAt(0);
		this.effect = new EffectNormal();
	}
	
	public Weapon(int x, int y) {
		this.pos = new Point(x, y);
		this.val=pickVal(5);
		this.maxDurability = val*10;
		this.resetDurability();
		this.effect = pickEffect();
		if(effect instanceof EffectNormal) 
		{ this.description = Resources.getEquipementAt(val)+" Sword"; } 
		else { this.description = effect.name()+" "+Resources.getEquipementAt(val)+" Sword"; }
	}
	
	public Weapon(int x, int y, int val, boolean isEquiped, int itemDur, int itemMaxDur, Effect e) {
		this.pos = new Point(x, y);
		this.val = val;
		this.durability = itemDur;
		this.maxDurability = itemMaxDur;
		this.effect = e;
		this.isEquiped = isEquiped;
		if(effect instanceof EffectNormal) 
		{ this.description = Resources.getEquipementAt(val)+" Sword"; } 
		else { this.description = effect.name()+" "+Resources.getEquipementAt(val)+" Sword"; }
	}
	
	@Override
	public Tile getTile() {
		return TileFactory.getInstance().createTileWeapon();
	}
	
	public Effect pickEffect() {
		Random rnd = new Random();
		int effectChance=rnd.nextInt(16);
		
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
		} else if(effectChance==7) {
			return new EffectStrength();
		}
		return new EffectNormal();
	}
	
	@Override
	public boolean isStackable() {
		return false;
	}
}
