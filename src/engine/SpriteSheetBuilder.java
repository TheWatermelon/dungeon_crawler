package engine;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheetBuilder {

	private BufferedImage spriteSheet;
	private int rows;
	private int cols;
	private int spriteWidth;
	private int spriteHeight;
	private int spriteCount;

	public SpriteSheetBuilder withSpriteSheet(BufferedImage img) {
		this.spriteSheet = img;
		return this;
	}

	public SpriteSheetBuilder withRows(int rows) {
		this.rows = rows;
		return this;
	}

	public SpriteSheetBuilder withCols(int cols) {
		this.cols = cols;
		return this;
	}

	public SpriteSheetBuilder withSpriteSize(int width, int height) {
		this.spriteWidth = width;
		this.spriteHeight = height;
		return this;
	}

	public SpriteSheetBuilder withSpriteCount(int count) {
		this.spriteCount = count;
		return this;
	}
	
	public BufferedImage getSpriteSheet() {
		return this.spriteSheet;
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public int getCols() {
		return this.cols;
	}
	
	public int getSpriteWidth() {
		return this.spriteWidth;
	}
	
	public int getSpriteHeight() {
		return this.spriteHeight;
	}
	
	public int getSpriteCount() {
		return this.spriteCount;
	}
	
	public SpriteSheet build() {
		int count = getSpriteCount();
		int rows = getRows();
		int cols = getCols();
		
		if(count == 0) {
			count = rows * cols;
		}
		
		BufferedImage sheet = getSpriteSheet();
		
		int width = getSpriteWidth();
		int height = getSpriteHeight();
		
		if(width == 0) {
			width = sheet.getWidth() / cols;
		}
		
		if(height == 0) {
			height = sheet.getHeight() / rows;
		}
		
		int x = 0;
		int y = 0;
		
		ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>(count);
		
		for(int index = 0; index < count; index++) {
			sprites.add(sheet.getSubimage(x, y, width, height));
			y += height;
			if(y >= height * rows) {
				y = 0;
				x += width;
			}
		}
		
		return new SpriteSheet(sprites);
	}
	
}
