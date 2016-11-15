package objects;

public class LookerFactory {
	private static LookerFactory instance;
	
	private static LookerMob lookerMob = new LookerMob(0, 0);
	private static LookerEquip lookerEquip = new LookerEquip(0, 0);
	private static LookerGold lookerGold = new LookerGold(0, 0);
	private static LookerHealth lookerHealth = new LookerHealth(0, 0);
	private static LookerPotion lookerPotion = new LookerPotion(0, 0);
	
	private LookerFactory() {
		
	}
	
	public static LookerFactory getInstance() {
		if(instance == null) {
			instance = new LookerFactory();
		}
		return instance;
	}
	
	public LookerMob createLookerMob(int x, int y) { lookerMob.placeOn(x, y); return lookerMob; }
	public LookerEquip createLookerEquip(int x, int y) { lookerEquip.placeOn(x, y); return lookerEquip; }
	public LookerGold createLookerGold(int x, int y) { lookerGold.placeOn(x, y); return lookerGold; }
	public LookerHealth createLookerHealth(int x, int y) { lookerHealth.placeOn(x,  y); return lookerHealth; }
	public LookerPotion createLookerPotion(int x, int y, int val) { /*lookerPotion.updateLook(val);*/ lookerPotion.placeOn(x, y); return lookerPotion; }
}
