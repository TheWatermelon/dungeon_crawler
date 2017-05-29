package engine;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

import objects.looker.LookerStuff;
import tiles.*;

public class DungeonPanel extends JPanel {
	protected static final long serialVersionUID = 1L;
	
	protected Window win;
	protected int[][] light;
	protected boolean isLight;
	protected int[][] fireLine;
	
	protected Rectangle window;
	protected Rectangle player;
	
	// Indicators to check whether to refresh the table
	protected boolean dirty;
	protected String table;
	protected Color borderColor;
	

	protected Color[] basicColors;
	// Theme colors
	protected Color wall;
	
	public DungeonPanel(Window w) {
		super();
		setBorder(BorderFactory.createLineBorder(Color.white));
		this.win = w;
		this.light = Resources.drawCircle(9);
		this.isLight=true;
		
		initPlayerRectangle();
		calculateWindowRectangle();
		
		this.dirty=true;
		this.table="";
		this.borderColor = Resources.white;
		
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
		
		Resources.getInstance().theme = pickTheme();
	}
	
	public void setDirty(boolean val) { dirty = val; }
	
	public void showLight() { isLight=true; }
	public void hideLight() { isLight=false; }
	
	public Color pickTheme() {
		this.wall = basicColors[(new Random()).nextInt(basicColors.length)];
		return this.wall;
	}
	
	public void prepareColor(Graphics g, Tile[][] t, int i, int j) {
		if(!isLight(i, j)) { g.setColor(Resources.darkerGray); return; }
		
		if(t[i][j] instanceof TilePlayer) {
			g.setColor(win.getMap().getPlayer().getColor());
		} else if(t[i][j] instanceof TileMonster) {
			if(win.getMap().getMonster(j, i)!=null) { g.setColor(win.getMap().getMonster(j, i).getColor()); }
			else { g.setColor(t[i][j].getColor()); }
		} else if(win.getMap().isFireMode()) {
			if(isFireLine(i, j)) { g.setColor(Resources.white); }
			else { g.setColor(t[i][j].getColor()); }
		} else {
			g.setColor(t[i][j].getColor());
		}
	}
	
	public void printLooker(Graphics g, int offsetX, int offsetY) {
		char[] looker = new char[3];
		looker[0]=' ';
		looker[1]=' ';
		looker[2]=' ';
		
		if(!win.getMap().getPlayer().getLooker().isVisible()) { 
			g.setColor(Color.BLACK);
			g.drawChars(looker, 0, 1, offsetX-6, offsetY);
			g.drawChars(looker, 1, 1, offsetX+6, offsetY);
			g.drawChars(looker, 2, 1, offsetX, offsetY-3);
		} else {
			looker[0]=win.getMap().getPlayer().getLooker().getLeft();
			g.setColor(win.getMap().getPlayer().getLooker().getLeftColor());
			g.drawChars(looker, 0, 1, offsetX-6, offsetY);
			looker[1]=win.getMap().getPlayer().getLooker().getRight();
			g.setColor(win.getMap().getPlayer().getLooker().getRightColor());
			g.drawChars(looker, 1, 1, offsetX+6, offsetY);
			if(win.getMap().getPlayer().getHelmet().getMaxDurability()!=-1) {
				looker[2]=win.getMap().getPlayer().getHelmet().getTile().getSymbol();
				g.setColor(win.getMap().getPlayer().getHelmet().getColor());
				g.drawChars(looker, 2, 1, offsetX+1, offsetY-4);
			}
		}
		// Mise a jour de la bordure du cadre de jeu
		Color newBorderColor = (win.getMap().getPlayer().getLooker() instanceof LookerStuff || g.getColor()==Color.black)?Resources.white:g.getColor();
		if(borderColor!=newBorderColor)
		{ setBorder(BorderFactory.createLineBorder(newBorderColor)); borderColor = newBorderColor; }
	}
	
	public void printCommandsHelp(Graphics g, int offsetX, int offsetY) {
		char[] command = new char [4];
		
		// If there is an item under the player
		if(win.getMap().isItem(win.getMap().getPlayer().pos.x, win.getMap().getPlayer().pos.y) != null) {
			command[0] = Resources.Commands.Take.getKey();
		}
		
		// If there are monsters (may override item help)
		int[][] direction = {
				{0, -1},
				{1, 0},
				{0, 1},
				{-1, 0}
		};
		Resources.Commands[] allCommands = Resources.Commands.values();
		for(int i = 0; i<direction.length; i++) {
			int deltaX = win.getMap().getPlayer().pos.x + direction[i][0];
			int deltaY = win.getMap().getPlayer().pos.y + direction[i][1];
			if(win.getMap().isMonster(deltaX, deltaY)) {
				command[i] = allCommands[i].getKey();
			}
		}
		
		// Print the commands 2 cells away from the player
		int[][] guiDirection = {
				{offsetX, offsetY-2*16},
				{offsetX+2*14, offsetY},
				{offsetX, offsetY+2*16},
				{offsetX-2*14, offsetY}
		};
		for(int i = 0; i<guiDirection.length; i++) {
			if(command[i] != 0) {
				g.setColor(new Color(255, 0, 0, 100));
				g.drawRect(guiDirection[i][0]-3, guiDirection[i][1]-13, 14, 16);
				g.setColor(Color.black);
				g.fillRect(guiDirection[i][0]-2, guiDirection[i][1]-12, 13, 15);
				g.setColor(Color.white);
				g.drawChars(command, i, 1, guiDirection[i][0], guiDirection[i][1]);
			}
		}
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
	
	public boolean isFireLine(int i, int j) {
		int limitY=(int)Math.round(fireLine.length/2);
		int limitX=(int)Math.round(fireLine[0].length/2);
		if(i>=win.getMap().getPlayer().pos.y-limitY && i<=win.getMap().getPlayer().pos.y+limitY &&
				j>=win.getMap().getPlayer().pos.x-limitX && j<=win.getMap().getPlayer().pos.x+limitX) {
			int pointX = j-(win.getMap().getPlayer().pos.x-limitX);
			int pointY = i-(win.getMap().getPlayer().pos.y-limitY);
			if(fireLine[pointY][pointX]==1) {
				return true;
			}
		} 
		
		return false;
	}
	
	public void initPlayerRectangle() {
		int playerX1 = (win.getMap().getPlayer().pos.x-4)<0?0:win.getMap().getPlayer().pos.x-4;
		int playerY1 = (win.getMap().getPlayer().pos.y-4)<0?0:win.getMap().getPlayer().pos.y-4;
		int playerWidth = playerX1+9>win.getMap().getWidth()?win.getMap().getWidth()-playerX1:9;
		int playerHeight = playerY1+9>win.getMap().getHeight()?win.getMap().getHeight()-playerY1:9;
		player = new Rectangle(playerX1, playerY1, playerWidth, playerHeight);
	}
	
	public void calculatePlayerRectangle() {
		Point pos = win.getMap().getPlayer().pos;
		
		if(pos.x<player.x) {							// WEST
			player = new Rectangle(player.x-1, player.y, player.width, player.height);
		} else if(pos.x>player.x+player.width) {		// EAST
			player = new Rectangle(player.x+1, player.y, player.width, player.height);
		} else if(pos.y<player.y) {						// NORTH
			player = new Rectangle(player.x, player.y-1, player.width, player.height);
		} else if(pos.y>player.y+player.height) {		// SOUTH
			player = new Rectangle(player.x, player.y+1, player.width, player.height);
		}
	}
	
	public void calculateWindowRectangle() {
		int width = (int)Math.floor(getWidth()*54/782);
		int height = (int)Math.floor(getHeight()*29/488);
		int x1 = ((player.x+4-width/2)<0)?0:(player.x+4-width/2);
		int maxX = x1+width>win.getMap().getWidth()?win.getMap().getWidth()-x1:width;
		int y1 = ((player.y+4-height/2)<0)?0:(player.y+4-height/2);
		int maxY = y1+height>win.getMap().getHeight()?win.getMap().getHeight()-y1:height;
		window = new Rectangle(x1, y1, maxX, maxY);
	}
	
	public void refreshRectangles() {
		if(!player.contains(win.getMap().getPlayer().pos)) {
			calculatePlayerRectangle();
		}
		calculateWindowRectangle();
	}
	
	public void refreshFireLine() {
		if(win.getMap().isFireMode()) {
			int playerX = win.getMap().getPlayer().pos.x,
					playerY = win.getMap().getPlayer().pos.y,
					fireX = win.getMap().getFirePoint().x - playerX,
					fireY = win.getMap().getFirePoint().y - playerY;
			// Detect the octant
			int octant=0;
			if(fireX>=0) {
				if(fireY>=0) {
					octant = (fireX>fireY)? 0 : 1;
				} else {
					octant = (fireX>-fireY)? 7 : 6;
				}
			} else {
				if(fireY>=0) {
					octant = (-fireX>fireY)? 3 : 2;
				} else {
					octant = (-fireX>-fireY)? 4 : 5;
				}
			}
			Point p = Resources.switchToOctantZeroFrom(octant, fireX, fireY);		
			fireLine = Resources.drawLine(0, 0, p.x, p.y, octant);
		}
	}
	
	protected void refreshTable() {
		this.table="";
		Tile[][] table = win.getMap().getTable();	
		for(int i=0; i<table.length; i++) {
			for(int j=0; j<table[0].length; j++) {
				this.table+=table[i][j].getSymbol();
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 14));
		
		if(dirty) { refreshTable(); dirty=false; }
		
		refreshFireLine();
		
		refreshRectangles();
			
		int offsetX=15, offsetY=30, playerOffsetX=15, playerOffsetY=30;
		char[] c = new char[1];
		for(int i=window.y; i<window.getHeight()+window.y; i++) {
			for(int j=window.x; j<window.getWidth()+window.x; j++) {
				if(win.getMap().isFireMode() &&
						i==win.getMap().getFirePoint().y &&
						j==win.getMap().getFirePoint().x) {
					c[0] = 'X';
					g.setColor(Resources.white);
				} else {
					c[0]=table.charAt(i*win.getMap().getWidth()+j);
					prepareColor(g, win.getMap().getTable(), i, j);
				}
				g.drawChars(c, 0, 1, offsetX, offsetY);
				if(table.charAt(i*win.getMap().getWidth()+j)==TileFactory.getInstance().createTilePlayer().getSymbol()) { 
					printLooker(g, offsetX, offsetY); 
					playerOffsetX = offsetX;
					playerOffsetY = offsetY;
				}
				offsetX+=14; 
			}
			offsetY+=16; 
			offsetX=15;
		}
		
		if(Resources.getInstance().commandsHelp) {
			printCommandsHelp(g, playerOffsetX, playerOffsetY);
		}
	}
}
