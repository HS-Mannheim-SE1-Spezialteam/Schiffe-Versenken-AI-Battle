package se1.schiffeVersenken.ais.superSpezialTeam.playerCreator;

import java.util.Random;

import se1.schiffeVersenken.ais.superSpezialTeam.takeTurn.PlayerAI;
import se1.schiffeVersenken.interfaces.GameSettings.ShipBorderConditions;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.ShipPlacer;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.TurnAction;
import se1.schiffeVersenken.interfaces.exception.action.InvalidActionException;
import se1.schiffeVersenken.interfaces.exception.shipPlacement.InvalidShipPlacementException;
import se1.schiffeVersenken.interfaces.util.Direction;
import se1.schiffeVersenken.interfaces.util.Position;

public class MyPlayer implements Player {
	private Ship[] ships;
	private Field myField;
	private Field enemyField;
	private int[] numberOfShips;
	private ShipBorderConditions ourShipBorderCondition;
	private PlayerAI ki = new PlayerAI();

	public MyPlayer(int[] numberOfShips, ShipBorderConditions ourShipBorderCondition) {
		this.numberOfShips = numberOfShips;
		this.ourShipBorderCondition = ourShipBorderCondition;
		myField = new Field();
		enemyField = new Field();
		PlayerAI ki = new PlayerAI();
	}

	@Override
	public void placeShips(ShipPlacer placer) {
		// this method sets the ships to field
		// numberOfShips = number of ships of certain length
		for (int i = 0; i < numberOfShips.length; i++) {
			int length = i + 1;
			for (int j = 0; j < numberOfShips[i]; j++) {
				// finds valid Position for certain direction and length
				Direction tempDirection = randomDirection();
				Position tempPosition = myField.getValidPosition(length, tempDirection);

				// creates new Ship, adds it to ships[] and updates field
				Ship tempShip = new Ship(tempPosition, tempDirection, length);
				addShip(tempShip);
				myField.setShip(tempShip);
			}
		}
		// displays field with set ships
		System.out.println("Print field:");
		myField.printField();
		for(Ship ship: ships){
			System.out.println(ship);
		}

		// method from spezialteam that checks if ships are set correctly
		try {
			placer.setShips(ships);
		} catch (InvalidShipPlacementException e) {
			e.printStackTrace();
			// this case will never happen
		}

	}

	@Override
	// ki implementation
	public void takeTurn(TurnAction turnAction) {
		System.out.println("--takeTurn--");
		// TODO Auto-generated method stub
		// field update nicht vergessen!
		//turnAction.shootTile(position);
		Position position = ki.nextPositionToShoot();
		try {
			Tile tile = turnAction.shootTile(position);
			System.out.println("shot tile value: " + tile.toString());
			ki.markTile(tile);
			System.out.println("");
			System.out.println("");
			// mark tile in enemyfield vergessen!!!!
		} catch (InvalidActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	private Direction randomDirection() {
		Random rand = new Random();
		int direction = rand.nextInt(100);
		if (direction % 2 == 0)
			return Direction.VERTICAL;
		else
			return Direction.HORIZONTAL;
	}
	
	private void addShip(Ship ship) {
		// adds a Ship to ships[]
		if (ships == null) {
			ships = new Ship[1];
			ships[0] = ship;
		} else {
			Ship[] tempShips = new Ship[ships.length + 1];
			for (int i = 0; i < tempShips.length - 1; i++) {
				tempShips[i] = ships[i];
			}
			tempShips[tempShips.length - 1] = ship;
			ships = tempShips;
		}

	}

	public Ship[] getShips() {
		return ships;
	}
	
	public Field getField(){
		return myField;
	}
	
	protected Tile shootTile0(Position position) throws InvalidActionException {
		MyTile tile = myField.getTileAt(position);
		if(tile.getStatus() == MyTileEnum.SHIP){
			enemyField.getTileAt(position).setStatus(MyTileEnum.SHIP_HIT);
			if(ships[0].isSunk())
			return Tile.SHIP;
		} else if(tile.getStatus() == MyTileEnum.WATER){
			enemyField.getTileAt(position).setStatus(MyTileEnum.WATER_HIT);
			return Tile.WATER;
		}
		return Tile.WATER;
	}

	@Override
	// not very important
	public void onEnemyShot(Position position, Tile tile, Ship ship) {
		// TODO Auto-generated method stub

	}

	@Override
	// not very important
	public void gameOver(boolean youHaveWon) {
		// TODO Auto-generated method stub
		// cry
	}

}
