package se1.schiffeVersenken.botBattle.exceptions;

public class GameBreakingException extends RuntimeException {
	public GameBreakingException() {
	}
	
	public GameBreakingException(String message) {
		super(message);
	}
	
	public GameBreakingException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public GameBreakingException(Throwable cause) {
		super(cause);
	}
}
