package tiles;

import objects.Weapon;

public class TileWeapon extends Tile {
	private Weapon w;

	public TileWeapon() {
		this.walkable=true;
		this.description="Weapon";
		this.w = new Weapon();
	}
	
	public TileWeapon(int atk) {
		this.walkable=true;
		this.description="Weapon";
		this.w = new Weapon(atk);
	}
	
	public Weapon getWeapon() {
		return this.w;
	}
	
	@Override
	public char getSymbol() {
		return '/';
	}

}
