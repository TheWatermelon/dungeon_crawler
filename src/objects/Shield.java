package objects;

public class Shield extends Equipement {	
	public Shield() {
		this.val=0;
		this.maxDurability = -1;
		this.resetDurability();
	}
	
	public Shield(int v) {
		this.val=v;
		this.maxDurability = v*10;
		this.resetDurability();
	}
}
