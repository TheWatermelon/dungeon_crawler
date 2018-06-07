package engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Saver {
	protected Dungeon dungeon;
	
	public Saver(Dungeon d) {
		this.dungeon = d;
	}
	
	public void save(String filename) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("saves/"+filename))) {
			bw.write(Resources.getInstance().difficulty);
			bw.write(Resources.getInstance().resolution);
			if(Resources.getInstance().commandsHelp) { bw.write(1); }
			else { bw.write(0); }
			bw.append('\n');
			
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
			
			bw.write(this.dungeon.getPlayer().description+'\n');

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
			bw.append('\n');
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Saved !");
		
		Loader l = new Loader();
		l.load(filename);
	}
}
