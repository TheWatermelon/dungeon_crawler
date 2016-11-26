package objects.mob;

import java.awt.Point;

import tiles.TileFactory;

public class Boss extends Monster {
	public Boss(int x, int y, int bonus, char s, String desc) {
		super(x, y, s, desc);
		this.pos = new Point(x, y);
		this.maxHealth=this.hp=40+(bonus*10);
		this.atk=bonus*5;
		this.def=bonus*5;
		this.vit=pickVit();
		this.dead=false;
		this.symbol=s;
		this.description=desc;
		this.floor = TileFactory.getInstance().createTileMonster(this.symbol);
		this.mobTile = TileFactory.getInstance().createTileMonster(this.symbol);
	}
	
	public void murder() {
		this.dead = true;
		this.mobTile = TileFactory.getInstance().createTileStairsDown();
	}
}
