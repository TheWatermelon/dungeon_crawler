package objects.item;

import java.awt.Color;

import engine.Resources;

public class Antidote extends Potion {
	public Antidote(int x, int y) {
		super(x, y);
		this.description="Antidote";
	}
	
	@Override
	public boolean isEqualTo(Item i) {
		if(i instanceof Antidote) { return true; }
		return false;
	}
	
	@Override
	public Color getColor() {
		return Resources.cyan;
	}
}
