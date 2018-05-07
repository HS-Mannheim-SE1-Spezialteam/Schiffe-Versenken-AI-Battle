package se1.schiffeVersenken.botBattle;

import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

interface ShipWorld extends TileWorld {
	
	Ship[] getShips();
	
	Object getObject(Position position);
	
	@Override
	Tile getTile(Position position);
	
	Ship getShip(Position position);
}
