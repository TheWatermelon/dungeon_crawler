package engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class SpriteSheet {
	private ArrayList<BufferedImage> sprites16;
	private ArrayList<BufferedImage> coloredSprites16;
	private ArrayList<BufferedImage> sprites32;
	private ArrayList<BufferedImage> coloredSprites32;
	private Color[] spritesColor;
	
	/*
	//// TEST ////
	JPanel spritesTable;
	JPanel coloredSpritesTable;
	////////////////////
	 */
	
	public SpriteSheet(ArrayList<BufferedImage> sprites) {
		this.sprites16 = new ArrayList<BufferedImage>(sprites);
		this.coloredSprites16 = new ArrayList<BufferedImage>();
		for(BufferedImage sprite : this.sprites16) {
			this.coloredSprites16.add(SpriteSheet.copyImage(sprite));
		}
		this.sprites32 = new ArrayList<BufferedImage>();
		this.coloredSprites32 = new ArrayList<BufferedImage>();
		this.spritesColor = new Color[this.sprites16.size()];
		for(int i=0; i<sprites.size(); i++) { spritesColor[i] = Color.BLACK; }
		/*
		for(int i=0; i<sprites.size(); i++) { 
			BufferedImage sprite32 = this.doubleSpriteSize(sprites16.get(i));
			this.sprites32.add(sprite32);
			this.coloredSprites32.add(sprite32);
		}
		*/
		
		/*
		//// TEST ////
		JFrame spriteFrame = new JFrame("Sprites");
		spriteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.spritesTable = new SpritesTable(sprites16, coloredSprites16);
		spriteFrame.setSize(spritesTable.getSize());
		spriteFrame.add(spritesTable);
		spriteFrame.setVisible(true);
		//////////////
		 */
	}
	
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics2D g = b.createGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
	
	public int count() {
		return this.sprites16.size();
	}
	
	public BufferedImage getSprite16(int index) {
		return this.sprites16.get(index);
	}

	public BufferedImage getSprite32(int index) {
		return this.sprites32.get(index);
	}

	public BufferedImage getColoredSprite16(BufferedImage sprite, Color c) {
		int spriteIndex = this.sprites16.indexOf(sprite);
		if(this.spritesColor[spriteIndex].getRGB() == c.getRGB()) {
			return this.coloredSprites16.get(spriteIndex);
		} else {
			BufferedImage newSprite = SpriteSheet.copyImage(this.sprites16.get(spriteIndex));
			this.spritesColor[spriteIndex] = c;
			Color darkC = c.darker();
	
			for(int i=0; i<sprite.getWidth(); i++) {
				for(int j=0; j<sprite.getHeight(); j++) {
					if(sprite.getRGB(i,j)<0) {
						if(sprite.getRGB(i,j)==Color.BLACK.getRGB()) {
							newSprite.setRGB(i, j, c.getRGB());
						} else {
							newSprite.setRGB(i, j, darkC.getRGB());
						}
					} else {
						newSprite.setRGB(i, j, 0);
					}
				}
			}
			this.coloredSprites16.set(spriteIndex, newSprite);
			return newSprite;
		}

	}
	
	public BufferedImage getColoredSprite32(BufferedImage sprite, Color c) {
		int spriteIndex = this.sprites32.indexOf(sprite);
		if(this.spritesColor[spriteIndex] == c) {
			return this.coloredSprites32.get(spriteIndex);
		} else {
			BufferedImage newSprite = SpriteSheet.copyImage(this.coloredSprites32.get(spriteIndex));
			this.spritesColor[spriteIndex] = c;
			Color darkC = c.darker();
	
			for(int i=0; i<sprite.getWidth(); i++) {
				for(int j=0; j<sprite.getHeight(); j++) {
					if(sprite.getRGB(i,j)<0) {
						if(sprite.getRGB(i,j)==Color.BLACK.getRGB()) {
							newSprite.setRGB(i, j, c.getRGB());
						} else {
							newSprite.setRGB(i, j, darkC.getRGB());
						}
					} else {
						newSprite.setRGB(i, j, 0);
					}
				}
			}
			this.coloredSprites32.set(spriteIndex, newSprite);
			return newSprite;
		}

	}

	public BufferedImage doubleSpriteSize(BufferedImage sprite) {
		BufferedImage newSprite = new BufferedImage(2*sprite.getWidth(), 2*sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for(int i = 0; i < newSprite.getWidth(); i += 2) {
			for(int j = 0; j < newSprite.getHeight(); j += 2) {
				int c = sprite.getRGB(i/2, j/2);
				newSprite.setRGB(i, j, c);
				newSprite.setRGB(i+1, j, c);
				newSprite.setRGB(i+1, j+1, c);
				newSprite.setRGB(i, j+1, c);
			}
		}

		return newSprite;
	}
}
