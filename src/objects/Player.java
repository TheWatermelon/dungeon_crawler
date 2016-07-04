package objects;

import java.awt.Point;

public class Player extends Mob {
	private int gold;
	private int level;
	
	public Player() {
		this.hp=100;
		this.gold=0;
		this.level=1;
		this.dead = false;
		this.pos = new Point();
		this.symbol = '@';
	}
	
	public Player(int x, int y) {
		this.hp=100;
		this.gold=0;
		this.level=1;
		this.pos = new Point(x, y);
		this.symbol = '@';
		this.description = "Player";
	}
	
	public void murder() {
		this.dead = true;
	}
	
	public void addGold(int amount) {
		this.gold+=amount;
	}
	
	public String getInfo() {
		return "  HP : "+Math.round(this.hp)+"\tLevel : "+this.level+"\n  Gold : "+this.gold;
	}
}
