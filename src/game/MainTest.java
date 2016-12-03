package game;

import objects.mob.*;

import engine.*;

public class MainTest {
	public static void main(String[] args) {
		Player p = new Player(0, 0, new MessageLog());
		System.out.println(p.drawHealthBar());
		for(int i=0; i<20; i++) {
			p.harm(5);
			System.out.println(p.drawHealthBar());
		}
	}
}
