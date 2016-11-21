package objects;

import java.util.*;

public class LookerBarrel extends Looker {
	public LookerBarrel(int x, int y) {
		super(x, y);
		setupLooker();
	}
	
	protected void setupLooker() {
		Random rnd = new Random();
		int val = rnd.nextInt(3);
		
		switch(val) {
			case 0:
				this.left='\'';
				this.right='`';
				break;
			case 1:
				this.left='¨';
				this.right='`';
				break;
			case 2:
				this.left=',';
				this.right='.';
		}
	}
}
