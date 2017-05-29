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
	
	public void dropItem() {
		if(focusedItem<inv.getSize()) {
			inv.dropItem(inv.get(focusedItem));
			initPanel();
		}
	}
	
	public void repareItem() {
		if(focusedItem<inv.getSize()) {
			inv.repareItem(inv.get(focusedItem));
			initPanel();
		}
	}
	
	@Override
	public void incFocusedItem() {
		focusedItem++;
		while(focusedItem>=inv.getSize()&&focusedItem<inv.getMaxSize()) { focusedItem++; }
		if(focusedItem==items.length) { focusedItem=0; }
	}
	
	@Override
	public void decFocusedItem() {
		focusedItem--;
		while(focusedItem>=inv.getSize()&&focusedItem<inv.getMaxSize()) { focusedItem--; }
		if(focusedItem<0) { focusedItem = items.length-1; }
	}
	
	@Override
	public void exitMenu() {
		focusedItem=0;
		win.showDungeon();
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
		if(inv.getSize()==0) { focusedItem=inv.getMaxSize(); }
		for(int i=0; i<inv.getMaxSize(); i++) {
			if(i<inv.getSize()) {
				if(inv.get(i) instanceof Equipement) {
					if(((Equipement)inv.get(i)).isEquiped()) {
						items[i]="[E] ";
					} else {
						items[i]="";
					}
					items[i]+=inv.get(i).toString()+" ("+((Equipement)inv.get(i)).getDurability()+"/"+((Equipement)inv.get(i)).getMaxDurability()+")";
				} else {
					items[i]=inv.get(i).toString()+" ("+inv.get(i).getVal()+")";
				}
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
		String name = win.getMap().getPlayer().toString(), 
				healthBar = win.getMap().getPlayer().drawHealthBar(),
				stuff = win.getMap().getPlayer().getWeaponInfo();
		g.drawString(name, getWidth()/2-(name.length()*13/2), 60);
		g.drawString(healthBar, getWidth()/2-(healthBar.length()*12/2), 80);
		g.drawString(stuff, getWidth()/2-(stuff.length()*12/2), 100);

		g.setFont(new Font("Monospaced", Font.PLAIN, 50));
		g.setColor(win.getMap().getPlayer().getLooker().getLeftColor());
		g.drawString(""+win.getMap().getPlayer().getLooker().getLeft(), getWidth()/2-45, 150);
		g.setColor(win.getMap().getPlayer().getColor());
		g.drawString(""+win.getMap().getPlayer().getSymbol(), getWidth()/2-15, 150);
		g.setColor(win.getMap().getPlayer().getLooker().getRightColor());
		g.drawString(""+win.getMap().getPlayer().getLooker().getRight(), getWidth()/2+15, 150);
		if(win.getMap().getPlayer().getHelmet().getMaxDurability()!=-1) {
			g.setColor(win.getMap().getPlayer().getHelmet().getColor());
			g.drawString(""+win.getMap().getPlayer().getHelmet().getTile().getSymbol(), getWidth()/2-13, 140);
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		g.drawString("Inventory", getWidth()/2-(9*13/2), getHeight()/2-87);
		
		initPanel();
		
		String gold = "Gold : "+win.getMap().getPlayer().getGold();
		g.setColor(Resources.yellow);
		g.drawString(gold, getWidth()/2-(gold.length()*13/2), getHeight()/2-37);
		
		int offsetY=getHeight()/2-12;
		for(int i=0; i<items.length; i++) {
			if(i == focusedItem) { g.setColor(Resources.orange); } 
			else { g.setColor(Resources.white); }
			int offsetX = items[i].length()*13/2;
			g.drawString(items[i], getWidth()/2-offsetX, offsetY);
			if(i<inv.getSize()) {
				g.setColor(inv.get(i).getColor());
				g.drawString(""+inv.get(i).getTile().getSymbol(), getWidth()/2+offsetX, offsetY);
			}
			offsetY+=25;
		}
		
		String commands=Resources.Commands.Up.getKey()+": Up, "+
						Resources.Commands.Down.getKey()+": Down, "+
						Resources.Commands.Take.getKey()+": Use/Equip, "+
						Resources.Commands.RepareWeapon.getKey()+"/"+
						Resources.Commands.RepareShield.getKey()+": Repare, "+
						Resources.Commands.Left.getKey()+"/"+
						Resources.Commands.Right.getKey()+": Drop";
		g.setColor(Resources.white);
		g.drawString(commands, getWidth()/2-(commands.length()*13/2), getHeight()-30);
	}
}
