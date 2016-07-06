package tiles;

import objects.Shield;

public class TileShield extends Tile {
	private Shield s;

	public TileShield() {
		this.walkable=true;
		this.description="Shield";
		this.s = new Shield();
	}
	
	public TileShield(int val) {
		this.walkable=true;
		this.description="Shield";
		this.s = new Shield(val);
	}
	
	public Shield getShield() {
		return this.s;
	}
	
	@Override
	public char getSymbol() {
		return ']';
	}
}
