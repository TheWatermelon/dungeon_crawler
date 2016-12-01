package objects.effect;

import java.util.Random;

import objects.mob.Mob;

public class EffectHeavy extends EffectSelf {
	public EffectHeavy() { super("HVY"); }

	@Override
	public String name() { return "Heavy"; }

	@Override
	public void start(Mob m) { 
		affected=m;
	}

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
