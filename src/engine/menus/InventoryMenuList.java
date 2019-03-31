package engine.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyListener;

import engine.Resources;
import engine.Window;
import objects.item.*;

public class InventoryMenuList extends Menu {
	private static final long serialVersionUID = 1L;
	
	protected Inventory inv;
	public boolean dropWanted;
	private static final String dropText = "Press again to drop, other key to cancel";

	public InventoryMenuList(Window win) {
		super(win);
		dropWanted=false;
		inv = win.getMap().getPlayer().getInventory();
		items = new String[inv.getMaxSize()+1];
		initPanel();
	}
	
	public void setInventory(Inventory i) { this.inv = i; }
	
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
	
	public void focusedItemToQuickAction1() {
		Resources.playSelectMenuSound();
		inv.setQuickItem1(inv.get(focusedItem));
	}
	
	public void focusedItemToQuickAction2() {
		Resources.playSelectMenuSound();
		inv.setQuickItem2(inv.get(focusedItem));
	}
	
	public void resetFocusedItem() {
		if(inv.getSize() != 0) { this.focusedItem = 0; }
		else { this.focusedItem = inv.getMaxSize(); }
	}
	
	@Override
	public void incFocusedItem() {
		focusedItem++;
		while(focusedItem>=inv.getSize()&&focusedItem<inv.getMaxSize()) { focusedItem++; }
		if(focusedItem==items.length) { focusedItem=0; }
		Resources.playCycleMenuSound();
	}
	
	@Override
	public void decFocusedItem() {
		focusedItem--;
		while(focusedItem>=inv.getSize()&&focusedItem<inv.getMaxSize()) { focusedItem--; }
		if(focusedItem<0) { focusedItem = items.length-1; }
		Resources.playCycleMenuSound();
	}
	
	@Override
	public void exitMenu() {
		Resources.playExitMenuSound();
		focusedItem=0;
		win.showDungeon();
	}

	@Override
	public void selectFocusedItem() {
		Resources.playSelectMenuSound();
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
		
		if(dropWanted && focusedItem != inv.getMaxSize()) { items[focusedItem] = dropText; }
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Resources.getInstance().background);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Resources.getInstance().foreground);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		String name = win.getMap().getPlayer().toString(); 
		String healthBar = win.getMap().getPlayer().drawHealthBar();
		//String stuff = win.getMap().getPlayer().getWeaponInfo();
		g.drawString(name, getWidth()/2-(name.length()*12/2), 60);
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
			g.drawString(""+win.getMap().getPlayer().getHelmet().getTile().getSymbol(), getWidth()/2-12, 140);
		}
		g.setColor(Resources.getInstance().foreground);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		g.drawString("Inventory", getWidth()/2-(9*12/2), getHeight()/2-87);
		
		initPanel();
		
		String gold = "Gold : "+win.getMap().getPlayer().getGold();
		g.setColor(Resources.yellow);
		g.drawString(gold, getWidth()/2-(gold.length()*12/2), getHeight()/2-37);
		
		int offsetY=getHeight()/2-12;
		for(int i=0; i<items.length; i++) {
			if(i == focusedItem) { g.setColor(Resources.orange); } 
			else { g.setColor(Resources.getInstance().foreground); }
			int offsetX = items[i].length()*12/2;
			g.drawString(items[i], getWidth()/2-offsetX, offsetY);
			if(i<inv.getSize()) {
				g.setColor(inv.get(i).getColor());
				g.drawString(""+inv.get(i).getTile().getSymbol(), getWidth()/2+offsetX, offsetY);
			}
			offsetY+=25;
		}
		
		// Quick action 1
		g.setColor(Resources.getInstance().foreground);
		int QAoffsetX = getWidth()/2-(int)Math.round(4*35);
		int QAoffsetY= getHeight()/2-87;
		g.drawRect(QAoffsetX, QAoffsetY, 35, 35);
		g.setFont(new Font("Monospaced", Font.PLAIN, 16));
		g.drawString(""+Character.toUpperCase(Resources.Commands.QuickAction1.getKey()), QAoffsetX-15, QAoffsetY+28);
		if(inv.getQuickItem1()!=null) {
			if(inv.getQuickItem1() instanceof Equipement &&
					((Equipement)inv.getQuickItem1()).isEquiped()) {
				g.setColor(Resources.darkerYellow);
				g.fillRect(QAoffsetX+1, QAoffsetY+1, 34, 34);
			}
			g.setFont(new Font("Monospaced", Font.PLAIN, 20));
			g.setColor(inv.getQuickItem1().getColor());
			g.drawString(""+inv.getQuickItem1().getTile().getSymbol(), QAoffsetX+12, QAoffsetY+23);
			g.setFont(new Font("Monospaced", Font.PLAIN, 10));
			g.setColor(Resources.getInstance().foreground);
			if(inv.getQuickItem1() instanceof Equipement) {
				g.drawString(""+((Equipement)inv.getQuickItem1()).getDurability(), QAoffsetX+20, QAoffsetY+30);
			} else {
				g.drawString(""+inv.getQuickItem1().getVal(), QAoffsetX+20, QAoffsetY+30);
			}
			g.setFont(new Font("Monospaced", Font.PLAIN, 16));
		}
		// Quick action 2
		QAoffsetX = getWidth()/2+(int)Math.round(3*35);
		g.drawRect(QAoffsetX, QAoffsetY, 35, 35);
		g.drawString(""+Character.toUpperCase(Resources.Commands.QuickAction2.getKey()), QAoffsetX+40, QAoffsetY+28);
		if(inv.getQuickItem2()!=null) {
			if(inv.getQuickItem2() instanceof Equipement &&
					((Equipement)inv.getQuickItem2()).isEquiped()) {
				g.setColor(Resources.darkerYellow);
				g.fillRect(QAoffsetX+1, QAoffsetY+1, 34, 34);
			}
			g.setFont(new Font("Monospaced", Font.PLAIN, 20));
			g.setColor(inv.getQuickItem2().getColor());
			g.drawString(""+inv.getQuickItem2().getTile().getSymbol(), QAoffsetX+12, QAoffsetY+23);
			g.setFont(new Font("Monospaced", Font.PLAIN, 10));
			g.setColor(Resources.getInstance().foreground);
			if(inv.getQuickItem2() instanceof Equipement) {
				g.drawString(""+((Equipement)inv.getQuickItem2()).getDurability(), QAoffsetX+20, QAoffsetY+30);
			} else {
				g.drawString(""+inv.getQuickItem2().getVal(), QAoffsetX+20, QAoffsetY+30);
			}
		}
		
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		String commands=Resources.Commands.Up.getKey()+": Up, "+
						Resources.Commands.Down.getKey()+": Down, "+
						Resources.Commands.Take.getKey()+": Use/Equip, "+
						Resources.Commands.QuickAction1.getKey()+"/"+
						Resources.Commands.QuickAction2.getKey()+": Quick Slot, "+
						Resources.Commands.Left.getKey()+"/"+
						Resources.Commands.Right.getKey()+": Drop";
		g.setColor(Resources.getInstance().foreground);
		g.drawString(commands, getWidth()/2-(commands.length()*12/2), getHeight()-30);
	}

	@Override
	public KeyListener getKeyListener() {
		return new InventoryMenuListKeyListener(this);
	}
}
