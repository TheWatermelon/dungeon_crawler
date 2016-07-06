package objects;

import java.awt.Point;

import tiles.Tile;
import tiles.TileFactory;
import engine.Ressources;

public class Weapon extends Equipement {
	
	public Weapon() {
		this.val=0;
		this.maxDurability = -1;
		this.resetDurability();
		this.description = Ressources.getWeaponAt(0);
	}
	
	public Weapon(int x, int y) {
		this.pos = new Point(x, y);
		this.val=pickVal(5);
		this.maxDurability = val*10;
		this.resetDurability();
		this.description = Ressources.getWeaponAt(val);
	}
	
	public Tile getTile() {
		return TileFactory.getInstance().createTileWeapon();
	}
}
