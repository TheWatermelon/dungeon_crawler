package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Loader {
	public Loader() {
	}
	
	public Dungeon load(String filename) {
		try (BufferedReader br = new BufferedReader(new FileReader("saves/"+filename))) {
			int difficulty = br.read();			// Difficulty
			Resources.getInstance().difficulty = difficulty;
			System.out.println("difficulty: "+difficulty);
			int resolution = br.read();			// Resolution
			Resources.getInstance().resolution = resolution;
			System.out.println("resolution: "+resolution);
			int commHelp = br.read();			// Commands help (1 -> true)
			Resources.getInstance().commandsHelp = (commHelp==1)?true:false;
			System.out.println("commandsHelp: "+commHelp);
			br.readLine();						// new line
			
			String commands = br.readLine();	// Commands
			Resources.Commands.Up.setKey(commands.charAt(0));
			Resources.Commands.Right.setKey(commands.charAt(1));
			Resources.Commands.Down.setKey(commands.charAt(2));
			Resources.Commands.Left.setKey(commands.charAt(3));
			Resources.Commands.Take.setKey(commands.charAt(4));
			Resources.Commands.Inventory.setKey(commands.charAt(5));
			Resources.Commands.QuickAction1.setKey(commands.charAt(6));
			Resources.Commands.QuickAction2.setKey(commands.charAt(7));
			Resources.Commands.Pause.setKey(commands.charAt(8));
			Resources.Commands.Restart.setKey(commands.charAt(9));
			System.out.println("commands: "+commands);
			
			String desc = br.readLine();		// Player name
			System.out.println("description: "+desc);

			int hp = br.read();					// Player hp
			System.out.println("hp: "+hp);
			int maxHealth = br.read();			// Player maxHealth
			System.out.println("maxHealth: "+maxHealth);
			int atk = br.read();				// Player atk
			System.out.println("atk: "+atk);
			int bonusAtk = br.read();			// Player bonusAtk
			System.out.println("bonusAtk: "+bonusAtk);
			int def = br.read();				// Player def
			System.out.println("def: "+def);
			int bonusDef = br.read();			// Player bonusDef
			System.out.println("bonusDef: "+bonusDef);
			int vit = br.read();				// Player vit
			System.out.println("vit: "+vit);
			int bonusVit = br.read();			// Player bonusVit
			System.out.println("bonusVit: "+bonusVit);
			int gold = br.read();				// Player gold
			System.out.println("gold: "+gold);
			int monstersKilled = br.read();		// Player monsters killed
			System.out.println("monstersKilled: "+monstersKilled);
			int potionEffect = br.read();		// Player potion effect
			System.out.println("potionEffect: "+potionEffect);
			br.readLine();						// new line
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
