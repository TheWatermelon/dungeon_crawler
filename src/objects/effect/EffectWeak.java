package objects.effect;

import objects.mob.*;

public class EffectWeak extends EffectSelf {
	public EffectWeak() { super("WEK");	}

	@Override
	public String name() { return "Weak"; }

	@Override
	public void start(Mob m) {
		affected=m;		
	}

	@Override
	public boolean apply() {
		Player p = (Player)affected;
		
		if(p.getWeapon().getEffect()==this) {
			p.useWeapon();
		} else if(p.getShield().getEffect()==this) {
			p.useShield();
		}
		
		return true;
	}

}
