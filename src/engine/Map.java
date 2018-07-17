package engine;

import java.awt.*;
import java.util.*;

import objects.item.*;
import objects.looker.LookerFactory;
import objects.mob.*;
import rooms.*;
import tiles.*;

public class Map extends Observable {
	protected Tile[][] table;
	protected Vector<Room> rooms;
	protected Vector<Monster> monsters;
	protected Vector<Item> items;
	protected MessageLog log;
	protected Point stairUp;
	protected Point stairDown;
	protected Player jerry;
	protected Boss boss;
	protected Monster focusedMonster;
	protected int level;
	protected Dungeon dungeon;
	protected int width;
	protected int height;
	protected Random rnd;
	public String oldString;
	
	protected boolean fireMode;
	protected Point firePoint;
	
	public Map(int width, int height, Dungeon d) {
		this.dungeon = d;
		this.width = width;
		this.height = height;
		this.table = new Tile[height][width];
		this.rooms = new Vector<Room>();
		this.monsters = new Vector<Monster>();
		this.items = new Vector<Item>();
		this.level = d.getLevel();
		this.stairDown = new Point();
		this.log = d.getLog();
		this.jerry = d.getPlayer();
		this.stairUp = new Point(width/2, height/2);
		this.jerry.setFloor(TileFactory.getInstance().createTileStone());
		this.rnd = new Random();
		this.oldString="";
	}

	public Map(Dungeon d, int height, int width, Vector<Room> r, Vector<Monster> m, Vector<Item> i, Point sU, Point sD) {
		this.dungeon = d;
		this.log = this.dungeon.getLog();
		this.jerry = this.dungeon.getPlayer();
		this.level = this.dungeon.getLevel();
		this.height = height;
		this.width = width;
		this.table = new Tile[height][width];
		this.rooms = r;
		this.monsters = m;
		this.items = i;
		this.stairDown = sD;
		this.stairUp = sU;
		this.rnd = new Random();
		this.oldString="";
	}
	
	public Map(Dungeon d, Tile[][] t, Vector<Room> r, Vector<Monster> m, Vector<Item> i, Point sU, Point sD) {
		this.dungeon = d;
		this.log = this.dungeon.getLog();
		this.jerry = this.dungeon.getPlayer();
		this.level = this.dungeon.getLevel();
		this.height = t.length;
		this.width = t[0].length;
		this.table = t;
		this.rooms = r;
		this.monsters = m;
		this.items = i;
		this.stairDown = sD;
		this.stairUp = sU;
		this.rnd = new Random();
		this.oldString="";
	}
	
	public Tile[][] getTable() { return this.table; }
	public int getHeight() { return this.height; }
	public int getWidth() { return this.width; }
	public Vector<Monster> getMonsters() { return monsters; }
	public Vector<Item> getItems() { return items; }

	/**
	 * fillRectangle : fill the tile table with a rectangle (x1, x2, y1, y2) of a tile t
	 */
	public void fillRectangle(Tile[][] table, int x1, int y1, int x2, int y2, Tile t) {
		if(x1 < 0 || y1 < 0 || x2 > table[0].length-1 || y2 > table.length-1) {
			return;
		}
		
		for(int i=x1; i<=x2; i++) {
			for(int j=y1; j<=y2; j++) {
				table[j][i] = t;
			}
		}
	}
	/**
	 * checkOn : check if the rectangle (x1, x2, y1, y2) is available on the tile table
	 */ 
	public boolean checkOn(int x1, int y1, int x2, int y2) {
		if(x1<0 || y1<0 || x2>=this.table[0].length || y2>=this.table.length)
			return false;
		
		Tile[][] tmpTable = new Tile[getHeight()][getWidth()];
		fillRectangle(tmpTable, 0, 0, getWidth()-1, getHeight()-1, TileFactory.getInstance().createTileVoid());
		
		for(int i=0; i<this.rooms.size(); i++) {
			this.rooms.get(i).print(tmpTable);
		}
		
		for(int i=y1+1; i<y2; i++) {
			for(int j=x1+1; j<x2; j++) {
				if(!(tmpTable[i][j] instanceof TileVoid)) return false;
			}
		}
		return true;
	}
	
	/**
	 * isVisible : check if the room containing the point (x, y) is visible
	 */
	public boolean isVisible(int x, int y) {
		for(int i=0; i<rooms.size(); i++) {
			if(x>rooms.get(i).p1.x && x<rooms.get(i).p2.x
					&& y>rooms.get(i).p1.y && y<rooms.get(i).p2.y) {
				return this.rooms.get(i).isVisible();
			}
		}
		
		return true;
	}
	
	/**
	 * isWalkable : check if the tile at (x, y) is walkable
	 */
	public boolean isWalkable(int x, int y) {
		return this.table[y][x].isWalkable() && !isMonster(x, y);
	}
	
	/**
	 * generateDungeon : generate a level : place rooms,
	 * monsters, items, stairs and player
	 */
	public void generateDungeon() {
		Room room;
		int roomIndex, height, width;
				
		// Position joueur
		this.jerry.placeOn(getWidth()/2, getHeight()/2);
		
		// Remise a zero de la carte
		this.rooms = new Vector<Room>();
		this.monsters = new Vector<Monster>();
		this.items = new Vector<Item>();
		this.oldString = "";
		fillRectangle(this.table, 0, 0, getWidth()-1, getHeight()-1, TileFactory.getInstance().createTileVoid());	
		
		// Premiere salle, au centre du niveau
		height = rnd.nextInt(5)+9;
		width = rnd.nextInt(5)+6;
		rooms.add(new RectangleRoom((this.table[0].length/2)-(width/2), (this.table.length/2)-(height/2), (this.table[0].length/2)+(width/2), (this.table.length/2)+(height/2), "Main Room"));
		rooms.get(rooms.size()-1).show();
		rooms.get(rooms.size()-1).printOn(this.table);
		
		// Generation des salles
		for(int i=0; i<500; i++) {
			roomIndex = rnd.nextInt(rooms.size());
			room = this.rooms.get(roomIndex);
			
			if(room instanceof Corridor) {
				if(rnd.nextInt(4)==0) {
					// EXTENSION COULOIR
					generateCorridor(room, true);
				} else {
					// SALLE
					generateRectangleRoom(room);
				}
			} else {
				// COULOIR
				generateCorridor(room, false);
			}
			printDungeon();
		}
		generateItems();
		generateMonsters();
		if(!(this.level%5==0 && this.level>0)) {
			placeStairs();
		}
	}

 /**
  * generateRectangleRoom : generate a new room next to room
  * @param room : the room closed to the new room 
  */
	protected void generateRectangleRoom(Room room) {
		int wallSide, height, width, mid;
		
		// SALLE
		height = rnd.nextInt(5)+6;
		width = rnd.nextInt(5)+6;
		
		// De quel cote on etend le donjon
		wallSide = rnd.nextInt(4);
		if(wallSide == 0) {
			// NORTH
			mid = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
			if(checkOn(mid-(width/2), room.p1.y-height, mid+(width/2), room.p1.y)) {
				rooms.add(new RectangleRoom(mid-(width/2), room.p1.y-height, mid+(width/2), room.p1.y, "Dungeon Room "+this.rooms.size()));
				// porte pour la premiere salle
				room.addDoor(new Point(mid, room.p1.y));
				// porte pour la deuxieme salle
				rooms.get(rooms.size()-1).addDoor(new Point(mid, room.p1.y));
				
			}
		} else if(wallSide == 1) {
			// SOUTH
			mid = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
			if(checkOn(mid-(width/2), room.p2.y, mid+(width/2), room.p2.y+height)) {
				rooms.add(new RectangleRoom(mid-(width/2), room.p2.y, mid+(width/2), room.p2.y+height, "Dungeon Room "+this.rooms.size()));
				// porte pour la premiere salle
				room.addDoor(new Point(mid, room.p2.y));
				// porte pour la deuxieme salle
				rooms.get(rooms.size()-1).addDoor(new Point(mid, room.p2.y));
			}
		} else if(wallSide == 2) {
			// EAST
			mid = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
			if(checkOn(room.p2.x, mid-(height/2), room.p2.x+width, mid+(height/2))) {
				rooms.add(new RectangleRoom(room.p2.x, mid-(height/2), room.p2.x+width, mid+(height/2), "Dungeon Room "+this.rooms.size()));
				// porte pour la premiere salle
				room.addDoor(new Point(room.p2.x, mid));
				// porte pour la deuxieme salle
				rooms.get(rooms.size()-1).addDoor(new Point(room.p2.x, mid));
			}
		} else if(wallSide == 3) {
			// WEST
			mid = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
			if(checkOn(room.p1.x-width, mid-(height/2), room.p1.x, mid+(height/2))) {
				rooms.add(new RectangleRoom(room.p1.x-width, mid-(height/2), room.p1.x, mid+(height/2), "Dungeon Room "+this.rooms.size()));
				// porte pour la premiere salle
				room.addDoor(new Point(room.p1.x, mid));
				// porte pour la deuxieme salle
				rooms.get(rooms.size()-1).addDoor(new Point(room.p1.x, mid));
			}
		}
	}
	
	/**
	 * generateCorridor : generate a corridor next to room or a corridor.
	 * @param room : the existing room (or corridor)
	 * @param junction : if true, the existing room is a corridor
	 */
	protected void generateCorridor(Room room, boolean junction) {
		int wallSide, height, width;

		// De quel cote on etend le donjon
		wallSide = rnd.nextInt(4);
		if(wallSide == 0) {
			// NORTH
			height = rnd.nextInt(4)+5;
			width = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
			if(checkOn(width-1, room.p1.y-height, width+1, room.p1.y)) {
				rooms.add(new Corridor(width-1, room.p1.y-height, width+1, room.p1.y, "Corridor North "+this.rooms.size()));
				if(junction) {
					room.addDoor(new Point(width, room.p1.y), TileFactory.getInstance().createTileStone());
					rooms.get(rooms.size()-1).addDoor(new Point(width, room.p1.y), TileFactory.getInstance().createTileStone());
				} else {
					room.addDoor(new Point(width, room.p1.y));
					rooms.get(rooms.size()-1).addDoor(new Point(width, room.p1.y));
				}
			}
		} else if(wallSide == 1) {
			// SOUTH
			height = rnd.nextInt(4)+5;
			width = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
			if(checkOn(width-1, room.p2.y, width+1, room.p2.y+height)) {
				rooms.add(new Corridor(width-1, room.p2.y, width+1, room.p2.y+height, "Corridor South "+this.rooms.size()));
				if(junction) {
					room.addDoor(new Point(width, room.p2.y), TileFactory.getInstance().createTileStone());
					rooms.get(rooms.size()-1).addDoor(new Point(width, room.p2.y), TileFactory.getInstance().createTileStone());
				} else {
					room.addDoor(new Point(width, room.p2.y));
					rooms.get(rooms.size()-1).addDoor(new Point(width, room.p2.y));
				}
			}
		} else if(wallSide == 2) {
			// EAST
			width = rnd.nextInt(4)+5;
			height = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
			if(checkOn(room.p2.x, height-1, room.p2.x+width, height+1)) {
				rooms.add(new Corridor(room.p2.x, height-1, room.p2.x+width, height+1, "Corridor East "+this.rooms.size()));
				if(junction) {
					room.addDoor(new Point(room.p2.x, height), TileFactory.getInstance().createTileStone());
					rooms.get(rooms.size()-1).addDoor(new Point(room.p2.x, height), TileFactory.getInstance().createTileStone());
				} else {
					room.addDoor(new Point(room.p2.x, height));
					rooms.get(rooms.size()-1).addDoor(new Point(room.p2.x, height));
				}
			}
		} else if(wallSide == 3) {
			// WEST
			width = rnd.nextInt(4)+5;
			height = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
			if(checkOn(room.p1.x-width, height-1, room.p1.x, height+1)) {
				rooms.add(new Corridor(room.p1.x-width, height-1, room.p1.x, height+1, "Corridor West "+this.rooms.size()));
				if(junction) {
					room.addDoor(new Point(room.p1.x, height), TileFactory.getInstance().createTileStone());
					rooms.get(rooms.size()-1).addDoor(new Point(room.p1.x, height), TileFactory.getInstance().createTileStone());
				} else {
					room.addDoor(new Point(room.p1.x, height));
					rooms.get(rooms.size()-1).addDoor(new Point(room.p1.x, height));
				}
			}
		}
	}
	
	/**
	 * generateItems : place items in the rooms
	 */
	protected void generateItems() {
		for(int i=0; i<rooms.size(); i++) {
			rooms.get(i).parsingFloor(items, level);
		}
	}
	
	/**
	 * generateMonsters : generate the monsters and place them in the rooms
	 */
	protected void generateMonsters() {
		Room room;
		int roomNumber, roomIndex, monsterNumber, height, width, monsterName;
		
		roomNumber = (rnd.nextInt(rooms.size())+1)/2;
		// Monsters
		for(int i=0; i<roomNumber; i++) {
			do {
				roomIndex = rnd.nextInt(rooms.size()-1)+1;
				room = this.rooms.get(roomIndex);
			} while (room instanceof Corridor);
			
			monsterNumber = rnd.nextInt((room.getHeight()-2)*(room.getWidth()-2)/(4-Resources.getInstance().difficulty))+1;
			for(int j=0; j<monsterNumber; j++) {
				do {
					width = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
					height = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
				} while (((width==this.jerry.pos.x) && (height==this.jerry.pos.y)) && !isMonster(width, height));

				monsterName = rnd.nextInt(26);
				this.monsters.add(new Monster(width, height, Resources.getLetterAt(monsterName), Resources.getNameAt(monsterName), this.log));
			}
		}
		// BOSS
		if(this.level>0 && this.level%5==0) {
			do {
				roomIndex = rnd.nextInt(rooms.size()-1)+1;
				room = this.rooms.get(roomIndex);
			} while (room instanceof Corridor);

			do {
				width = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
				height = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
			} while (((width==this.jerry.pos.x) && (height==this.jerry.pos.y)) && !isMonster(width, height));
			monsterName = rnd.nextInt(26);
			boss = new Boss(width, height, this.level/5, Resources.getCapitalLetterAt(monsterName), Resources.getCapitalNameAt(monsterName), this.log);
			monsters.add(boss);
		}
	}
	
	/**
	 * placeStairs : generate the stairs
	 */
	protected void placeStairs() {
		Room selectedRoom;
		int height, width;
		
		do {
			// choisit une salle
			selectedRoom = rooms.get(rnd.nextInt(rooms.size()));
		} while(selectedRoom instanceof Corridor);
		do {
			// choisit un point dans la salle
			height = rnd.nextInt(selectedRoom.getHeight()-3)+2+selectedRoom.p1.y;
			width = rnd.nextInt(selectedRoom.getWidth()-3)+2+selectedRoom.p1.x;
		} while(width == this.jerry.pos.x && height == this.jerry.pos.y);
		// placement de l'escalier
		this.stairDown = new Point(width, height);
	}
	
	/**
	 * integratePlayer : place the player in the Tile table
	 */
	protected void integratePlayer() {
		this.table[this.jerry.pos.y][this.jerry.pos.x] = TileFactory.getInstance().createTilePlayer();
	}
	
	/**
	 * integrateMobs : place the mobs (monsters, boss and player) in the Tile table
	 */
	protected void integrateMobs() {
		for(int i=0; i<this.monsters.size(); i++) {
			this.monsters.get(i).setFloor(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x]);
			if(!(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] instanceof TileVoid)) {
				if(this.monsters.get(i).isDead()) {
					if(!(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] instanceof TileItem) 
							&& !(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] instanceof TileStairsDown) 
							&& !(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] instanceof TileStairsUp) 
							&& !(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] instanceof TileMonster)) {
						this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] = this.monsters.get(i).getMobTile();
					}
				} else {
					if(!(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] instanceof TileMonster)) {
						this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] = this.monsters.get(i).getMobTile();
					}
				}
			}
		}
		// Boss
		for(int i=0; i<this.monsters.size(); i++) {
			if(this.monsters.get(i) instanceof Boss) {
				this.monsters.get(i).setFloor(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x]);
				break;
			}
		}
		// Player
		integratePlayer();
	}
	
	/**
	 * integrateItems : place all common items in Tile table
	 */
	protected void integrateItems() {
		for(int i=0; i<items.size(); i++) {
			if(!isMonster(items.get(i).pos.x, items.get(i).pos.y) && !(this.table[items.get(i).pos.y][items.get(i).pos.x] instanceof TileVoid) && 
					!(this.table[items.get(i).pos.y][items.get(i).pos.x] instanceof TileStairsDown) &&
					!(this.table[items.get(i).pos.y][items.get(i).pos.x] instanceof TileStairsUp) &&
					!(this.table[items.get(i).pos.y][items.get(i).pos.x] instanceof TileFountain)) {
				this.table[items.get(i).pos.y][items.get(i).pos.x] = items.get(i).getTile();
			}
		}
	}
	
	/**
	 * playerIn : get the room the player is in,
	 * activate effects if the player steps on something
	 */
	protected Room playerIn(int x, int y) {
		Room room=null;
		for(int i=0; i<this.rooms.size(); i++) {
			if(x >= this.rooms.get(i).p1.x && x <= this.rooms.get(i).p2.x 
					&& y >= this.rooms.get(i).p1.y && y <= this.rooms.get(i).p2.y) {
				room=rooms.get(i);
				room.isDoor(x, y);
				checkItem(x, y);
				if(room instanceof RectangleRoom) { ((RectangleRoom)room).checkMoss(x, y, items); }
			}
		}
		return room;
	}  
	
	/**
	 * isPlayerDead : check if the player is dead
	 */
	public boolean isPlayerDead() { return this.jerry.isDead(); }
	
	/**
	 * isStairsUp : check if the tile at (x, y) is a StairUp
	 */
	protected boolean isStairsUp(int x, int y) {
		return this.table[y][x] instanceof TileStairsUp;
	}
	
	/**
	 * isStairsDown : check if the tile at (x, y) is a StairDown
	 */
	protected boolean isStairsDown(int x, int y) {
		return this.table[y][x] instanceof TileStairsDown;
	}
	
	/**
	 * isItem : return the Item at (x, y), null if no item 
	 */
	public Item isItem(int x, int y) {
		for(int i=items.size()-1; i>=0; i--) {
			if(x==items.get(i).pos.x && y==items.get(i).pos.y) {
				return items.get(i);
			}
		}
		return null;
	}
	
	/**
	 * checkItem : trigger the action of the item placed at (x, y)
	 * (triggered when the player steps in)
	 */
	protected void checkItem(int x, int y) {
		Item i = isItem(x, y);
		
		if(i == null) { return; }
		 
		if(i instanceof Barrel) {
			int content=((Barrel)i).open();
			this.jerry.setLooker(LookerFactory.getInstance().createLookerBarrel(this.jerry.pos.x, this.jerry.pos.y));
			items.remove(i);
			if(content == 0) {
				if(rnd.nextInt(2)==0) {
					log.appendMessage("Open Barrel... Potion!");
					if(rnd.nextInt(2)==0) {
						items.add(new HealingPotion(x, y));
					} else {
						items.add(new Antidote(x, y));
					}
				} else {
					int itemChance = rnd.nextInt(4);
					int itemValue = rnd.nextInt((level-1)%5+1);
					if (itemValue==0) itemValue++;
					if(itemChance==0) {
						log.appendMessage("Open Barrel... Weapon!");
						items.add(new Weapon(x, y, itemValue));
					} else if(itemChance==1) {
						log.appendMessage("Open Barrel... Shield!");
						items.add(new Shield(x, y, itemValue));
					} else if(itemChance==2) {
						log.appendMessage("Open Barrel... Bow!");
						items.add(new Bow(x, y, itemValue));
					} else if(itemChance==3) {
						log.appendMessage("Open Barrel... Helmet!");
						items.add(new Helmet(x, y, itemValue));
					}
				}
			} else if(content==1) {
				log.appendMessage("Open Barrel... Gold!");
				items.add(new Gold(x, y));
			} else if(content == 2) {
				log.appendMessage("Open Barrel... Boom!");
				this.jerry.harm(25);
			} else if(content == 3) {
				Monster m = new Monster(i.pos.x, i.pos.y, Resources.getLetterAt(level-1), Resources.getNameAt(level-1), log);
				this.monsters.add(m);
				log.appendMessage("A "+m.description+" popped out the barrel !");
			} else {
				log.appendMessage("Open Barrel... Nothing!");
			}
			if(content == 2) { Resources.playBarrelExplodeSound(); }
			else { Resources.playBarrelSound(); }
		}
	}
	
	/**
	 * checkAction : trigger the action corresponding to the point (x, y)
	 * (triggered by a player action : take item, fire, ...)
	 */
	public void checkAction(int x, int y) {
		Item i = isItem(x, y);
		
		if(i == null) { checkFire(); }
		if(i instanceof Gold) {
			this.jerry.addGold(i.getVal());
			items.remove(i);
			this.jerry.setLooker(LookerFactory.getInstance().createLookerGold(this.jerry.pos.x, this.jerry.pos.y));
			Resources.playGoldSound();
		}
		else if(i instanceof Fountain) {
			if(!jerry.isFullHealth()) {
				Resources.playFountainSound();
				if(i.getVal()-1>=0) {
					this.jerry.cure();
					this.jerry.setLooker(LookerFactory.getInstance().createLookerHealth(x, y));
					// Player has used the fountain
					i.setVal(i.getVal()-1);
				}
				if(i.getVal()==0){
					log.appendMessage("The fountain disappear...");
					items.remove(i);
				}
			}
		}
		else if(i instanceof Equipement ||
				i instanceof Potion) {
			if(this.jerry.getInventory().addItem(i)) {
				Resources.playPickupSound();
				items.remove(i);	
				this.jerry.setFloor(TileFactory.getInstance().createTileStone());
				this.jerry.setLooker(LookerFactory.getInstance().createLookerEquip(this.jerry.pos.x, this.jerry.pos.y));
			}
		}
	}
	
	/**
	 * checkFire : trigger action when player fires at firePoint
	 */
	public void checkFire() {
		if(fireMode) {
			fireMode = false;
			if(checkSuccessfulFire()) {
				checkMonster(firePoint.x, firePoint.y);
				playerIn(firePoint.x, firePoint.y);
				moveAllMonsters();
				jerry.useWeapon();
				Resources.playFireBowSound();
			}
			if(jerry.pos.x==firePoint.x &&
					jerry.pos.y==firePoint.y) {
				log.appendMessage("This would be too easy...");
			}
		} else {
			if(jerry.getWeapon() instanceof Bow) {
				fireMode = true;
				if(this.focusedMonster != null) {
					firePoint = new Point(this.focusedMonster.pos.x, this.focusedMonster.pos.y);
				} else {
					firePoint = new Point(jerry.pos.x, jerry.pos.y);
				}
				Resources.playReadyBowSound();
			}
		}
	}
	
	/**
	 * checkSuccessfulFire : check if the arrow as made it to firePoint
	 * (arrow can be bloqued by walls, barrels, fountains)
	 */
	public boolean checkSuccessfulFire() {
		if(firePoint.x==jerry.pos.x && firePoint.y==jerry.pos.y) { return false; }
		for(int i=Math.min(jerry.pos.y, firePoint.y); i<=Math.max(jerry.pos.y, firePoint.y); i++) {
			for(int j=Math.min(jerry.pos.x, firePoint.x); j<=Math.max(jerry.pos.x, firePoint.x); j++) {
				if(dungeon.getWin().getDungeonPanel().isFireLine(i, j)) {
					if(table[i][j] instanceof TileWall ||
							table[i][j] instanceof TileBarrel ||
							table[i][j] instanceof TileFountain) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * getFireRadius : count the distance in tiles from the player to the arrow
	 */
	public int getFireRadius() {
		int radius=0;
		this.dungeon.getWin().getDungeonPanel().refreshFireLine();
		for(int i=Math.min(jerry.pos.y, firePoint.y); i<=Math.max(jerry.pos.y, firePoint.y); i++) {
			for(int j=Math.min(jerry.pos.x, firePoint.x); j<=Math.max(jerry.pos.x, firePoint.x); j++) {
				if(dungeon.getWin().getDungeonPanel().isFireLine(i, j)) {
					radius++;
				}
			}
		}
		
		return radius;
	}
	
	/**
	 * checkPlayerPos : trigger action when player stands on (x, y)
	 */
	protected void checkPlayerPos(int x, int y) {
		if(isStairsUp(x, y)) dungeon.levelUp();
		else if(isStairsDown(x, y)) {
			if(this.dungeon.getLevel()%5==0 && this.dungeon.getLevel()>0) {
				this.stairDown=new Point(x, y);
			}
			dungeon.levelDown();
		}
		playerIn(x, y);
	}
	
	protected void monsterDropItem(Monster m) {
		Random rnd = new Random();
		if(rnd.nextInt(4)==0) {
			int itemDrop = rnd.nextInt(6);
			int itemValue = rnd.nextInt((level-1)%5+1);
			if (itemValue==0) itemValue++;
			if(itemDrop==0) {
				this.items.add(new Gold(m.pos.x, m.pos.y, 5+rnd.nextInt(this.level)));
			} else if(itemDrop==1) {
				this.items.add(new Weapon(m.pos.x, m.pos.y, itemValue));
			} else if(itemDrop==2) {
				this.items.add(new Shield(m.pos.x, m.pos.y, itemValue));
			} else if(itemDrop==3) {
				this.items.add(new Bow(m.pos.x, m.pos.y, itemValue));
			} else if(itemDrop==4) {
				this.items.add(new Helmet(m.pos.x, m.pos.y, itemValue));
			} else {
				if(rnd.nextInt(2)==0) 
				{ this.items.add(new HealingPotion(m.pos.x, m.pos.y)); } 
				else { this.items.add(new Antidote(m.pos.x, m.pos.y)); }
			}
		}
	}
	
	/**
	 * checkMonster : trigger action when a monster at (x, y) is targetted
	 */
	protected void checkMonster(int x, int y) {
		boolean monsterKilled;
		String battleLog="";
		for(int i=0; i<this.monsters.size(); i++) {
			if(this.monsters.get(i).isDead()) { continue; }
			if(this.jerry.isDead()) { return; }
			// The player attacks the monster he is facing
			if((x == this.monsters.get(i).pos.x) && (y == this.monsters.get(i).pos.y)) {
				monsterKilled=this.jerry.fight(this.monsters.get(i));
				if(monsterKilled && !(monsters.get(i) instanceof Boss)) {
					monsterDropItem(monsters.get(i));
					this.focusedMonster = null;
				} else if(!monsterKilled) {
					this.focusedMonster = this.monsters.get(i);
				}
			}
			// Check if another monster is facing the player
			String tmp=monsterAttack(monsters.get(i));
			if(!tmp.equals("")) { battleLog+=(!battleLog.equals(""))?(", "+tmp):tmp; }
		}
		if(!battleLog.equals("")) { log.appendMessage(battleLog); }
	}
	
	/**
	 * monstersAttack : provoke all monsters
	 */
	protected void monstersAttack() {
		String battleLog="";
		for(Monster m : monsters) {
			if(m.isDead() || !this.isVisible(m.pos.x, m.pos.y)) { continue; }
			if(m.getEffect().apply()) {
				String tmp=monsterAttack(m);
				if(!tmp.equals("")) { battleLog+=(!battleLog.equals(""))?(", "+tmp):tmp; }
			}
		}
		if(!battleLog.equals("")) { log.appendMessage(battleLog); }
	}
	
	/**
	 * monsterAttack : the monster m attacks only if it is arround the player
	 */
	protected String monsterAttack(Monster m) {	
		String battleLog="";
		int x = jerry.pos.x, y = jerry.pos.y;
		int[][] dir = new int[4][2];
		// NORTH
		dir[0][0]=0; dir[0][1]=-1;
		// EAST
		dir[1][0]=1; dir[1][1]=0;
		// SOUTH
		dir[2][0]=0; dir[2][1]=1;
		// WEST
		dir[3][0]=-1; dir[3][1]=0;
				
		// If the monster is surrounding the player then it attacks
		for(int i=0; i<dir.length; i++) {
			if((dir[i][0]+x == m.pos.x) && (dir[i][1]+y == m.pos.y) && !m.isDead()) {
				battleLog=m.fightTurn(jerry);
				if(jerry.hp<=0) {
					log.appendMessage(battleLog);
					jerry.murder();
					return "";
				}
				break;
			}
		}
		return battleLog;
	}
	
	/**
	 * isMonster : check if a monster is at (x, y)
	 */
	public boolean isMonster(int x, int y) {
		for(int i=0; i<this.monsters.size(); i++) {
			if(((x == this.monsters.get(i).pos.x) && (y == this.monsters.get(i).pos.y) && !this.monsters.get(i).isDead()) || ((x==this.jerry.pos.x) && (y==this.jerry.pos.y))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * getMonster : returns Monster at (x, y)
	 */
	public Monster getMonster(int x, int y) {
		for(int i=0; i<this.monsters.size(); i++) {
			if(((x == this.monsters.get(i).pos.x) && (y == this.monsters.get(i).pos.y) && !this.monsters.get(i).isDead()) || ((x==this.jerry.pos.x) && (y==this.jerry.pos.y))) {
				return this.monsters.get(i);
			}
		}
		return null;
	}
		
	/**
	 * movePlayer : move the player at (x, y), provoke the monsters and move them
	 */
	protected void movePlayer(int x, int y) {
		if(this.jerry.getEffect().apply() && this.jerry.applyOnWalkEffect()) {
			this.table[this.jerry.pos.y][this.jerry.pos.x] = this.jerry.getFloor();
			this.jerry.move(x, y);
			this.jerry.setFloor(this.table[this.jerry.pos.y][this.jerry.pos.x]);
			checkPlayerPos(this.jerry.pos.x, this.jerry.pos.y);
		} else {
			monstersAttack();
		}
		moveAllMonsters();
	}
	
	/**
	 * moveTo : move the player to direction dir, and trigger actions based on new pos
	 */
	public void moveTo(Direction dir) {
		if(fireMode) {
			if(!(table[firePoint.y+dir.getY()][firePoint.x+dir.getX()] instanceof TileWall) &&
					!(table[firePoint.y+dir.getY()][firePoint.x+dir.getX()] instanceof TileVoid)) { 
				firePoint.x+=dir.getX();
				firePoint.y+=dir.getY();
				if(getFireRadius()>jerry.getWeapon().getVal()+2) {
					firePoint.x-=dir.getX();
					firePoint.y-=dir.getY();
				}
			}
		} else if(isWalkable(this.jerry.pos.x+dir.getX(), this.jerry.pos.y+dir.getY())) {
			movePlayer(this.jerry.pos.x+dir.getX(), this.jerry.pos.y+dir.getY());
		} else if(isMonster(this.jerry.pos.x+dir.getX(), this.jerry.pos.y+dir.getY())){
			checkMonster(this.jerry.pos.x+dir.getX(), this.jerry.pos.y+dir.getY());
			moveAllMonsters();
		} else {
			playerIn(this.jerry.pos.x+dir.getX(), this.jerry.pos.y+dir.getY());
		}
	}
	
	/**
	 * placePlayerStairsUp : place the player on the stairUp
	 */
	public void placePlayerStairsUp() {
		this.jerry.placeOn(this.stairUp.x, this.stairUp.y);
	}
	
	/**
	 * placePlayerStairsDown : place the player on the stairDown
	 */
	public void placePlayerStairsDown() {
		this.jerry.placeOn(this.stairDown.x, this.stairDown.y);
	}
	
	/**
	 * moveMonster : move the monster m to the direction dir
	 */
	protected void moveMonster(Monster m, Direction dir) {
		m.pos.x+=dir.getX();
		m.pos.y+=dir.getY();
	}
	
	/**
	 * moveAllMonsters : move all monsters to them closer to the player
	 */
	protected void moveAllMonsters() {
		for(Monster m : this.monsters) {
			if(!m.isDead() && rnd.nextInt(10)>1 && isVisible(m.pos.x, m.pos.y) && m.getEffect().apply()) {
				if(this.jerry.pos.y<m.pos.y) {
					// NORTH
					if(isWalkable(m.pos.x, m.pos.y-1)) {
						moveMonster(m, Direction.North);
					} else if(this.jerry.pos.x<m.pos.x) {
						// ALT WEST
						if(isWalkable(m.pos.x-1, m.pos.y)) {
							moveMonster(m, Direction.West);
						}
					} else if(this.jerry.pos.x>m.pos.x) {
						// ALT EAST
						if(isWalkable(m.pos.x+1, m.pos.y)) {
							moveMonster(m, Direction.East);
						}
					}
				} else if(this.jerry.pos.y>m.pos.y) {
					// SOUTH
					if(isWalkable(m.pos.x, m.pos.y+1)) {
						moveMonster(m, Direction.South);
					} else if(this.jerry.pos.x<m.pos.x) {
						// ALT WEST
						if(isWalkable(m.pos.x-1, m.pos.y)) {
							moveMonster(m, Direction.West);
						}
					} else if(this.jerry.pos.x>m.pos.x) {
						// ALT EAST
						if(isWalkable(m.pos.x+1, m.pos.y)) {
							moveMonster(m, Direction.East);
						}
					}
				} else if(this.jerry.pos.x<m.pos.x) {
					// WEST
					if(isWalkable(m.pos.x-1, m.pos.y)) {
						moveMonster(m, Direction.West);
					} else if(this.jerry.pos.y<m.pos.y) {
						// ALT NORTH
						if(isWalkable(m.pos.x, m.pos.y-1)) {
							moveMonster(m, Direction.North);
						}
					} else if(this.jerry.pos.y>m.pos.y) {
						// ALT SOUTH
						if(isWalkable(m.pos.x, m.pos.y+1)) {
							moveMonster(m, Direction.South);
						}
					}
				} else if(this.jerry.pos.x>m.pos.x) {
					// EAST
					if(isWalkable(m.pos.x+1, m.pos.y)) {
						moveMonster(m, Direction.East);
					} else if(this.jerry.pos.y<m.pos.y) {
						// ALT NORTH
						if(isWalkable(m.pos.x, m.pos.y-1)) {
							moveMonster(m, Direction.North);
						}
					} else if(this.jerry.pos.y>m.pos.y) {
						// ALT SOUTH
						if(isWalkable(m.pos.x, m.pos.y+1)) {
							moveMonster(m, Direction.South);
						}
					}
				}
			}
		}
	}
	
	/**
	 * printDungeon : print all objects (rooms, items, mobs) on Tile table
	 */
	public void printDungeon() {
		fillRectangle(this.table, 0, 0, getWidth()-1, getHeight()-1, TileFactory.getInstance().createTileVoid());
		for(int i=0; i<rooms.size(); i++) {
			rooms.get(i).printOn(this.table);
		}
		if(!(this.table[this.stairDown.y][this.stairDown.x] instanceof TileVoid) && !isMonster(this.stairDown.x, this.stairDown.y) && !(this.level%5==0 && this.level>0)) {
			this.table[this.stairDown.y][this.stairDown.x] = TileFactory.getInstance().createTileStairsDown();
		}
		if(!(this.table[this.stairUp.y][this.stairUp.x] instanceof TileVoid) && !isMonster(this.stairUp.x, this.stairUp.y) && this.level>0) {
			this.table[this.stairUp.y][this.stairUp.x] = TileFactory.getInstance().createTileStairsUp();
		}
		integrateMobs();
		integrateItems();
		integratePlayer();
	}
	
	/**
	 * generateMapInfo : generate a String with the dungeon level, the room, the tile the player is on
	 */
	public String generateMapInfo() {
		String info="  ";
		if(this.dungeon.getLevel()%5==0 && this.dungeon.getLevel()>0 && this.boss!=null) {
			info+=playerIn(this.jerry.pos.x, this.jerry.pos.y).toString()+" ("+this.jerry.getFloor()+")\n  Boss Level "+this.dungeon.getLevel();
		} else {
			info+=playerIn(this.jerry.pos.x, this.jerry.pos.y).toString()+" ("+this.jerry.getFloor()+")\n  Level "+this.dungeon.getLevel();
		}
		return info;
	}
	
	/**
	 * getPrintableInfo : generate a String with a formatted dungeon level
	 */
	public String getPrintableLevelInfo() {
		String info="";
		if(this.dungeon.getLevel()%5==0 && this.dungeon.getLevel()>0 && this.boss!=null) {
			info+="Boss Level "+this.dungeon.getLevel();
		} else {
			info+="Level "+this.dungeon.getLevel();
		}
		return info;
	}
	
	/**
	 * getMobInfo : generate String with the focused mob info
	 */
	public String getMobInfo() {
		return this.focusedMonster != null ? this.focusedMonster.getPrintableMobInfo() : "";
	}
	
	/**
	 * isFireMode : check if the fireMode is on
	 */
	public boolean isFireMode() { return fireMode; }
	/**
	 * getFirePoint : getter for firePoint
	 */
	public Point getFirePoint() { return firePoint; }
	/**
	 * getPlayer : getter for player
	 */
	public Player getPlayer() {
		return this.jerry;
	}
	
	/**
	 * getLog : getter for the latest logs
	 */
	public String getLog() {
		this.log.clean();
		return this.log.getLast(4);
	}
	
	/**
	 * getFinalScreen : generate String for the death screen
	 */
	public String getFinalScreen() {
		return "  Game Over\t\n  Level "+this.level+"\t\n  Press 'r' to restart";
	}
	
	/**
	 * printOnConsole : old method to print map on console
	 */
	public void printOnConsole() {		
		for(int i=0; i<this.table.length; i++) {
			for(int j=0; j<this.table[0].length; j++) {
				System.out.print(""+this.table[i][j].getSymbol()+' ');
			}
			System.out.println();
		}
		System.out.println();
	}
}