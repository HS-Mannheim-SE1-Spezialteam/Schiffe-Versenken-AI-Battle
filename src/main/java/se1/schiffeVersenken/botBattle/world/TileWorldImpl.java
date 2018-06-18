package se1.schiffeVersenken.botBattle.world;

import se1.schiffeVersenken.botBattle.util.Grid2;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

public class TileWorldImpl implements TileWorld {
	
	private final Grid2<Tile> tiles;
	
	public TileWorldImpl() {
		this(null);
	}
	
	public TileWorldImpl(Tile defaultTile) {
		tiles = new Grid2<>(GameSettings.SIZE_OF_PLAYFIELD_VECTOR, defaultTile);
	}
	
	@Override
	public Tile getTile(Position position) {
		return tiles.get(position);
	}
	
	@Override
	public void setTile(Position position, Tile tile) {
		tiles.set(position, tile);
	}
	
}
