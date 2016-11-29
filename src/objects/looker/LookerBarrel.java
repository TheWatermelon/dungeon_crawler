package objects.looker;

import java.util.*;

public class LookerBarrel extends Looker {
	public LookerBarrel(int x, int y) {
		super(x, y);
		setupLooker();
	}
	
	protected void setupLooker() {
		Random rnd = new Random();
		int val = rnd.nextInt(5);
		
		switch(val) {
			case 0:
				this.left='\'';
				this.right='`';
				break;
			case 1:
				this.left='\"';
				this.right='`';
				break;
			case 2:
				this.left=',';
				this.right='.';
				break;
			case 3:
				this.left='\'';
				this.right='^';
				break;
			case 4:
				this.left='`';
				this.right='^';
				break;
		}
	}
}
