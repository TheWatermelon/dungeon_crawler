package objects.effect;

import java.awt.Color;
import java.util.Random;

import engine.Resources;
import objects.mob.*;

public class EffectStrong extends EffectEquipement {
	public EffectStrong() { super("STR"); }
	
	@Override
	public void start(Mob m) {
		affected=m;
	}
	
	@Override
	public void stop() {}
	
	@Override
	public Color getColor() { return Resources.darkYellow; }
	
	@Override
	public boolean apply() {
		if((new Random()).nextInt(3)==0) {
			if(((Player)affected).getWeapon().getEffect()==this) {
				((Player)affected).getWeapon().setDurability(((Player)affected).getWeapon().getDurability()+1);
			} else if(((Player)affected).getShield().getEffect()==this) {
				((Player)affected).getShield().setDurability(((Player)affected).getShield().getDurability()+1);
			}
		}
		return true;
	}

	@Override
	public String name() {
		return "Strong";
	}
}
