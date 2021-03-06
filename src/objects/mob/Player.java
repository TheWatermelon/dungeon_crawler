package objects.mob;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import engine.Dungeon;
import engine.Message;
import engine.MessageLog;
import engine.Resources;
import objects.effect.*;
import objects.item.*;
import objects.looker.*;

public class Player extends Mob {
	private Inventory inventory;
	private int gold;
	private int monstersKilled;
	private int potionEffect;
	private Equipement w;
	private Shield s;
	private Helmet h;
	private Dungeon dungeon;
	
	public Player(int x, int y, MessageLog l, Dungeon d) {
		this.maxHealth=this.hp=100;
		this.atk=5;
		this.def=0;
		this.vit=this.potionEffect=0;
		this.bonusAtk = this.bonusDef = this.bonusVit = 0;
		this.w = new Weapon();
		this.s = new Shield();
		this.h = new Helmet();
		this.gold=0;
		this.monstersKilled=0;
		this.dead = false;
		this.pos = new Point(x, y);
		this.looker = new LookerStuff(x, y);
		this.log = l;
		this.symbol = '@';
		this.description = Resources.generatePlayerName();
		this.effect = new EffectNormal();
		this.inventory = new Inventory(6, l, this);
		this.dungeon=d;
	}
	
	public String murder() {
		this.dead = true;
		this.hp=0;
		Resources.playGameOverSound();
		return "Dead! Press "+Resources.Commands.Restart.getKey()+" to restart";
	}
	
	public String fight(Monster m) {		
		if(!effect.apply()) { return ""; }
		
		String battleLog = "";
		
		// Player attacks once
		battleLog+=fightTurn(m);
		if(m.hp <= 0) { 
			battleLog += ", " + m.murder(); 
			this.monstersKilled++;
		}
		return battleLog;
	}
	
	private String fightTurn(Monster m) {
		String battleLog="";
		Random rnd = new Random();
		int deg, dmg;
		
		deg = rnd.nextInt(3);
		if(deg==0) { 
			battleLog+=description+" miss";
			setLooker(LookerFactory.getInstance().createLookerMiss(pos.x, pos.y));
			Resources.playWhooshSound();
		} else if(deg==1) { 
			dmg=(deg*this.getAtk())-m.getDef();
			if(dmg<=0) {
				battleLog+=m.description+" dodged"; 
				setLooker(LookerFactory.getInstance().createLookerMiss(pos.x, pos.y));
				Resources.playWhooshSound();
			} else {
				m.hp -= dmg;
				battleLog+=description+" deals "+dmg+" to "+m.description;
				if(w.getEffect() instanceof EffectOther) { this.w.getEffect().start(m); }
				else if(w.getEffect() instanceof EffectSelf) { this.w.getEffect().apply(); }
				else if(w.getEffect() instanceof EffectEquipement) { this.w.getEffect().apply(); }
				useWeapon();
				setLooker(LookerFactory.getInstance().createLookerMob(pos.x, pos.y, dmg));
				Resources.playMonsterHurtSound();
			}
		} else if(deg==2) {
			dmg=deg*this.getAtk();
			m.hp -= dmg;
			battleLog+=description+" deals !"+dmg+"! to "+m.description; 
			if(w.getEffect() instanceof EffectOther) { this.w.getEffect().start(m); }
			else if(w.getEffect() instanceof EffectSelf) { this.w.getEffect().apply(); }
			else if(w.getEffect() instanceof EffectEquipement) { this.w.getEffect().apply(); }
			useWeapon();
			setLooker(LookerFactory.getInstance().createLookerMob(pos.x, pos.y, dmg));
			Resources.playMonsterHurtSound();
		}
		
		return battleLog;
	}
	
	public void move(int x, int y) {
		pos = new Point(x, y);
		restoreHealth();
		consumePotionEffect();
		stuffLooker();
	}
	
	public void cure() {
		resetHealth();
		effect = new EffectNormal();
		log.appendMessage(description+" is cured!", Message.Type.Other);
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
		log.appendMessage("Gained "+amount+"G", Message.Type.Important);
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
	
	public void resetBonuses() {
		this.bonusAtk = this.bonusDef = this.bonusVit = 0;
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
				log.appendMessage("Potion effect vanished!", Message.Type.Normal);
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
	
	public void useHelmet() {
		this.h.use();
		checkHelmet();
	}
	
	private void checkWeapon() {
		if(this.w.getDurability()==0) {
			inventory.removeItem(this.w);
			this.w.unequip();
			this.w = new Weapon();
			if(this.w instanceof Weapon) {
				log.appendMessage("Weapon broke!", Message.Type.Important);
			} else if(this.w  instanceof Bow) {
				log.appendMessage("Bow broke!", Message.Type.Important);
			}
			Resources.playWeaponWornOutSound();
		}
	}
	
	private void checkShield() {
		if(this.s.getDurability()==0) {
			inventory.removeItem(this.s);
			this.s.unequip();
			this.s = new Shield();
			log.appendMessage("Shield broke!", Message.Type.Important);
			Resources.playWeaponWornOutSound();
		}
	}
	
	private void checkHelmet() {
		if(this.h.getDurability()==0) {
			inventory.removeItem(this.h);
			this.h.unequip();
			this.h = new Helmet();
			log.appendMessage(this.h.getType()+" fell off!", Message.Type.Important);
			Resources.playWeaponWornOutSound();
		}
	}
	
	public void setWeapon(Equipement weapon) {
		this.w.unequip();
		this.w = weapon;
		this.w.equip();
		if(w instanceof Bow) { 
			s.unequip();
			s = new Shield();
		}
		if(this.w.getEffect() instanceof EffectSelf) { this.w.getEffect().start(this); }
		if(this.w.getEffect() instanceof EffectEquipement) { this.w.getEffect().start(this); }
	}
	
	public void setShield(Shield shield) {
		this.s.unequip();
		this.s = shield;
		this.s.equip();
		if(this.w instanceof Bow) { 
			this.w.unequip(); 
			this.w = new Weapon();
		}
		if(this.s.getEffect() instanceof EffectSelf) { this.s.getEffect().start(this); }
		if(this.s.getEffect() instanceof EffectEquipement) { this.s.getEffect().start(this); }
	}
	
	public void setHelmet(Helmet helmet) {
		this.h.unequip();
		this.h = helmet;
		this.h.equip();
		if(this.h.getEffect() instanceof EffectSelf) { this.h.getEffect().start(this); }
		if(this.h.getEffect() instanceof EffectEquipement) { this.h.getEffect().start(this); }
	}
	
	public void unequip(Item i) {
		if(i == this.h) { 
			this.h.unequip();
			this.h = new Helmet();
		} else if(i == this.s) {
			this.s.unequip();
			this.s = new Shield();
		} else if(i == this.w) {
			this.w.unequip();
			this.w = new Weapon();
		}
	}
	
	public void repareWeapon() {
		repareEquipement(w);
	}
	
	public void repareShield() {
		repareEquipement(s);
	}
	
	public void repareEquipement(Equipement e) {
		if(e.getDurability()<e.getMaxDurability() && e.getVal()>0) {
			if(this.gold>=e.getVal()) {
				this.gold -= e.getVal();
				e.setDurability(e.getDurability()+1);
				log.appendMessage("Spent "+e.getVal()+" to repare "+e, Message.Type.Normal);
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
	
	public void stuffLooker() {
		this.looker = LookerFactory.getInstance().createLookerStuff(pos.x, pos.y, this);
		this.looker.show();
	}
	
	public void changeName() { this.description = Resources.generatePlayerName(); }
	
	public void reset() {
		this.hp=this.maxHealth;
		this.atk=5;
		this.gold=0;
		setWeapon(new Weapon());
		setShield(new Shield());
		setHelmet(new Helmet());
		this.inventory.clear();
		this.effect=new EffectNormal();
		stuffLooker();
		this.monstersKilled=0;
		this.dead=false;
	}
	
	@Override
	public Color getColor() {
		if(effect instanceof EffectNormal) {
			if(hp<10) {
				return new Color(0xAA, 0x00, 0x00);
			} else if(hp<20) {
				return new Color(0xDD, 0x00, 0x00);
			} else if(hp<30) {
				return new Color(0xFF, 0x33, 0x00);
			} else if(hp<40) {
				return new Color(0xFF, 0x66, 0x00);
			} else if(hp<50) {
				return new Color(0xFF, 0xAA, 0x00);
			} else if(hp<60) {
				return new Color(0xFF, 0xFF, 0x00);
			} else if(hp<70) {
				return new Color(0xDD, 0xFF, 0x00);
			} else if(hp<80) {
				return new Color(0xAA, 0xFF, 0x00);
			} else if(hp<90) {
				return new Color(0x66, 0xFF, 0x00);
			} else {
				return Resources.green;
			}
		} else {
			return effect.getColor();
		}
	}
	
	public Color getLookerColor() {
		if(!(this.looker instanceof LookerStuff)) {
			return this.looker.getColor();
		}
		return Resources.white;
	}
	
	public int getGold() { return this.gold; }
	public void setGold(int val) { this.gold = val; }
	
	public int getAtk() { return this.atk+this.bonusAtk+this.w.getVal(); }
	
	public int getDef() { return this.def+this.bonusDef+this.s.getVal(); }
	
	public int getKills() {	return this.monstersKilled; }
	public void setKills(int val) { this.monstersKilled = val; }
	
	public int getPotionEffect() { return this.potionEffect; }
	
	public Equipement getWeapon() { return this.w; }
	
	public Shield getShield() { return this.s; }
	
	public Helmet getHelmet() { return this.h; }
	
	public Inventory getInventory() { return this.inventory; }
	public void setInventory(Inventory i) { this.inventory = i; }
	
	public Dungeon getDungeon() { return this.dungeon; }
	
	public String getInfo() {
		String strEffect="";
		if(!(this.effect instanceof EffectNormal)) {
			strEffect += "["+this.effect+"] ";
		}
		return strEffect+description+"\n"+drawHealthBar()+"\nGold : "+this.gold+" Kills : "+this.monstersKilled;
	}
	
	public String getAllInfo() {
		String weapon="\t", shield="\t";
		
		if(this.w.getVal()>0) {
			weapon=this.w+" +"+this.w.getVal()+" ("+this.w.getDurability()+"/"+this.w.getMaxDurability()+")";
		}
		if(this.s.getVal()>0) {
			shield=this.s+" +"+this.s.getVal()+" ("+this.s.getDurability()+"/"+this.s.getMaxDurability()+")";
		}
		return "   "+drawHealthBar()+" "+this.effect+" G:"+this.gold+"\n   "+weapon+"\n"+"   "+shield;
	}
	
	public String getWeaponInfo() {
		String res="";
		if(this.h.getVal()>0) {
			res+="  "+this.h+" ("+this.h.getDurability()+"/"+this.h.getMaxDurability()+")\n";
		}
		if(this.w.getVal()>0) {
			res+="  "+this.w+" ("+this.w.getDurability()+"/"+this.w.getMaxDurability()+")\n";
		}
		if(this.s.getVal()>0) {
			res+="  "+this.s+" ("+this.s.getDurability()+"/"+this.s.getMaxDurability()+")";
		}
		return res;
	}
}
