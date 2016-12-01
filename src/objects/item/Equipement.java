package objects.item;

import objects.effect.Effect;

public abstract class Equipement extends Item {
	protected int maxDurability;
	protected int durability;
	protected Effect effect;
	
	public final int getDurability() { return this.durability; }
	public final int getMaxDurability() { return this.maxDurability; }
	public final void setDurability(int val) { this.durability = val; }
	public final void resetDurability() { this.durability = this.maxDurability; }
	public final void use() { this.durability--; }
	public final Effect getEffect() { return this.effect; }
}
