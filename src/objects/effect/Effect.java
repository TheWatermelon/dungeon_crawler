package objects.effect;

import java.awt.Color;

import objects.mob.Mob;

public abstract class Effect {
	protected String description;
	protected int id, maxDuration, duration;
	protected Mob affected;
	
	protected Effect(String d) { this.description=d; }
	
	protected final String getDescription() { return this.description; }
	public final int getId() { return this.id; }
	public abstract String name();
	public abstract Color getColor();
	public abstract void start(Mob m);
	public abstract void stop();
	public abstract boolean apply();
	
	@Override
	public String toString() { return description; }
}
