package objects.item;

public abstract class Equipement extends Item {
	protected int maxDurability;
	protected int durability;
	
	public final int getDurability() { return this.durability; }
	public final int getMaxDurability() { return this.maxDurability; }
	public final void setDurability(int val) { this.durability = val; }
	public final void resetDurability() { this.durability = this.maxDurability; }
	public final void use() { this.durability--; }
}
