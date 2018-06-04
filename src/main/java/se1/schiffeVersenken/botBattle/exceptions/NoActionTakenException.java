package se1.schiffeVersenken.botBattle.exceptions;

public class NoActionTakenException extends GameBreakingException {
	
	public NoActionTakenException(String name) {
		super("Player " + name + " did not take his action!");
	}
}
