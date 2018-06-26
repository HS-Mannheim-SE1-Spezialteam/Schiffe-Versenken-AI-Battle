package se1.schiffeVersenken.ais.weDontGiveAShip.UI.panels;



import se1.schiffeVersenken.ais.weDontGiveAShip.AI;
import se1.schiffeVersenken.ais.weDontGiveAShip.PlayerImpl;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

public class MatchPanel{
	
	/** Pass class of the player that shot **/
	public static Tile whatDidIHit(Class<? extends Player> class1, int x, int y) {
		Position pos = new Position(x, y);
		
		if(class1.equals(AI.class)) {
			Ship[] ships = PlayerImpl.ships;
			
			for (int i = 0; i < ships.length; i++) {
				for (Position position : ships[i].getOccupiedSpaces()) {
					if(position.equals(pos)) {
						ships[i].takeHit();
						
						if(ships[i].isSunk()) {
							System.out.println("*** Es wurde eins deiner Schiffe an Position '"+((char)(x+1+'0'))+""+y+"' getroffen und versenkt! ***");
							return Tile.SHIP_KILL;
						}else {
							System.out.println("*** Es wurde eins deiner Schiffe an Position '"+((char)(x+1+'A'))+""+y+"' getroffen! ***");
							return Tile.SHIP;
						}
						
					}
				}
			}
			
			System.out.println("*** Es wurde keins von deinen Schiffen getroffen! ***");
			return Tile.WATER;
			
		}else {
			Ship[] ships = AI.ships;
			
			for (int i = 0; i < ships.length; i++) {
				for (Position position : ships[i].getOccupiedSpaces()) {
					if(position.equals(pos)) {
						ships[i].takeHit();
						
						if(ships[i].isSunk()) {
							return Tile.SHIP_KILL;
						}else {
							return Tile.SHIP;
						}
						
					}
				}
			}
			
			return Tile.WATER;
		}
		
	}
	
	
	/** Pass class of the player of which ships you refer to **/
	public static Ship getHitShip(Class<? extends Player> class1, int x, int y) {
		Ship[] ships;
		
		if(class1.equals(AI.class)) {
			ships = AI.ships;
			
		}else {
			ships = PlayerImpl.ships;
		}
			
		for(int i = 0; i < ships.length; i++) {
			for(Position pos : ships[i].getOccupiedSpaces()) {
				if(pos.equals(new Position(x, y))) {
					return ships[i];
				}
			}
		}
		
		return null;
	}
}
