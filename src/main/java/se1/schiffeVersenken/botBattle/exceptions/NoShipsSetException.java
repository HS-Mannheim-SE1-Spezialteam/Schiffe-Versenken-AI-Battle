package se1.schiffeVersenken.botBattle.exceptions;

public class NoShipsSetException extends GameBreakingException {
	
	public NoShipsSetException(String name) {
		super("No ships were set by " + name);
	}
}
