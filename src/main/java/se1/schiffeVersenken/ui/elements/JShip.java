package se1.schiffeVersenken.ui.elements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import se1.schiffeVersenken.interfaces.util.Direction;

public class JShip extends JComponent{

	private static final long serialVersionUID = 1L;
	
	private static BufferedImage objects = null;

	
	private int size = 0;
	private Direction direction = Direction.HORIZONTAL;
	
	static {
		try {
			objects = ImageIO.read(JShip.class.getResourceAsStream("objects.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public JShip(int size, Direction direction){
		this.size = size;
		this.direction = direction;
		this.setPreferredSize(new Dimension(direction == Direction.HORIZONTAL ? size * 48 : 48, direction == Direction.VERTICAL ? size * 48 : 48));
	}
	
	@Override
	public void paint(Graphics gfx){
		if(size == 1) {
			gfx.drawImage(objects,
					(int) 0,
					(int) 0,
					(int) 48,
					(int) 48,
					48, 96, 96, 155, null);
		}else {
			for(int i = 0; i < size; i++) {
				
				int u = direction == Direction.HORIZONTAL ? 0 : 92;
				int v = direction == Direction.VERTICAL ? 0 : i == 0 ? 0 : i + 1 == size ? 96 : 48;
				
				gfx.drawImage(objects,
						direction == Direction.HORIZONTAL ? 48 * i : 0,
						direction == Direction.VERTICAL ? 48 * i : 0,
						direction == Direction.HORIZONTAL ? 48 + 48 * i : 48,
						direction == Direction.VERTICAL ? 48 + 48 * i : 48,
						u,v, u + 48, v + 48, this);
			}
		}
	}
}