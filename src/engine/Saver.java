package engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import objects.item.Equipement;
import objects.item.Item;
import objects.item.Weapon;
import objects.mob.Monster;
import rooms.Corridor;
import rooms.RectangleRoom;
import rooms.Room;

public class Saver {
	protected Dungeon dungeon;
	
	public Saver(Dungeon d) {
		this.dungeon = d;
	}
	
	public void save(String filename) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("saves/"+filename))) {
			/* Resources options */
			bw.write(Resources.getInstance().difficulty);
			bw.write(Resources.getInstance().resolution);
			if(Resources.getInstance().commandsHelp) { bw.write(1); }
			else { bw.write(0); }
			bw.append('\n');
			/* Resources commands */
			bw.append(Resources.Commands.Up.getKey());
			bw.append(Resources.Commands.Right.getKey());
			bw.append(Resources.Commands.Down.getKey());
			bw.append(Resources.Commands.Left.getKey());
			bw.append(Resources.Commands.Take.getKey());
			bw.append(Resources.Commands.Inventory.getKey());
			bw.append(Resources.Commands.QuickAction1.getKey());
			bw.append(Resources.Commands.QuickAction2.getKey());
			bw.append(Resources.Commands.Pause.getKey());
			bw.append(Resources.Commands.Restart.getKey());
			bw.append('\n');
			
			/* Player basic info */
			bw.write(this.dungeon.getPlayer().description+'\n');
			bw.write(this.dungeon.getPlayer().pos.x);
			bw.write(this.dungeon.getPlayer().pos.y);
			bw.write(this.dungeon.getPlayer().hp);
			bw.write(this.dungeon.getPlayer().maxHealth);
			bw.write(this.dungeon.getPlayer().atk);
			bw.write(this.dungeon.getPlayer().bonusAtk);
			bw.write(this.dungeon.getPlayer().def);
			bw.write(this.dungeon.getPlayer().bonusDef);
			bw.write(this.dungeon.getPlayer().vit);
			bw.write(this.dungeon.getPlayer().bonusVit);
			bw.write(this.dungeon.getPlayer().getGold());
			bw.write(this.dungeon.getPlayer().getKills());
			bw.write(this.dungeon.getPlayer().getPotionEffect());
			bw.write(this.dungeon.getPlayer().getEffect().getId());
			bw.append('\n');
			/* Player's helmet */
			bw.write(this.dungeon.getPlayer().getHelmet().getType()+'\n');
			bw.write(this.dungeon.getPlayer().getHelmet().getVal());
			bw.write(this.dungeon.getPlayer().getHelmet().getDurability());
			bw.write(this.dungeon.getPlayer().getHelmet().getMaxDurability());
			bw.write(this.dungeon.getPlayer().getHelmet().getEffect().getId());
			bw.append('\n');
			/* Player's weapon */
			if(this.dungeon.getPlayer().getWeapon() instanceof Weapon) { bw.write(1); }
			else { bw.write(0); }
			bw.write(this.dungeon.getPlayer().getWeapon().getVal());
			bw.write(this.dungeon.getPlayer().getWeapon().getDurability());
			bw.write(this.dungeon.getPlayer().getWeapon().getMaxDurability());
			bw.write(this.dungeon.getPlayer().getWeapon().getEffect().getId());
			bw.append('\n');
			/* Player's shield */
			bw.write(this.dungeon.getPlayer().getShield().getVal());
			bw.write(this.dungeon.getPlayer().getShield().getDurability());
			bw.write(this.dungeon.getPlayer().getShield().getMaxDurability());
			bw.write(this.dungeon.getPlayer().getShield().getEffect().getId());
			bw.append('\n');
			/* Player inventory */
			bw.write(this.dungeon.getPlayer().getInventory().getMaxSize());
			bw.write(this.dungeon.getPlayer().getInventory().getSize());
			bw.append('\n');
			for(int i=0; i<this.dungeon.getPlayer().getInventory().getSize(); i++) {
				Item current = this.dungeon.getPlayer().getInventory().get(i);
				int itemId = Resources.getItemIdFrom(current);
				bw.write(itemId);
				if(itemId > 2) {
					if(((Equipement)current).isEquiped()) { bw.write(1); }
					else { bw.write(0); }
					bw.write(((Equipement)current).getDurability());
					bw.write(((Equipement)current).getMaxDurability());
					bw.write(((Equipement)current).getEffect().getId());
				}
				bw.write(current.getVal());
				if(current == this.dungeon.getPlayer().getInventory().getQuickItem1()) { bw.write(1); }
				else if(current == this.dungeon.getPlayer().getInventory().getQuickItem2()) { bw.write(2); }
				else { bw.write(0); }
				bw.append('\n');
			}
			
			/* Dungeon */
			bw.write(this.dungeon.currentLevel);
			bw.write(this.dungeon.levels.size());
			bw.append('\n');
			
			/* Maps */
			for(int i=0; i<this.dungeon.levels.size(); i++) {
				Map m = this.dungeon.levels.get(i);
				
				/* Rooms */
				bw.write(m.rooms.size());
				bw.append('\n');
				for(int j=0; j<m.rooms.size(); j++) {
					Room r = m.rooms.get(j);
					if(r instanceof Corridor) { bw.write(0); }
					else { 
						bw.write(1); 
						bw.write(((RectangleRoom)r).getMossSize());
						for(int moss=0; moss < ((RectangleRoom)r).getMossSize(); moss++) {
							bw.write(((RectangleRoom)r).getMossAt(moss).x);
							bw.write(((RectangleRoom)r).getMossAt(moss).y);
						}
						bw.write(((RectangleRoom)r).getDirtSize());
						for(int dirt=0; dirt < ((RectangleRoom)r).getDirtSize(); dirt++) {
							bw.write(((RectangleRoom)r).getDirtAt(dirt).x);
							bw.write(((RectangleRoom)r).getDirtAt(dirt).y);
						}
					}
					bw.append('\n');
					bw.write(r.toString()+'\n');
					bw.write(r.p1.x);
					bw.write(r.p1.y);
					bw.write(r.p2.x);
					bw.write(r.p2.y);
					if(r.isVisible()) { bw.write(1); }
					else { bw.write(0); }
					bw.append('\n');
					bw.write(r.door.size());
					for(int door=0; door < r.door.size(); door++) {
						bw.write(r.door.get(door).getX());
						bw.write(r.door.get(door).getY());
						if(r.door.get(door).isOpen()) { bw.write(1); }
						else { bw.write(0); }
					}
					bw.append('\n');
				}
				
				/* Monsters */
				bw.write(m.monsters.size());
				bw.append('\n');
				for(int j=0; j<m.monsters.size(); j++) {
					Monster monster = m.monsters.get(j);
					
					System.out.println(monster.getSymbol()+" ("+monster.pos.x+","+monster.pos.y+") "+((monster.isDead())?"dead":"alive"));
					
					bw.write(monster.description+"\n");
					bw.write(monster.pos.x);
					bw.write(monster.pos.y);
					bw.write((monster.isDead())?1:0);
					if(!monster.isDead()) {
						bw.write(monster.hp);
						bw.write(monster.maxHealth);
						bw.write(monster.atk);
						bw.write(monster.def);
						bw.write(monster.vit);
						bw.write(monster.getEffect().getId());
						bw.write(monster.getEffectSpreader().getId());
					}
					bw.append('\n');
				}
				
				/* Items */
				bw.write(m.items.size());
				bw.append('\n');
				for(int j=0; j<m.items.size(); j++) {
					Item item = m.items.get(j);
					int itemId = Resources.getItemIdFrom(item);
					bw.write(item.pos.x);
					bw.write(item.pos.y);
					bw.write(itemId);
					if(itemId > 2 && itemId < 10) {
						bw.write(((Equipement)item).getDurability());
						bw.write(((Equipement)item).getMaxDurability());
						bw.write(((Equipement)item).getEffect().getId());
					}
					bw.write(item.getVal());
				}
				bw.append('\n');
				
				/* Stairs */
				bw.write(m.stairDown.x);
				bw.write(m.stairDown.y);
				bw.write(m.stairUp.x);
				bw.write(m.stairUp.y);
				bw.append('\n');
				
				/* Height and width */
				bw.write(m.height);
				bw.write(m.width);
				bw.append('\n');
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Saved "+filename+" !");
		
		Loader l = new Loader(this.dungeon);
		l.load(filename);
	}
}
