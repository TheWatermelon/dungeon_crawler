package objects.effect;

import objects.mob.Mob;

public class EffectSleep extends EffectOther {
	public EffectSleep() { super("ZzZ"); maxDuration=5; }
	
	public String name() { return "Sleep"; }
	
	public void start(Mob m) { 
		this.duration=this.maxDuration; 
		this.affected=m; 
		m.setEffect(this); 
		m.getLog().appendMessage(m+" fall asleep!");
	}
	
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
