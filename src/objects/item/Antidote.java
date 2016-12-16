package objects.item;

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
}
