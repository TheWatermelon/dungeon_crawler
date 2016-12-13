package engine;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

import tiles.*;

public class DungeonPanel extends JPanel {
	protected static final long serialVersionUID = 1L;
	
	protected Window win;
	protected int[][] light;
	protected boolean isLight;

	protected Color[] basicColors;
	// Theme colors
	protected Color wall;
	
	public DungeonPanel(Window w) {
		super();
		setBorder(BorderFactory.createLineBorder(Color.white));
		this.win = w;
		this.light = Resources.drawCircle(9);
		this.isLight=true;
		
		this.basicColors = new Color[10];
		this.basicColors[0] = Resources.lightGray;
		this.basicColors[1] = Resources.white;
		this.basicColors[2] = Resources.brown;
		this.basicColors[3] = Resources.orange;
		this.basicColors[4] = Resources.red;
		this.basicColors[5] = Resources.pink;
		this.basicColors[6] = Resources.magenta;
		this.basicColors[7] = Resources.blue;
		this.basicColors[8] = Resources.cyan;
		this.basicColors[9] = Resources.green;
		
		pickTheme();
	}
	
	public void showLight() { isLight=true; }
	public void hideLight() { isLight=false; }
	
	public void pickTheme() {
		this.wall = basicColors[(new Random()).nextInt(basicColors.length)];
	}
	
	public void prepareColor(Graphics g, Tile[][] t, int i, int j) {
		if(!isLight(i, j)) { g.setColor(Resources.darkerGray); return; }
		
		if(t[i][j] instanceof TilePlayer) {
			g.setColor(getPlayerColor());
		} else if(t[i][j] instanceof TileWall){
			g.setColor(wall);
		} else if(t[i][j] instanceof TileDoor){
			g.setColor(Resources.brown);
		} else if(t[i][j] instanceof TileStone){
			g.setColor(Resources.darkGray);
		} else if(t[i][j] instanceof TileMoss){
			g.setColor(Resources.darkGreen);
		} else if(t[i][j] instanceof TileStairsDown){
			g.setColor(Resources.orange);
		} else if(t[i][j] instanceof TileStairsUp){
			g.setColor(Resources.orange);
		} else if(t[i][j] instanceof TileGold){
			g.setColor(Resources.yellow);
		} else if(t[i][j] instanceof TileFountain){
			g.setColor(Resources.cyan);
		} else if(t[i][j] instanceof TileItem){
			g.setColor(Resources.lightGray);
		} else if(t[i][j] instanceof TileBarrel){
			g.setColor(Resources.brown);
		} else if(t[i][j] instanceof TileMob){
			g.setColor(Resources.coolRed);
		} else if(t[i][j] instanceof TileCorpse){
			g.setColor(Resources.gray);
		} else {
			g.setColor(Resources.white);
		}
	}
	
	public Color getPlayerColor() {
		int hp = win.getMap().getPlayer().hp;
		if(hp<10) {
			return new Color(0xAA, 0x00, 0x00);
		} else if(hp<20) {
			return new Color(0xDD, 0x00, 0x00);
		} else if(hp<30) {
			return new Color(0xFF, 0x33, 0x00);
		} else if(hp<40) {
			return new Color(0xFF, 0x66, 0x00);
		} else if(hp<50) {
			return new Color(0xFF, 0xAA, 0x00);
		} else if(hp<60) {
			return new Color(0xFF, 0xFF, 0x00);
		} else if(hp<70) {
			return new Color(0xDD, 0xFF, 0x00);
		} else if(hp<80) {
			return new Color(0xAA, 0xFF, 0x00);
		} else if(hp<90) {
			return new Color(0x66, 0xFF, 0x00);
		} else {
			return Resources.green;
		}
	}
	
	public void printLooker(Graphics g, int offsetX, int offsetY) {
		char[] looker = new char[2];
		looker[0]=' ';
		looker[1]=' ';
		if(!win.getMap().getPlayer().getLooker().isVisible()) { 
			g.setColor(Color.BLACK);
			g.drawChars(looker, 0, 1, offsetX-6, offsetY);
			g.drawChars(looker, 1, 1, offsetX+6, offsetY);
		} else {
			looker[0]=win.getMap().getPlayer().getLooker().getLeft();
			g.setColor(win.getMap().getPlayer().getLooker().getLeftColor());
			g.drawChars(looker, 0, 1, offsetX-6, offsetY);
			looker[1]=win.getMap().getPlayer().getLooker().getRight();
			g.setColor(win.getMap().getPlayer().getLooker().getRightColor());
			g.drawChars(looker, 1, 1, offsetX+6, offsetY);
		}
		// Mise a jour de la bordure du cadre de jeu
		setBorder(BorderFactory.createLineBorder(g.getColor()==Color.BLACK?Resources.lightGray:g.getColor()));
	}
	
	public boolean isLight(int i, int j) {
		if(isLight) { return true; }
		
		int limit=(int)Math.round(light.length/2);
		if(i>win.getMap().getPlayer().pos.y-limit && i<win.getMap().getPlayer().pos.y+limit &&
				j>win.getMap().getPlayer().pos.x-limit && j<win.getMap().getPlayer().pos.x+limit) {
			int minX=win.getMap().getPlayer().pos.x-limit;
			int minY=win.getMap().getPlayer().pos.y-limit;
			int pointX = j-minX;
			int pointY = i-minY;
			
			if(light[pointY][pointX]==1) {
				return true;
			}
		} 
		return false;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		Tile[][] table = win.getMap().getTable();		
		int i, offsetX=15, offsetY=26;
		char[] c = new char[1];
		for(i=0; i<table.length; i++) {
			for(int j=0; j<table[0].length; j++) {
				c[0]=table[i][j].getSymbol();
				prepareColor(g, table, i, j);
				g.drawChars(c, 0, 1, offsetX, offsetY);
				if(table[i][j] instanceof TilePlayer) { printLooker(g, offsetX, offsetY); }
				offsetX+=14; 
			}
			offsetY+=16; 
			offsetX=15;
		}
	}
}
