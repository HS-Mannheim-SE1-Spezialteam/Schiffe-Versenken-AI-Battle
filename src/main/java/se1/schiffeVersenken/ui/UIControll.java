package se1.schiffeVersenken.ui;

import javax.swing.JFrame;

import se1.schiffeVersenken.ReferenceAi.ReferencePlayerCreator;
import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.botBattle.PlayerInfo;
import se1.schiffeVersenken.botBattle.gameCallback.ConsoleOutputCallback;
import se1.schiffeVersenken.botBattle.gameCallback.GameCallback;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.GameSettingsBuilder;
import se1.schiffeVersenken.ui.frames.fast.FastCallback;
import se1.schiffeVersenken.ui.frames.game.GameArea;
import se1.schiffeVersenken.ui.frames.setup.GameSetup;

public class UIControll {
	
	public static JFrame setup;
	
	public static float sizeMultiplier = 1.0F;
	
	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		setup = new GameSetup(new PlayerInfo[]{
			new PlayerInfo(new se1.schiffeVersenken.ais.voidQuality.AICreator()),
				new PlayerInfo(new se1.schiffeVersenken.ais.superSpezialTeam.MyPlayerCreator()),

			new PlayerInfo(new ReferencePlayerCreator()),

			new PlayerInfo(new ReferencePlayerCreator()),

			new PlayerInfo(new ReferencePlayerCreator())
		});
		setup.setVisible(true);
	}
	
	public static void initGame(GameSettings.ShipBorderConditions border, int[] ships, PlayerInfo p1, PlayerInfo p2, boolean fastMode) {
		GameSettingsBuilder builder = new GameSettingsBuilder()
				.setShipBorderConditions(border);
		for (int i = 0; i < ships.length; i++)
			builder.setNumOfShips(i + 1, ships[i]);
		
		if(!fastMode){
			Thread trd = new Thread(() -> new Game(builder.createGameSettings(), p1, p2, GameCallback.mergeCallback(new GameArea(1000), new ConsoleOutputCallback().setDelay(0))).run());
			trd.start();
		}else{
			new Game(builder.createGameSettings(), p1, p2, new FastCallback()).run();
		}
	}
}
