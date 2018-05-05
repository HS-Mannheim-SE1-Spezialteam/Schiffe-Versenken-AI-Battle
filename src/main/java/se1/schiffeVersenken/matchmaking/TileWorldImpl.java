package se1.schiffeVersenken.matchmaking;

import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;
import se1.schiffeVersenken.matchmaking.util.Grid2;

class TileWorldImpl implements TileWorld {
	
	private final Grid2<Tile> tiles;
	
	public TileWorldImpl() {
		this(Tile.UNDISCOVERED);
	}
	
	public TileWorldImpl(Tile defaultTile) {
		tiles = new Grid2<>(GameSettings.SIZE_OF_PLAYFIELD_VECTOR, defaultTile);
	}
	
	@Override
	public Tile getTile(Position position) {
		return tiles.get(position);
	}
	
	public void setTile(Position position, Tile tile) {
		tiles.set(position, tile);
	}

//	public SecureTileWorld makeSecureTileWorld() {
//		return new SecureTileWorld();
//	}
//
//	private class SecureTileWorld implements TileWorld {
//
//		@Override
//		public Tile getTile(Position position) {
//			return TileWorldImpl.this.getTile(position);
//		}
//	}
}
