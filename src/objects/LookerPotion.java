package objects;

public class LookerPotion extends Looker {
	public LookerPotion(int x, int y) {
		super(x, y);
		this.left='?';
		this.right='!';
	}
	
	public void updateLook(int val) {
		if(val>0) {
			this.left='\\';
			this.right='/';
		} else {
			this.left='/';
			this.right='\\';
		}
	}
}
