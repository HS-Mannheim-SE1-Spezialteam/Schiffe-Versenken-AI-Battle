package se1.schiffeVersenken.ui;

import javax.swing.JFrame;

import se1.schiffeVersenken.ui.frames.game.GameArea;
import se1.schiffeVersenken.ui.frames.setup.GameSetup;

public class UITest {

	public static void main(String[] args) {

//		JFrame setup = new GameSetup();
//		setup.setVisible(true);
//		
		JFrame game = new GameArea();
		game.setVisible(true);
	}

}
