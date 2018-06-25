package se1.schiffeVersenken.ais.voidQuality;

import java.util.ArrayList;
import java.util.List;

import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.ShipPlacer;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.TurnAction;
import se1.schiffeVersenken.interfaces.exception.action.InvalidActionException;
import se1.schiffeVersenken.interfaces.util.Direction;
import se1.schiffeVersenken.interfaces.util.Position;

public class AI implements se1.schiffeVersenken.interfaces.Player {
	private Ship[] ships;
	private GameSettings settings;	
	private List<Position> freePositions;
	
	public AI(GameSettings settings) {
		this.settings = settings;
		freePositions = new ArrayList<Position>();
		for (int x = 0; x < GameSettings.SIZE_OF_PLAYFIELD; x++) {
			for (int y = 0; y < GameSettings.SIZE_OF_PLAYFIELD; y++) {
				freePositions.add(new Position(x, y));
			}
		}
	}
	
	@Override
	public void placeShips(ShipPlacer placer) {
		boolean success = false;
		while (!success) {
			int totalCount = 0;
			for (int i = 0; i < settings.getNumberOfShips().length; i++) {
				totalCount+=settings.getNumberOfShips()[i];
			}
			
			ships = new Ship[totalCount];
			
			int absoluteShipIndex = 0;
			for (int size = 1; size <= settings.getNumberOfShips().length; size++) {
				for (int j = 0; j < settings.getNumberOfShips(size); j++) {
					ships[absoluteShipIndex] = new Ship(new Position((int)(Math.random()*GameSettings.SIZE_OF_PLAYFIELD), (int)(Math.random()*GameSettings.SIZE_OF_PLAYFIELD)), (Math.random()*2 == 1) ? Direction.HORIZONTAL : Direction.VERTICAL , size);
					absoluteShipIndex++;
				}
			}
			
			try {
				placer.setShips(ships);
				success = true;
			}
			catch(Exception e) {
			}
		}
	}

	@Override
	public void takeTurn(TurnAction turnAction) {
		int index = (int)(Math.random()*freePositions.size());
		try {
			
			turnAction.shootTile(freePositions.get(index));
		} catch (InvalidActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		freePositions.remove(index);
	}
	
	final static String[] messagesHit = { "BuhhhhhHHH!", "*ergs*", "Stoooopid bitch!", "huheuheuheuhehueh", "Damn son!", "You hax!", "Eat shit!", "Your mother must be proud of you.", "You will suffer for this!", "Unfair...", "I don't like your attitude...", "phewww" };
	
	@Override
	public void onEnemyShot(Position position, Tile tile, Ship ship) {
		if (tile == Tile.SHIP_KILL) {
			System.out.println(messagesHit[(int)(Math.random()*messagesHit.length)]);
		}
	}

	@Override
	public void gameOver(boolean youHaveWon) {
		if (youHaveWon)
			System.out.println("Uga ugaaaaaaaAAA!");
		else
			System.out.println("Uh sheet I puuped my pehnts :-(");
	}
	
}
