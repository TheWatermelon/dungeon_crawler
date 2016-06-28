package engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

import tiles.Tile;

public class Window extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private Map map;
	private Tile[][] tab;
	
	private JPanel global;
	private JLabel head;
	private JTextArea body;
	
	public Window(String title, Map m) {
		super(title);
		this.map = m;
		this.tab = m.getTable();
		this.setSize(600, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.global = new JPanel();
		this.global.setLayout(new BorderLayout());
		this.head = new JLabel("GAME");
		this.body = new JTextArea();
		this.body.setEditable(false);
		this.body.setBackground(Color.black);
		this.body.setForeground(Color.white);
		this.body.setFont(new Font("Consolas", Font.PLAIN, 12));
		this.body.addKeyListener(new MyKeyListener(this.map, this));
		
		this.global.add(this.head, BorderLayout.NORTH);
		this.global.add(this.body, BorderLayout.CENTER);
		this.add(this.global);
		
		this.setVisible(true);
	}
	
	public void setLabel(String s) {
		this.head.setText(s);
		this.repaint();
	}
	
	public void refresh() {
		String s="";
		
		this.map.printDungeon();
		
		for(int i=0; i<this.tab.length; i++) {
			for(int j=0; j<this.tab[0].length; j++) {
				s+=""+this.tab[i][j].getSymbol()+" ";
			}
			s+="\n";
		}
		
		this.body.setText(s);
		this.body.repaint();
	}
}
