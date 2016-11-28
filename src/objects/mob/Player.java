package objects.mob;

import java.awt.Point;
import java.util.Random;

import engine.MessageLog;
import objects.item.Shield;
import objects.item.Weapon;
import objects.looker.Looker;
import objects.looker.LookerFactory;

public class Player extends Mob {
	private int gold;
	private int monstersKilled;
	private int potionEffect;
	private Weapon w;
	private Shield s;
	private MessageLog log;
	
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
	}
	
	public void murder() {
		this.dead = true;
		this.hp=0;
		log.appendMessage("Dead! Press r to restart");
	}
	
	public boolean fight(Monster m) {
		String battleLog="";
		
		// Fighting turns
		if(this.vit!=0 && m.vit>=2*this.vit) {
			// Monster is twice faster than player : it hits twice without being hit
			for(int i=0; i<2; i++) {
				battleLog+=m.fightTurn(this);
				if(this.hp <= 0) {
					murder();
					return false;
				}
				battleLog+=" ";
			}
			log.appendMessage(battleLog);
		} else if(this.vit!=0 && this.vit>=2*m.vit) {
			// Player is twice faster than monster : he hits twice without being hit
			for(int i=0; i<2; i++) {
				battleLog+=fightTurn(m);
				if(m.hp <= 0) { 
					m.murder(); 
					this.monstersKilled++;
					battleLog+=", "+m.description+" killed";
					log.appendMessage(battleLog);
					return true;
				}
				battleLog+=" ";
			}
			log.appendMessage(battleLog);
		} else if(this.vit<m.vit) {
			// Monster engage the fight
			battleLog+=m.fightTurn(this);
			if(this.hp <= 0) {
				murder();
				return false;
			}
			battleLog+=", "+fightTurn(m);
			log.appendMessage(battleLog);
			if(m.hp <= 0) { 
				m.murder(); 
				this.monstersKilled++;
				log.appendMessage(m.description+" killed");
				return true;
			}
		} else {
			// Player engage the fight
			battleLog+=fightTurn(m);
			if(m.hp <= 0) { 
				m.murder(); 
				this.monstersKilled++;
				battleLog+=", "+m.description+" killed";
				log.appendMessage(battleLog);
				return true;
			}
			battleLog+=", "+m.fightTurn(this);
			log.appendMessage(battleLog);
			if(this.hp <= 0) {
				murder();
				return false;
			}
		}

		return false;
	}
	
	private String fightTurn(Monster m) {
		String battleLog="";
		Random rnd = new Random();
		int deg, dmg;
		
		deg = rnd.nextInt(3);
		if(deg==0) { 
			battleLog+=description+" miss";
			setLooker(LookerFactory.getInstance().createLookerMiss(pos.x, pos.y));
		} else if(deg==1) { 
			dmg=(deg*this.getAtk())-m.getDef();
			if(dmg<=0) {
				battleLog+=m.description+" dodged"; 
				setLooker(LookerFactory.getInstance().createLookerMiss(pos.x, pos.y));
			} else {
				m.hp -= dmg;
				useWeapon();
				battleLog+=description+" deals "+dmg+" to "+m.description; 
				setLooker(LookerFactory.getInstance().createLookerDamage(pos.x, pos.y, dmg));
			}
		} else if(deg==2) {
			dmg=deg*this.getAtk();
			m.hp -= dmg;
			useWeapon();
			battleLog+=description+" deals !"+dmg+"! to "+m.description; 
			setLooker(LookerFactory.getInstance().createLookerDamage(pos.x, pos.y, dmg));
		}
		return battleLog;
	}
	
	public void heal(int val) {
		this.hp += val;
		if(this.hp>this.maxHealth) {
			resetHealth();
		}
	}
	
	public void harm(int val) {
		this.hp -= val;
		setLooker(LookerFactory.getInstance().createLookerMob(pos.x, pos.y));
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
	
	public void reset() {
		this.hp=this.maxHealth;
		this.atk=5;
		this.gold=0;
		this.w=new Weapon();
		this.s=new Shield();
		this.monstersKilled=0;
		this.dead=false;
	}
	
	public int getAtk() {
		return this.atk+this.w.getVal();
	}
	
	public int getDef() {
		return this.def+this.s.getVal();
	}
	
	public int getKills() {
		return this.monstersKilled;
	}
	
	public String getInfo() {
		return ""+drawHealthBar()+"\nGold : "+this.gold+"\n"+"Kills : "+this.monstersKilled;
	}
	
	public String getAllInfo() {
		String weapon="\t", shield="\t", vit=""+this.vit;
		
		if(this.w.getVal()>0) {
			weapon=this.w+" +"+this.w.getVal()+" ("+this.w.getDurability()+"/"+this.w.getMaxDurability()+")";
		}
		if(this.s.getVal()>0) {
			shield=this.s+" +"+this.s.getVal()+" ("+this.s.getDurability()+"/"+this.s.getMaxDurability()+")";
		}
		if(potionEffect>0) {
			vit+=" ("+potionEffect+")";
		}
		return "   "+drawHealthBar()+" G:"+this.gold+"  VIT:"+vit+"\n   "+weapon+"\n"+"   "+shield;
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
