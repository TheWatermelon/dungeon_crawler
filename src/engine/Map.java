package engine;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import rooms.*;
import tiles.*;
import game.Player;

public class Map {
	private Tile[][] table;
	private Vector<Room> rooms;
	private Point stairDown;
	private Player jerry;
	private int level;
	
	public Map(int height, int width) {
		this.table = new Tile[height][width];
		this.rooms = new Vector<Room>();
		this.level = -1;
		this.jerry = new Player(width/2, height/2);
		this.jerry.setFloor(TileFactory.getInstance().createTileStone()); 
		this.stairDown = new Point();
	}
	
	public Tile[][] getTable() { return this.table; }
	
	public int getHeight() { return this.table.length; }
	public int getWidth() { return this.table[0].length; }
	
	public void fillRectangle(Tile[][] table, int x1, int y1, int x2, int y2, Tile t) {
		if(x1 < 0 || y1 < 0 || x2 > table.length-1 || y2 > table[0].length-1) {
			return;
		}
		
		for(int i=x1; i<=x2; i++) {
			for(int j=y1; j<=y2; j++) {
				table[i][j] = t;
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
	
	public void generateDungeon() {
		Random rnd = new Random();
		Room room;
		int roomIndex, wallSide, height, width, mid;
		
		this.jerry.pos.x = getWidth()/2;
		this.jerry.pos.y = getHeight()/2;
		
		this.level++;
		
		this.rooms = new Vector<Room>();

		fillRectangle(this.table, 0, 0, getWidth()-1, getHeight()-1, TileFactory.getInstance().createTileVoid());	
		
		// Premiere salle, au centre du niveau
		height = rnd.nextInt(5)+9;
		width = rnd.nextInt(5)+6;
		rooms.add(new RectangleRoom((this.table[0].length/2)-(width/2), (this.table.length/2)-(height/2), (this.table[0].length/2)+(width/2), (this.table.length/2)+(height/2), "Main Room"));
		rooms.get(rooms.size()-1).show();
		rooms.get(rooms.size()-1).printOn(this.table);
		
		for(int i=0; i<100; i++) {
			roomIndex = rnd.nextInt(rooms.size());
			room = this.rooms.get(roomIndex);
			
			//if(rnd.nextInt(2)==0) {
			if(room instanceof Corridor) {
				// SALLE
				height = rnd.nextInt(5)+9;
				width = rnd.nextInt(5)+6;
				
				// De quel cote on etend le donjon
				wallSide = rnd.nextInt(4);
				if(wallSide == 0) {
					// NORTH
					mid = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
					if(checkOn(mid-(width/2), room.p1.y-height, mid+(width/2), room.p1.y)) {
						rooms.add(new RectangleRoom(mid-(width/2), room.p1.y-height, mid+(width/2), room.p1.y, "Room"+i));
						// porte pour la premiere salle
						room.addDoor(new Point(mid, room.p1.y));
						// porte pour la deuxieme salle
						rooms.get(rooms.size()-1).addDoor(new Point(mid, room.p1.y));
						
					}
				} else if(wallSide == 1) {
					// SOUTH
					mid = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
					if(checkOn(mid-(width/2), room.p2.y, mid+(width/2), room.p2.y+height)) {
						rooms.add(new RectangleRoom(mid-(width/2), room.p2.y, mid+(width/2), room.p2.y+height, "Room"+i));
						// porte pour la premiere salle
						room.addDoor(new Point(mid, room.p2.y));
						// porte pour la deuxieme salle
						rooms.get(rooms.size()-1).addDoor(new Point(mid, room.p2.y));
					}
				} else if(wallSide == 2) {
					// EAST
					mid = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
					if(checkOn(room.p2.x, mid-(height/2), room.p2.x+width, mid+(height/2))) {
						rooms.add(new RectangleRoom(room.p2.x, mid-(height/2), room.p2.x+width, mid+(height/2), "Room"+i));
						// porte pour la premiere salle
						room.addDoor(new Point(room.p2.x, mid));
						// porte pour la deuxieme salle
						rooms.get(rooms.size()-1).addDoor(new Point(room.p2.x, mid));
					}
				} else if(wallSide == 3) {
					// WEST
					mid = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
					if(checkOn(room.p1.x-width, mid-(height/2), room.p1.x, mid+(height/2))) {
						rooms.add(new RectangleRoom(room.p1.x-width, mid-(height/2), room.p1.x, mid+(height/2), "Room"+i));
						// porte pour la premiere salle
						room.addDoor(new Point(room.p1.x, mid));
						// porte pour la deuxieme salle
						rooms.get(rooms.size()-1).addDoor(new Point(room.p1.x, mid));
					}
				}
			} else {
				// COULOIR
				
				// De quel cote on etend le donjon
				wallSide = rnd.nextInt(4);
				if(wallSide == 0) {
					// NORTH
					height = rnd.nextInt(4)+5;
					width = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
					if(checkOn(width-1, room.p1.y-height, width+1, room.p1.y)) {
						rooms.add(new Corridor(width-1, room.p1.y-height, width+1, room.p1.y, "CorridorNorth"+i));
						// porte pour la premiere salle
						room.addDoor(new Point(width, room.p1.y));
						// porte pour la deuxieme salle
						rooms.get(rooms.size()-1).addDoor(new Point(width, room.p1.y));
					}
				} else if(wallSide == 1) {
					// SOUTH
					height = rnd.nextInt(4)+5;
					width = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
					if(checkOn(width-1, room.p2.y, width+1, room.p2.y+height)) {
						rooms.add(new Corridor(width-1, room.p2.y, width+1, room.p2.y+height, "CorridorSouth"+i));
						// porte pour la premiere salle
						room.addDoor(new Point(width, room.p2.y));
						// porte pour la deuxieme salle
						rooms.get(rooms.size()-1).addDoor(new Point(width, room.p2.y));
					}
				} else if(wallSide == 2) {
					// EAST
					width = rnd.nextInt(4)+5;
					height = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
					if(checkOn(room.p2.x, height-1, room.p2.x+width, height+1)) {
						rooms.add(new Corridor(room.p2.x, height-1, room.p2.x+width, height+1, "CorridorEast"+i));
						// porte pour la premiere salle
						room.addDoor(new Point(room.p2.x, height));
						// porte pour la deuxieme salle
						rooms.get(rooms.size()-1).addDoor(new Point(room.p2.x, height));
					}
				} else if(wallSide == 3) {
					// WEST
					width = rnd.nextInt(4)+5;
					height = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
					if(checkOn(room.p1.x-width, height-1, room.p1.x, height+1)) {
						rooms.add(new Corridor(room.p1.x-width, height-1, room.p1.x, height+1, "CorridorWest"+i));
						// porte pour la premiere salle
						room.addDoor(new Point(room.p1.x, height));
						// porte pour la deuxieme salle
						rooms.get(rooms.size()-1).addDoor(new Point(room.p1.x, height));
					}
				}
			}
			printDungeon();
		}
		integratePlayer();
		placeStairs();
	}
	
	private void placeStairs() {
		Vector<Room> roomsIndex = new Vector<Room>();
		Room selectedRoom;
		Random rnd = new Random();
		int height, width;
		
		// Recupere les salles
		for(int i=0; i<this.rooms.size(); i++) {
			if(this.rooms.get(i) instanceof RectangleRoom) {
				roomsIndex.add(this.rooms.get(i));
			}
		}
		// choisit une salle
		selectedRoom = roomsIndex.get(rnd.nextInt(roomsIndex.size()));
		// choisit un point dans la salle
		height = rnd.nextInt(selectedRoom.getHeight()-1)+1+selectedRoom.p1.y;
		width = rnd.nextInt(selectedRoom.getWidth()-1)+1+selectedRoom.p1.x;
		
		// placement de l'escalier
		this.stairDown = new Point(width, height);
	}
	
	private void integratePlayer() {
		this.table[this.jerry.pos.y][this.jerry.pos.x] = TileFactory.getInstance().createTilePlayer();
	}
	
	private String playerStepOn() {
		String roomName=this.jerry.getFloor().toString();
		
		for(int i=0; i<this.rooms.size(); i++) {
			if(this.jerry.pos.x >= this.rooms.get(i).p1.x && this.jerry.pos.x <= this.rooms.get(i).p2.x
					&& this.jerry.pos.y >= this.rooms.get(i).p1.y && this.jerry.pos.y <= this.rooms.get(i).p2.y) {
				roomName = this.rooms.get(i).toString();
				this.rooms.get(i).show();
			}
		}
		return roomName;
	}
	
	private boolean isStairs(int x, int y) {
		if(this.table[y][x] instanceof TileStairsDown) {
			return true;
		}
		return false;
	}
	
	public void movePlayerUp() {
		if((this.table[this.jerry.pos.y-1][this.jerry.pos.x].isWalkable())) {
			this.table[this.jerry.pos.y][this.jerry.pos.x] = this.jerry.getFloor();
			this.jerry.pos.y--;
			//this.jerry.setFloor(this.table[this.jerry.pos.y][this.jerry.pos.x]);
			if(isStairs(this.jerry.pos.x, this.jerry.pos.y)) generateDungeon();
			integratePlayer();
		}
	}
	
	public void movePlayerDown() {
		if((this.table[this.jerry.pos.y+1][this.jerry.pos.x].isWalkable())) {
			this.table[this.jerry.pos.y][this.jerry.pos.x] = this.jerry.getFloor();
			this.jerry.pos.y++;
			//this.jerry.setFloor(this.table[this.jerry.pos.y][this.jerry.pos.x]);
			if(isStairs(this.jerry.pos.x, this.jerry.pos.y)) generateDungeon();
			integratePlayer();
		}
	}
	
	public void movePlayerLeft() {
		if((this.table[this.jerry.pos.y][this.jerry.pos.x-1].isWalkable())) {
			this.table[this.jerry.pos.y][this.jerry.pos.x] = this.jerry.getFloor();
			this.jerry.pos.x--;
			//this.jerry.setFloor(this.table[this.jerry.pos.y][this.jerry.pos.x]);
			if(isStairs(this.jerry.pos.x, this.jerry.pos.y)) generateDungeon();
			integratePlayer();
		}
	}
	
	public void movePlayerRight() {
		if((this.table[this.jerry.pos.y][this.jerry.pos.x+1].isWalkable())) {
			this.table[this.jerry.pos.y][this.jerry.pos.x] = this.jerry.getFloor();
			this.jerry.pos.x++;
			//this.jerry.setFloor(this.table[this.jerry.pos.y][this.jerry.pos.x]);
			if(isStairs(this.jerry.pos.x, this.jerry.pos.y)) generateDungeon();
			integratePlayer();
		}
	}
	
	public void printDungeon() {
		fillRectangle(this.table, 0, 0, getWidth()-1, getHeight()-1, TileFactory.getInstance().createTileVoid());
		playerStepOn();
		for(int i=0; i<rooms.size(); i++) {
			rooms.get(i).printOn(this.table);
		}
		integratePlayer();
		this.table[this.stairDown.y][this.stairDown.x] = TileFactory.getInstance().createTileStairsDown();
	}
	
	public String generateLabel() {
		return ""+playerStepOn()+" | "+this.jerry.getFloor()+" | level "+this.level;
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
		Window win = new Window("Dungeon Generator", this);
		
		win.setLabel(playerStepOn()+" | "+this.jerry.getFloor());
		
		win.refresh();
	}
}
