package se1.schiffeVersenken.ais.team3;

public class Spielfeld {

	private boolean[][] field = new boolean[10][10];
	private int maxX = 10;
	private int maxY = 10;
	private int shipType;
	private int positioning;

	/*
	 * default rules
	 */
	public Spielfeld() {
		this.shipType = 0;
		this.positioning = 0;
	}

	public Spielfeld(int shipType, int positioning) {
		this.shipType = shipType;
		this.positioning = positioning;
	}

	/*
	 * regular ships, different placement rule
	 */
	public Spielfeld(int positioning) {
		this.shipType = 0;
		this.positioning = positioning;
	}

	public boolean[][] getField() {
		return field;
	}

	public int getShipType() {
		return this.shipType;
	}

	public int getPositioning() {
		return this.positioning;
	}

	/*
	 * please make sure position is in bounds BEFORE calling this method
	 */
	public boolean getTile(Position pos) {
		return this.field[pos.getY()][pos.getX()];
	}

	/*
	 * please make sure position is in bounds BEFORE calling this method
	 */
	public boolean getTile(int x, int y) {
		return this.field[y][x];
	}

	/*
	 * Will check for bounds
	 */
	public boolean setTile(Position pos, int value) {
		boolean bounds = checkBounds(pos);
		if (bounds) {
			this.field[pos.getY()][pos.getX()] = true;
		}
		return bounds;
	}

	public boolean setTile(int x, int y, int value) {// will check for bounds
		boolean bounds = checkBounds(x, y);
		if (bounds) {
			this.field[y][x] = true;
		}
		return bounds;
	}

	public boolean checkBounds(Position pos) {
		boolean inside = true;
		inside &= (pos.getX() > -1);
		inside &= (pos.getY() > -1);
		inside &= (pos.getX() < this.maxX);
		inside &= (pos.getY() < this.maxY);

		return inside;
	}

	public boolean checkBounds(int x, int y) {
		boolean inside = true;
		inside &= (x > -1);
		inside &= (y > -1);
		inside &= (x < this.maxX);
		inside &= (y < this.maxY);

		return inside;
	}

	public boolean setShip(int ship, Position startPosition, int direction) {
		if (isNotEnoughSpace(ship, startPosition, direction)) {
			return false;
		} else {
			boolean set;
			Position current = startPosition;
			for (int i = 0; i < ship; i++) {
				if (getTile(current) == false) {
					// if set is false, you'll know the placement failed
					set = setTile(current, ship);
					if (!set) {
						return false;
					}
				} else {
					return false;
				}
				// to the right
				if (direction == 1) {
					current = current.right();
				}
				// to the left
				else if (direction == -1) {
					current = current.left();
				}
				// up
				else if (direction == -2) {
					current = current.up();
				}
				// down
				else if (direction == 2) {
					current = current.down();
				}
			}
			return true;
		}
	}

	public boolean isNotEnoughSpace(int ship, Position pos, int direction) {
		if (positioning == 0) {
			return isEnoughSpaceForWater(ship, pos, direction);
		} else if (positioning == 1) {
			return isEnoughSpaceAtCorners(ship, pos, direction);
		} else if (positioning == 2) {
			return isEnoughSpaceAtGenerall(ship, pos, direction);
		} else {
			return false;
		}

	}

	public boolean isEnoughSpaceAtGenerall(int ship, Position pos, int direction) {
		Position inside = pos;
		for (int i = 0; i < ship; i++) {
			if (!checkBounds(inside)) {
				return true;
			} else if (getTile(inside) != false) {
				return true;
			}
			if (direction == 1) {
				inside = inside.right();
			} else if (direction == -1) {
				inside = inside.left();
			} else if (direction == 2) {
				inside = inside.down();
			} else if (direction == -2) {
				inside = inside.up();
			} else {
				return true;
			}
		}
		return false;
	}

	public boolean isEnoughSpaceAtCorners(int ship, Position pos, int direction) {
		Position inside = pos;
		Position aroundOne = pos.getSorrounder(direction, 1);
		Position aroundTwo = pos.getSorrounder(direction, 2);

		if (checkBounds(inside.moveToDirection(-direction)) && getTile(inside.moveToDirection(-direction)) != false) {
			return true;
		}
		for (int i = 0; i <= ship; i++) {
			if (!checkBounds(inside)) {
				return true;
			} else if (checkBounds(aroundOne) && checkBounds(aroundTwo)) {
				if (getTile(inside) != false || getTile(aroundOne) != false || getTile(aroundTwo) != false) {
					return true;
				}
			} else if (checkBounds(aroundOne) && !checkBounds(aroundTwo)) {
				if (getTile(inside) != false || getTile(aroundOne) != false) {
					return true;
				}
			} else if (!checkBounds(aroundOne) && checkBounds(aroundTwo)) {
				if (getTile(inside) != false || getTile(aroundTwo) != false) {
					return true;
				}
			}

			if (direction == 1) {
				inside = inside.right();
				aroundOne = aroundOne.right();
				aroundTwo = aroundTwo.right();
			} else if (direction == -1) {
				inside = inside.left();
				aroundOne = aroundOne.left();
				aroundTwo = aroundTwo.down();
			} else if (direction == 2) {
				inside = inside.down();
				aroundOne = aroundOne.down();
				aroundTwo = aroundTwo.down();
			} else if (direction == -2) {
				inside = inside.up();
				aroundOne = aroundOne.up();
				aroundTwo = aroundTwo.up();
			} else {
				return true;
			}
		}
		return false;
	}

	public boolean isEnoughSpaceForWater(int ship, Position pos, int direction) {
		Position inside = pos.moveToDirection((direction * -1));
		Position aroundOne = inside.getSorrounder(direction, 1);
		Position aroundTwo = inside.getSorrounder(direction, 2);

		for (int i = 0; i <= ship + 1; i++) {
			if (i > 0 && !checkBounds(inside)) {
				return true;
			} else if (checkBounds(aroundOne) && checkBounds(aroundTwo)) {
				if (getTile(inside) != false || getTile(aroundOne) != false || getTile(aroundTwo) != false) {
					return true;
				}
			} else if (checkBounds(aroundOne) && !checkBounds(aroundTwo)) {
				if (getTile(inside) != false || getTile(aroundOne) != false) {
					return true;
				}
			} else if (!checkBounds(aroundOne) && checkBounds(aroundTwo)) {
				if (getTile(inside) != false || getTile(aroundTwo) != false) {
					return true;
				}
			}

			if (direction == 1) {
				inside = inside.right();
				aroundOne = aroundOne.right();
				aroundTwo = aroundTwo.right();
			} else if (direction == -1) {
				inside = inside.left();
				aroundOne = aroundOne.left();
				aroundTwo = aroundTwo.left();
			} else if (direction == 2) {
				inside = inside.down();
				aroundOne = aroundOne.down();
				aroundTwo = aroundTwo.down();
			} else if (direction == -2) {
				inside = inside.up();
				aroundOne = aroundOne.up();
				aroundTwo = aroundTwo.up();
			} else {
				return true;
			}
		}
		return false;
	}

	public boolean shootOfAi(int x, int y) {
		if (checkHit(x, y)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkHit(int vertical, int horizontal) {
		if (field[vertical][horizontal] != false) {
			if (field[vertical][horizontal] == true) {
				field[vertical][horizontal] = false;
				return true;
			} else if (field[vertical][horizontal] == false) {
				return false;
			}
		} else {
			field[vertical][horizontal] = false;
		}
		return false;
	}

	public void printField() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				String mark = "W";
				if (field[j][i]) {
					mark = "S";
				}
				System.out.print("[" + mark + "]");
			}
			System.out.println("");
		}
	}
}
