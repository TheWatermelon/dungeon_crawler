package engine;

import java.util.Random;

public class Ressources {
	private static Ressources res;
	
	//private static char[] lowerAlphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	//private static char[] higherAlphabet= {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	private static String[] monsterName = { "alphyn", "bunyip", "cockatrice", "dandan", "eachy", "fachen", "goblin", "hag", "imp", "jasy", "kelpie", "leprechaun", "manticore", "nyan", "odo", "pazuzu", "qilin", "rat", "snake", "troll", "uxie", "vermillion", "wasp", "xylopod", "yolocamph", "zilly" };
	
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
