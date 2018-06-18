package se1.schiffeVersenken.ui.frames.setup;

import javax.swing.JFrame;

public class GameSetup extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public GameSetup(){
		this.setTitle("Schiffe Versenken - Setup");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(new SetupPanel());
		this.pack();
		this.setLayout(null);

		this.setLocationRelativeTo(null);
	};
}