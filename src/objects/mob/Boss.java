package objects.mob;

import java.awt.Point;

import engine.MessageLog;
import tiles.TileFactory;

public class Boss extends Monster {
	public Boss(int x, int y, int bonus, char s, String desc, MessageLog l) {
		super(x, y, s, desc, l);
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
		this.log = l;
	}
	
	public String murder() {
		this.dead = true;
		this.hp=0;
		this.mobTile = TileFactory.getInstance().createTileStairsDown();
		return "[Boss] "+description+" defeated, stairs revealed !";
	}
}
