package se1.schiffeVersenken.botBattle.world;

import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

public interface TileWorld {
	
	Tile getTile(Position position);
}
