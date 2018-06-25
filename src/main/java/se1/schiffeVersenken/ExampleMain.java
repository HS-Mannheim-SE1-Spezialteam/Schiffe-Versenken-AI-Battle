package se1.schiffeVersenken;

import se1.schiffeVersenken.ais.ReferenceAi.ReferencePlayerCreator;
import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.botBattle.PlayerInfo;
import se1.schiffeVersenken.botBattle.exceptions.NoShipsSetException;
import se1.schiffeVersenken.botBattle.gameCallback.ConsoleOutputCallback;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.GameSettingsBuilder;

@SuppressWarnings("UnnecessaryLocalVariable")
public class ExampleMain {
	
	public static void main(String[] args) throws NoShipsSetException {
		GameSettings gameSettings = new GameSettingsBuilder()
				.setShipBorderConditions(GameSettings.ShipBorderConditions.TOUCHING_ALLOWED)
				.setNumOfShips(1, 5)
				.setNumOfShips(2, 4)
				.setNumOfShips(3, 3)
				.setNumOfShips(4, 2)
				.setNumOfShips(5, 1)
				.createGameSettings();
		
		//BotBattle requires a PlayerInfo Object to work with AIs. Create them with an Instance of the PlayerCreator or the PlayerCreator Class
//		PlayerInfo player1 = new PlayerInfo(ReferencePlayerCreator.class);
		PlayerInfo player1 = new PlayerInfo(new ReferencePlayerCreator());
		PlayerInfo player2 = player1;
		
		//Define the OutputCallback here: Currently Console Output.
		//Console Output allows slowing down your battle with setDelay(time_in_millis)
		ConsoleOutputCallback callback = new ConsoleOutputCallback().setDelay(1000);
		
		//create the Game and run it
		new Game(gameSettings, callback).run(player1, player2);
	}
	
}
