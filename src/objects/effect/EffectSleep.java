package objects.effect;

import java.awt.Color;

import engine.Resources;
import objects.mob.Mob;

public class EffectSleep extends EffectOther {
	public EffectSleep() { super("ZzZ"); maxDuration=5; }

	@Override
	public String name() { return "Sleep"; }

	@Override
	public Color getColor() { return Resources.darkBlue; }
	
	@Override
	public void start(Mob m) { 
		this.duration=this.maxDuration; 
		this.affected=m; 
		m.setEffect(this); 
		m.getLog().appendMessage(m+" fall asleep!");
	}
	
	@Override
	public void stop() {}

	@Override
	public boolean apply() {
		if(duration>0) {
			affected.getLog().appendMessage(affected+" is sleeping!");
			duration--;
			return false;
		} else {
			affected.getLog().appendMessage(affected+" is awake!");
			affected.setEffect(new EffectNormal());
		}
		return true;
	}
}
