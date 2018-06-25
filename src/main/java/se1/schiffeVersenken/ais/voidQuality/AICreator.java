package se1.schiffeVersenken.ais.voidQuality;

import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.PlayableAI;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.PlayerCreator;

@PlayableAI("void(Quality);")
public class AICreator implements PlayerCreator {
	@Override
	public Player createPlayer(GameSettings settings, Class<? extends PlayerCreator> otherPlayer) {
		return new AI(settings);
	}
}
