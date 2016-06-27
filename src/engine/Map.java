package engine;

import java.util.Random;
import java.util.Vector;

import rooms.*;
import tiles.*;
import game.Player;

public class Map {
	private Tile[][] table;
	private Vector<Room> rooms;
	private Player jerry;
	
	public Map(int height, int width) {
		table = new Tile[height][width];
		rooms = new Vector<Room>();
		jerry = new Player(width/2, height/2);
		
		fillRectangle(0, 0, width-1, height-1, TileFactory.getInstance().createTileDirt());
	}
	
	public void fillRectangle(int x1, int y1, int x2, int y2, Tile t) {
		if(x1 < 0 || y1 < 0 || x2 > this.table.length-1 || y2 > this.table[0].length-1) {
			return;
		}
		
		for(int i=x1; i<=x2; i++) {
			for(int j=y1; j<=y2; j++) {
				table[i][j] = t;
			}
		}
	}
	
	public boolean checkOn(int x1, int y1, int x2, int y2) {
		for(int i=y1+1; i<y2; i++) {
			for(int j=x1+1; j<x2; j++) {
				if(!(this.table[i][j] instanceof TileDirt)) return false;
			}
		}
		return true;
	}
	
	private boolean isFull() {
		double empty=0.0, taken=0.0, ratio;
		
		for(int i=0; i<this.table.length; i++) {
			for(int j=0; j<this.table[0].length; j++) {
				if(!(this.table[i][j] instanceof TileDirt)) 
					taken++;
				else										
					empty++;
			}
		}
		
		ratio = (taken/empty) * 100;
		
		if(ratio > 75)	return true;
		else			return false;
	}
	
	public void generateDungeon() {
		Random rnd = new Random();
		Room room;
		int roomIndex, wallSide, height, width;
		
		// Premiere salle, au centre du niveau
		height = rnd.nextInt(5)+9;
		width = rnd.nextInt(5)+6;
		rooms.add(new RectangleRoom((this.table[0].length/2)-(width/2), (this.table.length/2)-(height/2), (this.table[0].length/2)+(width/2), (this.table.length/2)+(height/2), "Main Room"));
		rooms.get(rooms.size()-1).printOn(this.table);
		
		for(int i=0; i<rooms.size(); i++) {
			roomIndex = rnd.nextInt(rooms.size());
			room = this.rooms.get(roomIndex);
			
			// DEV
			// TODO : Placement des autres salles
			/*
			if(room instanceof Corridor) {
				height = rnd.nextInt(5)+9;
				width = rnd.nextInt(5)+6;
				
				// De quel cote on etend le donjon
				wallSide = rnd.nextInt(4);
				if(wallSide == 0) {
					// NORTH
					if(checkOn(room.p1.x-width, room.p1.y-height, width+1, room.p1.y)) {
						rooms.add(new CorridorNorth(width-1, room.p1.y-height, width+1, room.p1.y, "CorridorNorth"+i));
					}
				} else if(wallSide == 1) {
					// SOUTH
					height = rnd.nextInt(4)+3;
					width = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
					if(checkOn(width-1, room.p2.y, width+1, room.p2.y+height)) {
						rooms.add(new CorridorSouth(width-1, room.p2.y, width+1, room.p2.y+height, "CorridorSouth"+i));
					}
				} else if(wallSide == 2) {
					// EAST
					width = rnd.nextInt(4)+3;
					height = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
					if(checkOn(room.p2.x, height-1, room.p2.x+width, height+1)) {
						rooms.add(new CorridorEast(room.p2.x, height-1, room.p2.x+width, height+1, "CorridorEast"+i));
					}
				} else if(wallSide == 3) {
					// WEST
					width = rnd.nextInt(4)+3;
					height = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
					if(checkOn(room.p1.x-width, height-1, room.p1.x, height+1)) {
						rooms.add(new CorridorWest(room.p1.x-width, height-1, room.p1.x, height+1, "CorridorWest"+i));
					}
				}
			} else {
			*/
				// De quel cote on etend le donjon
				wallSide = rnd.nextInt(4);
				if(wallSide == 0) {
					// NORTH
					height = rnd.nextInt(4)+3;
					width = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
					if(checkOn(width-1, room.p1.y-height, width+1, room.p1.y)) {
						rooms.add(new CorridorNorth(width-1, room.p1.y-height, width+1, room.p1.y, "CorridorNorth"+i));
					}
				} else if(wallSide == 1) {
					// SOUTH
					height = rnd.nextInt(4)+3;
					width = rnd.nextInt(room.getWidth()-1)+1+room.p1.x;
					if(checkOn(width-1, room.p2.y, width+1, room.p2.y+height)) {
						rooms.add(new CorridorSouth(width-1, room.p2.y, width+1, room.p2.y+height, "CorridorSouth"+i));
					}
				} else if(wallSide == 2) {
					// EAST
					width = rnd.nextInt(4)+3;
					height = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
					if(checkOn(room.p2.x, height-1, room.p2.x+width, height+1)) {
						rooms.add(new CorridorEast(room.p2.x, height-1, room.p2.x+width, height+1, "CorridorEast"+i));
					}
				} else if(wallSide == 3) {
					// WEST
					width = rnd.nextInt(4)+3;
					height = rnd.nextInt(room.getHeight()-1)+1+room.p1.y;
					if(checkOn(room.p1.x-width, height-1, room.p1.x, height+1)) {
						rooms.add(new CorridorWest(room.p1.x-width, height-1, room.p1.x, height+1, "CorridorWest"+i));
					}
				}
			//}
			printDungeon();
		}
		integratePlayer();
	}
	
	private void integratePlayer() {
		this.table[this.jerry.pos.y][this.jerry.pos.x] = TileFactory.getInstance().createTilePlayer();
	}
	
	private void printDungeon() {
		for(int i=0; i<rooms.size(); i++) {
			if(!(rooms.get(i) instanceof Corridor)) {
				rooms.get(i).printOn(this.table);
			}
		}
		for(int i=0; i<rooms.size(); i++) {
			if(rooms.get(i) instanceof Corridor) {
				rooms.get(i).printOn(this.table);
			}
		}
	}
	
	public void printOnConsole() {		
		for(int i=0; i<this.table.length; i++) {
			for(int j=0; j<this.table[0].length; j++) {
				System.out.print(""+this.table[i][j].getSymbol()+' ');
			}
			System.out.println();
		}
		
		for(int i=0; i<this.rooms.size(); i++) {
			System.out.print(this.rooms.get(i).toString()+"; ");
		}
		System.out.println();
	}
}
