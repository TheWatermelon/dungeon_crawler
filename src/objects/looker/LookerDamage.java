package objects.looker;

public class LookerDamage extends Looker {
	public LookerDamage(int x, int y, int val) {
		super(x, y);
		this.left = (new String(""+val/10)).charAt(0);
		this.right = (new String(""+val%10)).charAt(0);
	}
}
