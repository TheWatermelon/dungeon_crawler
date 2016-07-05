package game;

import engine.Map;

public class Main {
	public static void main(String[] args) {
		Map customMap = new Map(31, 54);
		customMap.generateDungeon();
		//customMap.printOnConsole();
		customMap.printOnWindow();
	}
}
