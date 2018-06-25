package se1.schiffeVersenken.ais.superSpezialTeam;

import java.util.concurrent.ThreadLocalRandom;

import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;

public class PlayerAI {
	private int x;
	private int y;
	private boolean shootRandom = true;

	private boolean[][] isShot = new boolean[10][10];
	private boolean[][] isShip = new boolean[10][10];
	private int[] lastHit = new int[2];
	private int[] initialHit = new int[2];

	public Position nextPositionToShoot() {
		System.out.println("--nextPositionToShoot--");
		System.out.println("shootRandom: " + shootRandom);
		// System.out.println("playerAI");
		for (boolean[] array : isShot) {
			for (boolean value : array)
				System.out.print(value);
			System.out.println();
		}
		System.out.println();
		if (shootRandom) {
			System.out.println("generating random coordinates");
			do {
				x = ThreadLocalRandom.current().nextInt(0, 10);
				y = ThreadLocalRandom.current().nextInt(0, 10);
				// System.out.println("x: " + x + ",y: " + y + " isShot: " +
				// isShot[x][y]);
			} while (isShot[x][y]);
			System.out.println("generated coordinates, x: " + x + ", y: " + y);
		} 
		isShot[x][y] = true;
		Position newPosition = new Position(x, y);
		System.out.println("newPosition: x: " + newPosition.x + ", y: " + newPosition.y);
		return newPosition;

	}

	// Handle the returned Tile and mark shot and and and
	public void markTile(Tile tile) {
		System.out.println("--markTile--");
		System.out.println(tile.toString());
		if (tile == Tile.SHIP) {
			isShip[x][y] = true;
			shootRandom = false;
			lastHit[0] = x;
			lastHit[1] = y;
		}
		if (tile == Tile.SHIP_KILL) {
			isShip[x][y] = true;
			shootRandom = true;
			lastHit[0] = x;
			lastHit[1] = y;
		}
//		if (tile == Tile.WATER) {
//			isShip[x][y] = false;
//			lastHit[0] = x;
//			lastHit[1] = y;
//		}

	}


}
