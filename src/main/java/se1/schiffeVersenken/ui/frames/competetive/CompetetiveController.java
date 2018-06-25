package se1.schiffeVersenken.ui.frames.competetive;

import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.botBattle.PlayerInfo;
import se1.schiffeVersenken.botBattle.gameCallback.GameCallback;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.GameSettingsBuilder;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;
import se1.schiffeVersenken.ui.frames.fast.FastCallback;

public class CompetetiveController implements GameCallback{

	private PlayerInfo[] playerInformations;
	
	private static int roundAmount = 10;
	
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
		Thread trd = new Thread(() -> new Game(this.competetiveGameSettings, switchSides ? allGames[gameRunning].getP2() : allGames[gameRunning].getP1(), switchSides ? allGames[gameRunning].getP1() : allGames[gameRunning].getP2(), this).run());
		switchSides = !switchSides;
		trd.start();
	}
	
	private void nextMatch() {
		this.gameRunning ++;
		if(this.gameRunning < allGames.length)
			nextGame();
	}
	
	@Override
	public void init(Game game) {}


	@Override
	public void onShot(int id, boolean isSide1, Position position, Tile tile, Ship ship) {
//		try {
//			Thread.sleep(0);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}


	@Override
	public void onGameOver(boolean isSide1) {
		boolean result = false;
		if(isSide1) {
			result = allGames[gameRunning].pointP1();
		}else {
			result = allGames[gameRunning].pointP2();
		}
		
		if(result)
			nextMatch();
		else
			nextGame();
	}
	
}
