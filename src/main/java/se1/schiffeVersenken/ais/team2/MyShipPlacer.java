package se1.schiffeVersenken.ais.team2;

import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.ShipPlacer;
import se1.schiffeVersenken.interfaces.exception.shipPlacement.InvalidShipPlacementException;

public class MyShipPlacer implements ShipPlacer {

	private Ship[] ships;
	private Field myField;
	
	
	@Override
	public void setShips(Ship[] ships) throws InvalidShipPlacementException {
		// muss prüfen ob die Schiffe richtig gesetzt wurden
		
	}

}
