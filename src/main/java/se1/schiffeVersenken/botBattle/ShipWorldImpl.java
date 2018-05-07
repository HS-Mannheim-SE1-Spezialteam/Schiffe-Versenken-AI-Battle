package se1.schiffeVersenken.botBattle;

import se1.schiffeVersenken.botBattle.util.Grid2;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.exception.InvalidShipPlacementException;
import se1.schiffeVersenken.interfaces.util.Position;

class ShipWorldImpl implements ShipWorld {
	
	private final Ship[] ships;
	/**
	 * either {@link Tile} or {@link Ship}
	 */
	private final Grid2<Ship> tiles;
	
	ShipWorldImpl(Ship[] ships) throws InvalidShipPlacementException {
		this.ships = ships;
		tiles = new Grid2<>(GameSettings.SIZE_OF_PLAYFIELD_VECTOR, (Ship) null);
		
		for (Ship ship : ships)
			for (Position vec : ship.getOccupiedSpaces())
				if (tiles.replace(vec, ship) != null)
					throw new InvalidShipPlacementException("Ship " + ship + " placed on top of ship" + tiles.get(vec) + "!");
	}
	
	//getter
	@Override
	public Ship[] getShips() {
		return ships;
	}
	
	//get at position
	@Override
	public Object getObject(Position position) {
		return tiles.get(position);
	}
	
	@Override
	public Tile getTile(Position position) {
		Object o = tiles.get(position);
		if (o == null)
			return Tile.WATER;
		return Tile.SHIP;
	}
	
	@Override
	public Ship getShip(Position position) {
		return tiles.get(position);
	}
}
