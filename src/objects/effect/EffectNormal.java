package objects.effect;

import objects.mob.Mob;

public class EffectNormal extends Effect {
	public EffectNormal() { super(""); }
	public String name() { return "Normal"; }
	public boolean apply() { return true; }
	public void start(Mob m) { this.affected=m; m.setEffect(this); }
}
