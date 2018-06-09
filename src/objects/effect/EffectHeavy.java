package objects.effect;

import java.awt.Color;
import java.util.Random;

import engine.Resources;
import objects.mob.Mob;

public class EffectHeavy extends EffectSelf {
	public EffectHeavy() { super("HVY"); this.id=2; }

	@Override
	public String name() { return "Heavy"; }
	
	@Override
	public Color getColor() { return Resources.gray; }

	@Override
	public void start(Mob m) { 
		affected=m;
	}

	@Override
	public void stop() {}
	
	@Override
	public boolean apply() {
		Random rnd = new Random();
		
		if(rnd.nextInt(7)==0) {
			affected.getLog().appendMessage("Hmmph that's too heavy!");
			return false;
		}
		return true;
	}

}
