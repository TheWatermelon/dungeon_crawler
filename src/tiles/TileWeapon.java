package tiles;

public class TileWeapon extends TileItem {

	public TileWeapon() {
		this.walkable=true;
		this.description="Weapon";
	}
	
	@Override
	public char getSymbol() {
		return '/';
	}

}
