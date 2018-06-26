package se1.schiffeVersenken.ui;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;

import se1.schiffeVersenken.ais.ReferenceAi.ReferencePlayerCreator;
import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.botBattle.PlayerInfo;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.GameSettingsBuilder;
import se1.schiffeVersenken.ui.frames.fast.FastCallback;
import se1.schiffeVersenken.ui.frames.game.GameArea;
import se1.schiffeVersenken.ui.frames.setup.GameSetup;

public class UIControll {
	
	public static JFrame setup;
	public static float sizeMultiplier = 1.0F;
	public static PrintStream originalOut;
	
	public static void main(String[] args) {
//		System.setSecurityManager(new SecurityManager());
		
		originalOut = System.out;
		System.setOut(new PrintStream(new NullPrintStream()));
		
		setup = new GameSetup(new PlayerInfo[]{
//				new PlayerInfo(new ReferenceShipPlacerWrapper(new se1.schiffeVersenken.ais.voidQuality.AICreator()), "void(Quality);"),
//				new PlayerInfo(new ReferenceShipPlacerWrapper(new se1.schiffeVersenken.ais.superSpezialTeam.playerCreator.MyPlayerCreator()), "super.spezialTeam();*"),
//				new PlayerInfo(new ReferencePlayerCreator()),
//				new PlayerInfo(new ReferenceShipPlacerWrapper(new se1.schiffeVersenken.ais.PlayerNumber9.MyPlayerCreator()), "PlayerNumber9")
				new PlayerInfo(new se1.schiffeVersenken.ais.voidQuality.AICreator(), "void(Quality);"),
				new PlayerInfo(new se1.schiffeVersenken.ais.superSpezialTeam.playerCreator.MyPlayerCreator(), "super.spezialTeam();*"),
				new PlayerInfo(new ReferencePlayerCreator()),
				new PlayerInfo(new se1.schiffeVersenken.ais.PlayerNumber9.MyPlayerCreator(), "PlayerNumber9")
		});
		setup.setVisible(true);
	}
	
	public static void initGame(GameSettings.ShipBorderConditions border, int[] ships, PlayerInfo p1, PlayerInfo p2, boolean fastMode) {
		GameSettingsBuilder builder = new GameSettingsBuilder()
				.setShipBorderConditions(border);
		for (int i = 0; i < ships.length; i++)
			builder.setNumOfShips(i + 1, ships[i]);
		
		if (!fastMode) {
			Thread trd = new Thread(() -> new Game(builder.createGameSettings(), p1, p2, new GameArea(1000)).run());
			trd.start();
		} else {
			new Game(builder.createGameSettings(), p1, p2, new FastCallback()).run();
		}
	}
	
	public static class NullPrintStream extends OutputStream {
		
		@Override
		public void write(int b) {
		
		}
		
		@Override
		public void write(byte[] b) {
		
		}
		
		@Override
		public void write(byte[] b, int off, int len) {
		
		}
		
		@Override
		public void flush() {
		
		}
		
		@Override
		public void close() {
		
		}
	}
	
}
