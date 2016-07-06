package engine;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import objects.Gold;
import objects.Item;
import objects.Monster;
import objects.Player;
import objects.Shield;
import objects.Weapon;
import rooms.*;
import tiles.Tile;
import tiles.TileDoor;
import tiles.TileFactory;
import tiles.TileItem;
import tiles.TileStairsDown;
import tiles.TileVoid;

public class Map {
	private Tile[][] table;
	private Vector<Room> rooms;
	private Vector<Monster> monsters;
	private Vector<Item> items;
	private MessageLog log;
	private Point stairDown;
	private Player jerry;
	private int level;
	private Window win;
	
	public Map(int height, int width) {
		this.table = new Tile[height][width];
		this.rooms = new Vector<Room>();
		this.monsters = new Vector<Monster>();
		this.items = new Vector<Item>();
		this.level = 0;
		this.stairDown = new Point();
		this.log = new MessageLog();
		this.jerry = new Player(width/2, height/2, log);
		this.jerry.setFloor(TileFactory.getInstance().createTileStone()); 
	}
	
	public Tile[][] getTable() { return this.table; }
	
	public int getHeight() { return this.table.length; }
	public int getWidth() { return this.table[0].length; }
	
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
	
	public boolean isWalkable(int x, int y) {
		return this.table[y][x].isWalkable() && !isMonster(x, y);
	}
	
	public void generateDungeon() {
		Random rnd = new Random();
		Room room;
		int roomIndex, height, width;
		
		// Position joueur
		this.jerry.placeOn(getWidth()/2, getHeight()/2);
		
		// Remise a zero de la carte
		this.rooms = new Vector<Room>();
		this.monsters = new Vector<Monster>();
		this.items = new Vector<Item>();
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
		placeStairs();
	}

	private void generateRectangleRoom(Room room) {
		Random rnd = new Random();
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
		Random rnd = new Random();
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
		Item item;
		
		for(int i=0; i<rooms.size(); i++) {
			item = rooms.get(i).parsingFloor();
			if(item != null) {
				items.add(item);
			}
		}
	}
	
	private void generateMonsters() {
		Random rnd = new Random();
		Room room;
		int roomNumber, roomIndex, monsterNumber, height, width, monsterName;
		
		roomNumber = (rnd.nextInt(rooms.size())+1)/2;
		
		for(int i=0; i<roomNumber; i++) {
			do {
				roomIndex = rnd.nextInt(rooms.size()-1)+1;
				room = this.rooms.get(roomIndex);
			} while (room instanceof Corridor);
			
			monsterNumber = rnd.nextInt(((room.getHeight()-1)/2)*((room.getWidth()-1)/2))+1;
			for(int j=0; j<monsterNumber; j++) {
				do {
					width = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
					height = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
				} while (((width==this.jerry.pos.x) && (height==this.jerry.pos.y)) && !isMonster(width, height));
				
				monsterName = rnd.nextInt(26);
				this.monsters.add(new Monster(width, height, Ressources.getLetterAt(monsterName), Ressources.getNameAt(monsterName)));
			}
		}
	}
	
	private void placeStairs() {
		Room selectedRoom;
		Random rnd = new Random();
		int height, width;
		
		do {
			// choisit une salle
			selectedRoom = rooms.get(rnd.nextInt(rooms.size()));
			// choisit un point dans la salle
			height = rnd.nextInt(selectedRoom.getHeight()-1)+1+selectedRoom.p1.y;
			width = rnd.nextInt(selectedRoom.getWidth()-1)+1+selectedRoom.p1.x;
		} while(selectedRoom instanceof Corridor);
		
		// placement de l'escalier
		this.stairDown = new Point(width, height);
	}
	
	private void integratePlayer() {
		this.table[this.jerry.pos.y][this.jerry.pos.x] = TileFactory.getInstance().createTilePlayer();
	}
	
	private void integrateMobs() {
		// Corpses
		for(int i=0; i<this.monsters.size(); i++) {
			this.monsters.get(i).setFloor(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x]);
			if(!(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] instanceof TileVoid)) {
				if(this.monsters.get(i).isDead() && !(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] instanceof TileItem) && !(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] instanceof TileStairsDown)) {
					this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] = TileFactory.getInstance().createTileCorpse();
				}
			}
		}
		// Monsters
		for(int i=0; i<this.monsters.size(); i++) {
			this.monsters.get(i).setFloor(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x]);
			if(!(this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] instanceof TileVoid || this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] instanceof TileStairsDown)) {
				if(!this.monsters.get(i).isDead()) {
					this.table[this.monsters.get(i).pos.y][this.monsters.get(i).pos.x] = TileFactory.getInstance().createTileMonster(this.monsters.get(i).getSymbol());
				}
			}
		}
		// Player
		integratePlayer();
	}
	
	private void integrateItems() {
		for(int i=0; i<items.size(); i++) {
			if(!isMonster(items.get(i).pos.x, items.get(i).pos.y) && !(this.table[items.get(i).pos.y][items.get(i).pos.x] instanceof TileVoid)) {
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
	
	private boolean isStairs(int x, int y) {
		if(this.table[y][x] instanceof TileStairsDown) {
			return true;
		}
		return false;
	}
	
	private Item isItem(int x, int y) {
		for(int i=0; i<items.size(); i++) {
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
			this.jerry.addGold(((Gold)i).getVal());
			removeItem(x, y);
		} else if(i instanceof Weapon) {
			this.log.appendMessage("Found "+((Weapon)i));
			this.jerry.setWeapon((Weapon)i);
			removeItem(x, y);
		} else if(i instanceof Shield) {
			this.log.appendMessage("Found "+((Shield)i));
			this.jerry.setShield(((Shield)i));
			removeItem(x, y);
		}
	}
	
	private void levelUp() {
		this.log.appendMessage("Going down...");
		this.level++;
		this.win.pickTheme();
		generateDungeon();
	}
	
	public void newGame() {
		this.level = 0;
		this.jerry.reset();
		generateDungeon();
		this.log.clear();
		this.win.dispose();
		printOnWindow();
	}
	
	private void checkPlayerPos(int x, int y) {
		if(isStairs(x, y)) levelUp();
		playerIn(x, y);
	}
	
	private void checkMonster(int x, int y) {
		for(int i=0; i<this.monsters.size(); i++) {
			if(((x == this.monsters.get(i).pos.x) && (y == this.monsters.get(i).pos.y) && !this.monsters.get(i).isDead())) {
				this.jerry.fight(this.monsters.get(i));
			}
		}
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
		this.table[this.jerry.pos.y][this.jerry.pos.x] = this.jerry.getFloor();
		this.jerry.pos.x = x;
		this.jerry.pos.y = y;
		this.jerry.setFloor(this.table[this.jerry.pos.y][this.jerry.pos.x]);
		this.jerry.restoreHealth();
		checkPlayerPos(this.jerry.pos.x, this.jerry.pos.y);
		moveAllMonsters();
	}
	
	public void movePlayerUp() {
		if(isWalkable(this.jerry.pos.x, this.jerry.pos.y-1)) {
			movePlayer(this.jerry.pos.x, this.jerry.pos.y-1);
		} else if(isMonster(this.jerry.pos.x, this.jerry.pos.y-1)){
			checkMonster(this.jerry.pos.x, this.jerry.pos.y-1);
		} else if(this.table[this.jerry.pos.y-1][this.jerry.pos.x] instanceof TileDoor) {
			playerIn(this.jerry.pos.x, this.jerry.pos.y-1);
		}
	}
	
	public void movePlayerDown() {
		if(isWalkable(this.jerry.pos.x, this.jerry.pos.y+1)) {
			movePlayer(this.jerry.pos.x, this.jerry.pos.y+1);
		} else if(isMonster(this.jerry.pos.x, this.jerry.pos.y+1)){
			checkMonster(this.jerry.pos.x, this.jerry.pos.y+1);
		} else if(this.table[this.jerry.pos.y+1][this.jerry.pos.x] instanceof TileDoor) {
			playerIn(this.jerry.pos.x, this.jerry.pos.y+1);
		}
	}
	
	public void movePlayerLeft() {
		if(isWalkable(this.jerry.pos.x-1, this.jerry.pos.y)) {
			movePlayer(this.jerry.pos.x-1, this.jerry.pos.y);
		} else if(isMonster(this.jerry.pos.x-1, this.jerry.pos.y)){
			checkMonster(this.jerry.pos.x-1, this.jerry.pos.y);
		} else if(this.table[this.jerry.pos.y][this.jerry.pos.x-1] instanceof TileDoor) {
			playerIn(this.jerry.pos.x-1, this.jerry.pos.y);
		}
	}
	
	public void movePlayerRight() {
		if(isWalkable(this.jerry.pos.x+1, this.jerry.pos.y)) {
			movePlayer(this.jerry.pos.x+1, this.jerry.pos.y);
		} else if(isMonster(this.jerry.pos.x+1, this.jerry.pos.y)){
			checkMonster(this.jerry.pos.x+1, this.jerry.pos.y);
		} else if(this.table[this.jerry.pos.y][this.jerry.pos.x+1] instanceof TileDoor) {
			playerIn(this.jerry.pos.x+1, this.jerry.pos.y);
		}
	}
	
	private void moveMonster(Monster m, int x, int y) {
		m.pos.x = x;
		m.pos.y = y;
		//m.setFloor(this.table[m.pos.y][m.pos.x]);
	}
	
	private void moveAllMonsters() {
		Random rnd = new Random();
		Monster m;
		
		for(int i=0; i<this.monsters.size(); i++) {
			m=this.monsters.get(i);
			if(!m.isDead() && rnd.nextInt(10)>0) {
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
		//playerStepOn();
		for(int i=0; i<rooms.size(); i++) {
			rooms.get(i).printOn(this.table);
		}
		if(!(this.table[this.stairDown.y][this.stairDown.x] instanceof TileVoid)) {
			this.table[this.stairDown.y][this.stairDown.x] = TileFactory.getInstance().createTileStairsDown();
		}
		integrateMobs();
		integrateItems();
	}
	
	public String generateMapInfo() {
		//return ""+playerStepOn()+" ("+this.jerry.getFloor()+") | \t level "+this.level;
		return "  "+rooms.get(playerIn(this.jerry.pos.x, this.jerry.pos.y)).toString()+" ("+this.jerry.getFloor()+") \t\n  Level "+this.level+"\t";
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
	
	public void printOnWindow() {
		this.win = new Window("Dungeon Crawler", this);

		this.win.setLabel(generateMapInfo(), getPlayer().getAllInfo(), getLog());
		//this.win.setLabel(generateMapInfo(), this.jerry.getInfo(), getLog(), this.jerry.getWeaponInfo());
		
		this.win.firstPrint();
	}
}
