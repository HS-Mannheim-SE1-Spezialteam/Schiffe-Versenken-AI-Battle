package se1.schiffeVersenken.botBattle.exceptions;

public class InvalidPlayerCreator extends GameBreakingException {
	
	public InvalidPlayerCreator(String name) {
		super("Could not initialize PlayerCreator " + name + "!");
	}
	
	public InvalidPlayerCreator(String name, Throwable e) {
		super("Could not initialize PlayerCreator " + name + "!", e);
	}
}
