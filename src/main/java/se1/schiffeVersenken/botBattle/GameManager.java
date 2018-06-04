package se1.schiffeVersenken.botBattle;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import se1.schiffeVersenken.botBattle.exceptions.NoActionTakenException;
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
import se1.schiffeVersenken.interfaces.exception.action.ActionPositionOutOfBoundsException;
import se1.schiffeVersenken.interfaces.exception.action.AlreadyShotPositionException;
import se1.schiffeVersenken.interfaces.exception.action.InvalidActionException;
import se1.schiffeVersenken.interfaces.util.Position;

public class GameManager implements Runnable {
	
	public final Game g1;
	public final Game g2;
	public GameCallback callback;
	
	public GameManager(GameSettings settings, PlayerCreator playerCreator1, PlayerCreator playerCreator2, GameCallback callback) {
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
		
		public Game(GameSettings settings, PlayerCreator playerCreator, PlayerCreator other) {
			this.player = playerCreator.createPlayer(settings, other.getClass());
			
			ShipWorld[] ownWorld = new ShipWorld[1];
			player.placeShips(ships -> ownWorld[0] = ShipWorldImpl.create(settings, ships));
			if (ownWorld[0] == null)
				throw new NoShipsSetException(player.getClass().getName());
			this.ownWorld = ownWorld[0];
		}
		
		void takeTurn(Game other) {
			TurnAction turnAction = new TurnAction() {
				@Override
				protected Tile shootTile0(Position position) throws InvalidActionException {
					try {
						if (hitTiles.get(position))
							throw new AlreadyShotPositionException(position);
					} catch (ArrayIndexOutOfBoundsException e) {
						throw new ActionPositionOutOfBoundsException(position);
					}
					
					try {
						hitTiles.set(position, Boolean.TRUE);
						
						//changes start here
						Ship ship = other.ownWorld.getShip(position);
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
			};
			
			player.takeTurn(turnAction);
			if (!turnAction.isTaken())
				throw new NoActionTakenException(player.getClass().getSimpleName());
		}
		
		public boolean hasLost() {
			return Stream.of(ownWorld.getShips()).allMatch(Ship::isSunk);
		}
		
		public String toString(boolean allowColor) {
			return toString(allowColor, null);
		}
		
		public String toString(boolean allowColor, Position highlightPos) {
			AtomicInteger charCounter = new AtomicInteger();
			Map<Ship, Integer> shipToCharacter = Arrays.stream(ownWorld.getShips()).collect(Collectors.toMap(o -> o, o -> 'A' + charCounter.getAndIncrement()));
			
			StringBuilder b = new StringBuilder();
			for (int y = 0; y < GameSettings.SIZE_OF_PLAYFIELD; y++) {
				for (int x = 0; x < GameSettings.SIZE_OF_PLAYFIELD; x++) {
					Position pos = new Position(x, y);
					Ship ship = ownWorld.getShip(pos);
					boolean hit = hitTiles.get(pos);
					
					if (allowColor)
						b.append(pos.equals(highlightPos) ? "\u001b[31m" : hit ? "\u001b[33m" : "\u001b[36m");
					if (ship == null)
						b.append(hit ? "*" : " ");
					else
						b.append((char) shipToCharacter.get(ship).intValue());
				}
				if (allowColor)
					b.append("\u001b[0m");
				b.append('\n');
			}
			return b.toString();
		}
		
		@Override
		public String toString() {
			return toString(false);
		}
	}
	
}
