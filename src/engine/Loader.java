package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import objects.item.*;
import objects.mob.Monster;
import rooms.*;
import engine.builders.*;

public class Loader {
	private Dungeon dungeon;
	
	public Loader(Dungeon d) {
		this.dungeon = d;
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
			System.out.println("[Resources commands]");
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
			this.dungeon.getPlayer().description = desc;
			System.out.println("description: "+desc);
			int playerX = br.read();					// Player x
			System.out.println("posX: "+playerX);
			int playerY = br.read();					// Player y
			this.dungeon.getPlayer().pos = new Point(playerX, playerY);
			System.out.println("posY: "+playerY);
			int hp = br.read();					// Player hp
			this.dungeon.getPlayer().hp = hp;
			System.out.println("hp: "+hp);
			int maxHealth = br.read();			// Player maxHealth
			this.dungeon.getPlayer().maxHealth = maxHealth;
			System.out.println("maxHealth: "+maxHealth);
			int atk = br.read();				// Player atk
			this.dungeon.getPlayer().atk = atk;
			System.out.println("atk: "+atk);
			int bonusAtk = br.read();			// Player bonusAtk
			this.dungeon.getPlayer().bonusAtk = bonusAtk;
			System.out.println("bonusAtk: "+bonusAtk);
			int def = br.read();				// Player def
			this.dungeon.getPlayer().def = def;
			System.out.println("def: "+def);
			int bonusDef = br.read();			// Player bonusDef
			this.dungeon.getPlayer().bonusDef = bonusDef;
			System.out.println("bonusDef: "+bonusDef);
			int vit = br.read();				// Player vit
			this.dungeon.getPlayer().vit = vit;
			System.out.println("vit: "+vit);
			int bonusVit = br.read();			// Player bonusVit
			this.dungeon.getPlayer().bonusVit = bonusVit;
			System.out.println("bonusVit: "+bonusVit);
			int gold = br.read();				// Player gold
			this.dungeon.getPlayer().setGold(gold);
			System.out.println("gold: "+gold);
			int monstersKilled = br.read();		// Player monsters killed
			this.dungeon.getPlayer().setKills(monstersKilled);
			System.out.println("monstersKilled: "+monstersKilled);
			int potionEffect = br.read();		// Player potion effect
			this.dungeon.getPlayer().setPotionEffect(potionEffect);
			System.out.println("potionEffect: "+potionEffect);
			int effectId = br.read();		// Player effect id
			this.dungeon.getPlayer().setEffect(Resources.getEffectById(effectId));
			System.out.println("effectId: "+effectId);
			br.readLine();						// new line
			/* Preparing player inventory */
			ArrayList<Item> inv = new ArrayList<Item>();
			/* Player helmet */
			System.out.println("[Player helmet]");
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
			Helmet h = new Helmet(0,0, helmetVal, true, helmetDur, helmetMaxDur, Resources.getEffectById(helmetEffectId));
			this.dungeon.getPlayer().setHelmet(h);
			inv.add(h);
			br.readLine();
			/* Player weapon */
			System.out.println("[Player weapon]");
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
			Weapon w = null;
			Bow b = null;
			if(isWeapon==1) {
				w = new Weapon(0,0, weaponVal, true, weaponDur, weaponMaxDur, Resources.getEffectById(weaponEffectId));
				this.dungeon.getPlayer().setWeapon(w);
				inv.add(w);
			} else {
				b = new Bow(0,0, weaponVal, true, weaponDur, weaponMaxDur, Resources.getEffectById(weaponEffectId));
				this.dungeon.getPlayer().setWeapon(b);
				inv.add(b);
			}
			br.readLine();
			/* Player shield */
			System.out.println("[Player shield]");
			int shieldVal = br.read();			// shield val
			System.out.println("shieldVal: "+shieldVal);
			int shieldDur = br.read();			// shield durability
			System.out.println("shieldDur: "+shieldDur);
			int shieldMaxDur = br.read();		// shield max durability
			System.out.println("shieldMaxDur: "+shieldMaxDur);
			int shieldEffectId = br.read();		// shield effect id
			System.out.println("shieldEffectId: "+shieldEffectId);
			Shield s = new Shield(0,0, shieldVal, true, shieldDur, shieldMaxDur, Resources.getEffectById(shieldEffectId));
			this.dungeon.getPlayer().setShield(s);
			inv.add(s);
			br.readLine();
			/* Player inventory */
			System.out.println("[Player inventory]");
			int invMaxSize = br.read();
			System.out.println("invMaxSize: "+invMaxSize);
			int invSize = br.read();
			System.out.println("invSize: "+invSize);
			br.readLine();
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
				else if(itemId == 3) { item = (isEquiped)?b:new Bow(0,0, itemVal, isEquiped, itemDur, itemMaxDur, Resources.getEffectById(itemEffectId)); }
				else if(itemId == 4) { item = (isEquiped)?w:new Weapon(0,0, itemVal, isEquiped, itemDur, itemMaxDur, Resources.getEffectById(itemEffectId)); }
				else if(itemId == 5) { item = (isEquiped)?s:new Shield(0,0, itemVal, isEquiped, itemDur, itemMaxDur, Resources.getEffectById(itemEffectId)); }
				else if(itemId == 6) { item = (isEquiped)?h:new Helmet(0,0, itemVal, isEquiped, itemDur, itemMaxDur, Resources.getEffectById(itemEffectId)); }
				if(!inv.contains(item)) { inv.add(item); }
				
				int QA = br.read();
				if(QA==1) { quickItem1 = item; }
				else if(QA==2) { quickItem2 = item; }
				
				br.readLine();
			}
			System.out.println(inv);
			System.out.println(quickItem1);
			System.out.println(quickItem2);
			Inventory inventory = new Inventory(invMaxSize, this.dungeon.getLog(), this.dungeon.getPlayer(), inv, quickItem1, quickItem2);
			this.dungeon.getPlayer().setInventory(inventory);
			
			/* Dungeon */
			System.out.println("\n[Dungeon]");
			ArrayList<Map> mapList = new ArrayList<Map>();
			int currLvl = br.read();
			System.out.println("currentLevel: "+currLvl);
			int lvlSize = br.read();
			br.readLine();
			/* Maps */
			for(int i=0; i<lvlSize; i++) {
				System.out.println("\n[Map "+i+"]");
				int roomSize = br.read();
				br.readLine();
				Vector<Room> rooms = new Vector<Room>();
				/* Rooms */
				System.out.println("[Rooms]");
				for(int j=0; j<roomSize; j++) {
					boolean isRectangleRoom = (br.read()==1)? true : false;
					Vector<Point> mossList = new Vector<Point>();
					Vector<Point> dirtList = new Vector<Point>();
					if(isRectangleRoom) {
						int mossSize = br.read();
						for(int moss=0; moss<mossSize; moss++) {
							int mossX = br.read();
							int mossY = br.read();
							mossList.add(new Point(mossX, mossY));
						}
						int dirtSize = br.read();
						for(int dirt=0; dirt<dirtSize; dirt++) {
							int dirtX = br.read();
							int dirtY = br.read();
							dirtList.add(new Point(dirtX, dirtY));
						}
					}
					br.readLine();
					String roomDesc = br.readLine();
					System.out.print("roomDesc: "+roomDesc);
					int roomP1X = br.read(), roomP1Y = br.read(), roomP2X = br.read(), roomP2Y = br.read();
					System.out.print(" room points: ("+roomP1X+","+roomP1Y+") ("+roomP2X+","+roomP2Y+")");
					boolean isRoomVisible = (br.read()==1)? true : false;
					if(isRoomVisible) { System.out.print(" visible "); }
					else { System.out.print(" hidden "); }
					br.readLine();
					int doorSize = br.read();
					System.out.println(doorSize+" doors");
					Vector<Door> doors = new Vector<Door>();
					for(int door=0; door<doorSize; door++) {
						int doorX = br.read();
						int doorY = br.read();
						boolean doorOpened = (br.read()==1)? true : false;
						doors.add(new Door(new Point(doorX, doorY), doorOpened));
					}
					br.readLine();
					
					if(isRectangleRoom) {
						rooms.add(new RectangleRoomBuilder().
								withPoints(new Point(roomP1X, roomP1Y), new Point(roomP2X, roomP2Y)).
								withDescription(roomDesc).
								withShowBool(isRoomVisible).
								withDoors(doors).
								withMoss(mossList).
								withDirt(dirtList).
								build());
					} else {
						rooms.add(new CorridorBuilder().
								withPoints(new Point(roomP1X, roomP1Y), new Point(roomP2X, roomP2Y)).
								withDescription(roomDesc).
								withShowBool(isRoomVisible).
								withDoors(doors).
								build());
					}
				}
				
				/* Monsters */
				System.out.println("\n[Monsters]");
				int monsterSize = br.read();
				br.readLine();
				Vector<Monster> monsters = new Vector<Monster>();
				for(int j = 0; j<monsterSize; j++) {
					String monsterDesc = br.readLine();
					int monsterX = br.read(), monsterY = br.read();
					boolean isMonsterDead = (br.read()==1)? true : false;
					int monsterHp=0, monsterMaxHealth=0, monsterAtk=0, monsterDef=0, monsterVit=0;
					int monsterEffect=-1, monsterEffectSpreader=-1;
					if(!isMonsterDead) {
						monsterHp = br.read();
						monsterMaxHealth = br.read();
						monsterAtk = br.read();
						monsterDef = br.read();
						monsterVit = br.read();
						monsterEffect = br.read();
						monsterEffectSpreader = br.read();
					}
					br.readLine();
					System.out.println(monsterDesc.charAt(0)+" ("+monsterX+","+monsterY+") "+((isMonsterDead)?"dead":"alive"));
					
					monsters.add(new MonsterBuilder().
							withSymbol(monsterDesc.charAt(0)).
							withDescription(monsterDesc).
							withStats(monsterMaxHealth, monsterHp, monsterAtk, monsterDef, monsterVit).
							withDeadBool(isMonsterDead).
							withPos(new Point(monsterX, monsterY)).
							withLog(this.dungeon.getLog()).
							withEffects(Resources.getEffectById(monsterEffect), Resources.getEffectById(monsterEffectSpreader)).
							build());
				}
				
				/* Items */
				System.out.println("\n[Items]");
				int itemSize = br.read();
				br.readLine();
				Vector<Item> items = new Vector<Item>();
				for(int j=0; j<itemSize; j++) {
					int itemX = br.read(), itemY = br.read();
					itemId = br.read();
					itemDur = itemMaxDur = itemEffectId = 0;
					if(itemId > 2 && itemId < 10) {
						itemDur = br.read();
						itemMaxDur = br.read();
						itemEffectId = br.read();
					}
					itemVal = br.read();
					
					Item item = null;
					if(itemId==1) { item = new Antidote(itemX, itemY); item.setVal(itemVal); }
					else if(itemId==2) { item = new HealingPotion(itemX, itemY); item.setVal(itemVal); }
					else if(itemId==3) { item = new Bow(itemX, itemY, itemVal, false, itemDur, itemMaxDur, Resources.getEffectById(itemEffectId)); }
					else if(itemId==4) { item = new Weapon(itemX, itemY, itemVal, false, itemDur, itemMaxDur, Resources.getEffectById(itemEffectId)); }
					else if(itemId==5) { item = new Shield(itemX, itemY, itemVal, false, itemDur, itemMaxDur, Resources.getEffectById(itemEffectId)); }
					else if(itemId==6) { item = new Helmet(itemX, itemY, itemVal, false, itemDur, itemMaxDur, Resources.getEffectById(itemEffectId)); }
					else if(itemId==10) { item = new Barrel(itemX, itemY); }
					else if(itemId==11) { item = new Fountain(itemX, itemY); item.setVal(itemVal); }
					else if(itemId==12) { item = new Gold(itemX, itemY); item.setVal(itemVal); }
					items.add(item);
				}
				br.readLine();
				System.out.println(items);
				
				/* Stairs */
				System.out.println("\n[Stairs]");
				int sDX = br.read(), sDY = br.read();
				int sUX = br.read(), sUY = br.read();
				br.readLine();
				System.out.println("stairDown: ("+sDX+","+sDY+") stairUp: ("+sUX+","+sUY+")");
				
				/* Height and width */
				int mapHeight = br.read(), mapWidth = br.read();
				br.readLine();
				
				mapList.add(new MapBuilder().
						withDungeon(this.dungeon).
						withHeightWidth(mapHeight, mapWidth).
						withItems(items).
						withMonsters(monsters).
						withRooms(rooms).
						withStairDown(new Point(sDX, sDY)).
						withStairUp(new Point(sUX, sUY)).
						build());
			}
			this.dungeon.setLevels(mapList);
			this.dungeon.setCurrentLevel(currLvl);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return this.dungeon;
	}
}
