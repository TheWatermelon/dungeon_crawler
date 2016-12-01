package objects.effect;

import objects.mob.Mob;

public class EffectNormal extends EffectSelf {
	public EffectNormal() { super(""); }
	public String name() { return ""; }
	public boolean apply() { return true; }
	public void start(Mob m) {}
}
