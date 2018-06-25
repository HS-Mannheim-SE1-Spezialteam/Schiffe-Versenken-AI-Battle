package se1.schiffeVersenken.ui.frames.competetive;

import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.botBattle.PlayerInfo;
import se1.schiffeVersenken.botBattle.gameCallback.GameCallback;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.GameSettingsBuilder;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

public class CompetetiveController implements GameCallback{

	private PlayerInfo[] playerInformations;
	
	private static int roundAmount = 100;
	
	public CompetetiveController (PlayerInfo[] info) {
		playerInformations = info;
	}
	
	int gameRunning = 0;
	
	private SingleGame[] allGames = null;

	private GameSettings competetiveGameSettings = null;
	private boolean switchSides = false;
	
	public void init(GameSettings.ShipBorderConditions border, int[] ships) {
		GameSettingsBuilder builder = new GameSettingsBuilder()
				.setShipBorderConditions(border);
		for (int i = 0; i < ships.length; i++)
			builder.setNumOfShips(i + 1, ships[i]);
		
		this.competetiveGameSettings = builder.createGameSettings();
		
		int totalGames = 0;
		for(int i = 0; i < playerInformations.length; i++) {
			totalGames += playerInformations.length - (i + 1);
		}
		
		allGames = new SingleGame[totalGames];
		
		int currentGame = 0;
		for(int i = 0; i < playerInformations.length; i++) {
			for(int j = i + 1; j < playerInformations.length; j++) {
				allGames[currentGame++] = new SingleGame(playerInformations[i], playerInformations[j], 0, 0, roundAmount);
			}
		}
		
		MultipleGames games = new MultipleGames(allGames);
		games.setVisible(true);
		
		nextGame();
	}

	private void nextGame() {
		new Game(this.competetiveGameSettings, this).run(switchSides ? allGames[gameRunning].getP2() : allGames[gameRunning].getP1(), switchSides ? allGames[gameRunning].getP1() : allGames[gameRunning].getP2());
		switchSides = !switchSides;
	}
	
	private synchronized void nextMatch() {
		this.gameRunning ++;
		if(this.gameRunning < allGames.length)
			nextGame();
	}
	
	@Override
	public void init(Game game) {}

	@Override
	public void onShot(int id, boolean isSide1, Position position, Tile tile, Ship ship) {
//		try {
//			Thread.sleep(1);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public synchronized  void onGameOver(boolean isSide1, GameOverReason gameOverReason, Throwable throwable) {
		boolean result = false;
		if(isSide1 && switchSides) {
			result = allGames[gameRunning].pointP2();
		}else {
			result = allGames[gameRunning].pointP1();
		}
		
		if(result)
			nextMatch();
		else
			nextGame();
	}
	
}
