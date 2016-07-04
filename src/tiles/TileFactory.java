package tiles;

public class TileFactory {
	private static TileFactory fact = null;
	
	private TileDirt tileDirt = new TileDirt();
	private TileDoor tileClosedDoor = new TileDoor(false);
	private TileDoor tileOpenedDoor = new TileDoor(true);
	private TileWall tileWall = new TileWall();
	private TileVoid tileVoid = new TileVoid();
	private TileStone tileStone = new TileStone();
	private TileMoss tileMoss = new TileMoss();
	private TileGold tileGold = new TileGold();
	private TilePlayer tilePlayer = new TilePlayer();
	private TileStairsDown tileStairsDown = new TileStairsDown();
	private TileCorpse tileCorpse = new TileCorpse();

	
	private TileFactory() {
		// CRAP CONSTRUCTOR
	}
	
	public static TileFactory getInstance() {
		if(fact == null) fact=new TileFactory();
		return fact;
	}
	
	public TileDirt createTileDirt() { return tileDirt; }
	public TileDoor createTileDoor(boolean o) { if(o) { return tileOpenedDoor; } else { return tileClosedDoor; } }
	public TileWall createTileWall() { return tileWall; }
	public TileVoid createTileVoid() { return tileVoid; }
	public TileStone createTileStone() { return tileStone; }
	public TileMoss createTileMoss() { return tileMoss; }
	public TileGold createTileGold() { return tileGold; }
	public TilePlayer createTilePlayer() { return tilePlayer; }
	public TileStairsDown createTileStairsDown() { return tileStairsDown; }
	public TileMonster createTileMonster(char s) { return new TileMonster(s); }
	public TileCorpse createTileCorpse() { return tileCorpse; }
}
