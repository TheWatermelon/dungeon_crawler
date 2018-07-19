package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import engine.builders.SpriteSheetBuilder;
import game.Main;
import objects.effect.*;
import objects.item.*;
import objects.mob.Monster;
import rooms.*;
import tiles.Tile;

public class Resources {
	private static Resources res;
	
	private static String[] playerPrefix = { "Anu", "Ara", "Ash", "Cath", "Cer", "Chi", "Dio", "Esh", "Fau", "Fer", "Flo", "Goth", "Ha", "Heph", "Her", "Hog", "Io", "Iri", "Jan", "Jun", "Kya", "Kri", "Kuv", "Lak", "Log", "Lok", "Let", "Mith", "Mor", "Ogu", "Par", "Pah", "Rash", "Ram", "Shak", "Sham", "Sed", "Tar", "Ten", "Ven", "Vic", "Vul", "Wel", "Wot", "Yam", "Yog" };
	private static String[] playerSuffix = { "asu", "bis", "bog", "can", "cha", "chen", "dan", "dir", "dum", "duk", "gard", "gorn", "gun", "gus", "han", "hen", "kar", "ken", "ki", "lord", "man", "mer", "mon", "mora", "nar", "no", "num", "nus", "pai", "pala", "phyn", "pheus", "qi", "qun", "ros", "rion", "sha", "shi", "sta", "thor", "tri", "ten", "van", "uru", "zyr" };
		
	private static String[] monsterName = { "alphyn",	"bunny",	"chima",	"dandan",	"elemal",	"fachen",	"goblin",	"hog",		"imp",		"jest",		"koalad",	"linum",	"mummy",	"nyan",		"olos", 	"pata", 	"qi", 	"rat", 		"snake", "troll", "uxie", 	"vermillion", 	"wasp", 	"xylopod", 	"yolocamph", 	"zilly" };
	private static String[] monsterName1= { "Alpha", 	"Bunner", 	"Chimera", 	"Daran", 	"Elemental","Fraken", 	"Goblord", 	"Hedgard", 	"Imperos", 	"Jester", 	"Koamad", 	"Lordum", 	"Mastras", 	"Ninja", 	"Olopus", 	"Partara", 	"Quim", "Routard", 	"Snake", "Troll", "Uximer", "Vizir", 		"Werewolf", "Xinus", 	"Yopor", 		"Zebra" };
	
	private static String[] equipementName = { "void", "Wooden", "Copper", "Iron", "Silver", "Master" };
	
	private static String[] helmetName = { "void", "Childish", "Beginner", "Apprentice", "Expert", "Master" };
	private static String[] helmetType = { "Cap", "Hat", "Helmet" };
	
	private static Font dungeonFont;
		
	public static Color white = Color.WHITE;
	public static Color yellow = Color.YELLOW;
	public static Color darkYellow = new Color(0xA9, 0x96, 0x2D);
	public static Color darkerYellow = new Color(0xA9, 0x96, 0x2D, 126);
	public static Color lightOrange = new Color(0xFF, 0x66, 0x00);
	public static Color orange = Color.ORANGE;
	public static Color brown = new Color(0xA5, 0x68, 0x2A);
	public static Color darkBrown = new Color(0x92, 0x44, 0x00);
	public static Color lightGray = new Color(0xcf, 0xcf, 0xcf);
	public static Color gray = Color.GRAY;
	public static Color darkGray = new Color(0x6f, 0x6f, 0x6f);
	public static Color darkerGray = new Color(0x40, 0x40, 0x40);
	public static Color green = new Color(0x00, 0x80, 0x00);
	public static Color darkGreen = new Color(0x04, 0x78, 0x00);
	public static Color coolRed = new Color(0xEF, 0x3F, 0x23);
	public static Color red = Color.RED;
	public static Color darkRed = new Color(0x7F, 0x00, 0x00);
	public static Color blue = Color.BLUE;
	public static Color darkBlue = new Color(0x00, 0x00, 0x7F);
	public static Color cyan = Color.CYAN;
	public static Color magenta = Color.MAGENTA;
	public static Color pink = Color.PINK;
	
	public Color theme = lightGray;
	
	public int difficulty=1;
	
	public int resolution=30;
	
	public boolean commandsHelp = true;
	
	public boolean music = true;
	public float musicVolume = 0.0f;
	public boolean sound = true;
	public float soundVolume = 0.0f;
	
	public Clip dungeonMusic;
	public Clip menuMusic;
	
	public SpriteSheet sprites;
	
	public enum Commands {
		Up('z'),
		Right('d'),
		Down('s'),
		Left('q'),
		Take('f'),
		Inventory('i'),
		QuickAction1('a'),
		QuickAction2('e'),
		Pause('p'),
		Restart('r');
		
		private Commands(char s) { key = s; }
		
		public char getKey() { return key; }
		public void setKey(char k) { key = k; }
		
		private char key;
	};
	
	private Resources() {
		try {
			BufferedImage sheet = ImageIO.read(Main.class.getResourceAsStream("/char_table.png"));
			sprites = new SpriteSheetBuilder().
					withSpriteSheet(sheet).
					withCols(13).
					withRows(10).
					withSpriteCount(130).
					build();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Resources getInstance() {
		if(res==null) {
			res = new Resources();
		}
		return res;
	}
	
	public static Font getDungeonFont() {
		if(dungeonFont == null) {
			dungeonFont = new Font("Monospaced", Font.PLAIN, 16);
		}
	    return dungeonFont;
	}
	
	public static String generatePlayerName() {
		Random rnd = new Random();
		return playerPrefix[rnd.nextInt(playerPrefix.length)]+
				playerSuffix[rnd.nextInt(playerSuffix.length)];
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
	
	public static String getEquipementAt(int index) {
		return equipementName[index];
	}
	
	public static String getHelmetNameAt(int index) {
		return helmetName[index];
	}
	
	public static String getHelmetType() {
		return helmetType[(new Random()).nextInt(helmetType.length)];
	}
	
	public static Effect getEffectById(int id) {
		switch(id) {
			case 0:
				return new EffectFire();
			case 1:
				return new EffectHeal();
			case 2:
				return new EffectHeavy();
			case 3:
				return new EffectParalyze();
			case 4:
				return new EffectPoison();
			case 5:
				return new EffectSleep();
			case 6:
				return new EffectStrength();
			case 7:
				return new EffectStrong();
			case 8:
				return new EffectToughness();
			case 9:
				return new EffectWeak();
			default:
				return new EffectNormal();
		}
	}
	
	public static int getItemIdFrom(Item i) {
		if(i instanceof Antidote) 			{ return 1; }
		else if(i instanceof HealingPotion) { return 2; }
		else if(i instanceof Bow) 			{ return 3; }
		else if(i instanceof Weapon) 		{ return 4; }
		else if(i instanceof Shield) 		{ return 5; }
		else if(i instanceof Helmet) 		{ return 6; }
		else if(i instanceof Barrel) 		{ return 10; }
		else if(i instanceof Fountain) 		{ return 11; }
		else if(i instanceof Gold)			{ return 12; }
		else { return 0; }
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
	
	public static int[][] drawLine(int x1, int y1, int x2, int y2, int octant) {
		int dx = x2 - x1;
		int dy = y2 - y1;
		int[][] matrix;
		if(octant==1 || octant==2 || octant==5 || octant==6) {
			matrix = new int[dx*2+1][dy*2+1];
		} else {
			matrix = new int[dy*2+1][dx*2+1];
		}
		
		int D = 2*dy - dx;
		int y = y1;
		
		for(int x=x1; x<=x2; x++) {
			Point p = switchFromOctantZeroTo(octant, x, y);
			if(octant==1 || octant==2 || octant==5 || octant==6) {
				matrix[p.y+dx][p.x+dy]=1;
			} else {
				matrix[p.y+dy][p.x+dx]=1;
			}
			if(D>0) {
				y++;
				D-=dx;
			}
			D+=dy;
		}
		
		return matrix;
	}
	
	public static Point switchFromOctantZeroTo(int octant, int x, int y) {
		switch(octant) {
			case 0:
				return new Point(x, y);
			case 1:
				return new Point(y, x);
			case 2:
				return new Point(-y, x);
			case 3:
				return new Point(-x, y);
			case 4:
				return new Point(-x, -y);
			case 5:
				return new Point(-y, -x);
			case 6:
				return new Point(y, -x);
			case 7:
				return new Point(x, -y);
			default:
				return new Point(x, y);
		}
	}
	
	public static Point switchToOctantZeroFrom(int octant, int x, int y) {
		switch(octant) {
			case 0:
				return new Point(x, y);
			case 1:
				return new Point(y, x);
			case 2:
				return new Point(y, -x);
			case 3:
				return new Point(-x, y);
			case 4:
				return new Point(-x, -y);
			case 5:
				return new Point(-y, -x);
			case 6:
				return new Point(-y, x);
			case 7:
				return new Point(x, -y);
			default:
				return new Point(x, y);
		}
	}
	
	public static Map createVillage(Dungeon d) {
		Tile[][] t = new Tile[getInstance().resolution][getInstance().resolution*2];
		Vector<Room> r = new Vector<Room>();
		Village v = new Village();
		r.add(v);
		return new Map(d, t, r, new Vector<Monster>(), new Vector<Item>(), new Point(0, 0), new Point(v.p1.x+16,v.p1.y+16));
	}
	
	/* Menu sounds */
	public static void playOpenMenuSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/00_open_menu.wav")); }
	public static void playCycleMenuSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/00_cycle_menu.wav")); }
	public static void playSelectMenuSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/00_select_menu.wav")); }
	public static void playExitMenuSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/00_exit_menu.wav")); }
	public static void playEquipSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/00_equip.wav")); }
	
	/* Dungeon items sounds */
	public static void playGoldSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_gold.wav")); }
	public static void playFountainSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_fountain.wav")); }
	public static void playBarrelSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_barrel.wav")); }
	public static void playBarrelExplodeSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_barrel_explode.wav")); }
	public static void playDoorSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_door.wav")); }
	public static void playStairUpSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_stairUp_alt.wav")); }
	public static void playStairDownSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_stairDown_alt.wav")); }
	public static void playPickupSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_pickup_alt.wav")); }
	
	/* Player actions sounds */
	public static void playMossSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_cut_moss.wav")); }
	public static void playWhooshSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_whoosh.wav")); }
	public static void playReadyBowSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_drawing_bow.wav")); }
	public static void playFireBowSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_bow_fire.wav")); }
	public static void playWeaponWornOutSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_weapon_worn_out.wav")); }

	/* Monsters sounds */
	public static void playMonsterHurtSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_monster_00.wav")); }
	public static void playMonsterDeadSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_monster_00.wav")); }

	/* Misc sounds */
	public static void playGameOverSound() { if(Resources.getInstance().sound) SoundPlayer.playSound(Main.class.getResource("/sounds/01_game_over.wav")); }

	public static void playDungeonMusic() { 
		if(Resources.getInstance().music) {
			if(Resources.getInstance().dungeonMusic != null) {
				Resources.resumeClip(Resources.getInstance().dungeonMusic);
			} else {
				Resources.getInstance().dungeonMusic = SoundPlayer.playMusic(Main.class.getResource("/sounds/01_dungeon_music.wav"));
			}
		}
	}
	
	public static void pauseDungeonMusic() {
		if(Resources.getInstance().dungeonMusic != null) Resources.getInstance().dungeonMusic.stop();
	}
	
	public static void playMenuMusic() { 
		if(Resources.getInstance().music) {
			if(Resources.getInstance().menuMusic != null) {
				Resources.resumeClip(Resources.getInstance().menuMusic);
			} else {
				Resources.getInstance().menuMusic = SoundPlayer.playMusic(Main.class.getResource("/sounds/01_menu_music.wav"));
			}
		}  
	}
	
	public static void pauseMenuMusic() {
		if(Resources.getInstance().menuMusic != null) Resources.getInstance().menuMusic.stop();
	}
	
	public static void resumeClip(Clip clip) {
		FloatControl gainControl = 
        	    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(Resources.getInstance().musicVolume);
        clip.start();
	}
}
