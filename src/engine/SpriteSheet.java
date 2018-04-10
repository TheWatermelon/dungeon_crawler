package engine;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheet {
	private ArrayList<BufferedImage> sprites16;
	private ArrayList<BufferedImage> coloredSprites16;
	private ArrayList<BufferedImage> sprites32;
	private ArrayList<BufferedImage> coloredSprites32;
	private Color[] spritesColor;
	
	public SpriteSheet(ArrayList<BufferedImage> sprites) {
		this.sprites16 = new ArrayList<BufferedImage>(sprites);
		this.coloredSprites16 = new ArrayList<BufferedImage>(sprites);
		this.sprites32 = new ArrayList<BufferedImage>();
		this.coloredSprites32 = new ArrayList<BufferedImage>();
		this.spritesColor = new Color[this.sprites16.size()];
		for(int i=0; i<sprites.size(); i++) { spritesColor[i] = Color.BLACK; }
		for(int i=0; i<sprites.size(); i++) { 
			this.sprites32.add(this.doubleSpriteSize(sprites16.get(i)));
			this.coloredSprites32.add(this.doubleSpriteSize(sprites16.get(i)));
		}
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
		if(this.spritesColor[this.sprites16.indexOf(sprite)] == c) {
			return this.coloredSprites16.get(this.sprites16.indexOf(sprite));
		} else {
			BufferedImage newSprite = this.coloredSprites16.get(this.sprites16.indexOf(sprite));
			this.spritesColor[this.sprites16.indexOf(sprite)] = c;
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
			return newSprite;
		}

	}
	
	public BufferedImage getColoredSprite32(BufferedImage sprite, Color c) {
		if(this.spritesColor[this.sprites32.indexOf(sprite)] == c) {
			return this.coloredSprites32.get(this.sprites32.indexOf(sprite));
		} else {
			BufferedImage newSprite = this.coloredSprites32.get(this.sprites32.indexOf(sprite));
			this.spritesColor[this.sprites32.indexOf(sprite)] = c;
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
