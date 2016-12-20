package engine;

public enum Direction {
	North(0, -1),
	East(1, 0),
	South(0, 1),
	West(-1, 0);
	
	private Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	private int x;
	private int y;
}
