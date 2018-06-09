package engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import objects.item.Equipement;
import objects.item.Item;
import objects.item.Weapon;

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
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Saved !");
		
		Loader l = new Loader();
		l.load(filename);
	}
}
