package se1.schiffeVersenken.botBattle.gameCallback;

import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

public interface GameCallback {
	
	void init(Game game);
	
	void onShot(int id, boolean isSide1, Position position, Tile tile, Ship ship);
	
	void onGameOver(boolean isSide1, GameOverReason gameOverReason, Throwable throwable);
	
	enum GameOverReason {
		
		REASON_WIN,
		REASON_CRASH
		
	}
	
	static GameCallback emptyCallback() {
		return new GameCallback() {
			@Override
			public void init(Game game) {
			
			}
			
			@Override
			public void onShot(int id, boolean isSide1, Position position, Tile tile, Ship ship) {
			
			}
			
			@Override
			public void onGameOver(boolean isSide1, GameOverReason gameOverReason, Throwable throwable) {
			
			}
		};
	}
	
	static GameCallback mergeCallback(GameCallback c1, GameCallback c2) {
		return new GameCallback() {
			@Override
			public void init(Game game) {
				c1.init(game);
				c2.init(game);
			}
			
			@Override
			public void onShot(int id, boolean isSide1, Position position, Tile tile, Ship ship) {
				c1.onShot(id, isSide1, position, tile, ship);
				c2.onShot(id, isSide1, position, tile, ship);
			}
			
			@Override
			public void onGameOver(boolean isSide1, GameOverReason gameOverReason, Throwable throwable) {
				c1.onGameOver(isSide1, gameOverReason, throwable);
				c2.onGameOver(isSide1, gameOverReason, throwable);
			}
		};
	}
}
