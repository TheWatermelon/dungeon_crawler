package engine.menus;

import javax.swing.*;

import engine.Window;

public abstract class Menu extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected Window win;
	protected String[] items;
	protected int focusedItem;
	
	public Menu(Window win) { 
		super(); 
		this.win = win; 
	}
	
	public void setFocusedItem(int val) { this.focusedItem = val; }
	public void incFocusedItem() { if(focusedItem+1!=items.length) {focusedItem++; } }
	public void decFocusedItem() { if(focusedItem-1!=-1) {focusedItem--; } }
	public abstract void selectFocusedItem();
	
	protected abstract void initPanel();
}
