package objects.mob;

import java.awt.Point;
import java.util.Random;

import engine.MessageLog;
import objects.item.Shield;
import objects.item.Weapon;
import objects.looker.Looker;
import objects.looker.LookerFactory;

public class Player extends Mob {
	private int maxHealth;
	private int gold;
	private int monstersKilled;
	private Weapon w;
	private Shield s;
	private MessageLog log;
	private Looker looker;
	
	public Player(int x, int y, MessageLog l) {
		this.maxHealth=this.hp=100;
		this.atk=5;
		this.def=0;
		this.vit=0;
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
		log.appendMessage("Dead! Press r to restart");
	}
	
	public boolean fight(Monster m) {
		String battleLog="";
		
		if(this.vit<m.vit) {
			battleLog+=m.fightTurn(this);
			if(this.hp <= 0) {
				this.hp=0;
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
				this.hp=0;
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
		} else if(deg==1) { 
			dmg=(deg*this.getAtk())-m.getDef();
			if(dmg<=0) {
				battleLog+=m.description+" dodged"; 
			} else {
				m.hp -= dmg;
				useWeapon();
				battleLog+=description+" deals "+dmg+" to "+m.description; 
			}
		} else if(deg==2) {
			dmg=deg*this.getAtk();
			m.hp -= dmg;
			useWeapon();
			battleLog+=description+" deals !"+dmg+"! to "+m.description; 
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
	
	public void resetHealth() {
		this.hp = this.maxHealth;
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
	
	public String drawHealthBar() {
		String s="[";
		
		for(int i=0; i<20; i++) {
			if(i==8 && hp==maxHealth) {	// Show life count on bar
				s+=hp/10;
				i++;
			} else if(i==9 && hp>9 && hp<maxHealth) {
				s+=(hp/10);
			} else if(i==10) {
				s+=hp%10;
			} else {
				if(i<((int)20*hp/maxHealth)) {
					s+="|";
				} else {
					s+=" ";
				}
			}
		}
		s+="]";
		return s;
	}
	
	public String getInfo() {
		return ""+drawHealthBar()+"\nGold : "+this.gold+"\n"+"Kills : "+this.monstersKilled;
	}
	
	public String getAllInfo() {
		String weapon="\t", shield="\t";
		
		if(this.w.getVal()>0) {
			weapon=this.w+" +"+this.w.getVal()+" ("+this.w.getDurability()+"/"+this.w.getMaxDurability()+")";
		}
		if(this.s.getVal()>0) {
			shield=this.s+" +"+this.s.getVal()+" ("+this.s.getDurability()+"/"+this.s.getMaxDurability()+")";
		}
		return "   "+drawHealthBar()+"  Gold : "+this.gold+"\n   "+weapon+"\n"+"   "+shield;
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
	
	public void setLooker(Looker l) {
		this.looker = l;
		this.looker.show();
	}
	
	public Looker getLooker() {
		this.looker.placeOn(pos.x, pos.y);
		return this.looker;
	}
}
