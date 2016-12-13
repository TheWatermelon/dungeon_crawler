package objects.looker;

import objects.mob.Player;

public class LookerFactory {
	private static LookerFactory instance;
	
	private static LookerDamage lookerDamage = new LookerDamage(0, 0);
	private static LookerMiss lookerMiss = new LookerMiss(0, 0);
	private static LookerEquip lookerEquip = new LookerEquip(0, 0);
	private static LookerGold lookerGold = new LookerGold(0, 0);
	private static LookerHealth lookerHealth = new LookerHealth(0, 0);
	private static LookerPotion lookerPotion = new LookerPotion(0, 0);
	private static LookerStuff lookerStuff = new LookerStuff(0, 0);
	
	private LookerFactory() {
		
	}
	
	public static LookerFactory getInstance() {
		if(instance == null) {
			instance = new LookerFactory();
		}
		return instance;
	}
	
	public LookerMob createLookerMob(int x, int y, int val) { return new LookerMob(x, y, val); }
	public LookerMiss createLookerMiss(int x, int y) { lookerMiss.placeOn(x, y); return lookerMiss; }
	public LookerDamage createLookerDamage(int x, int y) { lookerDamage.placeOn(x, y); return lookerDamage; }
	public LookerEquip createLookerEquip(int x, int y) { lookerEquip.placeOn(x, y); return lookerEquip; }
	public LookerGold createLookerGold(int x, int y) { lookerGold.placeOn(x, y); return lookerGold; }
	public LookerHealth createLookerHealth(int x, int y) { lookerHealth.placeOn(x,  y); return lookerHealth; }
	public LookerPotion createLookerPotion(int x, int y, int val) { lookerPotion.updateLook(val); lookerPotion.placeOn(x, y); return lookerPotion; }
	public LookerBarrel createLookerBarrel(int x, int y) { return new LookerBarrel(x, y); }
	public LookerStuff createLookerStuff(int x, int y, Player p) { lookerStuff.placeOn(x, y); lookerStuff.refresh(p); return lookerStuff; }
}
