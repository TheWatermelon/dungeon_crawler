package engine;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;

import engine.menus.*;
import engine.menus.Menu;
import engine.menus.PopupMenu;

public class Window extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private Dungeon d;
	
	private KeyListener keyListener;
	
	private GamePanel global;
	private JPanel headPanel;
	private JPanel footPanel;
	private FadePanel leftPanel;
	private FadePanel rightPanel;
	private GamePanel mainMenu;
	private GamePanel loadMenu;
	private GamePanel saveMenu;
	private GamePanel pauseMenu;
	private GamePanel optionsMenuNewGame;
	private GamePanel optionsMenuInGame;
	private GamePanel commandsMenu;
	private GamePanel inventoryMenu;
	private JPanel headWest;
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

		initMenus();
		
		this.focusedPanel = this.mainMenu;
		//this.keyListener = ((Menu)mainMenu).getKeyListener();
		showMainMenu();
	}

	protected void initMenus() {
		this.mainMenu = new MainMenu(this);
		this.loadMenu = new LoadMenu(this);
		this.saveMenu = new SaveMenu(this);
		this.pauseMenu = new PauseMenu(this);
		this.optionsMenuNewGame = new OptionsMenuNewGame(this);
		this.optionsMenuInGame = new OptionsMenuInGame(this);
		this.commandsMenu = new CommandsMenu(this);
		this.inventoryMenu = new InventoryMenuList(this);
	}

	protected void initGamePanel() {
		this.keyListener = new DungeonKeyListener(d, d.getMap(), this);
		this.dungeon = new DungeonPanel(this);
		addKeyListener(keyListener);

		this.global = new GamePanel(this) {
			private static final long serialVersionUID = 1L;

			@Override
			public KeyListener getKeyListener() {
				return new DungeonKeyListener(this.win.d, this.win.d.getMap(), this.win);
			}};
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
		this.headWest = new LogPanel(d.getLog());
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
	}
	
	public void setLabel(String s, String s1, String s2, String s3, String s4) {
		if(!this.subFootCenterRight.getText().equals(s)) {
			this.subFootCenterRight.setText(s);
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
	public DungeonPanel getDungeonPanel() { return (DungeonPanel)dungeon; }
	public QuickActionsPanel getQuickActionPanel() { return (QuickActionsPanel)this.footEast; }
	public Dungeon getDungeon() { return d; }
	
	public boolean isMainMenu() { return isMainMenu; }
	
	public void showPanel(GamePanel panel, KeyListener newKeyListener) {
		remove(focusedPanel);
		add(panel);
		focusedPanel = panel;
		removeKeyListener(keyListener);
		keyListener = newKeyListener;
		addKeyListener(newKeyListener);
		revalidate();
		repaint();
	}
	
	public void showMainMenu() {
		this.isMainMenu=true;
		Resources.playMenuMusic();
		showPanel(mainMenu, ((Menu)mainMenu).getKeyListener());
	}
	
	public void showLoadMenu() {
		showPanel(loadMenu, ((Menu)loadMenu).getKeyListener());
	}
	
	public void showSaveMenu() {
		showPanel(saveMenu, ((Menu)saveMenu).getKeyListener());
	}
	
	public void showDungeon() {
		if(this.global == null) { initGamePanel(); }
		Resources.pauseMenuMusic();
		Resources.playDungeonMusic();
		showPanel(global, new DungeonKeyListener(d, d.getMap(), this));
		refresh();
	}
	
	public void showInventoryMenu() {
		((InventoryMenuList) inventoryMenu).setInventory(this.d.getPlayer().getInventory());
		((InventoryMenuList) inventoryMenu).resetFocusedItem();
		Resources.pauseDungeonMusic();
		Resources.playOpenMenuSound();
		Resources.playMenuMusic();
		showPanel(inventoryMenu, ((Menu)inventoryMenu).getKeyListener());
	}
	
	public void showPauseMenu() {
		this.isMainMenu=false;
		Resources.pauseDungeonMusic();
		Resources.playOpenMenuSound();
		Resources.playMenuMusic();
		showPanel(pauseMenu, ((Menu)pauseMenu).getKeyListener());
	}
	
	public void showOptionsMenuNewGame() {
		showPanel(optionsMenuNewGame, ((Menu)optionsMenuNewGame).getKeyListener());
	}

	public void showOptionsMenuInGame() {
		showPanel(optionsMenuInGame, ((Menu)optionsMenuInGame).getKeyListener());
	}
	
	public void showCommandsMenu() {
		showPanel(commandsMenu, ((Menu)commandsMenu).getKeyListener());
	}
	
	public void showPopup(PopupMenu popup) {
		showPanel(popup, popup.getKeyListener());
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
		d.getMap().printDungeon();
		setLabel(d.getMap().getMobInfo(), d.getMap().getLog(), d.getMap().getPlayer().getInfo(), d.getMap().getPlayer().getWeaponInfo(), d.getMap().getPrintableLevelInfo());
		refreshListener();
		refreshCommands();
		revalidate();
		repaint();
	}
}
