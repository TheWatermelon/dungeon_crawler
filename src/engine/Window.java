package engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import tiles.*;

public class Window extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private Map map;
	private Tile[][] tab;
	
	private JPanel global;
	private JPanel headPanel;
	private JPanel footPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JTextPane head;
	private JTextPane body;
	private JTextPane foot;
	private JTextPane foot1;
	
	private StyledDocument sDoc;
	private Style defaut;
	private Style orange;
	private Style gold;
	private Style brown;
	private Style green;
	private Style boldGrey;
	private Style darkGrey;
	private Style darkGreen;
	
	public Window(String title, Map m) {
		super(title);
		this.map = m;
		this.tab = m.getTable();
		this.setSize(800, 600);
		this.setResizable(false);
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
		this.body.setEditable(false);
		this.body.setBorder(BorderFactory.createLineBorder(Color.white));
		this.body.setBackground(Color.black);
		this.body.setForeground(Color.white);
		this.body.setFont(new Font("Consolas", Font.PLAIN, 12));
		this.body.addKeyListener(new MyKeyListener(this.map, this));
		
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
		this.footPanel.add(this.foot, BorderLayout.CENTER);

		this.foot1 = new JTextPane();
		this.foot1.setEditable(false);
		this.foot1.setFocusable(false);
		this.foot1.setBackground(Color.black);
		this.foot1.setForeground(Color.white);
		this.footPanel.add(this.foot1, BorderLayout.WEST);
		
		this.global.add(this.headPanel, BorderLayout.NORTH);
		this.global.add(this.body, BorderLayout.CENTER);
		this.global.add(this.leftPanel, BorderLayout.WEST);
		this.global.add(this.rightPanel, BorderLayout.EAST);
		this.global.add(this.footPanel, BorderLayout.SOUTH);
		this.add(this.global);
		
		initStyles();
		
		this.setVisible(true);
	}
	
	private void initStyles() {
		this.sDoc = (StyledDocument)this.body.getDocument();
		//
		// définition des styles pour les caractères des JTextPane
		//
		this.defaut = this.body.getStyle("defaut");
		
		this.orange = this.body.addStyle("orange", defaut);
		StyleConstants.setForeground(this.orange, Color.orange);
		
		this.boldGrey = this.body.addStyle("boldGrey", orange);
		StyleConstants.setBold(this.boldGrey, true);
		StyleConstants.setForeground(this.boldGrey, Color.gray);
		
		this.brown = this.body.addStyle("brown", boldGrey);
		StyleConstants.setForeground(this.brown, new Color(0xA5, 0x68, 0x2A));
		
		this.darkGrey = this.body.addStyle("darkGrey", brown);
		StyleConstants.setForeground(this.darkGrey, Color.darkGray);
		
		this.darkGreen = this.body.addStyle("darkGreen", darkGrey);
		StyleConstants.setForeground(this.darkGreen, new Color(0x00, 0x33, 0x00));
		
		this.green = this.body.addStyle("green", darkGreen);
		StyleConstants.setForeground(this.green, new Color(0x00, 0x80, 0x00));
		
		this.gold = this.body.addStyle("gold", green);
		StyleConstants.setForeground(this.gold, Color.yellow);
	}
	
	public void setLabel(String s, String s1) {
		this.head.setText(s);
		this.foot.setText(s1);
		this.headPanel.repaint();
	}
	
	public void firstPrint() {
		String s="";
		int pos=0;
		
		this.map.printDungeon();
		
		try {
			sDoc.insertString(pos, "\n", defaut);
			for(int i=0; i<this.tab.length; i++) {
				s+=" ";
				sDoc.insertString(pos, " ", defaut);
				pos++;
				for(int j=0; j<this.tab[0].length; j++) {
					s+=""+this.tab[i][j].getSymbol()+" ";
					if(this.tab[i][j] instanceof TilePlayer) {
						sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.green);
					} else if(this.tab[i][j] instanceof TileWall) {
						sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.boldGrey);
					} else if(this.tab[i][j] instanceof TileDoor) {
						sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.brown);
					} else if(this.tab[i][j] instanceof TileStone) {
						sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.darkGrey);
					} else if(this.tab[i][j] instanceof TileMoss) {
						sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.darkGreen);
					} else if(this.tab[i][j] instanceof TileStairsDown) {
						sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.orange);
					} else if(this.tab[i][j] instanceof TileGold) {
						sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.gold);
					} else {
						sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", defaut);
					}
					pos=s.length();
				}
				s+="\n";
				sDoc.insertString(pos, "\n", defaut);
				pos++;
			}
			sDoc.insertString(pos, "\n", defaut);
		} catch(BadLocationException e) {}
		
		//this.body.setText(s);
		this.body.repaint();	
	}
	
	public void refresh() {
		String s="";
		int pos=0;
		
		this.map.printDungeon();
		
		try {
			sDoc.insertString(pos, "\n", defaut);
			for(int i=0; i<this.tab.length; i++) {
				s+=" ";
				sDoc.insertString(pos, " ", defaut);
				pos++;
				for(int j=0; j<this.tab[0].length; j++) {
					s+=""+this.tab[i][j].getSymbol()+" ";
					if(s.substring(pos, pos+1)!=sDoc.getText(pos, pos+1)) {
						if(this.tab[i][j] instanceof TilePlayer) {
							sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.green);
						} else if(this.tab[i][j] instanceof TileWall) {
							sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.boldGrey);
						} else if(this.tab[i][j] instanceof TileDoor) {
							sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.brown);
						} else if(this.tab[i][j] instanceof TileStone) {
							sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.darkGrey);
						} else if(this.tab[i][j] instanceof TileMoss) {
							sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.darkGreen);
						} else if(this.tab[i][j] instanceof TileStairsDown) {
							sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.orange);
						} else if(this.tab[i][j] instanceof TileGold) {
							sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", this.gold);
						} else {
							sDoc.insertString(pos, ""+this.tab[i][j].getSymbol()+" ", defaut);
						}
						pos=s.length();
					}
				}
				s+="\n";
				sDoc.insertString(pos, "\n", defaut);
				pos++;
			}
			sDoc.insertString(pos, "\n", defaut);
		} catch(BadLocationException e) {}
		
		//this.body.setText(s);
		this.body.repaint();
	}
}
