package objects.looker;

import java.awt.Color;

import engine.Resources;

public class LookerPotion extends Looker {
	protected int val;
	
	public LookerPotion(int x, int y) {
		super(x, y);
		this.left='!';
		this.right='?';
	}

	@Override
	public Color getColor() { return (val>0)?Resources.green:Resources.coolRed; }
	
	public void updateLook(int val) {
		this.val = val;
		if(val>0) {
			this.left='!';
			this.right='+';
		} else {
			this.left='!';
			this.right='-';
		}
	}
	
	public int getVal() { return val; }
}
