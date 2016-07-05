package objects;

import java.awt.Point;
import java.util.Random;

import engine.MessageLog;

public class Player extends Mob {
	private int gold;
	private int level;
	
	public Player() {
		this.hp=100;
		this.atk=5;
		this.gold=0;
		this.level=1;
		this.dead = false;
		this.pos = new Point();
		this.symbol = '@';
		this.description = "Player";
	}
	
	public Player(int x, int y) {
		this.hp=100;
		this.atk=5;
		this.gold=0;
		this.level=1;
		this.dead = false;
		this.pos = new Point(x, y);
		this.symbol = '@';
		this.description = "Player";
	}
	
	public void murder() {
		this.dead = true;
	}
	
	public void fight(Monster m, MessageLog l) {
		Random rnd = new Random();
		int deg;
		
		deg = rnd.nextInt(3);
		this.hp -= deg*m.atk;
		if(deg==0) { l.appendMessage(m.description+" miss"); }
		else { l.appendMessage(m.description+" deals "+(deg*m.atk)+" dmg to "+description); }
		if(this.hp <= 0) {
			this.hp=0;
			murder();
			l.appendMessage("Dead! Press r to restart");
			return;
		}

		deg = rnd.nextInt(3);
		m.hp -= deg*this.atk;
		if(deg==0) { l.appendMessage(description+" miss"); }
		else { l.appendMessage(description+" deals "+(deg*this.atk)+" dmg to "+m.description); }
		if(m.hp <= 0) { 
			m.murder(); 
			l.appendMessage(m.description+" killed");
			if(rnd.nextInt(5)==0) {
				l.appendMessage("Gained "+rewardGold()+"G");
			}
		}
	}
	
	public int rewardGold() {
		Random rnd = new Random();
		int gold = rnd.nextInt(5)+1;
		this.addGold(gold);
		return gold;
	}
	
	private void addGold(int amount) {
		this.gold+=amount;
	}
	
	public void restoreHealth() {
		Random rnd = new Random();
		if(this.hp<100) {
			if(rnd.nextInt(5)==0) {
				this.hp += rnd.nextInt(4)+1;
				if(this.hp>100) { this.hp = 100; }
			}
		}
	}
	
	public void reset() {
		this.hp=100;
		this.atk=5;
		this.gold=0;
		this.atk=5;
		this.level=1;
		this.dead=false;
	}
	
	public String getInfo() {
		return "   HP : "+this.hp+"\t\n   Gold : "+this.gold+"\n"+"   Level : "+this.level;
	}
}
