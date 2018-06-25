package se1.schiffeVersenken.ais.PlayerNumber9;

import se1.schiffeVersenken.interfaces.GameSettings.ShipBorderConditions;
import se1.schiffeVersenken.interfaces.*;
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
	public void setShips(Ship[] ships) {
		
		
	}

}
