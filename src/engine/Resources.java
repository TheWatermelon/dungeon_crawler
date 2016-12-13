package engine;

import java.awt.Color;
import java.util.Random;

public class Resources {
	private static Resources res;
		
	private static String[] monsterName = { "alphyn",	"bunny",	"chima",	"dandan",	"elemal",	"fachen",	"goblin",	"hog",		"imp",		"jest",		"koalad",	"linum",	"mummy",	"nyan",		"olos", 	"pata", 	"qi", 	"rat", 		"snake", "troll", "uxie", 	"vermillion", 	"wasp", 	"xylopod", 	"yolocamph", 	"zilly" };
	private static String[] monsterName1= { "Alpha", 	"Bunner", 	"Chimera", 	"Daran", 	"Elemental","Fraken", 	"Goblord", 	"Hedgard", 	"Imperos", 	"Jester", 	"Koamad", 	"Lordum", 	"Mastras", 	"Ninja", 	"Olopus", 	"Partara", 	"Quim", "Routard", 	"Snake", "Troll", "Uximer", "Vizir", 		"Werewolf", "Xinus", 	"Yopor", 		"Zebra" };
	
	//private static String[] equipementAdj = { "Light" };
	private static String[] weaponName = { "void", "Wooden Sword", "Dagger", "Sword", "Broadsword", "Master Sword" };
	
	private static String[] shieldName = { "void", "Wooden Shield", "Copper Shield", "Iron Shield", "Silver Shield", "Master Shield" };
	
	public static Color white = Color.WHITE;
	public static Color yellow = Color.YELLOW;
	public static Color darkYellow = new Color(0xA9, 0x96, 0x2D);
	public static Color orange = Color.ORANGE;
	public static Color brown = new Color(0xA5, 0x68, 0x2A);
	public static Color lightGray = Color.GRAY;
	public static Color gray = new Color(0x60, 0x60, 0x60);
	public static Color darkGray = new Color(0x30, 0x30, 0x30);
	public static Color darkerGray = new Color(0x15, 0x15, 0x15);
	public static Color green = new Color(0x00, 0x80, 0x00);
	public static Color darkGreen = new Color(0x00, 0x33, 0x00);
	public static Color coolRed = new Color(0xEF, 0x3F, 0x23);
	public static Color red = Color.RED;
	public static Color darkRed = new Color(0x7F, 0x00, 0x00);
	public static Color blue = Color.BLUE;
	public static Color darkBlue = new Color(0x00, 0x00, 0x7F);
	public static Color cyan = Color.CYAN;
	public static Color magenta = Color.MAGENTA;
	public static Color pink = Color.PINK;
	
	private Resources() {
		
	}
	
	public static Resources getInstance() {
		if(res==null) {
			res = new Resources();
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
	
	public static int[][] drawCircle(int size) {
		int[][] matrix = new int[size][size];

		double midPoint=(matrix.length-1)/2.0;
		
		for(int j=0; j<matrix.length; j++) {
			int[] row = new int[matrix.length];
			double yy = j - midPoint;
			for(int i=0; i<row.length; i++) {
				double xx = i - midPoint;
				if(Math.sqrt(xx*xx+yy*yy)<=midPoint) {
					row[i]=1;
				}
			}
			matrix[j]=row;
		}
		
		return matrix;
	}
}
