package se1.schiffeVersenken.ais.weDontGiveAShip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.weDontGiveAShip.UI.panels.FieldPanel;
import com.weDontGiveAShip.UI.panels.MatchPanel;

import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.PlayableAI;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.PlayerCreator;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.ShipPlacer;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.TurnAction;
import se1.schiffeVersenken.interfaces.exception.shipPlacement.InvalidShipPlacementException;
import se1.schiffeVersenken.interfaces.util.Direction;
import se1.schiffeVersenken.interfaces.util.Position;

@PlayableAI("We Don't Give A Ship's AI")
public class AI extends PlayerImpl implements PlayerCreator {

	public static final int SHIP_COUNT_1 = 0;
	public static final int SHIP_COUNT_2 = 5;
	public static final int SHIP_COUNT_3 = 4;
	public static final int SHIP_COUNT_4 = 2;
	public static final int SHIP_COUNT_5 = 1;
	// total number of ships
	public static final int SHIP_COUNT = SHIP_COUNT_1 + SHIP_COUNT_2 + SHIP_COUNT_3 + SHIP_COUNT_4 + SHIP_COUNT_5;

	public static final int PLAYFIELD_SIZE = 10;

	private List<Position> alreadyTakenPositions = new ArrayList<Position>();
	public static List<Position> alreadyShotOnPositions = new ArrayList<Position>();
	
	static FieldPanel panel;

	public static Ship[] ships;
	
//	public static void main(String args[]) {
//		JFrame f = new JFrame();
//
//		f.setSize(600, 800);
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		panel = new FieldPanel(PLAYFIELD_SIZE, false);
//		f.add(panel);
//		f.setVisible(true);
//
//		new AI().placeShips(new ShipPlacerImpl());
//	}

	@Override
	public void placeShips(ShipPlacer placer) {
		Ship[] ships = generateShips();

		try {
			placer.setShips(ships);

		} catch (InvalidShipPlacementException e) {
			e.printStackTrace();
		}
		
		
//		// zeichnet auf den test frame hier in der klasse (kann später gelöscht werden)
//		for (int i = 0; i < ships.length; i++) {
//			for (Position position : ships[i].getOccupiedSpaces()) {
//				panel.setColor(position.x, position.y, Color.RED);
//			}
//		}
		
		AI.ships = ships;
	}

	@Override
	public void takeTurn(TurnAction turnAction) {

	}
	
	public Tile turn() {
		Position shootAtPosition = getShootAtPosition();
		Tile hitField = MatchPanel.whatDidIHit(AI.class, shootAtPosition.x, shootAtPosition.y);

//		try {
//			hitField = turnAction.shootTile(shootAtPosition);
//			
//		} catch (InvalidActionException e) {
//			e.printStackTrace();
//			hitField = null;
//		}

		// Hit a ship
		if (hitField == Tile.SHIP) {

			
			// Successfully destroyed a ship
		} else if (hitField == Tile.SHIP_KILL) {

			
			// Hit water
		} else if (hitField == Tile.WATER) {
			
			// do nothing i guess
		}
		
		return hitField;
	}

	//	Fürs erste einfach nur auf random Positions schießen
	private Position getShootAtPosition() {
		Random rand = new Random();
		Position position;
		
		do {
			position = new Position(rand.nextInt(PLAYFIELD_SIZE), rand.nextInt(PLAYFIELD_SIZE));
			
		}while(alreadyShotOnPositions.contains(position));
		
		
		alreadyShotOnPositions.add(position);
		
		return position;
		
	}


	Position lastPosition = null;
	Tile lastHitTile = null;
	Ship lastHitShip = null;
	
	List<Position> allEnemeyShotPositions = new ArrayList<Position>();
	
	@Override
	public void onEnemyShot(Position lastPosition, Tile lastHitTile, Ship lastHitShip) {
		this.lastPosition = lastPosition;
		this.lastHitTile = lastHitTile;
		this.lastHitShip = lastHitShip;
		allEnemeyShotPositions.add(lastPosition);

		//	do something smart here
		
	}

	@Override
	public void gameOver(boolean youHaveWon) {
		// TODO Auto-generated method stub
	}

	public Ship[] generateShips() {
		List<Ship> shipList = new ArrayList<Ship>();

		// add ships of size 1
		for (int i = 0; i < SHIP_COUNT_1; i++) {
			shipList.add(generateShip(1));
		}

		// add ships of size 2
		for (int i = 0; i < SHIP_COUNT_2; i++) {
			shipList.add(generateShip(2));
		}

		// add ships of size 3
		for (int i = 0; i < SHIP_COUNT_3; i++) {
			shipList.add(generateShip(3));
		}

		// add ships of size 4
		for (int i = 0; i < SHIP_COUNT_4; i++) {
			shipList.add(generateShip(4));
		}

		// add ships of size 5
		for (int i = 0; i < SHIP_COUNT_5; i++) {
			shipList.add(generateShip(5));
		}

		return shipList.toArray(new Ship[SHIP_COUNT]);
	}

	private Ship generateShip(int size) {
		Ship ship;
		boolean continueSearching = false;

		Position position;
		Direction direction;

		do {
			continueSearching = false;

			direction = getRandomDirection();

			position = getRandomPosition(size, direction);

			ship = new Ship(position, direction, size);

			for (Position occupiedPosition : ship.getOccupiedSpaces()) {
				if (alreadyTakenPositions.contains(occupiedPosition)) {
					continueSearching = true;
				}
			}

		} while (continueSearching);
		
		//	Adds all occupied positions of the ship to this list, so newly generated ships don't overlap
		alreadyTakenPositions.addAll(Arrays.asList(ship.getOccupiedSpaces()));

		return ship;
	}

	private Position getRandomPosition(int size, Direction direction) {
		Random rand = new Random();

		int x, y;

		if (direction == Direction.HORIZONTAL) {
			x = rand.nextInt(PLAYFIELD_SIZE - 1 - size);
			y = rand.nextInt(PLAYFIELD_SIZE - 1);

		} else {
			x = rand.nextInt(PLAYFIELD_SIZE - 1 );
			y = rand.nextInt(PLAYFIELD_SIZE - 1 - size);
		}
		
		return new Position(x, y);
	}

	private Direction getRandomDirection() {
		Direction direction;

		// Random boolean für Ausrichtung
		if (new Random().nextBoolean()) {
			direction = Direction.HORIZONTAL;

		} else {
			direction = Direction.VERTICAL;
		}

		return direction;
	}

	@Override
	public Player createPlayer(GameSettings settings, Class<? extends PlayerCreator> otherPlayer) {
		return new AI();
	}

}
