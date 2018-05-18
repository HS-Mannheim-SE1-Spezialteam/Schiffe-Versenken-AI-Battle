package se1.schiffeVersenken.ui.frames.game;

import javax.swing.JFrame;

public class GameArea extends JFrame{

	public GameArea(){
		this.setTitle("Schiffe Versenken - Playground");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(new GamePanel());
		this.pack();
		this.setLayout(null);

		this.setLocationRelativeTo(null);
	}
	
}
