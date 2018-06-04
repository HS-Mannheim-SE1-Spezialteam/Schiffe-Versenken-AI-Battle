package se1.schiffeVersenken.ReferenceAi;

import se1.schiffeVersenken.botBattle.ConsoleOutputCallback;
import se1.schiffeVersenken.botBattle.GameManager;
import se1.schiffeVersenken.botBattle.exceptions.NoShipsSetException;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.GameSettingsBuilder;

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
		
		ReferencePlayerCreator referencePlayerCreator = new ReferencePlayerCreator().setPrintShipConfig(true).setAllowTalking(false);
		new GameManager(gameSettings, referencePlayerCreator, referencePlayerCreator, new ConsoleOutputCallback().setDelay(1000)).run();
	}
	
}
