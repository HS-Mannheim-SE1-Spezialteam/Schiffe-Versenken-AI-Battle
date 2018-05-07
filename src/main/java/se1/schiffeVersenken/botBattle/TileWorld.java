package se1.schiffeVersenken.botBattle;

import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

interface TileWorld {
	
	Tile getTile(Position position);
}
