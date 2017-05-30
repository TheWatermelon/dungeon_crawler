package engine.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import engine.Resources;
import engine.Window;
import objects.item.Equipement;
import objects.item.Inventory;

public class InventoryMenuGrid extends Menu {
	private static final long serialVersionUID = 1L;
	
	protected Inventory inv;
	public boolean dropWanted;
	private static final String dropText = "Press again to drop, other key to cancel";
	
	public InventoryMenuGrid(Window win) {
		super(win);
		focusedItem=0;
		dropWanted=false;
		inv = win.getMap().getPlayer().getInventory();
		items = new String[inv.getMaxSize()+1];
		initPanel();
	}
	
	public void dropItem() {
		if(!dropWanted) {
			dropWanted=true;
		} else {
			dropWanted=false;
			if(focusedItem<inv.getSize()) {
				inv.dropItem(inv.get(focusedItem));
				initPanel();
			}
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
	public void exitMenu() {
		focusedItem=0;
		win.showDungeon();
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
		
		if(dropWanted) { items[focusedItem] = dropText; }
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		String name = win.getMap().getPlayer().toString(); 
		String healthBar = win.getMap().getPlayer().drawHealthBar();
		//String stuff = win.getMap().getPlayer().getWeaponInfo();
		g.drawString(name, getWidth()/2-(name.length()*13/2), 60);
		g.drawString(healthBar, getWidth()/2-(healthBar.length()*12/2), 80);
		//g.drawString(stuff, getWidth()/2-(stuff.length()*12/2), 100);

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
		
		if(focusedItem < inv.getMaxSize()) {
			g.setColor(Resources.white);
			g.drawString(items[focusedItem], getWidth()/2-(items[focusedItem].length()*13/2), getHeight()/2);
		}
		
		int offsetY=getHeight()/2+23, focusOffsetX=0, focusOffsetY=0;
		// for each line of 3 columns
		for(int i=0; i<inv.getMaxSize()/3; i++) {
			int offsetX = getWidth()/2-(int)Math.round(1.5*35);
			// for each column
			for(int j=0; j<3; j++) {
				int index = i*3+j;
				g.setColor(Resources.white); 
				g.drawRect(offsetX, offsetY, 35, 35);
				if(index < inv.getSize()) {
					if(inv.get(index) instanceof Equipement &&
							((Equipement)inv.get(index)).isEquiped()) {
						g.setColor(Resources.darkerYellow);
						g.fillRect(offsetX+1, offsetY+1, 34, 34);
					}
					g.setColor(inv.get(index).getColor());
					g.drawString(""+inv.get(index).getTile().getSymbol(), offsetX+12, offsetY+23);
					g.setFont(new Font("Monospaced", Font.PLAIN, 10));
					g.setColor(Resources.white);
					if(inv.get(index) instanceof Equipement) {
						g.drawString(""+((Equipement)inv.get(index)).getDurability(), offsetX+20, offsetY+30);
					} else {
						g.drawString(""+inv.get(index).getVal(), offsetX+20, offsetY+30);
					}
					g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				}
				if(index == focusedItem) { 
					focusOffsetX = offsetX;
					focusOffsetY = offsetY;
				}
				offsetX+=35;
			}
			offsetY+=35;
		}
		if(focusedItem < inv.getSize()) {
			g.setColor(Resources.orange);
			g.drawRect(focusOffsetX-1, focusOffsetY-1, 37, 37);
			g.drawRect(focusOffsetX, focusOffsetY, 35, 35);
		}
		
		offsetY+=35;
		if(focusedItem == inv.getMaxSize()) { g.setColor(Resources.orange); }
		else { g.setColor(Resources.white); }
		g.drawString(items[inv.getMaxSize()], getWidth()/2-(items[inv.getMaxSize()].length()*13/2), offsetY);
		
		String commands=Character.toUpperCase(Resources.Commands.Up.getKey())+"/"+
						Character.toUpperCase(Resources.Commands.Down.getKey())+": Move, "+
						Character.toUpperCase(Resources.Commands.Take.getKey())+": Use/Equip, "+
						Character.toUpperCase(Resources.Commands.RepareWeapon.getKey())+"/"+
						Character.toUpperCase(Resources.Commands.RepareShield.getKey())+": Repare, "+
						Character.toUpperCase(Resources.Commands.Left.getKey())+"/"+
						Character.toUpperCase(Resources.Commands.Right.getKey())+": Drop";
		g.setColor(Resources.white);
		g.drawString(commands, getWidth()/2-(commands.length()*13/2), getHeight()-30);
	}
}
