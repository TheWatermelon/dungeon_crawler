package engine;

import java.awt.Color;

public class Message {
	public enum Type {
		Normal(Resources.getInstance().foreground),
		Unimportant(Color.GRAY),
		Important(Color.YELLOW),
		Urgent(Color.RED),
		Other(Color.CYAN);
		
		private Type(Color c) { color = c; }
		
		public Color getColor() { return color; }
		public void setColor(Color c) { color = c; }
		
		private Color color;
	};
	
	protected String messageText;
	protected Type messageType;
	
	public Message(String text, Type type) {
		this.messageText = text;
		this.messageType = type;
	}
	
	@Override
	public String toString() { return this.messageText; }
	
	public Color getColor() { return this.messageType.getColor(); }
}
