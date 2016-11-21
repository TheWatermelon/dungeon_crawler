package objects;

import java.awt.*;

public class Looker {
	protected Point pos;
	protected char left;
	protected char right;
	protected boolean show;
	
	public Looker(int x, int y) {
		this.pos = new Point(x, y);
		this.show = false;
		this.left='{';
		this.right='}';
	}
	
	public void placeOn(int x, int y) {
		this.pos = new Point(x, y);
	}
	
	public void show() { this.show = true; }
	public void hide() { this.show = false; }
	public void toggle() {
		if(isVisible()) 	{hide();}
		else				{show();}
	}
	
	public boolean isVisible() { return this.show; }
	
	public int getX() { return this.pos.x; }
	public int getY() { return this.pos.y; }
	public char getLeft() { return this.left; }
	public char getRight() { return this.right; }
}
