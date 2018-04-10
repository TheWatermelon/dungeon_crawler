package objects.effect;

import java.awt.Color;

import engine.Resources;
import objects.mob.Mob;

public class EffectNormal extends EffectSelf {
	public EffectNormal() { super(""); }
	@Override
	public String name() { return ""; }
	@Override
	public Color getColor() { return Resources.lightGray; }
	@Override
	public boolean apply() { return true; }
	@Override
	public void start(Mob m) {}
	@Override
	public void stop() {}
}
