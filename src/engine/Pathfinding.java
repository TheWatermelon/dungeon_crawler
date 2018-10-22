package engine;

import java.util.ArrayList;
import java.awt.Point;

import tiles.Tile;

class Coordinates {
	int x, y, val;

	public Coordinates(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.val = value;
	}

	public boolean equalPos(final Coordinates c) {
		return this.x == c.x && this.y == c.y;
	}

	@Override
	public String toString() {
		return "("+this.x+","+this.y+","+this.val+")";
	}
}

public class Pathfinding {
	public static ArrayList<Coordinates> getPathFor(Point end, Point start, Tile[][] table) {
		Coordinates startCoord = new Coordinates(start.x, start.y, 0), endCoord = new Coordinates(end.x, end.y, 0);
		System.out.println("Starting Pathfinding with start:"+start+", end:"+end);
		ArrayList<Coordinates> queue = new ArrayList<Coordinates>();
		queue.add(endCoord);
		ArrayList<Coordinates> adjacents = new ArrayList<Coordinates>();
		ArrayList<Coordinates> validAdjacents = new ArrayList<Coordinates>();
		boolean solved = false;
		for(int i=0; !solved; ++i) {
			for(Coordinates pos : queue) {
				adjacents.add(new Coordinates(pos.x+Direction.North.getX(), pos.y+Direction.North.getY(), pos.val+1));
				adjacents.add(new Coordinates(pos.x+Direction.East.getX(), pos.y+Direction.East.getY(), pos.val+1));
				adjacents.add(new Coordinates(pos.x+Direction.South.getX(), pos.y+Direction.South.getY(), pos.val+1));
				adjacents.add(new Coordinates(pos.x+Direction.West.getX(), pos.y+Direction.West.getY(), pos.val+1));
				for(Coordinates neighbor : adjacents) {
					if(table[neighbor.y][neighbor.x].isWalkable()) {
						Coordinates inQueue = null;
						for(Coordinates search : queue) {
							if(neighbor.equalPos(search)) { 
								inQueue = search;
								break; 
							}	
						}
						if(inQueue != null && neighbor.val < inQueue.val) {
							validAdjacents.add(neighbor);
						} else if(inQueue == null) {
							validAdjacents.add(neighbor);
						}
					}
					if(neighbor.equalPos(startCoord)) { solved = true; }
				}
				adjacents.clear();
			}
			for(Coordinates coord : validAdjacents) {
				queue.add(coord);
			}
			validAdjacents.clear();
		}
		// Finding start in queue
		for(Coordinates coord : queue) {
			if(startCoord.equalPos(coord)) {
				System.out.println("Path found in "+coord.val+" steps");
			}
		}

		return queue;
	}

	public static ArrayList<Point> convertCoordsToPoints(ArrayList<Coordinates> list) {
			ArrayList<Point> res = new ArrayList();
			for(Coordinates c : list) {
				res.add(new Point(c.x, c.y));
			}
			return res;
	}
}