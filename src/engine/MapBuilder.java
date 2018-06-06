package engine;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import objects.item.Item;
import objects.mob.Boss;
import objects.mob.Monster;
import objects.mob.Player;
import rooms.Room;
import tiles.Tile;

public class MapBuilder {
	protected Tile[][] table;
	protected Vector<Room> rooms;
	protected Vector<Monster> monsters;
	protected Vector<Item> items;
	protected MessageLog log;
	protected Point stairUp;
	protected Point stairDown;
	protected Player player;
	protected Boss boss;
	protected int level;
	protected Dungeon dungeon;
	
	public MapBuilder withDungeon(Dungeon d) {
		this.dungeon = d;
		return this;
	}
	
	public MapBuilder withTiles(Tile[][] t) {
		this.table = t;
		return this;
	}
	
	public MapBuilder withRooms(Vector<Room> r) {
		this.rooms = r;
		return this;
	}
	
	public MapBuilder withMonsters(Vector<Monster> m) {
		this.monsters = m;
		return this;
	}
	
	public MapBuilder withItems(Vector<Item> i) {
		this.items = i;
		return this;
	}
	
	public MapBuilder withStairUp(Point p) {
		this.stairUp = p;
		return this;
	}
	
	public MapBuilder withStairDown(Point p) {
		this.stairDown = p;
		return this;
	}

	public Map build() {
		return new Map(this.dungeon, this.table, this.rooms, this.monsters, this.items, this.stairUp, this.stairDown);
	}
}
