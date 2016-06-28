package tiles;

public class TileFactory {
	private static TileFactory fact = null;
	
	private TileDirt tileDirt = new TileDirt();
	// Une porte differente a chaque piece
	private TileDoor tileDoor = new TileDoor();
	private TileWall tileWall = new TileWall();
	private TileVoid tileVoid = new TileVoid();
	private TileStone tileStone = new TileStone();
	private TilePlayer tilePlayer = new TilePlayer();
	private TileStairsDown tileStairsDown = new TileStairsDown();

	
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
	public TileVoid createTileVoid() { return tileVoid; }
	public TileStone createTileStone() { return tileStone; }
	public TilePlayer createTilePlayer() { return tilePlayer; }
	public TileStairsDown createTileStairsDown() { return tileStairsDown; }
}
