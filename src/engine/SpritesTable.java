package engine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class SpritesTable extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage[] sprites;

	public SpritesTable(BufferedImage[] sprites) {
		super();
		this.sprites = sprites;
		this.setMinimumSize(new Dimension(13*32, 10*32));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		int offsetX=0, offsetY=0;
		for(int i=0; i<13; i++) {
			for(int j=0; j<10; j++) {
				g2d.drawImage(sprites[i*10+j], offsetX, offsetY, this);
				offsetY+=32;
			}
			offsetX+=32;
			offsetY=0;
		}
	}
}
