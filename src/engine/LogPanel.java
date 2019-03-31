package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class LogPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	protected MessageLog log;
	
	public LogPanel(MessageLog l) {
		this.log = l;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Resources.getInstance().background);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		Message[] msgs = this.log.getLastMessages(5);
		int offsetY = 10;
		for(int i=0; i<5; i++) {
			if(msgs[i]==null) continue;
			g.setColor(msgs[i].getColor());
			g.drawString(msgs[i].toString(), 0, offsetY);
			offsetY+=13;
		}
	}
}
