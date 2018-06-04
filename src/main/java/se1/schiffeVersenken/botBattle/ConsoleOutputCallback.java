package se1.schiffeVersenken.botBattle;

import java.io.PrintStream;

import se1.schiffeVersenken.botBattle.GameManager.Game;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

public class ConsoleOutputCallback implements GameCallback {
	
	public final PrintStream out;
	public int delay = 500;
	public boolean colored = true;
	
	private Game firstPlayer;
	private Game secondPlayer;
	
	private Position firstPos;
	
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
	public void onShot(Game game, Game other, Position position, Tile tile, Ship ship) {
		if (firstPlayer == null) {
			firstPlayer = game;
			secondPlayer = other;
		}
		
		if (firstPos == null) {
			firstPos = position;
		} else {
			fancyStatusPrint(firstPos, position);
			firstPos = null;
			
			if (delay > 0) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException ignore) {
				
				}
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
		String[] s1 = firstPlayer.toString(colored, firstHighlight).split("\n");
		String[] s2 = secondPlayer.toString(colored, secondHighlight).split("\n");
		for (int i = 0; i < Math.max(s1.length, s2.length); i++)
			out.println("|" + s1[i] + "|      |" + s2[i] + "|");
		out.println("+----------+------+----------+");
		out.println();
	}
	
	@Override
	public void onGameOver(Game won, Game loose) {
		if (firstPos != null)
			fancyStatusPrint(firstPos, null);
		
		out.println("\u001b[32m" + "!!!Player " + won.player.getClass().getSimpleName() + " has won!!!");
		out.println("The looser is " + loose.player.getClass().getSimpleName() + "\u001b[0m");
		out.println();
		out.println("Final status: ");
		fancyStatusPrint();
	}
	
}
