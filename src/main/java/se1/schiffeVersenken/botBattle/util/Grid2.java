package se1.schiffeVersenken.botBattle.util;

import se1.schiffeVersenken.interfaces.util.Position;

import java.util.Arrays;

public class Grid2<T> {
	
	private final Position size;
	private T[] array;
	
	public Grid2(Position size, T filler) {
		this(size);
		Arrays.fill(array, filler);
	}
	
	@SuppressWarnings("unchecked")
	public Grid2(Position size) {
		this(size, (T[]) new Object[size.x * size.y]);
	}
	
	public Grid2(Position size, T[] array) {
		this.size = size;
		this.array = array;
	}
	
	//internal
	private int getIndex(Position position) {
		if (!position.boundsCheck(Position.NULL_VECTOR, size))
			throw new IllegalArgumentException("Out of Bounds! " + position);
		return position.y * size.x + position.x;
	}
	
	//access
	public T get(Position position) {
		return array[getIndex(position)];
	}
	
	public void set(Position position, T obj) {
		array[getIndex(position)] = obj;
	}
	
	public void set(Position[] position, T obj) {
		int[] index = new int[position.length];
		for (int i = 0; i < position.length; i++)
			index[i] = getIndex(position[i]);
		for (int i : index)
			array[i] = obj;
	}
	
	public T replace(Position position, T obj) {
		int index = getIndex(position);
		T ret = array[index];
		array[index] = obj;
		return ret;
	}
}
