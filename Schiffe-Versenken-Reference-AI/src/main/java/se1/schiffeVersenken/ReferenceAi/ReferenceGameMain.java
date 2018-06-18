package se1.schiffeVersenken.ReferenceAi;

import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.botBattle.PlayerInfo;
import se1.schiffeVersenken.botBattle.exceptions.NoShipsSetException;
import se1.schiffeVersenken.botBattle.gameCallback.ConsoleOutputCallback;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.GameSettingsBuilder;

@SuppressWarnings("UnnecessaryLocalVariable")
public class ReferenceGameMain {
	
	public static void main(String[] args) throws NoShipsSetException {
		GameSettings gameSettings = new GameSettingsBuilder()
				.setShipBorderConditions(GameSettings.ShipBorderConditions.TOUCHING_ALLOWED)
				.setNumOfShips(1, 5)
				.setNumOfShips(2, 4)
				.setNumOfShips(3, 3)
				.setNumOfShips(4, 2)
				.setNumOfShips(5, 1)
				.createGameSettings();
		
		PlayerInfo player1 = new PlayerInfo(new ReferencePlayerCreator());
		PlayerInfo player2 = player1;
		new Game(gameSettings, player1, player2, new ConsoleOutputCallback().setDelay(1000)).run();
	}
	
}
