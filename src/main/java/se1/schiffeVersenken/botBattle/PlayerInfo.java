package se1.schiffeVersenken.botBattle;

import java.lang.reflect.InvocationTargetException;

import se1.schiffeVersenken.botBattle.exceptions.InvalidPlayerCreator;
import se1.schiffeVersenken.interfaces.PlayableAI;
import se1.schiffeVersenken.interfaces.PlayerCreator;

public class PlayerInfo {
	
	public final PlayerCreator creator;
	public final String name;
	
	public PlayerInfo(Class<? extends PlayerCreator> creatorClass) throws InvalidPlayerCreator {
		this(createPlayerCreator(creatorClass));
	}
	
	public PlayerInfo(PlayerCreator creator) {
		this(creator, getName(creator.getClass()));
	}
	
	public PlayerInfo(PlayerCreator creator, String name) {
		this.creator = creator;
		this.name = name;
	}
	
	//init
	private static String getName(Class<? extends PlayerCreator> creator) {
		PlayableAI annotation = creator.getAnnotation(PlayableAI.class);
		return annotation != null ? annotation.value() : creator.getName();
	}
	
	private static PlayerCreator createPlayerCreator(Class<? extends PlayerCreator> creator) {
		try {
			return creator.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new InvalidPlayerCreator("", e);
		}
	}
}
