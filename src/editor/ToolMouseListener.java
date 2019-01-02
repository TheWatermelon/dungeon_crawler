package editor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ToolMouseListener implements MouseListener {
	private TileTool tool;
	
	public ToolMouseListener(TileTool t) {
		this.tool = t;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.tool.updateCurrentTile();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.tool.hover = true;
		this.tool.repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.tool.hover = false;
		this.tool.repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
