package se1.schiffeVersenken.ui.frames.setup;

import javax.swing.JFrame;

import se1.schiffeVersenken.botBattle.PlayerInfo;

public class GameSetup extends JFrame{
	
	private static final long serialVersionUID = 1L;

	
	public GameSetup(PlayerInfo[] playerInformations){
		this.setTitle("Schiffe Versenken - Setup");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(new SetupPanel(playerInformations));
		this.pack();
		this.setLayout(null);

		this.setLocationRelativeTo(null);
	};
}