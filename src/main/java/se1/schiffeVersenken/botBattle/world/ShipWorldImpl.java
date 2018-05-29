package se1.schiffeVersenken.botBattle.world;

import se1.schiffeVersenken.botBattle.util.Grid2;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.exception.InvalidShipPlacementException;
import se1.schiffeVersenken.interfaces.exception.OverlappingShipsException;
import se1.schiffeVersenken.interfaces.exception.TouchingShipsException;
import se1.schiffeVersenken.interfaces.util.Position;

public class ShipWorldImpl implements ShipWorld {
	
	private final Ship[] ships;
	/**
	 * either {@link Tile} or {@link Ship}
	 */
	private final Grid2<Ship> tiles;
	
	private ShipWorldImpl(Ship[] ships) throws InvalidShipPlacementException {
		this.ships = ships;
		tiles = new Grid2<>(GameSettings.SIZE_OF_PLAYFIELD_VECTOR, (Ship) null);
		
		for (Ship ship : ships) {
			for (Position vec : ship.getOccupiedSpaces()) {
				Ship replace = tiles.replace(vec, ship);
				if (replace != null)
					throw new OverlappingShipsException(vec, ship, replace);
			}
		}
	}
	
	//validate
	public static ShipWorld create(GameSettings settings, Ship[] ships) throws InvalidShipPlacementException {
		int[] numberOfShips = settings.getNumberOfShips();
		int[] count = new int[numberOfShips.length];
		for (Ship ship : ships)
			count[ship.getLength()]++;
		for (int i = 0; i < numberOfShips.length; i++)
			if (count[i] != numberOfShips[i])
				throw new InvalidShipPlacementException("Too many ships of length " + i);
		
		ShipWorldImpl shipWorld = new ShipWorldImpl(ships);
		for (Ship ship : ships)
			for (Position vec : ship.getEmptySpacesSurrounding(settings.getShipBorderConditions()))
				if (shipWorld.getTile(vec) == Tile.SHIP)
					throw new TouchingShipsException(vec, ship, shipWorld.getShip(vec));
		
		return shipWorld;
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
