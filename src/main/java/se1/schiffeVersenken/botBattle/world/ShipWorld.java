package se1.schiffeVersenken.botBattle.world;

import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

public interface ShipWorld {

	Ship[] getShips();
	
	Tile getTile(Position position);
	
	Ship getShip(Position position);
}
