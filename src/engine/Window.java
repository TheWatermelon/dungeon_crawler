package engine;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;

import engine.menus.*;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Dungeon d;
	
	private KeyListener keyListener;
	
	private JPanel global;
	private JPanel headPanel;
	private JPanel footPanel;
	private FadePanel leftPanel;
	private FadePanel rightPanel;
	private JPanel mainMenu;
	private JPanel loadMenu;
	private JPanel saveMenu;
	private JPanel pauseMenu;
	private JPanel optionsMenuNewGame;
	private JPanel optionsMenuInGame;
	private JPanel commandsMenu;
	private JPanel inventoryMenu;
	private JTextPane headWest;
	private JTextPane headEast;
	private JTextPane footWest;
	private JPanel footCenter;
	private JTextPane subFootCenterLeft;
	private JTextPane subFootCenterRight;
	private JPanel footEast;
	private DungeonPanel dungeon;
	
	private JPanel focusedPanel;
	private boolean isMainMenu;
	
	private String commands;
	
	public Window(String title, Dungeon d) {
		super(title);
		this.d = d;
		this.setSize(820, 620);
		this.setLocation(150, 100);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.keyListener = new DungeonKeyListener(d, d.getMap(), this);
		this.dungeon = new DungeonPanel(this);
		d.getMap().addObserver(dungeon);
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
		this.loadMenu = new LoadMenu(this);
		this.saveMenu = new SaveMenu(this);
		this.pauseMenu = new PauseMenu(this);
		this.optionsMenuNewGame = new OptionsMenuNewGame(this);
		this.optionsMenuInGame = new OptionsMenuInGame(this);
		this.commandsMenu = new CommandsMenu(this);
		this.inventoryMenu = new InventoryMenuList(this);
		
		this.global = new JPanel();
		this.global.setLayout(new BorderLayout());
		this.global.setBackground(Color.black);
		
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		SimpleAttributeSet justify = new SimpleAttributeSet();
		StyleConstants.setAlignment(justify, StyleConstants.ALIGN_JUSTIFIED);
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		
		this.headPanel = new JPanel();
		this.headPanel.setLayout(new BorderLayout());
		/* Log */
		this.headWest = new JTextPane();
		this.headWest.setEditable(false);
		this.headWest.setFocusable(false);
		this.headWest.setBackground(Color.black);
		this.headWest.setForeground(Color.white);
		this.headWest.setFont(new Font("Consolas", Font.PLAIN, 12));
		this.headWest.setPreferredSize(new Dimension(this.getWidth()/2, 70));
		StyledDocument doc3 = this.headWest.getStyledDocument();
		doc3.setParagraphAttributes(0, doc3.getLength(), justify, false);
		this.headPanel.add(this.headWest, BorderLayout.CENTER);
		/* Commands */
		this.headEast = new JTextPane();
		this.headEast.setEditable(false);
		this.headEast.setFocusable(false); 
		this.headEast.setBackground(Color.black);
		this.headEast.setForeground(Color.white);
		this.headEast.setFont(new Font("Consolas", Font.PLAIN, 12));
		this.headEast.setPreferredSize(new Dimension(this.getWidth()/4, 70));
		StyledDocument doc4 = this.headEast.getStyledDocument();
		doc4.setParagraphAttributes(0, doc4.getLength(), right, false);
		this.headPanel.add(headEast, BorderLayout.EAST);
		
		/* Color panels */
		this.leftPanel = new FadePanel(Color.black);
		this.rightPanel = new FadePanel(Color.black);
		
		this.footPanel = new JPanel();
		this.footPanel.setLayout(new BorderLayout());
		/* Player name, gold, health bar and monsters killed */
		this.footWest = new JTextPane(); 
		this.footWest.setEditable(false);
		this.footWest.setFocusable(false);
		this.footWest.setBackground(Color.black);
		this.footWest.setForeground(Color.white);
		this.footWest.setFont(new Font("Monospaced", Font.BOLD, 12));
		this.footWest.setPreferredSize(new Dimension(this.getWidth()/4, 50));
		StyledDocument doc1 = this.footWest.getStyledDocument();
		doc1.setParagraphAttributes(0, doc1.getLength(), center, false);
		this.footPanel.add(this.footWest, BorderLayout.WEST);
		this.footCenter = new JPanel();
		this.footCenter.setLayout(new BorderLayout());
		/* Player equipement */
		this.subFootCenterLeft = new JTextPane(); 
		this.subFootCenterLeft.setEditable(false);
		this.subFootCenterLeft.setFocusable(false);
		this.subFootCenterLeft.setBackground(Color.black);
		this.subFootCenterLeft.setForeground(Color.white);
		this.subFootCenterLeft.setFont(new Font("Monospaced", Font.BOLD, 12));
		this.subFootCenterLeft.setPreferredSize(new Dimension(this.getWidth()/4, 50));
		//StyledDocument doc = this.foot1.getStyledDocument();
		//doc.setParagraphAttributes(0, doc.getLength(), center, false);
		this.footCenter.add(this.subFootCenterLeft, BorderLayout.WEST);
		/* Monster name and health bar */
		this.subFootCenterRight = new JTextPane(); 
		this.subFootCenterRight.setEditable(false);
		this.subFootCenterRight.setFocusable(false);
		this.subFootCenterRight.setBackground(Color.black);
		this.subFootCenterRight.setForeground(Color.white);
		this.subFootCenterRight.setFont(new Font("Monospaced", Font.BOLD, 12));
		this.subFootCenterRight.setPreferredSize(new Dimension(this.getWidth()/4, 50));
		StyledDocument doc2 = this.subFootCenterRight.getStyledDocument();
		doc2.setParagraphAttributes(0, doc2.getLength(), center, false);
		this.footCenter.add(this.subFootCenterRight, BorderLayout.CENTER);
		this.footPanel.add(footCenter, BorderLayout.CENTER);
		/* Inventory Quick Actions */
		this.footEast = new QuickActionsPanel(d.getMap().getPlayer().getInventory());
		this.footEast.setPreferredSize(new Dimension(this.getWidth()/5, 75));
		this.footPanel.add(this.footEast, BorderLayout.EAST);
		
		/* Global panel */
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
	
	public void setLabel(String s, String s1, String s2, String s3, String s4) {
		if(!this.subFootCenterRight.getText().equals(s)) {
			this.subFootCenterRight.setText(s);
		}
		if(!this.headWest.getText().equals(s1)) {
			this.headWest.setText(s1);
		}
		this.refreshCommands();
		if(!this.headEast.getText().equals(this.commands)) {
			this.headEast.setText(this.commands);
		}
		if(!this.footWest.getText().equals(s2)) {
			this.footWest.setText(s2);
		}
		if(!this.subFootCenterLeft.getText().equals(s3)) {
			this.subFootCenterLeft.setText(s3);
		}
		((QuickActionsPanel)this.footEast).setInfoText(s4);
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
		this.leftPanel.repaint();
		this.rightPanel.setColor(Color.BLACK);
		this.rightPanel.repaint();
	}
	
	public Map getMap() { return d.getMap(); }
	
	public KeyListener getKeyListener() { return keyListener; }
	public DungeonPanel getDungeonPanel() { return dungeon; }
	public QuickActionsPanel getQuickActionPanel() { return (QuickActionsPanel)this.footEast; }
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
		Resources.playMenuMusic();
		revalidate();
		repaint();
	}
	
	public void showLoadMenu() {
		remove(focusedPanel);
		add(loadMenu);
		focusedPanel = loadMenu;
		removeKeyListener(keyListener);
		keyListener = new LoadMenuKeyListener((LoadMenu)loadMenu);
		addKeyListener(keyListener);
		revalidate();
		repaint();
	}
	
	public void showSaveMenu() {
		remove(focusedPanel);
		add(saveMenu);
		focusedPanel = saveMenu;
		removeKeyListener(keyListener);
		keyListener = new SaveMenuKeyListener((SaveMenu)saveMenu);
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
		keyListener = new DungeonKeyListener(d, d.getMap(), this);
		addKeyListener(keyListener);
		Resources.pauseMenuMusic();
		Resources.playDungeonMusic();
		revalidate();
		refresh();
	}
	
	public void showInventoryMenu() {
		remove(focusedPanel);
		add(inventoryMenu);
		focusedPanel = inventoryMenu;
		((InventoryMenuList) inventoryMenu).setInventory(this.d.getPlayer().getInventory());
		((InventoryMenuList) inventoryMenu).resetFocusedItem();
		removeKeyListener(keyListener);
		keyListener = new InventoryMenuListKeyListener((InventoryMenuList) inventoryMenu);
		addKeyListener(keyListener);
		Resources.pauseDungeonMusic();
		Resources.playOpenMenuSound();
		Resources.playMenuMusic();
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
		Resources.pauseDungeonMusic();
		Resources.playOpenMenuSound();
		Resources.playMenuMusic();
		revalidate();
		repaint();
	}
	
	public void showOptionsMenuNewGame() {
		remove(focusedPanel);
		add(optionsMenuNewGame);
		focusedPanel = optionsMenuNewGame;
		removeKeyListener(keyListener);
		keyListener = new MenuKeyListener((engine.menus.Menu)optionsMenuNewGame);
		addKeyListener(keyListener);
		revalidate();
		repaint();
	}

	public void showOptionsMenuInGame() {
		remove(focusedPanel);
		add(optionsMenuInGame);
		focusedPanel = optionsMenuInGame;
		removeKeyListener(keyListener);
		keyListener = new MenuKeyListener((engine.menus.Menu)optionsMenuInGame);
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
			((DungeonKeyListener)keyListener).refresh(d.getMap(), this);
		}
	}
	
	public void refreshCommands() {
		this.commands = Character.toUpperCase(Resources.Commands.Up.getKey())+","+
				Character.toUpperCase(Resources.Commands.Left.getKey())+","+
				Character.toUpperCase(Resources.Commands.Down.getKey())+","+
				Character.toUpperCase(Resources.Commands.Right.getKey())+
				": Move "+getMap().getPlayer()+"  \n"+
				Character.toUpperCase(Resources.Commands.Take.getKey())+": Take/Use Bow  \n"+
				Character.toUpperCase(Resources.Commands.Inventory.getKey())+": Inventory  \n"+
				Character.toUpperCase(Resources.Commands.Pause.getKey())+": Pause  ";
	}
	
	public void refresh() {
		dungeon.setDirty(true);
		if(!d.getMap().isPlayerDead()) {
			d.getMap().printDungeon();
			setLabel(d.getMap().getMobInfo(), d.getMap().getLog(), d.getMap().getPlayer().getInfo(), d.getMap().getPlayer().getWeaponInfo(), d.getMap().getPrintableLevelInfo());
		}
		refreshListener();
		revalidate();
		repaint();
	}
}
