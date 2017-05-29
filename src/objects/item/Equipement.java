package objects.item;

import java.awt.Color;

import engine.Resources;
import objects.effect.Effect;
import objects.effect.EffectNormal;

public abstract class Equipement extends Item {
	protected int maxDurability;
	protected int durability;
	protected boolean isEquiped;
	protected Effect effect;
	
	public final int getDurability() { return this.durability; }
	public final int getMaxDurability() { return this.maxDurability; }
	public final void setDurability(int val) { this.durability = val; }
	public final void resetDurability() { this.durability = this.maxDurability; }
	public final void use() { this.durability--; }
	public final Effect getEffect() { return this.effect; }
	public final boolean isEquiped() { return isEquiped; }
	public final void equip() { isEquiped=true; }
	public final void unequip() { isEquiped=false; effect.stop(); }
	public abstract Effect pickEffect();
	
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
}
