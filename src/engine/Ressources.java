package engine;

import java.util.Random;

public class Ressources {
	private static Ressources res;
		
	private static String[] monsterName = { "alphyn", "bunyip", "cockatrice", "dandan", "eachy", "fachen", "goblin", "hog", "imp", "jummy", "kelpy", "leprechaun", "mantis", "nyan", "odo", "pazu", "qilin", "rat", "snake", "troll", "uxie", "vermillion", "wasp", "xylopod", "yolocamph", "zilly" };
	private static String[] monsterName1= { "Alpha", "Bunny", "Cuckcoo", "Dino", "Elephant", "Fitrik", "Goblin mother", "Hugh", "Icetroll", "Jester", "Kangaroo", "Linen", "Mummy", "Ninja", "Olpus", "Pinat", "Quester", "Rat", "Snake", "Troll", "Uber", "Vizir", "Werewolf", "Xinus", "Yopor", "Zebra" };
	
	private static String[] weaponName = { "void", "Wooden Sword", "Dagger", "Iron Dagger", "Iron Sword", "Master Sword" };
	private static String[] shieldName = { "void", "Wooden Shield", "Copper Shield", "Iron Shield", "Gold Shield", "Master Shield" };
	
	private Ressources() {
		
	}
	
	public static Ressources getInstance() {
		if(res==null) {
			res = new Ressources();
		}
		return res;
	}
	
	public static char getLetter() {
		Random rnd = new Random();
		return monsterName[rnd.nextInt(monsterName.length)].charAt(0);
	}
	
	public static char getLetterAt(int index) {
		return monsterName[index].charAt(0);
	}
	
	public static char getCapitalLetterAt(int index) {
		return monsterName1[index].charAt(0);
	}
	
	public static String getName() {
		Random rnd = new Random();
		return monsterName[rnd.nextInt(monsterName.length)];
	}
	
	public static String getNameAt(int index) {
		return monsterName[index];
	}
	
	public static String getCapitalNameAt(int index) {
		return monsterName1[index];
	}
	
	public static String getWeaponAt(int index) {
		return weaponName[index];
	}
	
	public static String getShieldAt(int index) {
		return shieldName[index];
	}
}
