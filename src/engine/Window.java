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
		// Player equipement
		this.head = new JTextPane(); 
		this.head.setEditable(false);
		this.head.setFocusable(false);
		this.head.setBackground(Color.black);
		this.head.setForeground(Color.white);
		this.head.setFont(new Font("Monospaced", Font.BOLD, 12));
		this.head.setPreferredSize(new Dimension(this.getWidth()/3, 50));
		StyledDocument doc = this.head.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		this.headPanel.add(this.head, BorderLayout.WEST);
		// Player name, gold, health bar and monsters killed
		this.head1 = new JTextPane(); 
		this.head1.setEditable(false);
		this.head1.setFocusable(false);
		this.head1.setBackground(Color.black);
		this.head1.setForeground(Color.white);
		this.head1.setFont(new Font("Monospaced", Font.BOLD, 12));
		this.head1.setPreferredSize(new Dimension(this.getWidth()/3, 50));
		StyledDocument doc1 = this.head1.getStyledDocument();
		doc1.setParagraphAttributes(0, doc1.getLength(), center, false);
		this.headPanel.add(this.head1, BorderLayout.CENTER);
		// Monster name and health bar
		this.head2 = new JTextPane(); 
		this.head2.setEditable(false);
		this.head2.setFocusable(false);
		this.head2.setBackground(Color.black);
		this.head2.setForeground(Color.white);
		this.head2.setFont(new Font("Monospaced", Font.BOLD, 12));
		this.head2.setPreferredSize(new Dimension(this.getWidth()/3, 50));
		StyledDocument doc2 = this.head2.getStyledDocument();
		doc2.setParagraphAttributes(0, doc2.getLength(), center, false);
		this.headPanel.add(this.head2, BorderLayout.EAST);
		
		this.leftPanel = new FadePanel(Color.black);
		this.rightPanel = new FadePanel(Color.black);
		
		this.footPanel = new JPanel();
		this.footPanel.setLayout(new BorderLayout());
		// Inventory
		this.foot = new QuickActionsPanel(d.getMap().getPlayer().getInventory());
		this.foot.setPreferredSize(new Dimension(this.getWidth()/3, 75));
		this.footPanel.add(this.foot, BorderLayout.WEST);
		// Log
		this.foot1 = new JTextPane();
		this.foot1.setEditable(false);
		this.foot1.setFocusable(false);
		this.foot1.setBackground(Color.black);
		this.foot1.setForeground(Color.white);
		this.foot1.setPreferredSize(new Dimension(this.getWidth()/3, 50));
		StyledDocument doc3 = this.foot1.getStyledDocument();
		doc3.setParagraphAttributes(0, doc3.getLength(), center, false);
		this.footPanel.add(this.foot1, BorderLayout.CENTER);
		// Commands
		this.foot2 = new JTextPane();
		this.foot2.setEditable(false);
		this.foot2.setFocusable(false); 
		this.foot2.setBackground(Color.black);
		this.foot2.setForeground(Color.white);
		this.foot2.setFont(new Font("Monospaced", Font.PLAIN, 13));
		this.foot2.setPreferredSize(new Dimension(this.getWidth()/3, 50));
		StyledDocument doc4 = this.foot2.getStyledDocument();
		doc4.setParagraphAttributes(0, doc4.getLength(), right, false);
		this.footPanel.add(foot2, BorderLayout.EAST);
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
		this.leftPanel.repaint();
		this.rightPanel.setColor(Color.BLACK);
		this.rightPanel.repaint();
	}
	
	public Map getMap() { return d.getMap(); }
	
	public KeyListener getKeyListener() { return keyListener; }
	public DungeonPanel getDungeonPanel() { return dungeon; }
	public QuickActionsPanel getQuickActionPanel() { return (QuickActionsPanel)this.foot; }
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
			refreshCommands();
			setLabel(d.getMap().getPlayer().getWeaponInfo(), d.getMap().getPlayer().getInfo(), d.getMap().getMobInfo(), d.getMap().getPrintableLevelInfo(), d.getMap().getLog());
		} else {
			refreshCommands();
			setLabel(d.getMap().getPlayer().getWeaponInfo(), d.getMap().getPlayer().getInfo(), d.getMap().getMobInfo(), d.getMap().getPrintableLevelInfo(), d.getMap().getLog());
		}
		refreshListener();
		revalidate();
		repaint();
	}
}
