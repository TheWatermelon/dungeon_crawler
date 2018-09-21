package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class SpritesTable extends JPanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<BufferedImage> sprites1;
	private ArrayList<BufferedImage> sprites2;

	public SpritesTable(ArrayList<BufferedImage> sprites1, ArrayList<BufferedImage> sprites2) {
		super();
		this.sprites1 = sprites1;
		this.sprites2 = sprites2;
		this.setSize(new Dimension(13*32, 2*11*32));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g.setColor(Color.BLACK);
		g.fillRect(0, 320, 13*32, 10*32);
		
		int offsetX=0, offsetY=0;
		for(int i=0; i<13; i++) {
			for(int j=0; j<10; j++) {
				g2d.drawImage(sprites1.get(i*10+j), offsetX, offsetY, this);
				offsetY+=32;
			}
			offsetX+=32;
			offsetY=0;
		}
		
		offsetX = 0;
		offsetY = 320;
		for(int i=0; i<13; i++) {
			for(int j=0; j<10; j++) {
				g2d.drawImage(sprites2.get(i*10+j), offsetX, offsetY, this);
				offsetY+=32;
			}
			offsetX+=32;
			offsetY=320;
		}
	}
}
