package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import engine.Resources;
import tiles.Tile;

public class TileCell extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tile tile;
	
	public TileCell(Tile t) {
		super();
		this.setPreferredSize(new Dimension(16,16));
		this.tile = t;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 16, 16);
		Graphics2D g2d = (Graphics2D) g.create();
		BufferedImage sprite = Resources.getInstance().sprites.getSprite16((int)tile.getSymbol());
		sprite = Resources.getInstance().sprites.getColoredSprite16(sprite, this.tile.getColor());
		g2d.drawImage(sprite, 0, 0, this);
	}
}
