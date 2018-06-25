package se1.schiffeVersenken.ais.superSpezialTeam.takeTurn;

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
			initialHit[0] = x;
			initialHit[1] = y;
		} else {
			boolean repeat = false;
			System.out.println("Shot after hit");
			System.out.println("initial Hit, x: " + initialHit[0] + ", y: " + initialHit[1]);
			System.out.println("last Hit, x: " + lastHit[0] + ", y: " + lastHit[1]);
			System.out.println("find surrounding shot");
			int ekligerCounter = 0;
			do {
				repeat = false;
				if (lastHit[0] < 9 && !isShot[lastHit[0] + 1][lastHit[1]]) {
					x = lastHit[0] + 1;
					y = lastHit[1];
				} else if (lastHit[0] > 0 && !isShot[lastHit[0] - 1][lastHit[1]]) {
					x = lastHit[0] - 1;
					y = lastHit[1];
				} else if (lastHit[1] < 9 && !isShot[lastHit[0]][lastHit[1] + 1]) {
					x = lastHit[0];
					y = lastHit[1] + 1;
				} else if (lastHit[1] > 0 && !isShot[lastHit[0]][lastHit[1] - 1]) {
					x = lastHit[0];
					y = lastHit[1] - 1;
				} else if (ekligerCounter > 6) {
					repeat = false;
					shootRandom = true;
					do {
						System.out.println("generating random shot within AI");
						x = ThreadLocalRandom.current().nextInt(0, 10);
						y = ThreadLocalRandom.current().nextInt(0, 10);
					} while (isShot[x][y]);
				} else {
					lastHit[0] = initialHit[0];
					lastHit[1] = initialHit[1];
					repeat = true;
				}

				ekligerCounter++;
			} while (repeat);
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
