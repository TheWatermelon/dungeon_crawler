package game;

import engine.*;

public class Main {
	public static void main(String[] args) {
		Map customMap = new Map(50, 50);
		customMap.generateDungeon();
		customMap.printOnConsole();
	}
}
