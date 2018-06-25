package se1.schiffeVersenken.ais.PlayerNumber9;

import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.GameSettings.ShipBorderConditions;
import se1.schiffeVersenken.interfaces.PlayableAI;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.PlayerCreator;

@PlayableAI("PlayerNumber9")
public class MyPlayerCreator implements PlayerCreator{
	
	@Override
	public Player createPlayer (GameSettings settings, Class<? extends PlayerCreator> otherPlayer){
		
		ShipBorderConditions border = settings.getShipBorderConditions();
		int[] numberShips = settings.getNumberOfShips();
		
		Player superDuperPlayer = new MyPlayer(numberShips, border);
		
		return superDuperPlayer;
	}
}
