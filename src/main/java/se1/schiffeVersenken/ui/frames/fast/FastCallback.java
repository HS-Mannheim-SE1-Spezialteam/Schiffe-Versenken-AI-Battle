package se1.schiffeVersenken.ui.frames.fast;

import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.botBattle.gameCallback.GameCallback;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

public class FastCallback implements GameCallback{
	
	Game game;
	
	@Override
	public void init(Game game) {
		this.game = game;
	}
	
	@Override
	public void shipsSet() {
	
	}
	
	@Override
	public void onShot(int id, boolean isSide1, Position position, Tile tile, Ship ship) {}

	@Override
	public void onGameOver(boolean isSide1, GameOverReason gameOverReason, Throwable throwable) {
		new WinnerScreen(isSide1 ? game.side1.playerInfo.name : game.side2.playerInfo.name).setVisible(true);
	}
}
