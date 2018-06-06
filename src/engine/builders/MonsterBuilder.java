package engine.builders;

import java.awt.Point;

import engine.MessageLog;
import objects.mob.Monster;
import objects.effect.Effect;

public class MonsterBuilder {
	protected char symbol;
	protected String description;
	protected int maxHealth, hp, atk, def, vit;
	protected boolean dead;
	protected Point pos;
	protected MessageLog log;
	protected Effect effect;
	protected Effect effectSpreader;

	public MonsterBuilder withSymbol(char s) {
		this.symbol = s;
		return this;
	}

	public MonsterBuilder withDescription(String desc) {
		this.description = desc;
		return this;
	}

	public MonsterBuilder withStats(int maxHP, int hp, int atk, int def, int vit) {
		this.maxHealth = maxHP;
		this.hp = hp;
		this.atk = atk;
		this.def = def;
		this.vit = vit;
		return this;
	}

	public MonsterBuilder withDeadBool(boolean d) {
		this.dead = d;
		return this;
	}

	public MonsterBuilder withPos(Point p) {
		this.pos = p;
		return this;
	}

	public MonsterBuilder withLog(MessageLog l) {
		this.log = l;
		return this;
	}

	public MonsterBuilder withEffects(Effect e, Effect eS) {
		this.effect = e;
		this.effectSpreader = eS;
		return this;
	}
	
	public Monster build() {
		return new Monster(this.symbol, 
			this.description, 
			this.pos, 
			this.dead, 
			this.maxHealth, 
			this.hp, 
			this.atk,
			this.def,
			this.vit,
			this.log,
			this.effect,
			this.effectSpreader);
	}
}
