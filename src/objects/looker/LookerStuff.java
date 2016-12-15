package objects.looker;

import java.awt.Color;

import engine.Resources;
import objects.mob.Player;

public class LookerStuff extends Looker {
	private Color leftColor = Resources.lightGray;
	private Color rightColor = Resources.lightGray;
	
	public LookerStuff(int x, int y) { super(x, y); }

	@Override
	public Color getColor() { return Resources.lightGray; }

	@Override
	public Color getLeftColor() { return leftColor; }
	
	@Override
	public Color getRightColor() { return rightColor; }
	
	public void refresh(Player p) {
		if(p.getWeapon().getDurability()>0) {
			this.left = p.getWeapon().getTile().getSymbol();
			this.leftColor = p.getWeapon().getColor();
		} else {
			this.left = ' ';
		}
		if(p.getShield().getDurability()>0) {
			this.right = p.getShield().getTile().getSymbol();
			this.rightColor = p.getShield().getColor();
		} else {
			this.right = ' ';
		}
	}
}
