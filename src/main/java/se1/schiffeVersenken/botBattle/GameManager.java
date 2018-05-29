package se1.schiffeVersenken.botBattle;

import se1.schiffeVersenken.botBattle.world.ShipWorld;
import se1.schiffeVersenken.botBattle.world.ShipWorldImpl;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.PlayerCreator;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.TurnAction;
import se1.schiffeVersenken.interfaces.exception.ActionPositionOutOfBounds;
import se1.schiffeVersenken.interfaces.exception.InvalidActionException;
import se1.schiffeVersenken.interfaces.util.Position;

public class GameManager {
	
	public boolean running;
	public final Game g1;
	public final Game g2;
	
	public GameManager(GameSettings settings, PlayerCreator playerCreator1, PlayerCreator playerCreator2) {
		g1 = new Game(settings, playerCreator1, playerCreator2);
		g2 = new Game(settings, playerCreator2, playerCreator1);
	}
	
	public boolean hasNext() {
		return !running;
	}
	
	public void step() {
		//TODO: implement
	}
	
	public static class Game {
		
		final Player player;
		final ShipWorld ownWorld;
		
		public Game(GameSettings settings, PlayerCreator playerCreator, PlayerCreator other) {
			player = playerCreator.createPlayer(settings, other.getClass());
			ShipWorld[] ownWorld = new ShipWorld[1];
			player.placeShips(ships -> ownWorld[0] = ShipWorldImpl.create(settings, ships));
			this.ownWorld = ownWorld[0];
		}
		
		void takeTurn(Game other) {
			player.takeTurn(new TurnAction() {
				@Override
				protected Tile shootTile0(Position position) throws InvalidActionException {
					try {
						return other.ownWorld.getTile(position);
					} catch (ArrayIndexOutOfBoundsException e) {
						throw new ActionPositionOutOfBounds(position);
					}
				}
			});
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
