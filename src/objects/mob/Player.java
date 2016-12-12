package objects.mob;

import java.awt.Point;
import java.util.Random;

import engine.MessageLog;
import objects.effect.*;
import objects.item.*;
import objects.looker.*;

public class Player extends Mob {
	private int gold;
	private int monstersKilled;
	private int potionEffect;
	private Weapon w;
	private Shield s;
	
	public Player(int x, int y, MessageLog l) {
		this.maxHealth=this.hp=100;
		this.atk=5;
		this.def=0;
		this.vit=this.potionEffect=0;
		this.w = new Weapon();
		this.s = new Shield();
		this.gold=0;
		this.monstersKilled=0;
		this.dead = false;
		this.pos = new Point(x, y);
		this.looker = new Looker(x, y);
		this.log = l;
		this.symbol = '@';
		this.description = "Player";
		this.effect = new EffectNormal();
	}
	
	public void murder() {
		this.dead = true;
		this.hp=0;
		log.appendMessage("Dead! Press r to restart");
	}
	
	public boolean fight(Monster m) {
		String battleLog="";
		
		if(!effect.apply()) { return false; }
		
		/* RESERVED FOR FUTURE USE */
		/*
		if(this.vit!=0 && this.vit>=2*m.vit) {
			// Player is twice faster than monster : he hits twice
			for(int i=0; i<2; i++) {
				battleLog+=fightTurn(m);
				if(m.hp <= 0) { 
					m.murder(); 
					this.monstersKilled++;
					log.appendMessage(battleLog);
					return true;
				}
				battleLog+=" ";
			}
		} 
		*/
		
		// Player attacks once
		battleLog+=fightTurn(m);
		if(m.hp <= 0) { 
			m.murder(); 
			this.monstersKilled++;
			log.appendMessage(battleLog);
			return true;
		}
		
		if(!battleLog.equals("")) { log.appendMessage(battleLog); }
		return false;
	}
	
	private String fightTurn(Monster m) {
		String battleLog="";
		Random rnd = new Random();
		int deg, dmg;
		
		deg = rnd.nextInt(3);
		if(deg==0) { 
			battleLog+=description+" miss ";
			setLooker(LookerFactory.getInstance().createLookerMiss(pos.x, pos.y));
		} else if(deg==1) { 
			dmg=(deg*this.getAtk())-m.getDef();
			if(dmg<=0) {
				battleLog+=m.description+" dodged "; 
				setLooker(LookerFactory.getInstance().createLookerMiss(pos.x, pos.y));
			} else {
				m.hp -= dmg;
				battleLog+=description+" deals "+dmg+" to "+m.description+" ";
				if(w.getEffect() instanceof EffectOther) { this.w.getEffect().start(m); }
				else if(w.getEffect() instanceof EffectSelf) { this.w.getEffect().apply(); }
				else if(w.getEffect() instanceof EffectEquipement) { this.w.getEffect().apply(); }
				useWeapon();
				setLooker(LookerFactory.getInstance().createLookerMob(pos.x, pos.y, dmg));
			}
		} else if(deg==2) {
			dmg=deg*this.getAtk();
			m.hp -= dmg;
			battleLog+=description+" deals !"+dmg+"! to "+m.description+" "; 
			if(w.getEffect() instanceof EffectOther) { this.w.getEffect().start(m); }
			else if(w.getEffect() instanceof EffectSelf) { this.w.getEffect().apply(); }
			else if(w.getEffect() instanceof EffectEquipement) { this.w.getEffect().apply(); }
			useWeapon();
			setLooker(LookerFactory.getInstance().createLookerMob(pos.x, pos.y, dmg));
		}
		
		return battleLog;
	}
	
	public void move(int x, int y) {
		pos = new Point(x, y);
		restoreHealth();
		consumePotionEffect();
	}
	
	public void cure() {
		resetHealth();
		effect = new EffectNormal();
		log.appendMessage(description+" is cured!");
	}
	
	public void heal(int val) {
		this.hp += val;
		if(this.hp>this.maxHealth) {
			resetHealth();
		}
	}
	
	public void harm(int val) {
		this.hp -= val;
		setLooker(LookerFactory.getInstance().createLookerDamage(pos.x, pos.y));
		if(this.hp<=0) {
			this.hp=0;
			murder();
		}
	}
	
	public int rewardGold() {
		Random rnd = new Random();
		int gold = rnd.nextInt(5)+1;
		this.addGold(gold);
		return gold;
	}
	
	public int rewardGold(int limit) {
		Random rnd = new Random();
		int gold = rnd.nextInt(limit)+1;
		this.addGold(gold);
		return gold;
	}
	
	public void addGold(int amount) {
		this.gold+=amount;
		log.appendMessage("Gained "+amount+"G");
	}
	
	public boolean isFullHealth() {
		return hp == maxHealth;
	}
	
	public void restoreHealth() {
		Random rnd = new Random();
		if(this.hp<100) {
			if(rnd.nextInt(5)==0) {
				this.hp += rnd.nextInt(4)+1;
				if(this.hp>this.maxHealth) { 
					resetHealth(); 
					setLooker(LookerFactory.getInstance().createLookerHealth(pos.x, pos.y));
				}
			}
		}
	}
	
	public void setPotionEffect(int val) {
		this.vit=val;
		if(val<0) {
			this.potionEffect=-1*val;
		} else {
			this.potionEffect=val;
		}
	}
	
	public void consumePotionEffect() {
		if(potionEffect!=0) {
			potionEffect--;
			if(potionEffect==0) {
				vit=0;
				log.appendMessage("Potion effect vanished!");
			}
		}
	}
	
	public void setHealth(int val) {
		this.hp = val;
	}
	
	public void useWeapon() {
		this.w.use();
		checkWeapon();
	}
	
	public void useShield() {
		this.s.use();
		checkShield();
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
		if(this.w.getVal() <= weapon.getVal()) {
			this.w = weapon;
			if(this.w.getEffect() instanceof EffectSelf) { this.w.getEffect().start(this); }
			if(this.w.getEffect() instanceof EffectEquipement) { this.w.getEffect().start(this); }
		} else {
			if(this.w.getDurability()<this.w.getMaxDurability()) {
				this.w.setDurability(this.w.getDurability() + 2*weapon.getVal());
				if(this.w.getDurability()>this.w.getMaxDurability()) {
					this.w.resetDurability();
				}
			}
		}
	}
	
	public void setShield(Shield shield) {
		if(this.s.getVal() <= shield.getVal()) {
			this.s = shield;
			if(this.s.getEffect() instanceof EffectSelf) { this.s.getEffect().start(this); }
			if(this.s.getEffect() instanceof EffectEquipement) { this.s.getEffect().start(this); }
		} else {
			if(this.s.getDurability()<this.s.getMaxDurability()) {
				this.s.setDurability(this.s.getDurability() + 2*shield.getVal());
				if(this.s.getDurability()>this.s.getMaxDurability()) {
					this.s.resetDurability();
				}
			}
		}
	}
	
	public void repareWeapon() {
		if(this.w.getDurability()<this.w.getMaxDurability() && this.w.getVal()>0) {
			if(this.gold>=this.w.getVal()) {
				this.gold -= this.w.getVal();
				this.w.setDurability(this.w.getDurability()+1);
				log.appendMessage("Spent "+this.w.getVal()+" to repare "+this.w);
			}
		}
	}
	
	public void repareShield() {
		if(this.s.getDurability()<this.s.getMaxDurability() && this.s.getVal()>0) {
			if(this.gold>=this.s.getVal()) {
				this.gold -= this.s.getVal();
				this.s.setDurability(this.s.getDurability()+1);
				log.appendMessage("Spent "+this.s.getVal()+" to repare "+this.s);
			}
		}
	}
	
	public boolean applyOnWalkEffect() {
		boolean ret=true;
		if(w.getEffect() instanceof EffectSelf) {
			ret=w.getEffect().apply();
		}
		if(s.getEffect() instanceof EffectSelf) {
			if(!ret) {
				s.getEffect().apply();
				return ret;
			} else {
				ret=s.getEffect().apply();
			}
		}
		return ret;
	}
	
	public void idleLooker() {
		this.looker = LookerFactory.getInstance().createLookerEquipement(pos.x, pos.y, this);
		this.looker.show();
	}
	
	public void reset() {
		this.hp=this.maxHealth;
		this.atk=5;
		this.gold=0;
		this.w=new Weapon();
		this.s=new Shield();
		this.effect=new EffectNormal();
		this.looker.hide();
		this.monstersKilled=0;
		this.dead=false;
	}
	
	public int getAtk() { return this.atk+this.w.getVal(); }
	
	public int getDef() { return this.def+this.s.getVal(); }
	
	public int getKills() {	return this.monstersKilled; }
	
	public Weapon getWeapon() { return this.w; }
	
	public Shield getShield() { return this.s; }
	
	public String getInfo() {
		return ""+drawHealthBar()+"\nGold : "+this.gold+"\n"+"Kills : "+this.monstersKilled;
	}
	
	public String getAllInfo() {
		String weapon="\t", shield="\t"/*, vit=""+this.vit*/;
		
		if(this.w.getVal()>0) {
			weapon=this.w+" +"+this.w.getVal()+" ("+this.w.getDurability()+"/"+this.w.getMaxDurability()+")";
		}
		if(this.s.getVal()>0) {
			shield=this.s+" +"+this.s.getVal()+" ("+this.s.getDurability()+"/"+this.s.getMaxDurability()+")";
		}
		/*
		if(potionEffect>0) {
			vit+=" ("+potionEffect+")";
		}
		*/
		return "   "+drawHealthBar()+" "+this.effect+" G:"+this.gold+/*"  VIT:"+vit+*/"\n   "+weapon+"\n"+"   "+shield;
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
