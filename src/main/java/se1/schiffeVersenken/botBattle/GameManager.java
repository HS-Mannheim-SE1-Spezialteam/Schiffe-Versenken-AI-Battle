package se1.schiffeVersenken.botBattle;

import java.util.stream.Stream;

import se1.schiffeVersenken.botBattle.exceptions.NoShipsSetException;
import se1.schiffeVersenken.botBattle.util.Grid2;
import se1.schiffeVersenken.botBattle.world.ShipWorld;
import se1.schiffeVersenken.botBattle.world.ShipWorldImpl;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.PlayerCreator;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.TurnAction;
import se1.schiffeVersenken.interfaces.exception.ActionPositionOutOfBoundsException;
import se1.schiffeVersenken.interfaces.exception.InvalidActionException;
import se1.schiffeVersenken.interfaces.util.Position;

public class GameManager implements Runnable {
	
	public final Game g1;
	public final Game g2;
	public GameCallback callback;
	
	public GameManager(GameSettings settings, PlayerCreator playerCreator1, PlayerCreator playerCreator2, GameCallback callback) throws NoShipsSetException {
		this.g1 = new Game(settings, playerCreator1, playerCreator2);
		this.g2 = new Game(settings, playerCreator2, playerCreator1);
		this.callback = callback;
	}
	
	@Override
	public void run() {
		Game active = g1;
		Game other = g2;
		
		while (true) {
			active.takeTurn(other);
			if (active.hasLost())
				break;
			
			//switch players
			Game temp = active;
			active = other;
			other = temp;
		}
		onGameOver(other, active);
	}
	
	private void onGameOver(Game won, Game loose) {
		callback.onGameOver(won, loose);
		won.player.gameOver(true);
		loose.player.gameOver(false);
	}
	
	public class Game {
		
		public final Player player;
		public final ShipWorld ownWorld;
		public final Grid2<Boolean> hitTiles = new Grid2<>(GameSettings.SIZE_OF_PLAYFIELD_VECTOR, Boolean.FALSE);
		
		public Game(GameSettings settings, PlayerCreator playerCreator, PlayerCreator other) throws NoShipsSetException {
			this.player = playerCreator.createPlayer(settings, other.getClass());
			
			ShipWorld[] ownWorld = new ShipWorld[1];
			player.placeShips(ships -> ownWorld[0] = ShipWorldImpl.create(settings, ships));
			if (ownWorld[0] == null)
				throw new NoShipsSetException(player.getClass().getName());
			this.ownWorld = ownWorld[0];
		}
		
		void takeTurn(Game other) {
			player.takeTurn(new TurnAction() {
				@Override
				protected Tile shootTile0(Position position) throws InvalidActionException {
					Ship ship;
					try {
						ship = other.ownWorld.getShip(position);
						hitTiles.set(position, Boolean.TRUE);
					} catch (ArrayIndexOutOfBoundsException e) {
						throw new ActionPositionOutOfBoundsException(position);
					}
					
					try {
						//changes start here
						Tile tile = handle(ship);
						callback.onShot(Game.this, other, position, tile, ship);
						other.player.onEnemyShot(position, tile, ship);
						return tile;
					} catch (Exception e) {
						throw new RuntimeException("BotBattle error!", e);
					}
				}
				
				private Tile handle(Ship ship) {
					if (ship == null)
						return Tile.WATER;
					
					ship.takeHit();
					if (ship.isSunk())
						return Tile.SHIP_KILL;
					return Tile.SHIP;
				}
			});
		}
		
		public boolean hasLost() {
			return Stream.of(ownWorld.getShips()).allMatch(Ship::isSunk);
		}
	}
	
	//not sure if we need this, but keeping it here if we do
//	//static
//	public static Ship[][] orderShips(Ship[] ships) {
//		Ship[][] ret = new Ship[GameSettings.SIZE_OF_PLAYFIELD][];
//		Stream<Ship> stream = Stream.of(ships);
//		for (int i = 0; i < GameSettings.SIZE_OF_PLAYFIELD; i++) {
//			final int i2 = i;
//			ret[i] = stream.filter(ship -> ship.getLength() - 1 == i2).toArray(Ship[]::new);
//		}
//		return ret;
//	}
}
