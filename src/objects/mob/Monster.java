package objects.mob;

import java.awt.Point;
import java.util.Random;

import objects.effect.*;
import engine.MessageLog;
import engine.Resources;
import tiles.TileFactory;

public class Monster extends Mob {
	protected Effect effectSpreader;
	
	public Monster(int x, int y, char s, String desc, MessageLog l) {
		this.symbol = s;
		this.maxHealth=this.hp = 20+getSymbolLevel();
		this.atk = pickAtk();
		this.def = pickDef();
		this.vit = pickVit();
		this.dead = false;
		this.pos = new Point(x, y);
		this.description = desc;
		this.floor = TileFactory.getInstance().createTileMonster(this.symbol);
		this.mobTile = TileFactory.getInstance().createTileMonster(this.symbol);
		this.log=l;
		this.effect = new EffectNormal();
		this.effectSpreader = pickEffect();
	}

	public Monster(char s, 
			String desc, 
			Point p, 
			boolean d, 
			int maxHP, 
			int hp, 
			int atk,
			int def,
			int vit,
			MessageLog l,
			Effect e,
			Effect eSpreader) {
		this.symbol = s;
		this.description = desc;
		this.pos = p;
		this.dead = d;
		this.maxHealth = maxHP;
		if(dead) { 
			this.hp = this.atk = this.def = this.vit = 0; 
			this.floor = TileFactory.getInstance().createTileCorpse();
			this.mobTile = TileFactory.getInstance().createTileCorpse();
		}
		else { 
			this.hp = hp; 
			this.atk = atk;
			this.def = def;
			this.vit = vit;
			this.floor = TileFactory.getInstance().createTileMonster(this.symbol);
			this.mobTile = TileFactory.getInstance().createTileMonster(this.symbol);
		}
		this.log = l;
		this.effect = e;
		this.effectSpreader = eSpreader;
	}
	
	protected int pickAtk() {
		Random rnd = new Random();
		return rnd.nextInt(4)+1+getSymbolLevel();
	}
	
	protected int pickDef() {
		Random rnd = new Random();
		return rnd.nextInt(4)+getSymbolLevel();
	}
	
	protected int pickVit() {
		Random rnd = new Random();
		return rnd.nextInt(2);
	}
	
	protected Effect pickEffect() {
		Random rnd = new Random();
		int effectChance = (rnd).nextInt(4);
		
		if(rnd.nextInt(20)==0) {
			if(effectChance==0) {
				return new EffectPoison();
			} else if(effectChance==1) {
				return new EffectSleep();
			} else if(effectChance==2) {
				return new EffectParalyze();
			} else if(effectChance==3) {
				return new EffectFire();
			}
		}
		return new EffectNormal();
	}
	
	protected int getSymbolLevel() {
		return ((this.symbol - 'a') / 10);
	}
	
	public String murder() {
		if(!dead) {
			this.dead = true;
			this.hp=0;
			this.mobTile = TileFactory.getInstance().createTileCorpse();
			Resources.playMonsterDeadSound();
		}
		return description+" fainted!";
	}
	
	public String fightTurn(Player p) {
		String battleLog="";
		Random rnd = new Random();
		int deg;
		
		if(effect.apply() && p.getShield().getEffect().apply()) {
			deg = rnd.nextInt(3);
			if(deg==0) { 
				battleLog+=this.description+" miss "; 
			} else if(((deg*getAtk())-p.getDef())<=0) {
				p.useShield();
				p.useHelmet();
				battleLog+=p.description+" dodged "; 
			} else {
				int dmg = (deg*getAtk())-p.getDef();
				p.hp -= dmg;
				p.useShield();
				p.useHelmet();
				if(rnd.nextInt(3)==0) {	effectSpreader.start(p); }
				if(deg==1) {
					battleLog+=description+" deals "+dmg+" to "+p.description+" ";
				} else if(deg==2) {
					battleLog+=description+" deals !"+dmg+"! to "+p.description+" ";
				}
			}
		}
		
		return battleLog;
	}
	
	public int getAtk() {
		return this.atk;
	}
	
	public int getDef() {
		return this.def;
	}
	
	public Effect getEffectSpreader() {
		return this.effectSpreader;
	}
}
