package objects.looker;

import objects.mob.Player;
import tiles.TileFactory;

public class LookerEquipement extends Looker {
	public LookerEquipement(int x, int y) {
		super(x, y);
	}
	
	public void refresh(Player p) {
		if(p.getWeapon().getDurability()!=-1) {
			this.left = TileFactory.getInstance().createTileWeapon().getSymbol();
		} else {
			this.left = ' ';
		}
		if(p.getShield().getDurability()!=-1) {
			this.right = TileFactory.getInstance().createTileShield().getSymbol();
		} else {
			this.right = ' ';
		}
	}
}
