package se1.schiffeVersenken.ais.team3;

import se1.schiffeVersenken.interfaces.GameSettings.ShipBorderConditions;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.ShipPlacer;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.TurnAction;
import se1.schiffeVersenken.interfaces.exception.shipPlacement.InvalidShipPlacementException;
import se1.schiffeVersenken.interfaces.util.Position;

public class MyPlayer implements Player, ShipPlacer{
	private int[] numberShips;
	private ShipBorderConditions border;
	private AI ai;
	
	public MyPlayer(int[] numberShips, ShipBorderConditions border){
		this.numberShips = numberShips;
		this.border = border;
		if(border == ShipBorderConditions.NO_DIRECT_AND_DIAGONAL_TOUCH){
			ai = new AI(new Spielfeld(0));
		}
		else{
			ai = new AI(new Spielfeld(1));
		}
	}

	@Override
	public void placeShips(ShipPlacer placer) {
		ai.setShips();
		
	}

	@Override
	public void takeTurn(TurnAction turnAction) {
		ai.takeTurn();
		
	}

	@Override
	public void onEnemyShot(Position position, Tile tile, Ship ship) {
		
		
	}

	@Override
	public void gameOver(boolean youHaveWon) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShips(Ship[] ships) throws InvalidShipPlacementException {
		
		
	}

}
