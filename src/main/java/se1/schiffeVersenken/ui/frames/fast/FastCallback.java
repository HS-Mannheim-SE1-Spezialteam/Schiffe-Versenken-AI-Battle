package se1.schiffeVersenken.ui.frames.fast;

import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.botBattle.PlayerInfo;
import se1.schiffeVersenken.botBattle.gameCallback.GameCallback;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

public class FastCallback implements GameCallback{

	PlayerInfo side1;
	PlayerInfo side2;
	
	@Override
	public void init(Game game) {
		side1 = game.side1.playerInfo;
		side2 = game.side2.playerInfo;
	}

	@Override
	public void onShot(int id, boolean isSide1, Position position, Tile tile, Ship ship) {}

	@Override
	public void onGameOver(boolean isSide1) {
		new WinnerScreen(isSide1 ? side1.name : side2.name).setVisible(true);
	}
}
