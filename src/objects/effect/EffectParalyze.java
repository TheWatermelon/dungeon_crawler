package objects.effect;

import java.awt.Color;
import java.util.Random;

import engine.Resources;
import objects.mob.Mob;

public class EffectParalyze extends EffectOther {
	public EffectParalyze() { super("PAR"); maxDuration=50; this.id=3; }

	@Override
	public String name() { return "Paralyze"; }

	@Override
	public Color getColor() { return Resources.yellow; }
	
	@Override
	public void start(Mob m) { 
		this.duration=this.maxDuration; 
		this.affected=m; 
		m.setEffect(this); 
		m.getLog().appendMessage(m+" is paralyzed!");
	}
	
	@Override
	public void stop() {}

	@Override
	public boolean apply() {
		if(duration>0) {
			duration--;
			if((new Random()).nextInt(2)==0) {
				affected.getLog().appendMessage(affected+" is paralyzed!");
				return false;
			}
		} else {
			affected.getLog().appendMessage(affected+" is cured from paralysis!");
			affected.setEffect(new EffectNormal());
		}
		return true;
	}
}
