package objects.effect;

import java.awt.Color;
import java.util.Random;

import engine.Resources;
import objects.mob.Mob;

public class EffectToughness extends EffectSelf {
	public EffectToughness() {
		super("TGH");
		this.duration = (new Random()).nextInt(5)+1;
	}

	@Override
	public String name() {
		return "Tough+"+this.duration;
	}

	@Override
	public Color getColor() {
		return Resources.cyan;
	}

	@Override
	public void start(Mob m) {
		affected = m;
		m.bonusDef = this.duration;
	}
	
	@Override
	public void stop() {
		affected.bonusDef = 0;
	}

	@Override
	public boolean apply() {
		return true;
	}
}
