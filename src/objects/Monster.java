package objects;

import java.awt.Point;

import tiles.TileFactory;

public class Monster extends Mob {
	public Monster() {
		
	}
	
	public Monster(int x, int y, char s, String desc) {
		this.hp = 20;
		this.atk = 3;
		this.dead = false;
		this.pos = new Point(x, y);
		this.symbol = s;
		this.description = desc;
		this.floor = TileFactory.getInstance().createTileMonster(this.symbol);
	}
	
	public void murder() {
		this.dead = true;
		this.floor = TileFactory.getInstance().createTileCorpse();
	}
}
