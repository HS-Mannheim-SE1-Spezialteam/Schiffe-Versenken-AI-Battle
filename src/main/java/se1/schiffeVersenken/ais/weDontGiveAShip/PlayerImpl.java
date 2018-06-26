package se1.schiffeVersenken.ais.weDontGiveAShip;

import java.util.ArrayList;
import java.util.List;

import se1.schiffeVersenken.interfaces.*;
import se1.schiffeVersenken.interfaces.exception.shipPlacement.InvalidShipPlacementException;
import se1.schiffeVersenken.interfaces.util.Position;

public class PlayerImpl implements Player, PlayerCreator{

	public static Ship[] ships;
	public static List<Position> alreadyShotOnPositions = new ArrayList<Position>();
	
	@Override
	public void placeShips(ShipPlacer placer) {
		
		try {
			placer.setShips(ships);
		} catch (InvalidShipPlacementException e) {
			e.printStackTrace();
		}
		
	}

//	public Tile turn(int x, int y) {
//
//		Tile hitTile = MatchPanel.whatDidIHit(getClass(), x, y);
//
//		if(hitTile == Tile.WATER) {
//			System.out.println("*** Leider kein Treffer... ***");
//
////			Main.gui.matchPanel.field1.setColor(x, y, Color.GRAY);
//
//		}else if(hitTile == Tile.SHIP) {
//			System.out.println("*** Treffer! ***");
//
////			Main.gui.matchPanel.field1.setColor(x, y, Color.RED);
//
//		}else if(hitTile == Tile.SHIP_KILL) {
//			System.out.println("*** Treffer, versenkt! ***");
////			Ship hitShip = MatchPanel.getHitShip(AI.class, x, y);
////
////			for(Position pos : hitShip.getOccupiedSpaces()) {
////				Main.gui.matchPanel.field1.setColor(pos.x, pos.y, Color.BLACK);
////			}
//
//
//		}
//
//		alreadyShotOnPositions.add(new Position(x, y));
//
//		return hitTile;
//	}
	


	@Override
	public void takeTurn(TurnAction turnAction) {
//		turnAction.shootTile();
	}

	@Override
	public void onEnemyShot(Position position, Tile tile, Ship ship) {
		//	Position 
		
	}

	@Override
	public void gameOver(boolean youHaveWon) {
		
	}

	public void setShips(Ship[] ships) {
		PlayerImpl.ships = ships;
	}
	
	public Ship[] getShips() {
		return ships;
	}

	@Override
	public Player createPlayer(GameSettings settings, Class<? extends PlayerCreator> otherPlayer) {
		return new PlayerImpl();
	}
	
}
