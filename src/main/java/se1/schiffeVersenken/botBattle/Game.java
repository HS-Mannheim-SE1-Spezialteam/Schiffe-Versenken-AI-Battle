package se1.schiffeVersenken.botBattle;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import se1.schiffeVersenken.botBattle.exceptions.GameBreakingException;
import se1.schiffeVersenken.botBattle.exceptions.NoActionTakenException;
import se1.schiffeVersenken.botBattle.exceptions.NoShipsSetException;
import se1.schiffeVersenken.botBattle.gameCallback.GameCallback;
import se1.schiffeVersenken.botBattle.util.Grid2;
import se1.schiffeVersenken.botBattle.world.ShipWorld;
import se1.schiffeVersenken.botBattle.world.ShipWorldImpl;
import se1.schiffeVersenken.interfaces.*;
import se1.schiffeVersenken.interfaces.exception.action.ActionPositionOutOfBoundsException;
import se1.schiffeVersenken.interfaces.exception.action.AlreadyShotPositionException;
import se1.schiffeVersenken.interfaces.exception.action.InvalidActionException;
import se1.schiffeVersenken.interfaces.util.Position;

public class Game implements Runnable {
	
	public final GameSettings settings;
	public final PlayerInfo playerCreator1;
	public final PlayerInfo playerCreator2;
	public final GameCallback callback;
	
	public Side side1;
	public Side side2;
	
	public Game(GameSettings settings, PlayerInfo playerCreator1, PlayerInfo playerCreator2, GameCallback callback) {
		this.settings = settings;
		this.playerCreator1 = playerCreator1;
		this.playerCreator2 = playerCreator2;
		this.callback = callback;
	}
	
	public void run() {
		this.side1 = new Side(playerCreator1);
		this.side2 = new Side(playerCreator2);
		callback.init(this);
		
		try {
			side1.init(side2.playerInfo);
		} catch (Throwable e) {
			callback.onGameOver(false, GameCallback.GameOverReason.REASON_CRASH, e);
			return;
		}
		
		try {
			side2.init(side2.playerInfo);
		} catch (Throwable e) {
			callback.onGameOver(true, GameCallback.GameOverReason.REASON_CRASH, e);
			return;
		}
		callback.shipsSet();
		
		Side active = side1;
		Side other = side2;
		
		for (int id = 0; true; id++) {
			CustomTurnAction turnAction = new CustomTurnAction(active, other, id);
			try {
				//actually take the turn
				active.player.takeTurn(turnAction);
				if (!turnAction.isTaken())
					throw new NoActionTakenException(active.playerInfo.name);
				
				//if hasLost
				if (Stream.of(other.ownWorld.getShips()).allMatch(Ship::isSunk))
					break;
			} catch (Throwable e) {
				callback.onGameOver(side1 != active, GameCallback.GameOverReason.REASON_CRASH, e);
				return;
			}
			
			if (!turnAction.wasHit) {
				//switch players
				Side temp = active;
				active = other;
				other = temp;
			}
		}
		
		callback.onGameOver(side1 == active, GameCallback.GameOverReason.REASON_WIN, null);
		try {
			active.player.gameOver(true);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			other.player.gameOver(false);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A {@link Side} represents one {@link Player} of the two inside a Game.
	 */
	public class Side {
		
		public final PlayerInfo playerInfo;
		public Player player;
		public ShipWorld ownWorld;
		public final Grid2<Boolean> hitTiles = new Grid2<>(GameSettings.SIZE_OF_PLAYFIELD_VECTOR, Boolean.FALSE);
		
		public Side(PlayerInfo playerCreator) {
			this.playerInfo = playerCreator;
		}
		
		public void init(PlayerInfo other) {
			this.player = playerInfo.creator.createPlayer(settings, other.creator.getClass());
			
			ShipWorld[] ownWorld = new ShipWorld[1];
			player.placeShips(ships -> ownWorld[0] = ShipWorldImpl.create(settings, Arrays.copyOf(ships, ships.length)));
			if (ownWorld[0] == null)
				throw new NoShipsSetException(playerInfo.name);
			this.ownWorld = ownWorld[0];
		}
		
		//toString
		public String toString(boolean allowColor) {
			return toString(allowColor, null, null);
		}
		
		public String toString(boolean allowColor, Grid2<Boolean> hitTiles, Position highlightPos) {
			if (ownWorld == null)
				return "No world! ";
			
			AtomicInteger charCounter = new AtomicInteger();
			Map<Ship, Integer> shipToCharacter = Arrays.stream(ownWorld.getShips())
					.collect(Collectors.toMap(o -> o, o -> 'A' + charCounter.getAndIncrement()));
			
			StringBuilder b = new StringBuilder();
			for (int y = 0; y < GameSettings.SIZE_OF_PLAYFIELD; y++) {
				for (int x = 0; x < GameSettings.SIZE_OF_PLAYFIELD; x++) {
					Position pos = new Position(x, y);
					Ship ship = ownWorld.getShip(pos);
					boolean hit = hitTiles == null ? false : hitTiles.get(pos);
					
					if (allowColor)
						b.append(pos.equals(highlightPos) ? "\u001b[31m" : (hit ? "\u001b[33m" : "\u001b[36m"));
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
	
	private class CustomTurnAction extends TurnAction {
		
		private final Side active;
		private final Side other;
		private final int id;
		private boolean wasHit;
		
		public CustomTurnAction(Side active, Side other, int id) {
			this.active = active;
			this.other = other;
			this.id = id;
		}
		
		@Override
		protected Tile shootTile0(Position position) throws InvalidActionException {
			if (!position.boundsCheck(Position.NULL_VECTOR, GameSettings.SIZE_OF_PLAYFIELD_VECTOR))
				throw new ActionPositionOutOfBoundsException(position);
			if (active.hitTiles.get(position))
				throw new AlreadyShotPositionException(position);
			
			//critical code section: changes are being done here
			try {
				active.hitTiles.set(position, Boolean.TRUE);
				
				Ship ship = other.ownWorld.getShip(position);
				Tile tile = handle(ship);
				wasHit = tile == Tile.SHIP || tile == Tile.SHIP_KILL;
				callback.onShot(id, active == side1, position, tile, ship);
				other.player.onEnemyShot(position, tile, ship);
				return tile;
			} catch (Exception e) {
				throw new GameBreakingException("BotBattle error!", e);
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
	}
}
