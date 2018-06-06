package engine.builders;

import java.util.ArrayList;

import engine.Dungeon;
import engine.Map;
import objects.mob.Player;

public class DungeonBuilder {
	protected ArrayList<Map> levels;
	protected int currentLevel;
	protected Player player;
	
	public DungeonBuilder withLevels(ArrayList<Map> levels) {
		this.levels = levels;
		return this;
	}
	
	public DungeonBuilder withCurrentLevel(int i) {
		this.currentLevel = i;
		return this;
	}
	
	public DungeonBuilder withPlayer(Player p) {
		this.player = p;
		return this;
	}
	
	public Dungeon build() {
		return new Dungeon(this.levels, this.player, this.currentLevel);
	}
}
