package se1.schiffeVersenken.ais.superSpezialTeam.playerCreator;

import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.PlayableAI;
import se1.schiffeVersenken.interfaces.GameSettings.ShipBorderConditions;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.PlayerCreator;


@PlayableAI("super.spezialTeam();*")
public class MyPlayerCreator implements PlayerCreator {

	@Override
	public Player createPlayer(GameSettings settings, Class<? extends PlayerCreator> otherPlayer) {
		// otherPlayer can be ignored
		int[] numberOfShips = settings.getNumberOfShips();
		ShipBorderConditions ourShipBorderCondition = settings.getShipBorderConditions();

		Player myPlayer = new MyPlayer(numberOfShips, ourShipBorderCondition);
		return myPlayer;
	}

}
