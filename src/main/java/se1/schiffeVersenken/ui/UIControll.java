package se1.schiffeVersenken.ui;

import se1.schiffeVersenken.ReferenceAi.ReferencePlayerCreator;
import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.botBattle.PlayerInfo;
import se1.schiffeVersenken.botBattle.gameCallback.ConsoleOutputCallback;
import se1.schiffeVersenken.botBattle.gameCallback.GameCallback;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.GameSettingsBuilder;
import se1.schiffeVersenken.ui.frames.game.GameArea;
import se1.schiffeVersenken.ui.frames.setup.GameSetup;

import javax.swing.JFrame;

public class UIControll {
	
	public static final JFrame setup = new GameSetup();
//	public static final JFrame game = new GameArea();
	
	public static void main(String[] args) {
		setup.setVisible(true);
//		game.setVisible(true);
	}
	
	public static void initGame(GameSettings.ShipBorderConditions border, int[] ships, boolean fastMode) {
		GameSettingsBuilder builder = new GameSettingsBuilder()
				.setShipBorderConditions(border);
		for (int i = 0; i < ships.length; i++)
			builder.setNumOfShips(i + 1, ships[i]);
		
		PlayerInfo playerInfo = new PlayerInfo(new ReferencePlayerCreator());
		Thread trd = new Thread(() -> new Game(builder.createGameSettings(), playerInfo, playerInfo, GameCallback.mergeCallback(new GameArea(), new ConsoleOutputCallback().setDelay(0))).run());
		trd.start();
	}
}
