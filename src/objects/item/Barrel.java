package objects.item;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import engine.Resources;
import tiles.Tile;
import tiles.TileFactory;

public class Barrel extends Item {
	public Barrel(int x, int y) {
		this.pos = new Point(x, y);
		this.description = "Barrel";
		this.val = 0;
	}
	
	public int open() {
		Random rnd = new Random();
		return rnd.nextInt(5);
		
	}
	
	@Override
	public Tile getTile() {
		return TileFactory.getInstance().createTileBarrel();
	}
	
	@Override
	public boolean isStackable() {
		return false;
	}
	
	@Override
	public Color getColor() {
		return Resources.brown;
	}
}
