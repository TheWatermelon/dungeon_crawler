package engine;

import java.util.Vector;

public class MessageLog {
	private Vector<String> log;
	
	public MessageLog() {
		this.log = new Vector<String>();
	}
	
	public void appendMessage(String msg) {
		this.log.add(msg);
	}
	
	public String getLast(int lines) {
		String res="";
		
		if(log.size()-lines<0) {
			for(int i=0; i<log.size(); i++) {
				//res += "["+i+"] "+log.get(i);
				res += log.get(i);
				res += "\t\n";
			}
		} else {
			for(int i=lines; i>0; i--) {
				//res += "["+(log.size()-i)+"] "+log.get(log.size()-i);
				res += log.get(log.size()-i);
				res += "\t\n";
			}
			res = res.substring(0, res.length()-1);
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
		this.log = new Vector<String>();
	}
}
