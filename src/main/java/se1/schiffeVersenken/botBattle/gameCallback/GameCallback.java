package se1.schiffeVersenken.botBattle.gameCallback;

import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

public interface GameCallback {
	
	void init(Game game);
	
	void onShot(int id, boolean isSide1, Position position, Tile tile, Ship ship);
	
	void onGameOver(boolean isSide1);
	
	static GameCallback emptyCallback() {
		return new GameCallback() {
			@Override
			public void init(Game game) {
			
			}
			
			@Override
			public void onShot(int id, boolean isSide1, Position position, Tile tile, Ship ship) {
			
			}
			
			@Override
			public void onGameOver(boolean isSide1) {
			
			}
		};
	}
	
}
