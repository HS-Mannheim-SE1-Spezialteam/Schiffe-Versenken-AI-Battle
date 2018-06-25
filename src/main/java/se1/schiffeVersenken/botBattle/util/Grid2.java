package se1.schiffeVersenken.botBattle.util;

import java.util.Arrays;
import java.util.function.UnaryOperator;

import se1.schiffeVersenken.interfaces.util.Position;

import static se1.schiffeVersenken.interfaces.util.Position.NULL_VECTOR;

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
	private int getIndex(Position position) throws ArrayIndexOutOfBoundsException {
		if (!position.boundsCheck(NULL_VECTOR, size))
			throw new ArrayIndexOutOfBoundsException(NULL_VECTOR + " <= " + position + " < " + size);
		return position.y * size.x + position.x;
	}
	
	//access
	public T get(Position position) throws ArrayIndexOutOfBoundsException {
		return array[getIndex(position)];
	}
	
	public void set(Position position, T obj) throws ArrayIndexOutOfBoundsException {
		array[getIndex(position)] = obj;
	}
	
	public void set(Position[] position, T obj) throws ArrayIndexOutOfBoundsException {
		int[] index = new int[position.length];
		for (int i = 0; i < position.length; i++)
			index[i] = getIndex(position[i]);
		for (int i : index)
			array[i] = obj;
	}
	
	public T replace(Position position, T obj) throws ArrayIndexOutOfBoundsException {
		int index = getIndex(position);
		T ret = array[index];
		array[index] = obj;
		return ret;
	}
	
	public T accumulate(Position position, UnaryOperator<T> function) {
		int index = getIndex(position);
		T ret = function.apply(array[index]);
		array[index] = ret;
		return ret;
	}
}
