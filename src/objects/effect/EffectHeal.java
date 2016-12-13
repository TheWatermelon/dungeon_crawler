package objects.effect;

import java.awt.Color;

import engine.Resources;
import objects.mob.*;

public class EffectHeal extends EffectSelf {
	public EffectHeal() { super("HP+"); }
	
	@Override
	public String name() { return "Healing"; }

	@Override
	public Color getColor() { return Resources.green; }

	@Override
	public void start(Mob m) {
		affected=m;
	}

	@Override
	public boolean apply() {
		if(++affected.hp>affected.maxHealth) { affected.hp=affected.maxHealth; }
		return true;
	}
}
