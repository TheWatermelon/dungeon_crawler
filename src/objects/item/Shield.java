package objects.item;

import java.awt.Point;

import tiles.Tile;
import tiles.TileFactory;
import engine.Ressources;

public class Shield extends Equipement {	
	public Shield() {
		this.val=0;
		this.maxDurability = -1;
		this.resetDurability();
		this.description = Ressources.getShieldAt(0);
	}
	
	public Shield(int x, int y) {
		this.pos = new Point(x, y);
		this.val=pickVal(5);
		this.maxDurability = val*10;
		this.resetDurability();
		this.description = Ressources.getShieldAt(val);
	}
	
	public Tile getTile() {
		return TileFactory.getInstance().createTileShield();
	}
}
