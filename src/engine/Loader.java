package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import objects.item.*;

public class Loader {
	public Loader() {
	}
	
	public Dungeon load(String filename) {
		try (BufferedReader br = new BufferedReader(new FileReader("saves/"+filename))) {
			/* Resources options */
			System.out.println("\n[Resources options]");
			int difficulty = br.read();			// Difficulty
			Resources.getInstance().difficulty = difficulty;
			System.out.println("difficulty: "+difficulty);
			int resolution = br.read();			// Resolution
			Resources.getInstance().resolution = resolution;
			System.out.println("resolution: "+resolution);
			int commHelp = br.read();			// Commands help (1 -> true)
			Resources.getInstance().commandsHelp = (commHelp==1)?true:false;
			System.out.println("commandsHelp: "+commHelp);
			br.readLine();						// new line
			/* Resources commands */
			System.out.println("\n[Resources commands]");
			String commands = br.readLine();
			Resources.Commands.Up.setKey(commands.charAt(0));
			Resources.Commands.Right.setKey(commands.charAt(1));
			Resources.Commands.Down.setKey(commands.charAt(2));
			Resources.Commands.Left.setKey(commands.charAt(3));
			Resources.Commands.Take.setKey(commands.charAt(4));
			Resources.Commands.Inventory.setKey(commands.charAt(5));
			Resources.Commands.QuickAction1.setKey(commands.charAt(6));
			Resources.Commands.QuickAction2.setKey(commands.charAt(7));
			Resources.Commands.Pause.setKey(commands.charAt(8));
			Resources.Commands.Restart.setKey(commands.charAt(9));
			System.out.println("commands: "+commands);
			
			/* Player basic info */
			System.out.println("\n[Player basic info]");
			String desc = br.readLine();		// Player name
			System.out.println("description: "+desc);
			int playerX = br.read();					// Player x
			System.out.println("posX: "+playerX);
			int playerY = br.read();					// Player y
			System.out.println("posY: "+playerY);
			int hp = br.read();					// Player hp
			System.out.println("hp: "+hp);
			int maxHealth = br.read();			// Player maxHealth
			System.out.println("maxHealth: "+maxHealth);
			int atk = br.read();				// Player atk
			System.out.println("atk: "+atk);
			int bonusAtk = br.read();			// Player bonusAtk
			System.out.println("bonusAtk: "+bonusAtk);
			int def = br.read();				// Player def
			System.out.println("def: "+def);
			int bonusDef = br.read();			// Player bonusDef
			System.out.println("bonusDef: "+bonusDef);
			int vit = br.read();				// Player vit
			System.out.println("vit: "+vit);
			int bonusVit = br.read();			// Player bonusVit
			System.out.println("bonusVit: "+bonusVit);
			int gold = br.read();				// Player gold
			System.out.println("gold: "+gold);
			int monstersKilled = br.read();		// Player monsters killed
			System.out.println("monstersKilled: "+monstersKilled);
			int potionEffect = br.read();		// Player potion effect
			System.out.println("potionEffect: "+potionEffect);
			int effectId = br.read();		// Player effect id
			System.out.println("effectId: "+effectId);
			br.readLine();						// new line
			/* Player helmet */
			System.out.println("\n[Player helmet]");
			String helmetType = br.readLine();	// helmet type
			System.out.println("helmetType: "+helmetType);
			int helmetVal = br.read();			// helmet val
			System.out.println("helmetVal: "+helmetVal);
			int helmetDur = br.read();			// helmet durability
			System.out.println("helmetDur: "+helmetDur);
			int helmetMaxDur = br.read();		// helmet max durability
			System.out.println("helmetMaxDur: "+helmetMaxDur);
			int helmetEffectId = br.read();		// helmet effect id
			System.out.println("helmetEffectId: "+helmetEffectId);
			br.readLine();
			/* Player weapon */
			System.out.println("\n[Player weapon]");
			int isWeapon = br.read();
			if(isWeapon==1) { System.out.println("weapon"); }
			else { System.out.println("bow"); }
			int weaponVal = br.read();			// weapon val
			System.out.println("weaponVal: "+weaponVal);
			int weaponDur = br.read();			// weapon durability
			System.out.println("weaponDur: "+weaponDur);
			int weaponMaxDur = br.read();		// weapon max durability
			System.out.println("weaponMaxDur: "+weaponMaxDur);
			int weaponEffectId = br.read();		// weapon effect id
			System.out.println("weaponEffectId: "+weaponEffectId);
			br.readLine();
			/* Player shield */
			System.out.println("\n[Player shield]");
			int shieldVal = br.read();			// shield val
			System.out.println("shieldVal: "+shieldVal);
			int shieldDur = br.read();			// shield durability
			System.out.println("shieldDur: "+shieldDur);
			int shieldMaxDur = br.read();		// shield max durability
			System.out.println("shieldMaxDur: "+shieldMaxDur);
			int shieldEffectId = br.read();		// shield effect id
			System.out.println("shieldEffectId: "+shieldEffectId);
			br.readLine();
			/* Player inventory */
			System.out.println("\n[Player inventory]");
			int invMaxSize = br.read();
			System.out.println("invMaxSize: "+invMaxSize);
			int invSize = br.read();
			System.out.println("invSize: "+invSize);
			br.readLine();
			ArrayList<Item> inv = new ArrayList<Item>();
			Item quickItem1=null, quickItem2=null;
			int itemId=0, itemVal=0, itemDur=0, itemMaxDur=0, itemEffectId=0;
			boolean isEquiped=false;
			for(int i=0; i<invSize; i++) {
				itemId = br.read();
				if(itemId > 2) {
					isEquiped = (br.read()==1)? true : false;
					itemDur = br.read();
					itemMaxDur = br.read();
					itemEffectId = br.read();
				}
				itemVal = br.read();
				
				Item item=null;
				if(itemId == 1) { item = new Antidote(0,0); }
				else if(itemId == 2) { item = new HealingPotion(0,0); }
				else if(itemId == 3) { item = new Bow(0,0, itemVal, isEquiped, itemDur, itemMaxDur, Resources.getEffectById(itemEffectId)); }
				else if(itemId == 4) { item = new Weapon(0,0, itemVal, isEquiped, itemDur, itemMaxDur, Resources.getEffectById(itemEffectId)); }
				else if(itemId == 5) { item = new Shield(0,0, itemVal, isEquiped, itemDur, itemMaxDur, Resources.getEffectById(itemEffectId)); }
				else if(itemId == 6) { item = new Helmet(0,0, itemVal, isEquiped, itemDur, itemMaxDur, Resources.getEffectById(itemEffectId)); }
				inv.add(item);
				
				int QA = br.read();
				if(QA==1) { quickItem1 = item; }
				else if(QA==2) { quickItem2 = item; }
				
				br.readLine();
			}
			System.out.println(inv);
			System.out.println(quickItem1);
			System.out.println(quickItem2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
