package objects.effect;

import objects.mob.Mob;

public class EffectPoison extends Effect {
	public EffectPoison() { super("PSN"); maxDuration=10; }
	
	public String name() { return "Poison"; }
	
	public void start(Mob m) { 
		this.duration=this.maxDuration; 
		this.affected=m; 
		m.setEffect(this); 
		m.getLog().appendMessage(m+" is poisoned!");
	}
	
	public boolean apply() {
		if(duration>0) {
			affected.hp-=1;
			affected.getLog().appendMessage(affected+" suffer from poison!");
			if(affected.hp<=0) {
				affected.murder();
				return false;
			}
			duration--;
		} else {
			affected.getLog().appendMessage(affected+" is cured from poison!");
			affected.setEffect(new EffectNormal());
		}
		return true;
	}
}
