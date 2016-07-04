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
}
