package objects;

import java.awt.Point;
import java.util.Random;

import engine.MessageLog;
import tiles.TileFactory;

public class Monster extends Mob {
	
	public Monster() {
		
	}
	
	public Monster(int x, int y, char s, String desc) {
		this.hp = 20;
		this.atk = pickAtk();
		this.dead = false;
		this.pos = new Point(x, y);
		this.symbol = s;
		this.description = desc;
		this.floor = TileFactory.getInstance().createTileMonster(this.symbol);
	}
	
	private int pickAtk() {
		Random rnd = new Random();
		return rnd.nextInt(4)+3;
	}
	
	public void murder() {
		this.dead = true;
		this.floor = TileFactory.getInstance().createTileCorpse();
	}
	
	public void fight(Player p, MessageLog l) {
		Random rnd = new Random();
		int deg;

		deg = rnd.nextInt(3);
		p.hp -= deg*this.atk;
		if(deg==0) { l.appendMessage(description+" miss"); }
		else { l.appendMessage(description+" deals "+(deg*this.atk)+" dmg to "+p.description); }
		if(p.hp <= 0) { 
			p.murder(); 
			l.appendMessage("Dead! Press r to restart");
		}
	}
}
