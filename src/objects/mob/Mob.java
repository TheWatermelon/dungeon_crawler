package objects.mob;

import java.awt.Color;
import java.awt.Point;

import engine.MessageLog;
import objects.effect.Effect;
import objects.effect.EffectNormal;
import objects.looker.Looker;
import tiles.Tile;
import tiles.TileGold;
import tiles.TileMob;

public abstract class Mob {
	public Point pos;
	protected boolean dead;
	protected char symbol;
	protected Looker looker;
	protected Tile floor;
	protected Tile mobTile;
	protected MessageLog log;

	public String description;
	public int hp;
	public int maxHealth;
	public int atk;
	public int def;
	public int vit;
	public Effect effect;

	public abstract void murder();

	public abstract int getAtk();
	public abstract int getDef();
	
	public final Effect getEffect() { return this.effect; }
	public final void setEffect(Effect val) { this.effect = val; }
	
	public final void resetHealth() {
		this.hp = this.maxHealth;
	}
	
	public final void placeOn(int x, int y) {
		this.pos = new Point(x, y);
	}
	
	public final void setFloor(Tile t) {
		if(!(t instanceof TileMob || t instanceof TileGold)) {
			this.floor = t;
		}
	}
	
	public final Tile getFloor() {
		return this.floor;
	}
	
	public final Tile getMobTile() {
		return this.mobTile;
	}
	
	public Color getColor() {
		return (effect instanceof EffectNormal)?mobTile.getColor():effect.getColor();
	}
	
	
	public final boolean isDead() { return this.dead; }
	
	public final char getSymbol() { return this.symbol; }
		
	public final MessageLog getLog() { return this.log; }

	public final void setLooker(Looker l) {
		this.looker = l;
		this.looker.show();
	}
	
	public final Looker getLooker() {
		this.looker.placeOn(pos.x, pos.y);
		return this.looker;
	}
	
	public final String drawHealthBar() {
		String s="[";
		
		for(int i=0; i<20; i++) {
			if(i==8 && hp>99) {	// Show life count on bar
				s+=hp/10;
				i++;
			} else if(i==9 && hp>9 && hp<100) {
				s+=(hp/10);
			} else if(i==10) {
				s+=hp%10;
			} else {
				if(i<((int)20*hp/maxHealth)) {
					s+="|";
				} else {
					s+=" ";
				}
			}
		}
		s+="]";
		return s;
	}
	
	public String toString() { return this.description; }
}
