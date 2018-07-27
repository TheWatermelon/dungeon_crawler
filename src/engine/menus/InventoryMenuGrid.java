package engine.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyListener;

import engine.Resources;
import engine.Window;
import objects.item.Equipement;
import objects.item.Inventory;

public class InventoryMenuGrid extends Menu {
	private static final long serialVersionUID = 1L;
	
	protected Inventory inv;
	protected int columns;
	public boolean dropWanted;
	private static final String dropText = "Press again to drop, other key to cancel";
	
	public InventoryMenuGrid(Window win) {
		super(win);
		focusedItem=0;
		dropWanted=false;
		inv = win.getMap().getPlayer().getInventory();
		items = new String[inv.getMaxSize()+1];
		columns = 3;
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
	
	public void focusedItemToQuickAction1() {
		inv.setQuickItem1(inv.get(focusedItem));
	}
	
	public void focusedItemToQuickAction2() {
		inv.setQuickItem2(inv.get(focusedItem));
	}
	
	@Override
	public void incFocusedItem() {
		focusedItem++;
		if(focusedItem==items.length) { focusedItem=0; }
	}
	
	public void incLineFocusedItem() {
		focusedItem+=columns;
		if(focusedItem>=inv.getMaxSize()) { focusedItem = items.length-1; }
	}
	
	@Override
	public void decFocusedItem() {
		focusedItem--;
		if(focusedItem<0) { focusedItem = items.length-1; }
	}
	
	public void decLineFocusedItem() {
		focusedItem-=columns;
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
		// for each line of n columns
		for(int i=0; i<inv.getMaxSize()/columns; i++) {
			int offsetX = getWidth()/2-(int)Math.round(1.5*35);
			// for each column
			for(int j=0; j<columns; j++) {
				int index = i*columns+j;
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
		// Frame around focusedItem
		if(focusedItem < inv.getMaxSize()) {
			g.setColor(Resources.orange);
			g.drawRect(focusOffsetX-1, focusOffsetY-1, 37, 37);
			g.drawRect(focusOffsetX, focusOffsetY, 35, 35);
		}
		// Quick action 1
		g.setColor(Resources.white);
		int QAoffsetX = getWidth()/2-(int)Math.round(4*35);
		int QAoffsetY=getHeight()/2+23;
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
			g.setColor(Resources.white);
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
			g.setColor(Resources.white);
			if(inv.getQuickItem2() instanceof Equipement) {
				g.drawString(""+((Equipement)inv.getQuickItem2()).getDurability(), QAoffsetX+20, QAoffsetY+30);
			} else {
				g.drawString(""+inv.getQuickItem2().getVal(), QAoffsetX+20, QAoffsetY+30);
			}
		}
		
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		offsetY+=35;
		if(focusedItem == inv.getMaxSize()) { g.setColor(Resources.orange); }
		else { g.setColor(Resources.white); }
		g.drawString(items[inv.getMaxSize()], getWidth()/2-(items[inv.getMaxSize()].length()*13/2), offsetY);
		
		String commands=Character.toUpperCase(Resources.Commands.Up.getKey())+","+
						Character.toUpperCase(Resources.Commands.Left.getKey())+","+
						Character.toUpperCase(Resources.Commands.Down.getKey())+","+
						Character.toUpperCase(Resources.Commands.Right.getKey())+": Move, "+
						Character.toUpperCase(Resources.Commands.Take.getKey())+": Use/Equip, "+
						Character.toUpperCase(Resources.Commands.Inventory.getKey())+": Repare, "+
						Character.toUpperCase(Resources.Commands.Pause.getKey())+": Drop";
		g.setColor(Resources.white);
		g.drawString(commands, getWidth()/2-(commands.length()*13/2), getHeight()-30);
	}

	@Override
	public KeyListener getKeyListener() {
		return new InventoryMenuGridKeyListener(this);
	}
}
