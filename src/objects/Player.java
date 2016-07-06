package objects;

import java.awt.Point;
import java.util.Random;

import engine.MessageLog;

public class Player extends Mob {
	private int gold;
	private int level;
	private Weapon w;
	private Shield s;
	private MessageLog log;
	
	public Player() {
		this.hp=100;
		this.atk=5;
		this.w = new Weapon();
		this.s = new Shield();
		this.gold=0;
		this.level=1;
		this.dead = false;
		this.pos = new Point();
		this.symbol = '@';
		this.description = "Player";
	}
	
	public Player(int x, int y, MessageLog l) {
		this.hp=100;
		this.atk=5;
		this.w = new Weapon();
		this.s = new Shield();
		this.gold=0;
		this.level=1;
		this.dead = false;
		this.pos = new Point(x, y);
		this.log = l;
		this.symbol = '@';
		this.description = "Player";
	}
	
	public void murder() {
		this.dead = true;
	}
	
	public void fight(Monster m) {
		Random rnd = new Random();
		int deg;
		
		deg = rnd.nextInt(3);
		if(deg==0) { 
			log.appendMessage(m.description+" miss"); 
		} else if(((deg*m.atk)-this.s.getVal())<=0) {
			this.s.use();
			log.appendMessage(description+" dodged"); 
		} else {
			this.hp -= (deg*m.atk)-this.s.getVal();
			this.s.use();
			checkShield();
			log.appendMessage(m.description+" deals "+((deg*m.atk)-this.s.getVal())+" dmg to "+description); }
		if(this.hp <= 0) {
			this.hp=0;
			murder();
			log.appendMessage("Dead! Press r to restart");
			return;
		}

		deg = rnd.nextInt(3);
		if(deg==0) { 
			log.appendMessage(description+" miss"); 
		} else {
			m.hp -= deg*(this.atk+this.w.getVal());
			this.w.use();
			checkWeapon();
			log.appendMessage(description+" deals "+(deg*(this.atk+this.w.getVal()))+" dmg to "+m.description); 
		}
		if(m.hp <= 0) { 
			m.murder(); 
			log.appendMessage(m.description+" killed");
			if(rnd.nextInt(5)==0) {
				rewardGold();
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
		log.appendMessage("Gained "+amount+"G");
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
	
	public void setHealth(int val) {
		this.hp = val;
	}
	
	public void checkWeapon() {
		if(this.w.getDurability()==0) {
			this.w = new Weapon();
		}
	}
	
	public void checkShield() {
		if(this.s.getDurability()==0) {
			this.s = new Shield();
		}
	}
	
	public void setWeapon(Weapon weapon) {
		if(this.w.getVal() < weapon.getVal()) {
			this.w = weapon;
		}
	}
	
	public void setShield(Shield shield) {
		if(this.s.getVal() < shield.getVal()) {
			this.s = shield;
		}
	}
	
	public void reset() {
		this.hp=100;
		this.atk=5;
		this.gold=0;
		this.w=new Weapon();
		this.s=new Shield();
		this.level=1;
		this.dead=false;
	}
	
	public String getInfo() {
		return "   HP : "+this.hp+"\t\n   Gold : "+this.gold+"\n"+"   Level : "+this.level;
	}
	
	public String getWeaponInfo() {
		String res="";
		if(this.w.getVal()>0) {
			res+="Weapon +"+this.w.getVal()+" ("+this.w.getDurability()+"/"+this.w.getMaxDurability()+")  \n";
		}
		if(this.s.getVal()>0) {
			res+="Shield +"+this.s.getVal()+" ("+this.s.getDurability()+"/"+this.s.getMaxDurability()+")  ";
		}
		return res;
	}
}
