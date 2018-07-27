package engine;

import java.util.Vector;

public class MessageLog {
	private Vector<Message> log;
	
	public MessageLog() {
		this.log = new Vector<Message>();
	}
	
	public void appendMessage(String msg, Message.Type type) {
		if(msg.length()>Resources.getInstance().msgLength) {
			msg = msg.substring(0, Resources.getInstance().msgLength);
			msg += "...";
		}
		this.log.add(new Message("  "+msg, type));
	}
	
	public String getLastStrings(int lines) {
		String res="";
		
		/* Not enough lines in the log */
		if(log.size()-lines<0) {
			for(int i=0; i<log.size(); i++) {
				res += log.get(i);
				res += "\t\n";
			}
		} else {
			for(int i=lines; i>0; i--) {
				res += log.get(log.size()-i);
				res += "\t\n";
			}
			res = res.substring(0, res.length()-1);
		}
		
		return res;
	}
	
	public Message[] getLastMessages(int lines) {
		Message[] res = new Message[lines];
		
		/* Not enough lines in the log */
		if(log.size()-lines<0) {
			for(int i=0; i<log.size(); i++) {
				res[i] = log.get(i);
			}
		} else {
			for(int i=lines; i>0; i--) {
				res[lines-i] = log.get(log.size()-i);
			}
		}
		
		return res;
	}
	
	public void clean() {
		if(this.log.size()>=53) {
			for(int i=0; i<50; i++) {
				this.log.removeElementAt(0);
			}
		}
	}
	
	public void clear() {
		this.log.clear();
	}
}
