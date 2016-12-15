package engine.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import engine.Resources;
import engine.Window;
import objects.item.*;

public class InventoryMenu extends Menu {
	private static final long serialVersionUID = 1L;
	
	protected Inventory inv;

	public InventoryMenu(Window win) {
		super(win);
		inv = win.getMap().getPlayer().getInventory();
		items = new String[inv.getMaxSize()+1];
		initPanel();
	}
	
	public void destroyItem() {
		if(focusedItem<inv.getSize()) {
			inv.removeItem(inv.get(focusedItem));
			initPanel();
		}
	}

	@Override
	public void selectFocusedItem() {
		if(focusedItem<inv.getSize()) {
			inv.use(inv.get(focusedItem));
			initPanel();
		} else if(focusedItem==inv.getMaxSize()) {
			focusedItem=0;
			win.showDungeon();
		}
	}

	@Override
	protected void initPanel() {
		for(int i=0; i<inv.getMaxSize(); i++) {
			if(i<inv.getSize()) {
				if(inv.get(i) instanceof Equipement) {
					if(((Equipement)inv.get(i)).isEquiped()) {
						items[i]="[E] ";
					} else {
						items[i]="";
					}
				} else {
					items[i]="";
				}
				items[i]+=inv.get(i).toString()+" ("+((Equipement)inv.get(i)).getDurability()+"/"+((Equipement)inv.get(i)).getMaxDurability()+")";
			} else {
				items[i]="- empty -";
			}
		}
		items[inv.getMaxSize()]="Back";
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		String name = win.getMap().getPlayer().toString(), healthBar = win.getMap().getPlayer().drawHealthBar();
		g.drawString(name, getWidth()/2-(name.length()*13/2), 80);
		g.drawString(healthBar, getWidth()/2-(healthBar.length()*12/2), 100);

		g.setFont(new Font("Monospaced", Font.PLAIN, 50));
		g.setColor(win.getMap().getPlayer().getLooker().getLeftColor());
		g.drawString(""+win.getMap().getPlayer().getLooker().getLeft(), getWidth()/2-45, 150);
		g.setColor(win.getMap().getPlayer().getColor());
		g.drawString(""+win.getMap().getPlayer().getSymbol(), getWidth()/2-15, 150);
		g.setColor(win.getMap().getPlayer().getLooker().getRightColor());
		g.drawString(""+win.getMap().getPlayer().getLooker().getRight(), getWidth()/2+15, 150);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		g.drawString("Inventory", getWidth()/2-(9*13/2), getHeight()/2-87);
		
		initPanel();
		
		int offsetY=getHeight()/2-37;
		for(int i=0; i<items.length; i++) {
			if(i == focusedItem) { g.setColor(Resources.orange); } 
			else { g.setColor(Resources.white); }
			int offsetX = items[i].length()*13/2;
			g.drawString(items[i], getWidth()/2-offsetX, offsetY);
			offsetY+=25;
		}
		
		String commands=Resources.Commands.Up.getKey()+": Up, "+
						Resources.Commands.Down.getKey()+": Down, "+
						Resources.Commands.Take.getKey()+": Use/Equip, "+
						Resources.Commands.Left.getKey()+"/"+
						Resources.Commands.Right.getKey()+": Drop";
		g.setColor(Resources.white);
		g.drawString(commands, getWidth()/2-(commands.length()*13/2), getHeight()-30);
	}
}
