package se1.schiffeVersenken.ais.superSpezialTeam.playerCreator;

import java.util.Random;

import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.util.Direction;
import se1.schiffeVersenken.interfaces.util.Position;

public class Field {

	private MyTile[][] tilesOfField;

	public Field() {
		// creates new Field of 10x10 tiles with starting value WATER
		tilesOfField = new MyTile[GameSettings.SIZE_OF_PLAYFIELD][GameSettings.SIZE_OF_PLAYFIELD];
		for (int i = 0; i < tilesOfField.length; i++) {
			for (int j = 0; j < tilesOfField.length; j++) {
				tilesOfField[i][j] = new MyTile();
			}
		}
	}

	public void printField() {
		for (int i = 0; i < tilesOfField.length; i++) {
			for (int j = 0; j < tilesOfField.length; j++) {
				tilesOfField[i][j].print();
			}
			System.out.println();
		}
		System.out.println();
	}

	public void setShip(Ship ship) {
		// marks tiles as Ship
		Position shipPosition = ship.getPosition();
		int row = shipPosition.y;
		int column = shipPosition.x;
		int shipLength = ship.getLength();
		Direction shipDirection = ship.getDirection();

		if (shipDirection == Direction.HORIZONTAL) {
			for (int i = column; i < column + shipLength; i++) {
				tilesOfField[row][i].setStatus(MyTileEnum.SHIP);
			}
		} else if (shipDirection == Direction.VERTICAL) {
			for (int i = row; i < row + shipLength; i++) {
				tilesOfField[i][column].setStatus(MyTileEnum.SHIP);
			}
		}
		printField();
	}

	public Position getValidPosition(int length, Direction direction) {
		int validLimit = GameSettings.SIZE_OF_PLAYFIELD - length;
		Random rand = new Random();
		int randomColumn = 0;
		int randomRow = 0;
		boolean validPositionFound = false;

		if (direction == Direction.HORIZONTAL) {
			while (!validPositionFound) {
				// tries random coordinates
				randomColumn = rand.nextInt(validLimit + 1);
				randomRow = rand.nextInt(GameSettings.SIZE_OF_PLAYFIELD);
				validPositionFound = true;

				// checks if relevant tiles are water
				for (int i = randomColumn; i < randomColumn + length; i++) {
					if (tilesOfField[randomRow][i].getStatus() != MyTileEnum.WATER)
						validPositionFound = false;
				}
			}
			System.out.println("column: " + randomColumn + ", row:" + randomRow + ", length: " + length
					+ ", direction: " + direction);

			return new Position(randomColumn, randomRow);
		} else if (direction == Direction.VERTICAL) {
			while (!validPositionFound) {
				// tries random coordinates
				randomColumn = rand.nextInt(GameSettings.SIZE_OF_PLAYFIELD);
				randomRow = rand.nextInt(validLimit + 1);
				validPositionFound = true;

				// checks if relevant tiles are water
				for (int i = randomRow; i < randomRow + length; i++) {
					if (tilesOfField[i][randomColumn].getStatus() != MyTileEnum.WATER)
						validPositionFound = false;
				}
			}
			System.out.println("column: " + randomColumn + ", row:" + randomRow + ", length: " + length
					+ ", direction: " + direction);

			return new Position(randomColumn, randomRow);
		}

		return null;
	}

	public MyTile getTileAt(Position pos) {
		return tilesOfField[pos.x][pos.y];

	}
}
