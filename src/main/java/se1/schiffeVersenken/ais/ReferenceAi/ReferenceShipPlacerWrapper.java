package se1.schiffeVersenken.ais.ReferenceAi;

import se1.schiffeVersenken.interfaces.*;
import se1.schiffeVersenken.interfaces.util.Position;

@PlayableAI("Reference Ship Placer Wrapper")
public class ReferenceShipPlacerWrapper implements PlayerCreator {
	
	private static final ReferencePlayerCreator REFERENCE_PLAYER_CREATOR = new ReferencePlayerCreator();
	
	private final PlayerCreator playerCreator;
	
	public ReferenceShipPlacerWrapper(PlayerCreator playerCreator) {
		this.playerCreator = playerCreator;
	}
	
	@Override
	public Player createPlayer(GameSettings settings, Class<? extends PlayerCreator> otherPlayer) {
		return new Player() {
			Player player = playerCreator.createPlayer(settings, otherPlayer);
			
			@Override
			public void placeShips(ShipPlacer placer) {
				REFERENCE_PLAYER_CREATOR.createPlayer(settings, otherPlayer).placeShips(placer);
			}
			
			@Override
			public void takeTurn(TurnAction turnAction) {
				player.takeTurn(turnAction);
			}
			
			@Override
			public void onEnemyShot(Position position, Tile tile, Ship ship) {
				player.onEnemyShot(position, tile, ship);
			}
			
			@Override
			public void gameOver(boolean youHaveWon) {
				player.gameOver(youHaveWon);
			}
		};
	}
}
