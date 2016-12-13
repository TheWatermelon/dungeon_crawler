package objects.looker;

import java.awt.Color;

import engine.Resources;

public class LookerGold extends Looker {
	public LookerGold(int x, int y) {
		super(x, y);
		this.left='{';
		this.right='}';
	}

	@Override
	public Color getColor() { return Resources.yellow; }
}
