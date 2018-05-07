package se1.schiffeVersenken.botBattle;

import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.PlayerCreator;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.TurnAction;
import se1.schiffeVersenken.interfaces.exception.InvalidShipPlacementException;
import se1.schiffeVersenken.interfaces.util.Position;

import java.util.Iterator;
import java.util.stream.Stream;

public class GameManager {
	
	public PlayerCreator playerCreator1;
	public PlayerCreator playerCreator2;
	
	public GameManager(PlayerCreator playerCreator1, PlayerCreator playerCreator2) {
		this.playerCreator1 = playerCreator1;
		this.playerCreator2 = playerCreator2;
	}
	
	public void runGame(GameSettings settings) throws InvalidShipPlacementException {
		Game g1 = new Game(settings, playerCreator1);
		Game g2 = new Game(settings, playerCreator2);
		
		for (Game game : new RepeatingIterable<>(g1, g2)) {
		
		}
	}
	
	private class Game {
		
		Player player;
		ShipWorld ownWorld;
		
		public Game(GameSettings settings, PlayerCreator playerCreator) throws InvalidShipPlacementException {
			player = playerCreator.createPlayer(settings);
			ownWorld = new ShipWorldImpl(player.getShipPlacement());
		}
		
		public void takeTurn(Game other) {
			player.takeTurn(new TurnAction() {
				@Override
				public Tile shootTile(Position position) {
					return null;
				}
			});
		}
	}
	
	static class RepeatingIterable<T> implements Iterable<T> {
		
		public T[] array;
		
		@SafeVarargs
		public RepeatingIterable(T... array) {
			this.array = array;
		}
		
		@Override
		public Iterator<T> iterator() {
			return new Iterator<T>() {
				public int index;
				
				@Override
				public boolean hasNext() {
					return true;
				}
				
				@Override
				public T next() {
					if (index >= array.length)
						index = 0;
					return array[index++];
				}
			};
		}
	}
	
	//static
	public static Ship[][] orderShips(Ship[] ships) {
		Ship[][] ret = new Ship[GameSettings.SIZE_OF_PLAYFIELD][];
		Stream<Ship> stream = Stream.of(ships);
		for (int i = 0; i < GameSettings.SIZE_OF_PLAYFIELD; i++) {
			final int i2 = i;
			ret[i] = stream.filter(ship -> ship.length - 1 == i2).toArray(Ship[]::new);
		}
		return ret;
	}
}
