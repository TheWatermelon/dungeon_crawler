package objects;

import java.awt.Point;
import java.util.Random;

import engine.MessageLog;

public class Player extends Mob {
	private int gold;
	private int monstersKilled;
	private Weapon w;
	private Shield s;
	private MessageLog log;
	
	public Player() {
		this.hp=100;
		this.atk=5;
		this.w = new Weapon();
		this.s = new Shield();
		this.gold=0;
		this.monstersKilled=0;
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
		this.monstersKilled=0;
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
			checkShield();
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
			this.monstersKilled++;
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
	
	public void addGold(int amount) {
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
	
	private void checkWeapon() {
		if(this.w.getDurability()==0) {
			this.w = new Weapon();
			log.appendMessage("Weapon broke!");
		}
	}
	
	private void checkShield() {
		if(this.s.getDurability()==0) {
			this.s = new Shield();
			log.appendMessage("Shield broke!");
		}
	}
	
	public void setWeapon(Weapon weapon) {
		if(this.w.getVal() < weapon.getVal()) {
			this.w = weapon;
		} else {
			if(this.w.getDurability()<this.w.getMaxDurability()) {
				this.w.durability += 2*weapon.getVal();
				if(this.w.durability>this.w.maxDurability) {
					this.w.resetDurability();
				}
			}
		}
	}
	
	public void setShield(Shield shield) {
		if(this.s.getVal() < shield.getVal()) {
			this.s = shield;
		} else {
			if(this.s.getDurability()<this.s.getMaxDurability()) {
				this.s.durability += 2*shield.getVal();
				if(this.s.durability>this.s.maxDurability) {
					this.s.resetDurability();
				}
			}
		}
	}
	
	public void repareWeapon() {
		if(this.w.durability<this.w.maxDurability && this.w.getVal()>0) {
			if(this.gold>=this.w.getVal()) {
				this.gold -= this.w.getVal();
				this.w.durability++;
				log.appendMessage("Spent "+this.w.getVal()+" to repare "+this.w);
			}
		}
	}
	
	public void repareShield() {
		if(this.s.durability<this.s.maxDurability && this.s.getVal()>0) {
			if(this.gold>=this.s.getVal()) {
				this.gold -= this.s.getVal();
				this.s.durability++;
				log.appendMessage("Spent "+this.s.getVal()+" to repare "+this.s);
			}
		}
	}
	
	public void reset() {
		this.hp=100;
		this.atk=5;
		this.gold=0;
		this.w=new Weapon();
		this.s=new Shield();
		this.monstersKilled=0;
		this.dead=false;
	}
	
		public int getKills() {
			return this.monstersKilled;
		}
	
	public String getInfo() {
		return "   HP : "+this.hp+"\t\n   Gold : "+this.gold+"\t\n"+"   Kills : "+this.monstersKilled;
	}
	
	public String getAllInfo() {
		String weapon="\t", shield="\t";
		
		if(this.w.getVal()>0) {
			weapon=this.w+" +"+this.w.getVal()+" ("+this.w.getDurability()+"/"+this.w.getMaxDurability()+")";
		}
		if(this.s.getVal()>0) {
			shield=this.s+" +"+this.s.getVal()+" ("+this.s.getDurability()+"/"+this.s.getMaxDurability()+")";
		}
		return "   HP : "+this.hp+"\t"+weapon+"\n   Gold : "+this.gold+"\t"+shield+"\n"+"   Kills : "+this.monstersKilled;
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
