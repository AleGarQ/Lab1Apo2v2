package ui;

import model.*;

public class Main {
	private Controller system;
	
	public Main() {
		system = new Controller();
		for (int i = 0; i < 200; i++) {
			System.out.println(system.giveTurn());
		}
		
	}
	
	public static void main(String[] args) {
		Main m = new Main();
	}
}
