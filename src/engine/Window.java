package engine;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;

import objects.*;
import tiles.*;

public class Window extends JFrame implements HierarchyBoundsListener {
	private static final long serialVersionUID = 1L;
	
	private Map map;
	private Tile[][] tab;
	
	private MyKeyListener keyListener;
	
	private JPanel global;
	private JPanel headPanel;
	private JPanel footPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JTextPane head;
	private JTextPane body;
	private JTextPane foot;
	private JTextPane foot1;
	private JTextPane foot2;
	
	private StyledDocument sDoc;
	private Style[] basicColors;
	private Style wall;
	private Style floor;
	private Style mob;
	private Style defaut;
	private Style white;
	private Style yellow;
	private Style orange;
	private Style brown;
	private Style green;
	private Style lightGray;
	private Style gray;
	private Style darkGray;
	private Style darkGreen;
	private Style coolRed;
	private Style red;
	private Style darkRed;
	private Style blue;
	private Style darkBlue;
	private Style cyan;
	private Style magenta;
	private Style pink;
	
	
	public Window(String title, Dungeon d) {
		super(title);
		this.map = d.getMap();
		this.tab = d.getMap().getTable();
		this.setSize(820, 620);
		this.setLocation(150, 100);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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
				
		this.body = new JTextPane();
		this.body.setContentType("charset=UTF-8");
		this.body.setEditable(false);
		this.body.setBorder(BorderFactory.createLineBorder(Color.white));
		this.body.setBackground(Color.black);
		this.body.setForeground(Color.white);
		this.body.setFont(new Font("Monospaced", Font.PLAIN, 12));
		this.keyListener = new MyKeyListener(d, d.getMap(), this);
		this.body.addKeyListener(keyListener);
		
		this.leftPanel = new JPanel();
		this.leftPanel.setBackground(Color.black);
		
		this.rightPanel = new JPanel();
		this.rightPanel.setBackground(Color.black);
		
		this.footPanel = new JPanel();
		this.footPanel.setLayout(new BorderLayout());
		
		this.foot = new JTextPane();
		this.foot.setEditable(false);
		this.foot.setFocusable(false);
		this.foot.setBackground(Color.black);
		this.foot.setForeground(Color.white);
		this.footPanel.add(this.foot, BorderLayout.WEST);

		this.foot1 = new JTextPane();
		this.foot1.setEditable(false);
		this.foot1.setFocusable(false);
		this.foot1.setBackground(Color.black);
		this.foot1.setForeground(Color.white);
		StyledDocument doc1 = this.foot1.getStyledDocument();
		doc1.setParagraphAttributes(0, doc1.getLength(), center, false);
		this.footPanel.add(this.foot1, BorderLayout.CENTER);
		
		this.foot2 = new JTextPane();
		this.foot2.setEditable(false);
		this.foot2.setFocusable(false); 
		this.foot2.setBackground(Color.black);
		this.foot2.setForeground(Color.white);
		this.foot2.setText("z,q,s,d: move\na: repare weapon  \ne:  repare shield");
		this.footPanel.add(this.foot2, BorderLayout.EAST);
		
		this.global.add(this.headPanel, BorderLayout.NORTH);
		this.global.add(this.body, BorderLayout.CENTER);
		this.global.add(this.leftPanel, BorderLayout.WEST);
		this.global.add(this.rightPanel, BorderLayout.EAST);
		this.global.add(this.footPanel, BorderLayout.SOUTH);
		this.add(this.global);
		
		this.basicColors = new Style[10];
		initStyles();
		pickTheme();
		
		this.setVisible(true);
		
		//getContentPane().addHierarchyBoundsListener(this);
	}
	
	private void initStyles() {
		this.sDoc = (StyledDocument)this.body.getDocument();
		//
		// d�finition des styles pour les caract�res des JTextPane
		//
		this.defaut = this.body.getStyle("defaut");
		
		this.orange = this.body.addStyle("orange", defaut);
		StyleConstants.setForeground(this.orange, Color.orange);
		
		this.lightGray = this.body.addStyle("lightGray", orange);
		StyleConstants.setBold(this.lightGray, true);
		StyleConstants.setForeground(this.lightGray, Color.gray);
		
		this.brown = this.body.addStyle("brown", lightGray);
		StyleConstants.setForeground(this.brown, new Color(0xA5, 0x68, 0x2A));
		
		this.darkGray = this.body.addStyle("darkGray", brown);
		StyleConstants.setForeground(this.darkGray, new Color(0x30, 0x30, 0x30));
		
		this.gray = this.body.addStyle("gray", darkGray);
		StyleConstants.setForeground(this.gray, new Color(0x60, 0x60, 0x60));
		
		this.darkGreen = this.body.addStyle("darkGreen", gray);
		StyleConstants.setForeground(this.darkGreen, new Color(0x00, 0x33, 0x00));
		
		this.green = this.body.addStyle("green", darkGreen);
		StyleConstants.setBold(this.green, true);
		StyleConstants.setForeground(this.green, new Color(0x00, 0x80, 0x00));
		
		this.yellow = this.body.addStyle("yellow", green);
		StyleConstants.setForeground(this.yellow, Color.yellow);
		
		this.coolRed = this.body.addStyle("coolRed", yellow);
		StyleConstants.setForeground(this.coolRed, new Color(0xEF, 0x3F, 0x23));
		
		this.darkRed = this.body.addStyle("darkRed", coolRed);
		StyleConstants.setForeground(this.darkRed, new Color(0x7F, 0x00, 0x00));
		
		this.red = this.body.addStyle("red", darkRed);
		StyleConstants.setForeground(this.red, Color.red);
		
		this.darkBlue = this.body.addStyle("darkBlue", red);
		StyleConstants.setForeground(this.darkBlue, new Color(0x00, 0x00, 0x7F));
		
		this.blue = this.body.addStyle("blue", darkBlue);
		StyleConstants.setForeground(this.blue, Color.blue);
		
		this.cyan = this.body.addStyle("cyan", blue);
		StyleConstants.setForeground(this.cyan, Color.cyan);
		
		this.magenta = this.body.addStyle("magenta", cyan);
		StyleConstants.setForeground(this.magenta, Color.magenta);
		
		this.pink = this.body.addStyle("pink", magenta);
		StyleConstants.setForeground(this.pink, Color.pink);
		
		this.white = this.body.addStyle("white", pink);
		StyleConstants.setForeground(this.white, Color.white);

		this.basicColors[0] = this.lightGray;
		this.basicColors[1] = this.white;
		this.basicColors[2] = this.brown;
		this.basicColors[3] = this.orange;
		this.basicColors[4] = this.red;
		this.basicColors[5] = this.pink;
		this.basicColors[6] = this.magenta;
		this.basicColors[7] = this.blue;
		this.basicColors[8] = this.cyan;
		this.basicColors[9] = this.green;
	}
	
	public void pickTheme() {
		Random rnd = new Random();
		this.floor = this.darkGray;
		this.mob = this.coolRed;
		this.wall = this.basicColors[rnd.nextInt(this.basicColors.length)];
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
	
	private void printInColor(int pos, int i, int j) {
		try {
			if(this.tab[i][j] instanceof TilePlayer) {
				sDoc.insertString(pos, ""+this.tab[i][j].getSymbol(), this.green);
			} else if(this.tab[i][j] instanceof TileWall) {
				sDoc.insertString(pos, ""+this.tab[i][j].getSymbol(), this.wall);
			} else if(this.tab[i][j] instanceof TileDoor) {
				sDoc.insertString(pos, ""+this.tab[i][j].getSymbol(), this.brown);
			} else if(this.tab[i][j] instanceof TileStone) {
				sDoc.insertString(pos, ""+this.tab[i][j].getSymbol(), this.floor);
			} else if(this.tab[i][j] instanceof TileMoss) {
				sDoc.insertString(pos, ""+this.tab[i][j].getSymbol(), this.darkGreen);
			} else if(this.tab[i][j] instanceof TileStairsDown) {
				sDoc.insertString(pos, ""+this.tab[i][j].getSymbol(), this.orange);
			} else if(this.tab[i][j] instanceof TileStairsUp) {
				sDoc.insertString(pos, ""+this.tab[i][j].getSymbol(), this.orange);
			} else if(this.tab[i][j] instanceof TileGold) {
				sDoc.insertString(pos, ""+this.tab[i][j].getSymbol(), this.yellow);
			} else if(this.tab[i][j] instanceof TileItem) {
			 sDoc.insertString(pos, ""+this.tab[i][j].getSymbol(), this.pink);
			} else if(this.tab[i][j] instanceof TileBarrel) {
				sDoc.insertString(pos, ""+this.tab[i][j].getSymbol(), this.brown);
			} else if(this.tab[i][j] instanceof TileMonster) {
				sDoc.insertString(pos, ""+this.tab[i][j].getSymbol(), this.mob);
			} else if(this.tab[i][j] instanceof TileCorpse) {
				sDoc.insertString(pos, ""+this.tab[i][j].getSymbol(), this.gray);
			} else {
				sDoc.insertString(pos, ""+this.tab[i][j].getSymbol(), defaut);
			}
		} catch (BadLocationException e) {}
	}
	
	private void printLooker(int pos, int i, int j) {
		Style color=defaut;
		try {
			if((i==this.map.getPlayer().getLooker().getY()) && (j==this.map.getPlayer().getLooker().getX()) && (this.map.getPlayer().getLooker().isVisible())) {
				if(sDoc.getText(pos-1, 3).equals(" "+this.tab[i][j].getSymbol()+" ")) {
					if(this.map.getPlayer().getLooker() instanceof LookerMob) {
						color = red;
					} else if(this.map.getPlayer().getLooker() instanceof LookerEquip) {
						color = pink;
					} else if(this.map.getPlayer().getLooker() instanceof LookerGold) {
						color = yellow;
					} else if(this.map.getPlayer().getLooker() instanceof LookerHealth) {
						color = green;
					} else if(this.map.getPlayer().getLooker() instanceof LookerBarrel) {
						color = brown;
					} else if(this.map.getPlayer().getLooker() instanceof LookerPotion) {
						if(((LookerPotion)this.map.getPlayer().getLooker()).getVal()>0) {
							color = green;
						} else {
							color = red;
						}
					}
					sDoc.remove(pos-1,  1);
					sDoc.insertString(pos-1, ""+this.map.getPlayer().getLooker().getLeft(), color);
					sDoc.remove(pos+1,  1);
					sDoc.insertString(pos+1, ""+this.map.getPlayer().getLooker().getRight(), color);
				}
			} else if((i==this.map.getPlayer().getLooker().getY()) && (j==this.map.getPlayer().getLooker().getX()) && !(this.map.getPlayer().getLooker().isVisible())) {
				if(!sDoc.getText(pos-1, 3).equals(" "+this.tab[i][j].getSymbol()+" ")) {
					sDoc.remove(pos-1,  1);
					sDoc.insertString(pos-1, " ", defaut);
					sDoc.remove(pos+1,  1);
					sDoc.insertString(pos+1, " ", defaut);
				}
			}
		} catch(BadLocationException e) {}
	}
	
	public Map getMap() { return map; }
	
	public void setMap(Map m) {
		this.map = m;
	}
	
	public void refreshListener() {
		keyListener.refresh(this.map, this);
	}
	
	public void refreshTable() {
		this.tab = this.map.getTable();
		try {
			this.body.getDocument().remove(0, body.getDocument().getLength());
		} catch(BadLocationException e) {
			
		}
	}
	
	public void refresh() {
		if(!this.map.isPlayerDead()) {
			this.map.printDungeon();
			if(this.map.oldString.equals("")) {
				firstPrint();
			} else {
				printOnScreen();
			}
			setLabel(this.map.generateMapInfo(), this.map.getPlayer().getAllInfo(), this.map.getLog());
		} else {
			setLabel(this.map.getFinalScreen(), this.map.getPlayer().getAllInfo(), this.map.getLog());
		}
		refreshListener();
		revalidate();
	}
	
	public void firstPrint() {
		int pos=0;

		refreshTable();
		this.map.printDungeon();
		
		try {
			this.map.oldString+="\n";
			sDoc.insertString(pos, "\n", defaut);
			pos++;
			for(int i=0; i<this.tab.length; i++) {
				this.map.oldString+=" ";
				sDoc.insertString(pos, " ", defaut);
				pos++;
				for(int j=0; j<this.tab[0].length; j++) {
					this.map.oldString+=""+this.tab[i][j].getSymbol()+" ";
					printInColor(pos, i, j);
					pos++;
					sDoc.insertString(pos, " ", defaut);
					pos++;
				}
				this.map.oldString+="\n";
				sDoc.insertString(pos, "\n", defaut);
				pos++;
			}
			sDoc.insertString(pos, "\n", defaut);
		} catch(BadLocationException e) {}
		
		//this.body.repaint();	
	}
	
	public void printOnScreen() {
		String s="";
		int pos=0;
		
		try {
			s+="\n";
			pos++;
			for(int i=0; i<this.map.getHeight(); i++) {
				s+=" ";
				pos++;
				for(int j=0; j<this.map.getWidth(); j++) {
					s+=""+this.tab[i][j].getSymbol()+" ";
					if(s.charAt(pos) != this.map.oldString.charAt(pos)) {
						sDoc.remove(pos, 1);
						printInColor(pos, i, j);
					}
					printLooker(pos, i, j);
					pos+=2;
				}
				s+="\n";
				pos=s.length();
			}
			this.map.oldString=s;
		} catch(BadLocationException e) {}
	}

	@Override
	public void ancestorMoved(HierarchyEvent e) {
		
	}

	@Override
	public void ancestorResized(HierarchyEvent e) {
		//map.recalculateTable();
		//System.out.println("New inner size : "+body.getWidth()+"x"+body.getHeight()+" => "+map.getWidth()+"x"+map.getHeight());
	}
}
