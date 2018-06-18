package se1.schiffeVersenken.ui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import se1.schiffeVersenken.ReferenceAi.ReferencePlayerCreator;
import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.botBattle.PlayerInfo;
import se1.schiffeVersenken.botBattle.gameCallback.ConsoleOutputCallback;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.GameSettingsBuilder;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.ui.frames.game.GameArea;
import se1.schiffeVersenken.ui.frames.setup.GameSetup;

public class UIControll {

	public static final JFrame setup = new GameSetup();
//	public static final JFrame game = new GameArea();
	
	public static void main(String[] args) {
		setup.setVisible(true);
//		game.setVisible(true);
	}
	
	public static void initGame(GameSettings.ShipBorderConditions border, int[] ships, boolean fastMode) {
		GameSettingsBuilder builder = new GameSettingsBuilder().setShipBorderConditions(GameSettings.ShipBorderConditions.TOUCHING_ALLOWED);
				
		for(int i = 0; i < ships.length; i++) {
			if(ships[i] > 0) builder.setNumOfShips(i + 1, ships[i]);
		}
				
		PlayerInfo playerInfo = new PlayerInfo(new ReferencePlayerCreator());
		Thread trd = new Thread(new Runnable() {
			@Override
			public void run() {
				new Game(builder.createGameSettings(), playerInfo, playerInfo, new GameArea()).run();
			}
		});
		trd.start();
	}
}
