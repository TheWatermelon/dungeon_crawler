package objects.looker;

import java.awt.Color;

import engine.Resources;

public class LookerHealth extends Looker {
	public LookerHealth(int x, int y) {
		super(x, y);
		this.left = '(';
		this.right = ')';
	}

	@Override
	public Color getColor() { return Resources.green; }
}
