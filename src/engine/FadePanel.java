package engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FadePanel extends JComponent implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Color initColor;
	private Color fadeColor;
	private Timer timer;

	public FadePanel(Color c) {
		this.initColor = c;
		this.timer = new Timer(30, this);
		this.resetFading();
	}

	public void setColor(Color c) {
		this.initColor = c;
	}

	public void resetFading() {
		this.fadeColor = this.initColor;
		this.timer.stop();
	}

	public void startFading() {
		this.timer.start();
	}

	public void actionPerformed(ActionEvent ae) {
		if (this.fadeColor.getRGB() != Color.BLACK.getRGB()) {
			int step = 10;
			int red = (this.fadeColor.getRed()-step >= 0)? this.fadeColor.getRed()-step: 0;
			int green = (this.fadeColor.getGreen()-step >= 0)? this.fadeColor.getGreen()-step: 0;
			int blue = (this.fadeColor.getBlue()-step >= 0)? this.fadeColor.getBlue()-step: 0;
			//System.out.println(red+","+green+","+blue);
			this.fadeColor = new Color(red, green, blue);
			this.repaint();
		} else {
			this.timer.stop();
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(50, 100);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//System.out.println("paint");
		g.setColor(this.fadeColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}