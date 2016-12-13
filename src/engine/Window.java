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
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel pauseMenu;
	private JPanel optionsMenu;
	private JTextPane head;
	private JTextPane foot;
	private JTextPane foot1;
	private JTextPane foot2;
	private DungeonPanel dungeon;
	
	
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
		
		this.pauseMenu = new PauseMenu(this);
		this.optionsMenu = new OptionsMenu(this);
		
		this.global = new JPanel();
		this.global.setLayout(new BorderLayout());
		this.global.setBackground(Color.black);
		this.global.setBorder(BorderFactory.createLineBorder(Color.white));
		
		this.headPanel = new JPanel();
		this.headPanel.setLayout(new BorderLayout());
		
		this.head = new JTextPane(); 
		this.head.setEditable(false);
		this.head.setFocusable(false);
		this.head.setBackground(Color.black);
		this.head.setForeground(Color.white);
		StyledDocument doc = this.head.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		this.headPanel.add(this.head, BorderLayout.CENTER);
		
		this.leftPanel = new JPanel();
		this.leftPanel.setBackground(Color.black);
		
		this.rightPanel = new JPanel();
		this.rightPanel.setBackground(Color.black);
		
		this.footPanel = new JPanel();
		this.footPanel.setLayout(new BorderLayout());
		// HP, Gold, Kills, Weapon & Shield
		this.foot = new JTextPane();
		this.foot.setEditable(false);
		this.foot.setFocusable(false);
		this.foot.setBackground(Color.black);
		this.foot.setForeground(Color.white);
		StyledDocument doc0 = this.foot.getStyledDocument();
		doc0.setParagraphAttributes(0, doc0.getLength(), center, false);
		this.footPanel.add(this.foot, BorderLayout.WEST);
		// Log
		this.foot1 = new JTextPane();
		this.foot1.setEditable(false);
		this.foot1.setFocusable(false);
		this.foot1.setBackground(Color.black);
		this.foot1.setForeground(Color.white);
		StyledDocument doc1 = this.foot1.getStyledDocument();
		doc1.setParagraphAttributes(0, doc1.getLength(), center, false);
		this.footPanel.add(this.foot1, BorderLayout.CENTER);
		// Commands
		this.foot2 = new JTextPane();
		this.foot2.setEditable(false);
		this.foot2.setFocusable(false); 
		this.foot2.setBackground(Color.black);
		this.foot2.setForeground(Color.white);
		this.foot2.setText("Z,Q,S,D: Move player\nRepare we(A)pon, shi(E)ld   \nF: Take item");
		this.footPanel.add(this.foot2, BorderLayout.EAST);
		
		this.global.add(this.headPanel, BorderLayout.NORTH);
		//this.global.add(this.body, BorderLayout.CENTER);
		this.global.add(this.dungeon, BorderLayout.CENTER);
		this.global.add(this.leftPanel, BorderLayout.WEST);
		this.global.add(this.rightPanel, BorderLayout.EAST);
		this.global.add(this.footPanel, BorderLayout.SOUTH);
		this.add(this.global);
		
		this.setVisible(true);
	}
	
	public void setLabel(String s, String s1, String s2) {
		if(!this.head.getText().equals(s)) {
			this.head.setText(s);
		}
		if(!this.foot.getText().equals(s1)) {
			this.foot.setText(s1);
		}
		if(!this.foot1.getText().equals(s2)) {
			this.foot1.setText(s2);
		}
	}
	
	public void setLabel(String s, String s1, String s2, String s3) {
		if(!this.head.getText().equals(s)) {
			this.head.setText(s);
		}
		if(!this.foot.getText().equals(s1)) {
			this.foot.setText(s1);
		}
		if(!this.foot1.getText().equals(s2)) {
			this.foot1.setText(s2);
		}
		if(!this.foot2.getText().equals(s3)) {
			this.foot2.setText(s3);
		}
	}
	
	public Map getMap() { return map; }
	
	public void setMap(Map m) {
		this.map = m;
	}
	
	public DungeonPanel getDungeonPanel() { return dungeon; }
	
	public void showDungeon() {
		this.global.remove(pauseMenu);
		this.global.add(dungeon, BorderLayout.CENTER);
		removeKeyListener(keyListener);
		keyListener = new DungeonKeyListener(d, map, this);
		addKeyListener(keyListener);
		refresh();
		repaint();
	}
	
	public void showPauseMenu() {
		this.global.remove(dungeon);
		this.global.remove(optionsMenu);
		this.global.add(pauseMenu, BorderLayout.CENTER);
		this.head.setText("PAUSE\nMENU");
		removeKeyListener(keyListener);
		keyListener = new MenuKeyListener((engine.menus.Menu)pauseMenu);
		addKeyListener(keyListener);
		repaint();
	}
	
	public void showOptionsMenu() {
		this.global.remove(pauseMenu);
		this.global.add(optionsMenu, BorderLayout.CENTER);
		this.head.setText("OPTIONS\nMENU");
		removeKeyListener(keyListener);
		keyListener = new MenuKeyListener((engine.menus.Menu)optionsMenu);
		addKeyListener(keyListener);
		repaint();
	}
	
	public void refreshListener() {
		if(keyListener instanceof DungeonKeyListener) {
			((DungeonKeyListener)keyListener).refresh(this.map, this);
		}
	}
	
	public void refresh() {
		if(!this.map.isPlayerDead()) {
			this.map.printDungeon();
			dungeon.repaint();
			setLabel(this.map.generateMapInfo(), this.map.getPlayer().getAllInfo(), this.map.getLog());
		} else {
			setLabel(this.map.getFinalScreen(), this.map.getPlayer().getAllInfo(), this.map.getLog());
		}
		refreshListener();
		revalidate();
	}
}
