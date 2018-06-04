package se1.schiffeVersenken.botBattle;

import se1.schiffeVersenken.botBattle.GameManager.Game;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

public interface GameCallback {
	
	void onShot(Game game, Game other, Position position, Tile tile, Ship ship);
	
	void onGameOver(Game won, Game loose);
	
	static GameCallback emptyCallback() {
		return new GameCallback() {
			@Override
			public void onShot(Game game, Game other, Position position, Tile tile, Ship ship) {
			
			}
			
			@Override
			public void onGameOver(Game won, Game loose) {
			
			}
		};
	}
	
}
