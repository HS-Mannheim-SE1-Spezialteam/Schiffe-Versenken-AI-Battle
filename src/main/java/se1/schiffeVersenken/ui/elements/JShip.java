package se1.schiffeVersenken.ui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import se1.schiffeVersenken.interfaces.util.Direction;

public class JShip extends JComponent{

	private static final long serialVersionUID = 1L;
	
	private int size = 0;
	private Direction direction = Direction.HORIZONTAL;
	
	public JShip(int size, Direction direction){
		this.size = size;
		this.direction = direction;
		this.setPreferredSize(new Dimension(direction == Direction.HORIZONTAL ? size * 48 : 48, direction == Direction.VERTICAL ? size * 48 : 48));
	}
	
	@Override
	public void paint(Graphics gfx){
		gfx.setColor(new Color(0, 0, 255));
		gfx.fillRect(0, 0, direction == Direction.HORIZONTAL ? size * 48 : 48, direction == Direction.VERTICAL ? size * 48 : 48);
	}
}
