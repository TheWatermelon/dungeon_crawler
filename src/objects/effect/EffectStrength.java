package objects.effect;

import java.awt.Color;
import java.util.Random;

import engine.Resources;
import objects.mob.Mob;

public class EffectStrength extends EffectSelf {
	public EffectStrength() {
		super("STR");
		this.duration = (new Random()).nextInt(5)+1;
	}
	
	@Override
	public String name() {
		return "Stren+"+this.duration;
	}

	@Override
	public Color getColor() {
		return Resources.orange;
	}

	@Override
	public void start(Mob m) {
		affected=m;
		m.bonusAtk = this.duration;
	}
	
	@Override
	public void stop() {
		affected.bonusAtk = 0;
	}

	@Override
	public boolean apply() {
		return true;
	}

}
