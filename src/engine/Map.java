package engine;

import java.awt.*;
import java.util.*;

import objects.item.*;
import objects.looker.LookerFactory;
import objects.mob.*;
import rooms.*;
import tiles.*;

public class Map {
	protected Tile[][] table;
	protected Vector<Room> rooms;
	protected Vector<Monster> monsters;
	protected Vector<Item> items;
	protected MessageLog log;
	protected Point stairUp;
	protected Point stairDown;
	protected Player jerry;
	protected Boss boss;
	protected int level;
	protected Dungeon dungeon;
	protected int width;
	protected int height;
	protected Random rnd;
	public String oldString;
	
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
	
	public Tile[][] getTable() { return this.table; }
	public int getHeight() { return this.height; }
	public int getWidth() { return this.width; }
	public Vector<Monster> getMonsters() { return monsters; }
	
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
	
	public boolean isVisible(int x, int y) {
		for(int i=0; i<rooms.size(); i++) {
			if(x>rooms.get(i).p1.x && x<rooms.get(i).p2.x
					&& y>rooms.get(i).p1.y && y<rooms.get(i).p2.y) {
				return this.rooms.get(i).isVisible();
			}
		}
		
		return true;
	}
	
	public boolean isWalkable(int x, int y) {
		return this.table[y][x].isWalkable() && !isMonster(x, y);
	}
	
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

	private void generateRectangleRoom(Room room) {
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
	
	private void generateCorridor(Room room, boolean junction) {
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
	
	private void generateItems() {
		for(int i=0; i<rooms.size(); i++) {
			rooms.get(i).parsingFloor(items);
		}
	}
	
	private void generateMonsters() {
		Room room;
		int roomNumber, roomIndex, monsterNumber, height, width, monsterName;
		
		roomNumber = (rnd.nextInt(rooms.size())+1)/2;
		// Monsters
		for(int i=0; i<roomNumber; i++) {
			do {
				roomIndex = rnd.nextInt(rooms.size()-1)+1;
				room = this.rooms.get(roomIndex);
			} while (room instanceof Corridor);
			
			monsterNumber = (rnd.nextInt((room.getHeight()-1)*(room.getWidth()-1))+1)/3;
			for(int j=0; j<monsterNumber; j++) {
				do {
					width = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
					height = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
				} while (((width==this.jerry.pos.x) && (height==this.jerry.pos.y)) && !isMonster(width, height));

				monsterName = rnd.nextInt(26);
				this.monsters.add(new Monster(width, height, Ressources.getLetterAt(monsterName), Ressources.getNameAt(monsterName), this.log));
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
			boss = new Boss(width, height, this.level/5, Ressources.getCapitalLetterAt(monsterName), Ressources.getCapitalNameAt(monsterName), this.log);
			monsters.add(boss);
		}
	}
	
	private void placeStairs() {
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
	
	private void integratePlayer() {
		this.table[this.jerry.pos.y][this.jerry.pos.x] = TileFactory.getInstance().createTilePlayer();
	}
	
	private void integrateMobs() {
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
	
	private void integrateItems() {
		for(int i=0; i<items.size(); i++) {
			if(!isMonster(items.get(i).pos.x, items.get(i).pos.y) && !(this.table[items.get(i).pos.y][items.get(i).pos.x] instanceof TileVoid) && 
					!(this.table[items.get(i).pos.y][items.get(i).pos.x] instanceof TileStairsDown) &&
					!(this.table[items.get(i).pos.y][items.get(i).pos.x] instanceof TileStairsUp) &&
					!(this.table[items.get(i).pos.y][items.get(i).pos.x] instanceof TileFountain)) {
				this.table[items.get(i).pos.y][items.get(i).pos.x] = items.get(i).getTile();
			}
		}
	}
	
	private int playerIn(int x, int y) {
		int roomIndex=-1;
		
		for(int i=0; i<this.rooms.size(); i++) {
			if(x >= this.rooms.get(i).p1.x && x <= this.rooms.get(i).p2.x 
					&& y >= this.rooms.get(i).p1.y && y <= this.rooms.get(i).p2.y) {
				roomIndex=i;
				this.rooms.get(i).isDoor(x, y);
				checkItem(x, y);
			}
		}
		return roomIndex;
	}  
	
	public boolean isPlayerDead() { return this.jerry.isDead(); }
	
	private boolean isStairsUp(int x, int y) {
		return this.table[y][x] instanceof TileStairsUp;
	}
	
	private boolean isStairsDown(int x, int y) {
		return this.table[y][x] instanceof TileStairsDown;
	}
	
	private Item isItem(int x, int y) {
		for(int i=items.size()-1; i>=0; i--) {
			if(x==items.get(i).pos.x && y==items.get(i).pos.y) {
				return items.get(i);
			}
		}
		return null;
	}
	
	private void removeItem(int x, int y) {
		for(int i=0; i<items.size(); i++) {
			if(x==items.get(i).pos.x && y==items.get(i).pos.y) {
				items.remove(i);
			}
		}
	}
	
	private void checkItem(int x, int y) {
		Item i = isItem(x, y);
		
		if(i == null) { return; }
		 
		if(i instanceof Gold) {
			this.jerry.addGold(i.getVal());
			removeItem(x, y);
			this.jerry.setLooker(LookerFactory.getInstance().createLookerGold(this.jerry.pos.x, this.jerry.pos.y));
		}
		if(i instanceof Fountain) {
			if(!jerry.isFullHealth()) {
				if(i.getVal()-1>=0) {
					this.jerry.cure();
					this.jerry.setLooker(LookerFactory.getInstance().createLookerHealth(x, y));
					// Player has used the fountain
					i.setVal(i.getVal()-1);
				}
				if(i.getVal()==0){
					log.appendMessage("The fountain disappear...");
					removeItem(x, y);
				}
			}
		}  else if(i instanceof Barrel) {
			int content=((Barrel)i).open();
			this.jerry.setLooker(LookerFactory.getInstance().createLookerBarrel(this.jerry.pos.x, this.jerry.pos.y));
			removeItem(x, y);
			if(content == 0) {
				if(rnd.nextInt(2)==0) {
					log.appendMessage("Open Barrel... Gold!");
					items.add(new Gold(x, y));
				} else {
					if(rnd.nextInt(2)==0) {
						log.appendMessage("Open Barrel... Weapon!");
						items.add(new Weapon(x, y));
					} else {
						log.appendMessage("Open Barrel... Shield!");
						items.add(new Shield(x, y));
					}
				}
			} /*else if(content == 1) {
				log.appendMessage("Open Barrel... Potion!");
				items.add(new Potion(x, y));
			}*/ else if(content == 1) {
				log.appendMessage("Open Barrel... Boom!");
				this.jerry.harm(25);
			} else {
				log.appendMessage("Open Barrel... Nothing!");
			}
		}
	}
	
	public void checkPickableItem(int x, int y) {
		Item i = isItem(x, y);
		
		if(i == null) { return; }
		/*
		if(i instanceof Potion) {
			if(i.getVal()>=0) {
				this.log.appendMessage("Drink Potion... Fast effect "+i.getVal()+"!");
				this.jerry.setPotionEffect(i.getVal());
			} else {
				this.log.appendMessage("Drink Potion... Slow effect "+(i.getVal()*-1)+"!");
				this.jerry.setPotionEffect(i.getVal());
			}
			this.jerry.setLooker(LookerFactory.getInstance().createLookerPotion(x, y, i.getVal()));
		}
		*/
		if(i instanceof Weapon) {
			this.log.appendMessage("Took "+((Weapon)i));
			this.jerry.setWeapon((Weapon)i);
			this.jerry.setLooker(LookerFactory.getInstance().createLookerEquip(this.jerry.pos.x, this.jerry.pos.y));
		} else if(i instanceof Shield) {
			this.log.appendMessage("Took "+((Shield)i));
			this.jerry.setShield(((Shield)i));
			this.jerry.setLooker(LookerFactory.getInstance().createLookerEquip(this.jerry.pos.x, this.jerry.pos.y));
		}
		
		removeItem(x, y);
		this.jerry.setFloor(TileFactory.getInstance().createTileStone());
	}
	
	private void checkPlayerPos(int x, int y) {
		if(isStairsUp(x, y)) dungeon.levelUp();
		else if(isStairsDown(x, y)) {
			// Patch when the boss is the stairs
			if(this.dungeon.getLevel()%5==0 && this.dungeon.getLevel()>0) {
				this.stairDown=new Point(x, y);
			}
			dungeon.levelDown();
		}
		playerIn(x, y);
	}
	
	private void checkMonster(int x, int y) {
		boolean monsterKilled;
		String battleLog="";
		for(int i=0; i<this.monsters.size(); i++) {
			// The player attacks the monster he is facing
			if(((x == this.monsters.get(i).pos.x) && (y == this.monsters.get(i).pos.y) && !this.monsters.get(i).isDead())) {
				monsterKilled=this.jerry.fight(this.monsters.get(i));
				if(monsterKilled && !(monsters.get(i) instanceof Boss)) {
					Random rnd = new Random();
					if(rnd.nextInt(4)==0) {
						this.items.add(new Gold(monsters.get(i).pos.x, monsters.get(i).pos.y, 5+this.level));
					}
				}
			}
			// Check if another monster is facing the player
			String tmp=monsterAttack(monsters.get(i));
			if(!tmp.equals("")) { battleLog+=(!battleLog.equals(""))?(", "+tmp):tmp; }
		}
		if(!battleLog.equals("")) { log.appendMessage(battleLog); }
	}
	
	private String monsterAttack(Monster m) {	
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
					jerry.murder();
					return battleLog;
				}
				break;
			}
		}
		return battleLog;
	}
	
	private boolean isMonster(int x, int y) {
		for(int i=0; i<this.monsters.size(); i++) {
			if(((x == this.monsters.get(i).pos.x) && (y == this.monsters.get(i).pos.y) && !this.monsters.get(i).isDead()) || ((x==this.jerry.pos.x) && (y==this.jerry.pos.y))) {
				return true;
			}
		}
		return false;
	}
		
	private void movePlayer(int x, int y) {
		if(this.jerry.getEffect().apply() && this.jerry.applyOnWalkEffect()) {
			this.table[this.jerry.pos.y][this.jerry.pos.x] = this.jerry.getFloor();
			this.jerry.move(x,  y);
			this.jerry.setFloor(this.table[this.jerry.pos.y][this.jerry.pos.x]);
			checkPlayerPos(this.jerry.pos.x, this.jerry.pos.y);
		}
		moveAllMonsters();
	}
	
	public void movePlayerUp() {
		if(isWalkable(this.jerry.pos.x, this.jerry.pos.y-1)) {
			movePlayer(this.jerry.pos.x, this.jerry.pos.y-1);
		} else if(isMonster(this.jerry.pos.x, this.jerry.pos.y-1)){
			checkMonster(this.jerry.pos.x, this.jerry.pos.y-1);
		} else {
			playerIn(this.jerry.pos.x, this.jerry.pos.y-1);
		}
	}
	
	public void movePlayerDown() {
		if(isWalkable(this.jerry.pos.x, this.jerry.pos.y+1)) {
			movePlayer(this.jerry.pos.x, this.jerry.pos.y+1);
		} else if(isMonster(this.jerry.pos.x, this.jerry.pos.y+1)){
			checkMonster(this.jerry.pos.x, this.jerry.pos.y+1);
		} else {
			playerIn(this.jerry.pos.x, this.jerry.pos.y+1);
		}
	}
	
	public void movePlayerLeft() {
		if(isWalkable(this.jerry.pos.x-1, this.jerry.pos.y)) {
			movePlayer(this.jerry.pos.x-1, this.jerry.pos.y);
		} else if(isMonster(this.jerry.pos.x-1, this.jerry.pos.y)){
			checkMonster(this.jerry.pos.x-1, this.jerry.pos.y);
		} else {
			playerIn(this.jerry.pos.x-1, this.jerry.pos.y);
		}
	}
	
	public void movePlayerRight() {
		if(isWalkable(this.jerry.pos.x+1, this.jerry.pos.y)) {
			movePlayer(this.jerry.pos.x+1, this.jerry.pos.y);
		} else if(isMonster(this.jerry.pos.x+1, this.jerry.pos.y)){
			checkMonster(this.jerry.pos.x+1, this.jerry.pos.y);
		} else {
			playerIn(this.jerry.pos.x+1, this.jerry.pos.y);
		}
	}
	
	public void placePlayerStairsUp() {
		this.jerry.placeOn(this.stairUp.x, this.stairUp.y);
	}
	
	public void placePlayerStairsDown() {
		this.jerry.placeOn(this.stairDown.x, this.stairDown.y);
	}
	
	private void moveMonster(Monster m, int x, int y) {
		m.pos.x = x;
		m.pos.y = y;
		//m.getLooker().hide();
	}
	
	private void moveAllMonsters() {
		for(Monster m : this.monsters) {
			if(!m.isDead() && rnd.nextInt(10)>1 && isVisible(m.pos.x, m.pos.y) && m.getEffect().apply()) {
				if(this.jerry.pos.y<m.pos.y) {
					// NORTH
					if(isWalkable(m.pos.x, m.pos.y-1)) {
						moveMonster(m, m.pos.x, m.pos.y-1);
					} else if(this.jerry.pos.x<m.pos.x) {
						// ALT WEST
						if(isWalkable(m.pos.x-1, m.pos.y)) {
							moveMonster(m, m.pos.x-1, m.pos.y);
						}
					} else if(this.jerry.pos.x>m.pos.x) {
						// ALT EAST
						if(isWalkable(m.pos.x+1, m.pos.y)) {
							moveMonster(m, m.pos.x+1, m.pos.y);
						}
					}
				} else if(this.jerry.pos.y>m.pos.y) {
					// SOUTH
					if(isWalkable(m.pos.x, m.pos.y+1)) {
						moveMonster(m, m.pos.x, m.pos.y+1);
					} else if(this.jerry.pos.x<m.pos.x) {
						// ALT WEST
						if(isWalkable(m.pos.x-1, m.pos.y)) {
							moveMonster(m, m.pos.x-1, m.pos.y);
						}
					} else if(this.jerry.pos.x>m.pos.x) {
						// ALT EAST
						if(isWalkable(m.pos.x+1, m.pos.y)) {
							moveMonster(m, m.pos.x+1, m.pos.y);
						}
					}
				} else if(this.jerry.pos.x<m.pos.x) {
					// WEST
					if(isWalkable(m.pos.x-1, m.pos.y)) {
						moveMonster(m, m.pos.x-1, m.pos.y);
					} else if(this.jerry.pos.y<m.pos.y) {
						// ALT NORTH
						if(isWalkable(m.pos.x, m.pos.y-1)) {
							moveMonster(m, m.pos.x, m.pos.y-1);
						}
					} else if(this.jerry.pos.y>m.pos.y) {
						// ALT SOUTH
						if(isWalkable(m.pos.x, m.pos.y+1)) {
							moveMonster(m, m.pos.x, m.pos.y+1);
						}
					}
				} else if(this.jerry.pos.x>m.pos.x) {
					// EAST
					if(isWalkable(m.pos.x+1, m.pos.y)) {
						moveMonster(m, m.pos.x+1, m.pos.y);
					} else if(this.jerry.pos.y<m.pos.y) {
						// ALT NORTH
						if(isWalkable(m.pos.x, m.pos.y-1)) {
							moveMonster(m, m.pos.x, m.pos.y-1);
						}
					} else if(this.jerry.pos.y>m.pos.y) {
						// ALT SOUTH
						if(isWalkable(m.pos.x, m.pos.y+1)) {
							moveMonster(m, m.pos.x, m.pos.y+1);
						}
					}
				}
			}
		}
	}
	
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
	
	public String generateMapInfo() {
		String info="";
		if(this.dungeon.getLevel()%5==0 && this.dungeon.getLevel()>0 && this.boss!=null) {
			info="  "+rooms.get(playerIn(this.jerry.pos.x, this.jerry.pos.y)).toString()+" ("+this.jerry.getFloor()+") \t\n  Level "+this.dungeon.getLevel()+" Boss "+this.boss.drawHealthBar()+"\t";
		} else {
			info="  "+rooms.get(playerIn(this.jerry.pos.x, this.jerry.pos.y)).toString()+" ("+this.jerry.getFloor()+") \t\n  Level "+this.level+"\t";
		}
		return info;
	}
	
	public Player getPlayer() {
		return this.jerry;
	}
	
	public String getLog() {
		this.log.clean();
		return this.log.getLast(3);
	}
	
	public String getFinalScreen() {
		return "  Game Over\t\n  Level "+this.level+"\t";
	}
	
	public void printOnConsole() {		
		for(int i=0; i<this.table.length; i++) {
			for(int j=0; j<this.table[0].length; j++) {
				System.out.print(""+this.table[i][j].getSymbol()+' ');
			}
			System.out.println();
		}
		System.out.flush();
		for(int i=0; i<this.rooms.size(); i++) {
			System.out.print(this.rooms.get(i).toString()+"; ");
		}
		System.out.println();
	}
}
