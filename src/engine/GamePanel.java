package engine;

import java.awt.event.KeyListener;

import javax.swing.JPanel;

public abstract class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected Window win;

	public GamePanel(Window window) {
		this.win = window;
	}

	public abstract KeyListener getKeyListener();
}
