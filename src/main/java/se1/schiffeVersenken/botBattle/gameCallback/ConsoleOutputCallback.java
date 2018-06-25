package se1.schiffeVersenken.botBattle.gameCallback;

import java.io.PrintStream;

import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

import static se1.schiffeVersenken.botBattle.gameCallback.GameCallback.GameOverReason.*;

public class ConsoleOutputCallback implements GameCallback {
	
	public final PrintStream out;
	public int delay = 500;
	public boolean colored = true;
	
	private Game game;
	
	public ConsoleOutputCallback() {
		this(System.out);
	}
	
	public ConsoleOutputCallback(PrintStream out) {
		this.out = out;
	}
	
	public ConsoleOutputCallback setDelay(int delay) {
		this.delay = delay;
		return this;
	}
	
	public ConsoleOutputCallback setColored(boolean colored) {
		this.colored = colored;
		return this;
	}
	
	@Override
	public void init(Game game) {
		this.game = game;
	}
	
	@Override
	public void onShot(int id, boolean isSide1, Position position, Tile tile, Ship ship) {
		fancyStatusPrint(!isSide1 ? position : null, isSide1 ? position : null);
		
		if (delay > 0) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException ignore) {
			
			}
		}
	}
	
	private void fancyStatusPrint() {
		fancyStatusPrint(null, null);
	}
	
	private void fancyStatusPrint(Position firstHighlight, Position secondHighlight) {
		out.println("+----------------------------+");
		out.println("|   First  |      |  Second  |");
		out.println("+----------+------+----------+");
		String[] s1 = game.side1.toString(colored, game.side2.hitTiles, firstHighlight).split("\n");
		String[] s2 = game.side2.toString(colored, game.side1.hitTiles, secondHighlight).split("\n");
		for (int i = 0; i < Math.max(s1.length, s2.length); i++)
			out.println("|" + s1[i] + "|      |" + s2[i] + "|");
		out.println("+----------+------+----------+");
		out.println();
	}
	
	@Override
	public void onGameOver(boolean isSide1, GameOverReason gameOverReason, Throwable throwable) {
		out.println("\u001b[32m" + "!!!Player " + (isSide1 ? game.side1 : game.side2).playerInfo.name + " has won!!!");
		out.println("The looser is " + (!isSide1 ? game.side1 : game.side2).playerInfo.name + "\u001b[0m");
		out.println("Game over because " + (gameOverReason == REASON_WIN ? "someone won!" : gameOverReason == REASON_CRASH ? "the looser AI crashed!" : "unknown!"));
		out.println();
		out.println("Final status: ");
		fancyStatusPrint();
	}
	
}
