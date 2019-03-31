package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import objects.item.Equipement;
import objects.item.Inventory;

public class QuickActionsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected String info;
	protected Inventory inv;
	
	public QuickActionsPanel(Inventory inv) {
		super();
		this.info = "";
		this.inv = inv;
	}
	
	public void setInfoText(String s) { info = s; }
	public String getInfoText() { return info; }
	
	public void setInventory(Inventory i) { this.inv = i; }
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Resources.getInstance().background);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setFont(new Font("Monospaced", Font.PLAIN, 16));
		
		int offsetX=30, offsetY=10;
		// Quick Action 1
		g.setColor(Resources.getInstance().foreground);
		g.drawRect(offsetX, offsetY, 35, 35);
		g.drawString(""+Character.toUpperCase(Resources.Commands.QuickAction1.getKey()), offsetX-15, offsetY+28);
		if(inv.getQuickItem1()!=null) {
			if(inv.getQuickItem1() instanceof Equipement &&
					((Equipement)inv.getQuickItem1()).isEquiped()) {
				g.setColor(Resources.darkerYellow);
				g.fillRect(offsetX+1, offsetY+1, 34, 34);
			}
			g.setFont(new Font("Monospaced", Font.PLAIN, 20));
			g.setColor(inv.getQuickItem1().getColor());
			g.drawString(""+inv.getQuickItem1().getTile().getSymbol(), offsetX+12, offsetY+23);
			g.setFont(new Font("Monospaced", Font.PLAIN, 10));
			g.setColor(Resources.getInstance().foreground);
			if(inv.getQuickItem1() instanceof Equipement) {
				g.drawString(""+((Equipement)inv.getQuickItem1()).getDurability(), offsetX+20, offsetY+30);
			} else {
				g.drawString(""+inv.getQuickItem1().getVal(), offsetX+20, offsetY+30);
			}
			g.setFont(new Font("Monospaced", Font.PLAIN, 16));
		}
		// Quick Action 2
		offsetX+=50;
		g.drawRect(offsetX, offsetY, 35, 35);
		g.drawString(""+Character.toUpperCase(Resources.Commands.QuickAction2.getKey()), offsetX+40, offsetY+28);
		if(inv.getQuickItem2()!=null) {
			if(inv.getQuickItem2() instanceof Equipement &&
					((Equipement)inv.getQuickItem2()).isEquiped()) {
				g.setColor(Resources.darkerYellow);
				g.fillRect(offsetX+1, offsetY+1, 34, 34);
			}
			g.setFont(new Font("Monospaced", Font.PLAIN, 20));
			g.setColor(inv.getQuickItem2().getColor());
			g.drawString(""+inv.getQuickItem2().getTile().getSymbol(), offsetX+12, offsetY+23);
			g.setFont(new Font("Monospaced", Font.PLAIN, 10));
			g.setColor(Resources.getInstance().foreground);
			if(inv.getQuickItem2() instanceof Equipement) {
				g.drawString(""+((Equipement)inv.getQuickItem2()).getDurability(), offsetX+20, offsetY+30);
			} else {
				g.drawString(""+inv.getQuickItem2().getVal(), offsetX+20, offsetY+30);
			}
			g.setFont(new Font("Monospaced", Font.PLAIN, 16));
		}
		
		// Info
		g.setFont(new Font("Monospaced", Font.PLAIN, 13));
		g.drawString(this.info, 15, getHeight()-10);
		
	}
}
