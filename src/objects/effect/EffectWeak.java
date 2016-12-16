package objects.effect;

import java.awt.Color;

import engine.Resources;
import objects.mob.*;

public class EffectWeak extends EffectEquipement {
	public EffectWeak() { super("WEK");	}

	@Override
	public String name() { return "Weak"; }

	@Override
	public Color getColor() { return Resources.darkGray; }

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
