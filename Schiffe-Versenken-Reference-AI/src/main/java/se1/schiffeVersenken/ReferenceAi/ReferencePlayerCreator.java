package se1.schiffeVersenken.ReferenceAi;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.PlayableAI;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.PlayerCreator;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.ShipPlacer;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.TurnAction;
import se1.schiffeVersenken.interfaces.exception.action.AlreadyShotPositionException;
import se1.schiffeVersenken.interfaces.exception.action.InvalidActionException;
import se1.schiffeVersenken.interfaces.exception.shipPlacement.InvalidShipPlacementException;
import se1.schiffeVersenken.interfaces.util.Direction;
import se1.schiffeVersenken.interfaces.util.Position;

@PlayableAI("Reference Player")
public class ReferencePlayerCreator implements PlayerCreator {
	
	private static final int TRIES_COMPLETE_REDO = 100000;
	private static final int TRIES_REPLACE_SHIP = 200;
	
	private final AtomicInteger NAME_COUNTER = new AtomicInteger();
	private final Supplier<Random> RANDOM_CREATOR;
	private boolean printShipConfig = false;
	private boolean allowTalking = false;
	
	public ReferencePlayerCreator() {
		this(Random::new);
	}
	
	public ReferencePlayerCreator(int seed) {
		this(() -> new Random(seed));
	}
	
	public ReferencePlayerCreator(Supplier<Random> RANDOM_CREATOR) {
		this.RANDOM_CREATOR = RANDOM_CREATOR;
	}
	
	public ReferencePlayerCreator setPrintShipConfig(boolean printShipConfig) {
		this.printShipConfig = printShipConfig;
		return this;
	}
	
	public ReferencePlayerCreator setAllowTalking(boolean allowTalking) {
		this.allowTalking = allowTalking;
		return this;
	}
	
	@Override
	public Player createPlayer(GameSettings settings, Class<? extends PlayerCreator> otherPlayer) {
		return new ReferencePlayer(settings);
	}
	
	@PlayableAI("Reference Player")
	private class ReferencePlayer implements Player {
		
		private final int id;
		private final GameSettings settings;
		private final Random r = RANDOM_CREATOR.get();
		private Ship[] ships;
		
		private int shotCount;
		
		public ReferencePlayer(GameSettings settings) {
			this.settings = settings;
			id = NAME_COUNTER.getAndIncrement();
		}
		
		/**
		 * Warning: nested for loops ahead!!!
		 */
		@Override
		public void placeShips(ShipPlacer placer) {
			int[] numberOfShips = settings.getNumberOfShips();
			int totalShipCount = IntStream.of(numberOfShips).sum();
			
			if (printShipConfig)
				println("Placing ships...");
			labelCompleteRetry:
			for (int completeTry = 0; true; completeTry++) {
				if (!(completeTry < TRIES_COMPLETE_REDO))
					throw new RuntimeException("Out of tries!");
				
				int shipArrayIndex = 0;
				ships = new Ship[totalShipCount];
				for (int shipLength = numberOfShips.length; shipLength > 0; shipLength--) {
					int shipCount = numberOfShips[shipLength - 1];
					
					for (int i = 0; i < shipCount; i++) {
						for (int replaceTry = 0; true; replaceTry++) {
							if (!(replaceTry < TRIES_REPLACE_SHIP))
								continue labelCompleteRetry;
							
							Direction direction = r.nextBoolean() ? Direction.HORIZONTAL : Direction.VERTICAL;
							Position position = new Position(
									r.nextInt(GameSettings.SIZE_OF_PLAYFIELD - ((direction == Direction.VERTICAL) ? shipLength : 1)),
									r.nextInt(GameSettings.SIZE_OF_PLAYFIELD - ((direction == Direction.HORIZONTAL) ? shipLength : 1)));
							ships[shipArrayIndex] = new Ship(position, direction, shipLength);
							
							try {
								ShipWorldImplChanged.create(settings, Arrays.copyOf(ships, shipArrayIndex + 1));
								shipArrayIndex++;
								break;
							} catch (InvalidShipPlacementException ignore) {
								//the new ship doesn't work, retrying
							}
						}
					}
				}
				break;
			}
			
			if (printShipConfig) {
				println("All ships:");
				Stream.of(ships).forEach(s -> println(Objects.toString(s)));
				println();
			}
			
			try {
				placer.setShips(ships);
			} catch (InvalidShipPlacementException e) {
				throw new RuntimeException("Should not happen!", e);
			}
		}
		
		@Override
		public void takeTurn(TurnAction turnAction) {
			shotCount++;
			if (shotCount > GameSettings.SIZE_OF_PLAYFIELD * GameSettings.SIZE_OF_PLAYFIELD)
				throw new RuntimeException("Should not happen");
			
			while (true) {
				try {
					Position position = new Position(r.nextInt(GameSettings.SIZE_OF_PLAYFIELD), r.nextInt(GameSettings.SIZE_OF_PLAYFIELD));
					Tile tile = turnAction.shootTile(position);
					if (allowTalking)
						println(position + " -> " + tile);
					break;
				} catch (AlreadyShotPositionException ignore) {
					//continue
				} catch (InvalidActionException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
		@Override
		public void onEnemyShot(Position position, Tile tile, Ship ship) {
			if (allowTalking) {
				if (ship != null)
					println("Ship id " + indexOf(ships, ship) + (ship.isSunk() ? " got sunk!" : " got hit: " + ship.getHealth() + " HP left!"));
			}
		}
		
		private <T> int indexOf(T[] array, T obj) {
			for (int i = 0; i < array.length; i++)
				if (array[i] == obj)
					return i;
			return -1;
		}
		
		@Override
		public void gameOver(boolean youHaveWon) {
			if (allowTalking) {
				if (youHaveWon)
					println("Winner Winner Chicken Dinner!");
				else
					println("Screw this, I'm out!");
			}
		}
		
		//print
		private void println(String msg) {
			System.out.println("<ReferencePlayer" + id + ">: " + msg);
		}
		
		private void println() {
			System.out.println();
		}
	}
}
