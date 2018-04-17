package engine;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;

import engine.menus.*;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Map map;
	private Dungeon d;
	
	private KeyListener keyListener;
	
	private JPanel global;
	private JPanel headPanel;
	private JPanel footPanel;
	private FadePanel leftPanel;
	private FadePanel rightPanel;
	private JPanel mainMenu;
	private JPanel pauseMenu;
	private JPanel optionsMenu;
	private JPanel commandsMenu;
	private JPanel inventoryMenu;
	private JTextPane head;
	private JTextPane head1;
	private JTextPane head2;
	private JPanel foot;
	private JTextPane foot1;
	private JTextPane foot2;
	private DungeonPanel dungeon;
	
	private JPanel focusedPanel;
	private boolean isMainMenu;
	
	private String commands;
	
	public Window(String title, Dungeon d) {
		super(title);
		this.d = d;
		this.map = d.getMap();
		this.setSize(820, 620);
		this.setLocation(150, 100);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.keyListener = new DungeonKeyListener(d, d.getMap(), this);
		this.dungeon = new DungeonPanel(this);
		addKeyListener(keyListener);

		this.commands = Character.toUpperCase(Resources.Commands.Up.getKey())+","+
				Character.toUpperCase(Resources.Commands.Left.getKey())+","+
				Character.toUpperCase(Resources.Commands.Down.getKey())+","+
				Character.toUpperCase(Resources.Commands.Right.getKey())+
				": Move "+getMap().getPlayer()+"  \n"+
				Character.toUpperCase(Resources.Commands.Take.getKey())+": Take/Use Bow  \n"+
				Character.toUpperCase(Resources.Commands.Inventory.getKey())+": Inventory  \n"+
				Character.toUpperCase(Resources.Commands.Pause.getKey())+": Pause  ";
		
		this.mainMenu = new MainMenu(this);
		this.pauseMenu = new PauseMenu(this);
		this.optionsMenu = new OptionsMenu(this);
		this.commandsMenu = new CommandsMenu(this);
		this.inventoryMenu = new InventoryMenuGrid(this);
		
		this.global = new JPanel();
		this.global.setLayout(new BorderLayout());
		this.global.setBackground(Color.black);
		//this.global.setBorder(BorderFactory.createLineBorder(Color.white));

		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		SimpleAttributeSet justify = new SimpleAttributeSet();
		StyleConstants.setAlignment(justify, StyleConstants.ALIGN_JUSTIFIED);
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		
		this.headPanel = new JPanel();
		this.headPanel.setLayout(new GridLayout(1,3));
		this.headPanel.setPreferredSize(new Dimension(820, 50));
		// Player equipement
		this.head = new JTextPane(); 
		this.head.setEditable(false);
		this.head.setFocusable(false);
		this.head.setBackground(Color.black);
		this.head.setForeground(Color.white);
		this.head.setFont(new Font("Monospaced", Font.BOLD, 12));
		StyledDocument doc = this.head.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		this.headPanel.add(this.head);
		// Player name, gold, health bar and monsters killed
		this.head1 = new JTextPane(); 
		this.head1.setEditable(false);
		this.head1.setFocusable(false);
		this.head1.setBackground(Color.black);
		this.head1.setForeground(Color.white);
		this.head1.setFont(new Font("Monospaced", Font.BOLD, 12));
		StyledDocument doc1 = this.head1.getStyledDocument();
		doc1.setParagraphAttributes(0, doc1.getLength(), center, false);
		this.headPanel.add(this.head1);
		// Monster name and health bar
		this.head2 = new JTextPane(); 
		this.head2.setEditable(false);
		this.head2.setFocusable(false);
		this.head2.setBackground(Color.black);
		this.head2.setForeground(Color.white);
		this.head2.setFont(new Font("Monospaced", Font.BOLD, 12));
		StyledDocument doc2 = this.head2.getStyledDocument();
		doc2.setParagraphAttributes(0, doc2.getLength(), center, false);
		this.headPanel.add(this.head2);
		
		this.leftPanel = new FadePanel(Color.black);
		this.rightPanel = new FadePanel(Color.black);
		
		this.footPanel = new JPanel();
		this.footPanel.setLayout(new GridLayout(1, 3));
		// Map info
		/*
		this.foot = new JTextPane();
		this.foot.setEditable(false);
		this.foot.setFocusable(false);
		this.foot.setBackground(Color.black);
		this.foot.setForeground(Color.white);
		this.foot.setFont(new Font("Monospaced", Font.PLAIN, 13));
		StyledDocument doc0 = this.foot.getStyledDocument();
		doc0.setParagraphAttributes(0, doc0.getLength(), justify, false);
		*/
		this.foot = new QuickActionsPanel(map.getPlayer().getInventory());
		this.footPanel.add(this.foot);
		// Log
		this.foot1 = new JTextPane();
		this.foot1.setEditable(false);
		this.foot1.setFocusable(false);
		this.foot1.setBackground(Color.black);
		this.foot1.setForeground(Color.white);
		//this.foot1.setFont(new Font("Monospaced", Font.PLAIN, 13));
		StyledDocument doc3 = this.foot1.getStyledDocument();
		doc3.setParagraphAttributes(0, doc3.getLength(), center, false);
		this.footPanel.add(this.foot1);
		// Commands
		this.foot2 = new JTextPane();
		this.foot2.setEditable(false);
		this.foot2.setFocusable(false); 
		this.foot2.setBackground(Color.black);
		this.foot2.setForeground(Color.white);
		this.foot2.setFont(new Font("Monospaced", Font.PLAIN, 13));
		StyledDocument doc4 = this.foot2.getStyledDocument();
		doc4.setParagraphAttributes(0, doc4.getLength(), right, false);
		this.footPanel.add(foot2);
		// Global panel
		this.global.add(this.headPanel, BorderLayout.NORTH);
		this.global.add(this.dungeon, BorderLayout.CENTER);
		this.global.add(this.leftPanel, BorderLayout.WEST);
		this.global.add(this.rightPanel, BorderLayout.EAST);
		this.global.add(this.footPanel, BorderLayout.SOUTH);
		this.add(this.global);
		focusedPanel = global;
		
		showMainMenu();
		
		this.setVisible(true);
	}
	
	public void setLabel(String s, String s1, String s2) {
		if(!this.head.getText().equals(s)) {
			this.head.setText(s);
		}
		
		((QuickActionsPanel)this.foot).setInfoText(s1);
		
		if(!this.foot1.getText().equals(s2)) {
			this.foot1.setText(s2);
		}
		this.foot2.setText(commands);
	}
	
	public void setLabel(String s, String s1, String s2, String s3, String s4) {
		if(!this.head.getText().equals(s)) {
			this.head.setText(s);
		}
		if(!this.head1.getText().equals(s1)) {
			this.head1.setText(s1);
		}
		if(!this.head2.getText().equals(s2)) {
			this.head2.setText(s2);
		}
		
		((QuickActionsPanel)this.foot).setInfoText(s3);
		
		if(!this.foot1.getText().equals(s4)) {
			this.foot1.setText(s4);
		}
		this.foot2.setText(commands);
	}
	
	public void notifyColor(Color c) {
		this.leftPanel.setColor(c);
		this.leftPanel.resetFading();
		this.leftPanel.startFading();
		
		this.rightPanel.setColor(c);
		this.rightPanel.resetFading();
		this.rightPanel.startFading();
	}
	
	public void denotifyColor() {
		this.leftPanel.setColor(Color.BLACK);
		this.rightPanel.setColor(Color.BLACK);
	}
	
	public Map getMap() { return map; }
	
	public void setMap(Map m) {
		this.map = m;
	}
	public KeyListener getKeyListener() { return keyListener; }
	public DungeonPanel getDungeonPanel() { return dungeon; }
	public Dungeon getDungeon() { return d; }
	
	public boolean isMainMenu() { return isMainMenu; }
	
	public void showMainMenu() {
		isMainMenu=true;
		remove(focusedPanel);
		add(mainMenu);
		focusedPanel = mainMenu;
		removeKeyListener(keyListener);
		keyListener = new MenuKeyListener((engine.menus.Menu)mainMenu);
		addKeyListener(keyListener);
		revalidate();
		repaint();
	}
	
	public void showDungeon() {
		isMainMenu=false;
		remove(focusedPanel);
		add(global);
		focusedPanel = global;
		removeKeyListener(keyListener);
		keyListener = new DungeonKeyListener(d, map, this);
		addKeyListener(keyListener);
		revalidate();
		refresh();
	}
	
	public void showInventoryMenu() {
		remove(focusedPanel);
		add(inventoryMenu);
		focusedPanel = inventoryMenu;
		removeKeyListener(keyListener);
		keyListener = new InventoryMenuGridKeyListener((InventoryMenuGrid) inventoryMenu);
		addKeyListener(keyListener);
		revalidate();
		repaint();
	}
	
	public void showPauseMenu() {
		remove(focusedPanel);
		add(pauseMenu);
		focusedPanel = pauseMenu;
		removeKeyListener(keyListener);
		keyListener = new MenuKeyListener((engine.menus.Menu)pauseMenu);
		addKeyListener(keyListener);
		revalidate();
		repaint();
	}
	
	public void showOptionsMenu() {
		remove(focusedPanel);
		add(optionsMenu);
		focusedPanel = optionsMenu;
		removeKeyListener(keyListener);
		keyListener = new MenuKeyListener((engine.menus.Menu)optionsMenu);
		addKeyListener(keyListener);
		revalidate();
		repaint();
	}
	
	public void showCommandsMenu() {
		remove(focusedPanel);
		add(commandsMenu);
		focusedPanel = commandsMenu;
		removeKeyListener(keyListener);
		keyListener = new MenuKeyListener((engine.menus.Menu)commandsMenu);
		addKeyListener(keyListener);
		revalidate();
		repaint();
	}
	
	public void refreshListener() {
		if(keyListener instanceof DungeonKeyListener) {
			((DungeonKeyListener)keyListener).refresh(this.map, this);
		}
	}
	
	public void refresh() {
		dungeon.setDirty(true);
		if(!this.map.isPlayerDead()) {
			this.map.printDungeon();
			setLabel(this.map.getPlayer().getWeaponInfo(), this.map.getPlayer().getInfo(), this.map.getMobInfo(), this.map.getPrintableLevelInfo(), this.map.getLog());
		} else {
			setLabel(this.map.getPlayer().getWeaponInfo(), this.map.getPlayer().getInfo(), this.map.getMobInfo(), this.map.getPrintableLevelInfo(), this.map.getLog());
			leftPanel.setColor(Color.RED);
			leftPanel.repaint();
			rightPanel.setColor(Color.RED);
			rightPanel.repaint();
		}
		refreshListener();
		revalidate();
		repaint();
	}
}
