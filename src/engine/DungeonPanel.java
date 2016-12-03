package engine;

import java.awt.*;
import java.util.Random;

import javax.swing.*;

import objects.looker.*;
import tiles.*;

public class DungeonPanel extends JPanel {
	protected static final long serialVersionUID = 1L;
	
	protected Window win;
	protected int[][] light;
	protected boolean isLight;

	protected Color[] basicColors;
	protected Color white = Color.WHITE;
	protected Color yellow = Color.YELLOW;
	protected Color darkYellow = new Color(0xA9, 0x96, 0x2D);
	protected Color orange = Color.ORANGE;
	protected Color brown = new Color(0xA5, 0x68, 0x2A);
	protected Color lightGray = Color.GRAY;
	protected Color gray = new Color(0x60, 0x60, 0x60);
	protected Color darkGray = new Color(0x30, 0x30, 0x30);
	protected Color darkerGray = new Color(0x15, 0x15, 0x15);
	protected Color green = new Color(0x00, 0x80, 0x00);
	protected Color darkGreen = new Color(0x00, 0x33, 0x00);
	protected Color coolRed = new Color(0xEF, 0x3F, 0x23);
	protected Color red = Color.RED;
	protected Color darkRed = new Color(0x7F, 0x00, 0x00);
	protected Color blue = Color.BLUE;
	protected Color darkBlue = new Color(0x00, 0x00, 0x7F);
	protected Color cyan = Color.CYAN;
	protected Color magenta = Color.MAGENTA;
	protected Color pink = Color.PINK;
	// Theme colors
	protected Color wall;
	
	public DungeonPanel(Window w) {
		super();
		setBorder(BorderFactory.createLineBorder(Color.white));
		this.win = w;
		this.light = Ressources.drawCircle(9);
		this.isLight=true;
		
		this.basicColors = new Color[10];
		this.basicColors[0] = this.lightGray;
		this.basicColors[1] = this.white;
		this.basicColors[2] = this.brown;
		this.basicColors[3] = this.orange;
		this.basicColors[4] = this.red;
		this.basicColors[5] = this.pink;
		this.basicColors[6] = this.magenta;
		this.basicColors[7] = this.blue;
		this.basicColors[8] = this.cyan;
		this.basicColors[9] = this.green;
		
		pickTheme();
	}
	
	public void showLight() { isLight=true; }
	public void hideLight() { isLight=false; }
	
	public void pickTheme() {
		this.wall = basicColors[(new Random()).nextInt(basicColors.length)];
	}
	
	public void prepareColor(Graphics g, Tile[][] t, int i, int j) {
		if(!isLight(i, j)) { g.setColor(darkerGray); return; }
		
		if(t[i][j] instanceof TilePlayer) {
			g.setColor(getPlayerColor());
		} else if(t[i][j] instanceof TileWall){
			g.setColor(wall);
		} else if(t[i][j] instanceof TileDoor){
			g.setColor(brown);
		} else if(t[i][j] instanceof TileStone){
			g.setColor(darkGray);
		} else if(t[i][j] instanceof TileMoss){
			g.setColor(darkGreen);
		} else if(t[i][j] instanceof TileStairsDown){
			g.setColor(orange);
		} else if(t[i][j] instanceof TileStairsUp){
			g.setColor(orange);
		} else if(t[i][j] instanceof TileGold){
			g.setColor(yellow);
		} else if(t[i][j] instanceof TileFountain){
			g.setColor(cyan);
		} else if(t[i][j] instanceof TileItem){
			g.setColor(lightGray);
		} else if(t[i][j] instanceof TileBarrel){
			g.setColor(brown);
		} else if(t[i][j] instanceof TileMob){
			g.setColor(coolRed);
		} else if(t[i][j] instanceof TileCorpse){
			g.setColor(gray);
		} else {
			g.setColor(white);
		}
	}
	
	public void prepareLookerColor(Graphics g) {
		Looker l = win.getMap().getPlayer().getLooker();
		
		if(l instanceof LookerMob ||
				l instanceof LookerMiss ||
				l instanceof LookerDamage) {
			g.setColor(red);
		} else if(l instanceof LookerEquip) {
			g.setColor(lightGray);
		} else if(l instanceof LookerGold) {
			g.setColor(yellow);
		} else if(l instanceof LookerHealth) {
			g.setColor(green);
		} else if(l instanceof LookerBarrel) {
			g.setColor(brown);
		} else if(l instanceof LookerPotion) {
			if(((LookerPotion) l).getVal()>0) {
				g.setColor(green);
			} else {
				g.setColor(red);
			}
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
			return green;
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
			prepareLookerColor(g);
			looker[0]=win.getMap().getPlayer().getLooker().getLeft();
			looker[1]=win.getMap().getPlayer().getLooker().getRight();
			g.drawChars(looker, 0, 1, offsetX-6, offsetY);
			g.drawChars(looker, 1, 1, offsetX+6, offsetY);
		}
		// Mise a jour de la bordure du cadre de jeu
		setBorder(BorderFactory.createLineBorder(g.getColor()==Color.BLACK?Color.WHITE:g.getColor()));
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
