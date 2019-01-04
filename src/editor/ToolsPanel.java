package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JPanel;

import tiles.TileFactory;

public class ToolsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TileTool currentTile;
	
	public ToolsPanel() {
		super();
		this.setPreferredSize(new Dimension(50,600));
		this.setLayout(new FlowLayout());

		for(int i=0; i<TileFactory.getInstance().getTilesLength(); i++) {
			TileTool t = new TileTool(this, TileFactory.getInstance().getTileAt(i));
			t.addMouseListener(new ToolMouseListener(t));
			this.add(t);
		}

		this.currentTile = new TileTool(this, TileFactory.getInstance().createTileVoid());
	}
	
	public void setCurrentTile(TileTool t) {
		this.currentTile.selected = false;
		this.currentTile.repaint();
		this.currentTile = t;
		this.currentTile.selected = true;
		this.currentTile.repaint();
	}
	
	public TileTool getCurrentTile() {
		return this.currentTile;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 50, 600);
	}
}
