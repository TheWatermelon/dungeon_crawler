package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import engine.Resources;
import tiles.Tile;
import tiles.TileFactory;

public class TileGrid extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Tile[] table;
	public int res;

	public TileGrid(int res) {
		super();
		this.res = res;
		this.setPreferredSize(new Dimension((res*2)*16, res*16));
		this.table = new Tile[(res*2)*res];
		for(int i=0; i<(res*2)*res; i++) {
			this.table[i] = TileFactory.getInstance().createTileVoid();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, res*2*16, res*16);

		Graphics2D g2d = (Graphics2D) g.create();
		
		int offsetX = 0, offsetY = 0;
		for(int i = 0; i < res; i++) {
			for(int j = 0; j < res*2; j++) {
				Tile tile = this.table[i*16+j];
				BufferedImage sprite = Resources.getInstance().sprites.getSprite16((int)tile.getSymbol());
				sprite = Resources.getInstance().sprites.getColoredSprite16(sprite, tile.getColor());
				g2d.drawImage(sprite, offsetX, offsetY, this);
				g.setColor(Color.gray);
				g.drawRect(offsetX, offsetY, 16, 16);
				offsetX += 16;
			}
			offsetX = 0;
			offsetY += 16;
		}
	}
}
