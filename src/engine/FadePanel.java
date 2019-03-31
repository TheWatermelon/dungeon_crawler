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
		this.fadeColor = c;
	}

	public void resetFading() {
		this.fadeColor = this.initColor;
		this.timer.stop();
	}

	public void startFading() {
		this.timer.start();
	}

	public void actionPerformed(ActionEvent ae) {
		if (this.fadeColor.getRGB() != this.initColor.getRGB()) {
			int step = 10;
			int red=this.fadeColor.getRed(), green=this.fadeColor.getGreen(), blue=this.fadeColor.getBlue();
			if(this.fadeColor.getRed() > this.initColor.getRed()) {
				red = (this.fadeColor.getRed()-step >= this.initColor.getRed())? this.fadeColor.getRed()-step: this.initColor.getRed();
			} else if(this.fadeColor.getRed() < this.initColor.getRed()) {
				red = (this.fadeColor.getRed()+step <= this.initColor.getRed())? this.fadeColor.getRed()+step: this.initColor.getRed();
			}
			if(this.fadeColor.getGreen() > this.initColor.getGreen()) {
				green = (this.fadeColor.getGreen()-step >= this.initColor.getGreen())? this.fadeColor.getGreen()-step: this.initColor.getGreen();
			} else if(this.fadeColor.getGreen() < this.initColor.getGreen()) {
				green = (this.fadeColor.getGreen()+step <= this.initColor.getGreen())? this.fadeColor.getGreen()+step: this.initColor.getGreen();
			}
			if(this.fadeColor.getBlue() > this.initColor.getBlue()) {
				blue = (this.fadeColor.getBlue()-step >= this.initColor.getBlue())? this.fadeColor.getBlue()-step: this.initColor.getBlue();
			} else if(this.fadeColor.getBlue() < this.initColor.getBlue()) {
				blue = (this.fadeColor.getBlue()+step <= this.initColor.getBlue())? this.fadeColor.getBlue()+step: this.initColor.getBlue();
			}
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
		g.setColor(this.fadeColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
