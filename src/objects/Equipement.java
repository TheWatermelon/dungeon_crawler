package objects;

public abstract class Equipement {
	protected int val;
	protected int maxDurability;
	protected int durability;
	protected String description;
	
	public final int getVal() { return this.val; }
	public final int getDurability() { return this.durability; }
	public final int getMaxDurability() { return this.maxDurability; }
	public final void resetDurability() { this.durability = this.maxDurability; }
	public final void use() { this.durability--; }
	public String toString() { return this.description; }
}
