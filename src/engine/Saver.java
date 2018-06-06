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
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("doc/test.save"))) {
			bw.write(Resources.getInstance().difficulty);
			bw.write(Resources.getInstance().resolution);
			if(Resources.getInstance().commandsHelp) { bw.write(1); }
			else { bw.write(0); }
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
