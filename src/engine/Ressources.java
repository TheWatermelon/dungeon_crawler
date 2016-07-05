package engine;

import java.util.Random;

public class Ressources {
	private static Ressources res;
		
	private static String[] monsterName = { "alphyn", "bunyip", "cockatrice", "dandan", "eachy", "fachen", "goblin", "hag", "imp", "jasy", "kelpie", "leprechaun", "manticore", "nyan", "odo", "pazuzu", "qilin", "rat", "snake", "troll", "uxie", "vermillion", "wasp", "xylopod", "yolocamph", "zilly" };
	//private static String[] monsterName1= { "" };
	
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
	
	public static String getName() {
		Random rnd = new Random();
		return monsterName[rnd.nextInt(monsterName.length)];
	}
	
	public static String getNameAt(int index) {
		return monsterName[index];
	}
}
