package objects.looker;

import java.awt.Color;

import engine.Resources;

public class LookerMob extends Looker {
	public LookerMob(int x, int y, int val) {
		super(x, y);
		this.left = (new String(""+val/10)).charAt(0);
		this.right = (new String(""+val%10)).charAt(0);
	}

	@Override
	public Color getColor() { return Resources.coolRed; }
}
