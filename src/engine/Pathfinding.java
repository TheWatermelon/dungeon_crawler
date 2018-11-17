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
	private static ArrayList<Coordinates> getAdjacentsOf(Coordinates pos) {
		ArrayList<Coordinates> adjacents = new ArrayList();
		adjacents.add(new Coordinates(pos.x+Direction.North.getX(), pos.y+Direction.North.getY(), pos.val+1));
		adjacents.add(new Coordinates(pos.x+Direction.East.getX(), pos.y+Direction.East.getY(), pos.val+1));
		adjacents.add(new Coordinates(pos.x+Direction.South.getX(), pos.y+Direction.South.getY(), pos.val+1));
		adjacents.add(new Coordinates(pos.x+Direction.West.getX(), pos.y+Direction.West.getY(), pos.val+1));
		return adjacents;
	}

	/**
	 * areClose : two coordinates are close is separed by one cell
	 */
	private static boolean areClose(Coordinates c1, Coordinates c2) {
		return Math.abs(c1.x-c2.x)<=2 && Math.abs(c1.y-c2.y)<=2;
	}

	public static ArrayList<Coordinates> getPathFor(Point start, Point end, Tile[][] table) {
		Coordinates startCoord = new Coordinates(start.x, start.y, 0), endCoord = new Coordinates(end.x, end.y, 0);
		ArrayList<Coordinates> queue = new ArrayList<Coordinates>();
		queue.add(endCoord);

		//System.out.println("Starting Pathfinding with start:"+startCoord+", end:"+endCoord);
		
		if(Pathfinding.areClose(startCoord, endCoord)) {
			if((Math.abs(startCoord.x-endCoord.x)==1 && Math.abs(startCoord.y-endCoord.y)==0) || 
			   (Math.abs(startCoord.x-endCoord.x)==0 && Math.abs(startCoord.y-endCoord.y)==1)) { return null; }

			ArrayList<Coordinates> startAdjacents = Pathfinding.getAdjacentsOf(startCoord);
			ArrayList<Coordinates> endAdjacents = Pathfinding.getAdjacentsOf(endCoord);
			for(Coordinates coord1 : startAdjacents) {
				for(Coordinates coord2 : endAdjacents) {
					if(coord1.equalPos(coord2)) {
						queue.add(coord1);
						startCoord.val = coord1.val+1;
						queue.add(startCoord);
						return queue;
					}
				}
			}
			return null;
		}

		ArrayList<Coordinates> adjacents = new ArrayList<Coordinates>();
		ArrayList<Coordinates> validAdjacents = new ArrayList<Coordinates>();
		boolean solved = false;
		int nonWalkable = 0;
		for(int i=0; !solved; ++i) {
			for(Coordinates pos : queue) {
				adjacents = getAdjacentsOf(pos);
				for(Coordinates neighbor : adjacents) {
					if(table[neighbor.y][neighbor.x].isWalkable() || 
						neighbor.equalPos(startCoord) || 
						neighbor.equalPos(endCoord)) {
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
					} else {
						nonWalkable++;
					}
					if(neighbor.equalPos(startCoord)) {
						//System.out.println("Found "+neighbor);
						startCoord.val = neighbor.val;
						solved = true; 
						break;
					}
				}
				if(solved) { break; }
				if(nonWalkable==4) { /*System.out.println("unsolvable");*/ return null; } // problem unsolvable because all adjacents are non walkable
				nonWalkable = 0;
				adjacents.clear();
			}
			for(Coordinates coord : validAdjacents) {
				queue.add(coord);
			}
			validAdjacents.clear();
			//System.out.println(i);
		}
		//System.out.println("Path found in "+startCoord.val+" steps");

		return queue;
	}

	public static ArrayList<Point> convertCoordsToPoints(ArrayList<Coordinates> list) {
		ArrayList<Point> res = new ArrayList();
		for(Coordinates c : list) {
			res.add(new Point(c.x, c.y));
		}
		return res;
	}

	public static Point getNextStep(Point start, Point end, Tile[][] table) {
		ArrayList<Coordinates> path = getPathFor(start, end, table);
		if(path == null) { return null; } // No next step because no path

		Coordinates startCoord = new Coordinates(start.x, start.y, 0);
		// Getting start value == max steps
		for(Coordinates coord : path) {
			if(startCoord.equalPos(coord)) {
				startCoord.val = coord.val;
			}
		}
		// Searching for the next step == a step with val = max steps - 1
		for(Coordinates coord : path) {
			if((coord.val == startCoord.val-1) && (Math.abs(coord.x-startCoord.x)<=1 && Math.abs(coord.y-startCoord.y)<=1)) {
				return new Point(coord.x, coord.y);
			}
		}
		// If no next step found
		return null;
	}
}