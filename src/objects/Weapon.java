package objects;

public class Weapon extends Equipement {
	
	public Weapon() {
		this.val=0;
		this.maxDurability = -1;
		this.resetDurability();
	}
	
	public Weapon(int v) {
		this.val=v;
		this.maxDurability = v*10;
		this.resetDurability();
	}
}
