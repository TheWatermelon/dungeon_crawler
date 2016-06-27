package tiles;

public class TileFactory {
	private static TileFactory fact = null;
	
	private TileDirt tileDirt = new TileDirt();
	private TileDoor tileDoor = new TileDoor();
	private TileWall tileWall = new TileWall();
	private TileStone tileStone = new TileStone();
	private TilePlayer tilePlayer = new TilePlayer();

	
	private TileFactory() {
		// CRAP CONSTRUCTOR
	}
	
	public static TileFactory getInstance() {
		if(fact == null) fact=new TileFactory();
		return fact;
	}
	
	public TileDirt createTileDirt() { return tileDirt; }
	public TileDoor createTileDoor() { return tileDoor; }
	public TileWall createTileWall() { return tileWall; }
	public TileStone createTileStone() { return tileStone; }
	public TilePlayer createTilePlayer() { return tilePlayer; }
}
