package tiles;

public class TileShield extends TileItem {
	public TileShield() {
		this.walkable=true;
		this.description="Shield";
	}
	
	@Override
	public char getSymbol() {
		return ']';
	}
}
