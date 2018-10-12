package tiles;

public class TileFactory {
	private static TileFactory fact = null;
	
	private TileDirt tileDirt = new TileDirt();
	private TileDoorClosed tileClosedDoor = new TileDoorClosed();
	private TileDoorOpened tileOpenedDoor = new TileDoorOpened();
	private TileWall tileWall = new TileWall();
	private TileVoid tileVoid = new TileVoid();
	private TileStone tileStone = new TileStone();
	private TileMoss tileMoss = new TileMoss();
	private TileGold tileGold = new TileGold();
	private TileFountain tileFountain = new TileFountain();
	private TilePlayer tilePlayer = new TilePlayer();
	private TileStairsUp tileStairsUp = new TileStairsUp();
	private TileStairsDown tileStairsDown = new TileStairsDown();
	private TileCorpse tileCorpse = new TileCorpse();
	private TileWeapon tileWeapon = new TileWeapon();
	private TileShield tileShield = new TileShield();
	private TileBarrel tileBarrel = new TileBarrel();
	private TilePotion tilePotion = new TilePotion();
	private TileBow tileBow = new TileBow();
	private TileHelmet tileHelmet = new TileHelmet();

	private Tile[] tiles = {
		tileVoid,
		tileStone,
		tileDirt,
		tileMoss,
		tileWall,
		tileClosedDoor,
		tileOpenedDoor,
		tileStairsDown,
		tileStairsUp,
		tileBarrel,
		tileFountain,
		tileGold,
		tilePotion,
		tileHelmet,
		tileWeapon,
		tileBow,
		tileShield,
		tileCorpse,
		tilePlayer
	};
	
	private TileFactory() {
		// STUB
	}
	
	public static TileFactory getInstance() {
		if(fact == null) fact=new TileFactory();
		return fact;
	}
	
	public TileDirt createTileDirt() { return tileDirt; }
	public TileDoorClosed createTileDoorClosed() { return tileClosedDoor; }
	public TileDoorOpened createTileDoorOpened() { return tileOpenedDoor; }
	public TileWall createTileWall() { return tileWall; }
	public TileVoid createTileVoid() { return tileVoid; }
	public TileStone createTileStone() { return tileStone; }
	public TileMoss createTileMoss() { return tileMoss; }
	public TileGold createTileGold() { return tileGold; }
	public TileFountain createTileFountain() { return tileFountain; }
	public TilePlayer createTilePlayer() { return tilePlayer; }
	public TileStairsUp createTileStairsUp() { return tileStairsUp; }
	public TileStairsDown createTileStairsDown() { return tileStairsDown; }
	public TileMonster createTileMonster(char s) { return new TileMonster(s); }
	public TileCorpse createTileCorpse() { return tileCorpse; }
	public TileBarrel createTileBarrel() { return tileBarrel; }
	public TileWeapon createTileWeapon() { return tileWeapon; }
	public TileShield createTileShield() { return tileShield; }
	public TilePotion createTilePotion() { return tilePotion; }
	public TileBow createTileBow() { return tileBow; }
	public TileHelmet createTileHelmet() { return tileHelmet; }

	public Tile getTileAt(int index) {
		return this.tiles[index];
	}
}
