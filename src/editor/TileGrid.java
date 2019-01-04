package editor;

import java.awt.Dimension;

import javax.swing.JPanel;

import tiles.TileFactory;

public class TileGrid extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public TileCell[] table;
	public int res;
	public ToolsPanel tools;
	public boolean focused;

	public TileGrid(int res, ToolsPanel t) {
		super();
		
		this.res = res;
		this.tools = t;
		this.focused = false;
		
		this.setPreferredSize(new Dimension((res*2)*16, res*16));
		
		this.table = new TileCell[(res*2)*res];
		for(int i=0; i<(res*2)*res; i++) {
			this.table[i] = new TileCell(TileFactory.getInstance().createTileVoid());
			// mouse listener
			System.out.println(this.table[i]);
			this.add(this.table[i]);
		}
	}
	
	/*
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, res*2*16, res*16);

		Graphics2D g2d = (Graphics2D) g.create();
		
		int offsetX = 0, offsetY = 0;
		for(int i = 0; i < res; i++) {
			for(int j = 0; j < res*2; j++) {
				Tile tile = this.table[i*16+j].tile;
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
	*/
}
