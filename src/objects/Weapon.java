package objects;

import engine.Ressources;

public class Weapon extends Equipement {
	
	public Weapon() {
		this.val=0;
		this.maxDurability = -1;
		this.resetDurability();
		this.description = Ressources.getWeaponAt(0);
	}
	
	public Weapon(int v) {
		this.val=v;
		this.maxDurability = v*10;
		this.resetDurability();
		this.description = Ressources.getWeaponAt(v);
	}
}
