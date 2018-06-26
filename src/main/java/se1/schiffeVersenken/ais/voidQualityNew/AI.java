package se1.schiffeVersenken.ais.voidQualityNew;

import java.util.ArrayList;
import java.util.List;

import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.GameSettings.ShipBorderConditions;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.ShipPlacer;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.TurnAction;
import se1.schiffeVersenken.interfaces.util.Direction;
import se1.schiffeVersenken.interfaces.util.Position;

public class AI implements Player {

	private GameSettings settings;
	private Position lastHit;
	private int shotCurrentSquare;
	private Fieldstate[][] field = new Fieldstate[10][10];
	private Position enemyLastShot = null;
	private int mirrorMatchEfficiency = 1;
	private Ship[] ships;
	private List<Position> freePositions;

	private final Position[] initPositions = new Position[] {

			new Position(0, 0), new Position(5, 0), new Position(0, 5), new Position(5, 5)

	};

	/*
	 * 0,0 ---------------------------------> | | | x direction | 0 | 1 | | | |
	 * |-----------|------------ | | | 2 | 3 | | | | |------------------------ | y
	 * direction | | v
	 *
	 */

	public AI(GameSettings settings) {

		this.settings = settings;
		this.shotCurrentSquare = (int) (Math.random() * 4);
		initField();
		
		freePositions = new ArrayList<Position>();
		for (int x = 0; x < GameSettings.SIZE_OF_PLAYFIELD; x++) {
			for (int y = 0; y < GameSettings.SIZE_OF_PLAYFIELD; y++) {
				freePositions.add(new Position(x, y));
			}
		}

	}

	/**
	 * 
	 * says AI which field was hit no response to a shot means that the Filed was
	 * not hit (hit water)
	 * 
	 * flield looks like that
	 * 
	 * 0,0 ---------------------------------> | x direction | | | | | | | | y
	 * direction | | v
	 * 
	 * @
	 * 
	 * 	@param xPosition, yPosition
	 * 
	 */
	public void insertDataHitAt(Position pos) {

		field[pos.x][pos.y] = Fieldstate.HIT;
		lastHit = pos;

		if (enemyLastShot != null && lastHit.equals(enemyLastShot))
			mirrorMatchEfficiency++;
		else
			mirrorMatchEfficiency--;

		clearRuledempendingFields(pos);

	}

	private void initField() {

		for (int x = 0; x < 10; x++)
			for (int y = 0; y < 10; y++)
				field[x][y] = Fieldstate.NOT_SHOT;
	}

	private void clearRuledempendingFields(Position pos) {

		if (settings.getShipBorderConditions() == ShipBorderConditions.TOUCHING_ALLOWED) {

			// no touchingRule
			return;

		}

		if (settings.getShipBorderConditions() == ShipBorderConditions.NO_DIRECT_TOUCH) {

			// field left or right was hit --> horizontal ship
			if ((pos.x - 1 >= 0 && field[pos.x - 1][pos.y] == Fieldstate.HIT)
					|| (pos.x + 1 < 10 && field[pos.x + 1][pos.y] == Fieldstate.HIT))
				verticalShipHit(pos);

			// field above or under was hit --> vertical ship
			if ((pos.y - 1 >= 0 && field[pos.x][pos.y - 1] == Fieldstate.HIT)
					|| (pos.y + 1 < 10 && field[pos.x][pos.y + 1] == Fieldstate.HIT))
				horizontalShipHit(pos);

		}

		if (settings.getShipBorderConditions() == ShipBorderConditions.NO_DIRECT_AND_DIAGONAL_TOUCH) {

			// field left or right was hit --> horizontal ship
			if ((pos.x - 1 >= 0 && field[pos.x - 1][pos.y] == Fieldstate.HIT)
					|| (pos.x + 1 < 10 && field[pos.x + 1][pos.y] == Fieldstate.HIT))
				horizontalShipHit(pos);

			// field above or under was hit --> vertical ship
			if ((pos.y - 1 >= 0 && field[pos.x][pos.y - 1] == Fieldstate.HIT)
					|| (pos.y + 1 < 10 && field[pos.x][pos.y + 1] == Fieldstate.HIT))
				verticalShipHit(pos);

			checkEdges(pos);

		}

	}

	public String toString() {

		String s = "";

		for (int y = 0; y < 10; y++)
			for (int x = 0; x < 10; x++) {

				if (field[x][y] == Fieldstate.HIT)
					s += ("[X]");
				else if (field[x][y] == Fieldstate.NOT_HIT)
					s += ("[N]");
				else
					s += ("[ ]");

				if (x == 9)
					s += "\n";

			}

		return s;

	}

	public void insertDataEnemyLastShot(Position shotAt) {

		this.enemyLastShot = shotAt;
	}

	private Position mirrorMatch() {

		if (shotPremission(enemyLastShot))
			return enemyLastShot;

		return null;

	}

	private void checkEdges(Position pos) {

		// field down left
		if (pos.y + 1 < 10 && pos.x - 1 >= 0)
			field[pos.x - 1][pos.y + 1] = Fieldstate.NOT_HIT;

		// field down right
		if (pos.y + 1 < 10 && pos.x + 1 < 10)
			field[pos.x + 1][pos.y + 1] = Fieldstate.NOT_HIT;

		// field top right
		if (pos.y - 1 >= 0 && pos.x + 1 < 10)
			field[pos.x + 1][pos.y - 1] = Fieldstate.NOT_HIT;

		// field top left
		if (pos.y - 1 >= 0 && pos.x - 1 >= 0)
			field[pos.x - 1][pos.y - 1] = Fieldstate.NOT_HIT;
	}

	private int random0to4() {

		return (int) (Math.random() * 5);
	}

	int interSpaceDeathLoopCounter = 0;
	public Position getNextShot() {
		boolean correctshot = false;
		Position nextShot = null;
		
		interSpaceDeathLoopCounter++;
		if (interSpaceDeathLoopCounter >= 1000) {
			interSpaceDeathLoopCounter = 0;
			int pos = (int)(Math.random()*freePositions.size());
			nextShot = freePositions.get(pos);
			freePositions.remove(pos);
			return nextShot;
		}

		if (mirrorMatchEfficiency >= 0 && enemyLastShot != null)
			nextShot = mirrorMatch();

		if (nextShot != null) {

			// init every shot Field with not hit. Will be changed by messeging the ai with
			// a hit.
			field[nextShot.x][nextShot.y] = Fieldstate.NOT_HIT;
			return nextShot;
		}

		// check last hit
		if (lastHit != null)
			nextShot = checkHit(lastHit);

		if (nextShot != null) {
			// init every shot Field with not hit. Will be changed by messeging the ai with
			// a hit.
			field[nextShot.x][nextShot.y] = Fieldstate.NOT_HIT;
			return nextShot;
		}

		//System.out.println("last hit checked");

		// check every other hit
		for (int x = 0; x < 10; x++)
			for (int y = 0; y < 10; y++)
				if (field[x][y] == Fieldstate.HIT) {
					nextShot = checkHit(new Position(x, y));
					if (nextShot != null)
						return nextShot;
				}

		//System.out.println("all other hits checked");
		interSpaceDeathLoopCounter++;
		if (interSpaceDeathLoopCounter >= 1000) {
			interSpaceDeathLoopCounter = 0;
			int pos = (int)(Math.random()*freePositions.size());
			nextShot = freePositions.get(pos);
			freePositions.remove(pos);
			return nextShot;
		}
		while (!correctshot) {
			interSpaceDeathLoopCounter++;
			if (interSpaceDeathLoopCounter >= 1000) {
				interSpaceDeathLoopCounter = 0;
				int pos = (int)(Math.random()*freePositions.size());
				nextShot = freePositions.get(pos);
				freePositions.remove(pos);
				return nextShot;
			}
			// square completely shot?
			if (squareCompletelyShot(initPositions[shotCurrentSquare]))
				nextSquare();

			switch (shotCurrentSquare) {

			case 0:
				correctshot = shotPremission(
						nextShot = new Position(initPositions[0].x + random0to4(), initPositions[0].y + random0to4()));
				break;
			case 1:
				correctshot = shotPremission(
						nextShot = new Position(initPositions[1].x + random0to4(), initPositions[1].y + random0to4()));
				break;
			case 2:
				correctshot = shotPremission(
						nextShot = new Position(initPositions[2].x + random0to4(), initPositions[2].y + random0to4()));
				break;
			case 3:
				correctshot = shotPremission(
						nextShot = new Position(initPositions[3].x + random0to4(), initPositions[3].y + random0to4()));
				break;

			}

			//System.out.println("correct shot?" + correctshot);
		}

		nextSquare();

		// init every shot Field with not hit. Will be changed by messeging the ai with
		// a hit.
		field[nextShot.x][nextShot.y] = Fieldstate.NOT_HIT;

		return nextShot;

	}

	private boolean squareCompletelyShot(Position init) {
		for (int x = init.x; x < init.x + 5; x++)
			for (int y = init.y; y < init.y + 5; y++)
				if (field[x][y] == Fieldstate.NOT_SHOT)
					return false;

		return true;
	}

	private Position checkHit(Position hit) {

		// left from field
		if (hit.x - 1 >= 0 && field[hit.x - 1][hit.y] == Fieldstate.NOT_SHOT)
			return new Position(hit.x - 1, hit.y);

		// above field
		if (hit.y - 1 >= 0 && field[hit.x][hit.y - 1] == Fieldstate.NOT_SHOT)
			return new Position(hit.x, hit.y - 1);

		// right field
		if (hit.x + 1 < 10 && field[hit.x + 1][hit.y] == Fieldstate.NOT_SHOT)
			return new Position(hit.x + 1, hit.y);

		// bottom field
		if (hit.y + 1 < 10 && field[hit.x][hit.y + 1] == Fieldstate.NOT_SHOT)
			return new Position(hit.x, hit.y + 1);

		return null;
	}

	private void nextSquare() {

		shotCurrentSquare = (shotCurrentSquare + 1) % 4;
	}

	private boolean shotPremission(Position pos) {

		if (field[pos.x][pos.y] == Fieldstate.NOT_SHOT)
			return true;

		return false;
	}

	public void insertDataShipDestroyed(Position startPos, int length, Direction o) {

		if (settings.getShipBorderConditions() == ShipBorderConditions.TOUCHING_ALLOWED)
			return;

		if (o == Direction.HORIZONTAL) {

			// field left from start
			if (startPos.x - 1 >= 0)
				field[startPos.x - 1][startPos.y] = Fieldstate.NOT_HIT;

			// field right from end
			if (startPos.x + length < 10)
				field[startPos.x + length][startPos.y] = Fieldstate.NOT_HIT;

			clearRuledempendingFields(startPos);
			if (startPos.x + length - 1 < 10)
				clearRuledempendingFields(new Position(startPos.x + length - 1, startPos.y));

		} else {

			// field under start
			if (startPos.y + 1 < 10)
				field[startPos.x][startPos.y + 1] = Fieldstate.NOT_HIT;

			// field above end
			if (startPos.y - length - 1 >= 0)
				field[startPos.x][startPos.y - length - 1] = Fieldstate.NOT_HIT;

			clearRuledempendingFields(startPos);
			if (startPos.y - length - 1 >= 0)
				clearRuledempendingFields(new Position(startPos.x, startPos.y - length - 1));

		}

	}

	private void horizontalShipHit(Position pos) {

		horizontalShipHit(pos, false);

	}

	private void horizontalShipHit(Position pos, boolean backwards) {

		// above one has to be empty
		if (pos.y - 1 >= 0)
			field[pos.x][pos.y - 1] = Fieldstate.NOT_HIT;

		// bottom one has to be empty
		if (pos.y + 1 < 10)
			field[pos.x][pos.y + 1] = Fieldstate.NOT_HIT;

		// left one was hit?
		if (pos.x - 1 >= 0 && field[pos.x - 1][pos.y] == Fieldstate.HIT && !backwards)
			horizontalShipHit(new Position(pos.x - 1, pos.y), false);

		// right one was hit?
		if (pos.x + 1 < 10 && field[pos.x + 1][pos.y] == Fieldstate.HIT)
			horizontalShipHit(new Position(pos.x + 1, pos.y), true);

	}

	private void verticalShipHit(Position pos) {

		verticalShipHit(pos, false);
	}

	private void verticalShipHit(Position pos, boolean backwards) {

		// left one has to be empty
		if (pos.x - 1 >= 0)
			field[pos.x - 1][pos.y] = Fieldstate.NOT_HIT;

		// right one has to be empty
		if (pos.x + 1 < 10)
			field[pos.x + 1][pos.y] = Fieldstate.NOT_HIT;

		// above one was hit?
		if (pos.y - 1 >= 0 && field[pos.x][pos.y - 1] == Fieldstate.HIT && !backwards)
			verticalShipHit(new Position(pos.x, pos.y - 1), false);

		// bottom one was hit?
		if (pos.y + 1 < 10 && field[pos.x][pos.y + 1] == Fieldstate.HIT)
			verticalShipHit(new Position(pos.x, pos.y + 1), true);

	}

	@Override
	public void placeShips(ShipPlacer placer) {
		boolean success = false;
		while (!success) {
			int totalCount = 0;
			for (int i = 0; i < settings.getNumberOfShips().length; i++) {
				totalCount += settings.getNumberOfShips()[i];
			}

			ships = new Ship[totalCount];

			int absoluteShipIndex = 0;
			for (int size = 1; size <= settings.getNumberOfShips().length; size++) {
				for (int j = 0; j < settings.getNumberOfShips(size); j++) {
					ships[absoluteShipIndex] = new Ship(
							new Position((int) (Math.random() * GameSettings.SIZE_OF_PLAYFIELD),
									(int) (Math.random() * GameSettings.SIZE_OF_PLAYFIELD)),
							(Math.random() * 2 == 1) ? Direction.HORIZONTAL : Direction.VERTICAL, size);
					absoluteShipIndex++;
				}
			}

			try {
				placer.setShips(ships);
				success = true;
			} catch (Exception e) {
			}
		}
	}

	int hits = 0;
	Position firstHit = null;
	@Override
	public void takeTurn(TurnAction turnAction) {
		boolean success = false;
		while (!success) {
			try {
				Position p = getNextShot();
				Tile t = turnAction.shootTile(p);
				if (t == Tile.SHIP_KILL || t == Tile.SHIP) {
					if (hits == 0)
					{
						firstHit = p;
					}
					hits++;
					insertDataHitAt(p);
					if (t == Tile.SHIP_KILL) {
						//insertDataShipDestroyed(firstHit, hits, Direction.HORIZONTAL);
						hits = 0;
					}
				}
				success = true;
			} catch (Exception e) {

			}
		}
	}

	
	@Override
	public void onEnemyShot(Position position, Tile tile, Ship ship) {
		if (tile == Tile.SHIP_KILL)
			System.out.println("NEIN!");
	}

	@Override
	public void gameOver(boolean youHaveWon) {
		if (youHaveWon)
			System.out.println("Uggaa uggaaaa!");
		else
			System.out.println("whei yu hert mi? :(");
	}
}
