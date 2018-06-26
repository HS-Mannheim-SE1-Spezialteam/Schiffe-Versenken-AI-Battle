package se1.schiffeVersenken.ais.voidQualityNew;

import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.PlayableAI;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.PlayerCreator;

@PlayableAI("Laser cyclops from outta space of death")
public class AICreator implements PlayerCreator {
	@Override
	public Player createPlayer(GameSettings settings, Class<? extends PlayerCreator> otherPlayer) {
		return new AI(settings);
	}
}
