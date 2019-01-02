package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.Resources;
import tiles.Tile;

public class TileTool extends Tool {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ToolsPanel parent;
	private Tile t;
	public boolean hover;
	
	public TileTool(ToolsPanel p, Tile t) {
		super();
		this.setPreferredSize(new Dimension(18,18));
		this.parent = p;
		this.t = t;
		this.hover = false;
	}
	
	public Tile getTile() { return this.t; }
	
	@Override
	public void paintComponent(Graphics g) {
		// Tool background
		g.setColor(Color.black);
		g.fillRect(0, 0, 18, 18);
		
		// Tool image
		Graphics2D g2d = (Graphics2D) g.create();
		BufferedImage sprite = Resources.getInstance().sprites.getSprite16((int)t.getSymbol());
		sprite = Resources.getInstance().sprites.getColoredSprite16(sprite, this.t.getColor());
		g2d.drawImage(sprite, 1, 1, this);
		
		// Tool focus
		if(this.hover) {
			g.setColor(Color.red);
			g.drawRect(1, 1, 16, 16);
		} else {
			g.setColor(Color.white);
		}
		g.drawRect(0, 0, 18, 18);
	}
	
	public void updateCurrentTile() {
		this.parent.setCurrentTile(this.t);
	}
}
