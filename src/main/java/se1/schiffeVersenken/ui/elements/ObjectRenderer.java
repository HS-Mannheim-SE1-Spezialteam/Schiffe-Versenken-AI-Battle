package se1.schiffeVersenken.ui.elements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class ObjectRenderer {

	protected static BufferedImage objects = null;
	
	static {
		try {
			objects = ImageIO.read(JShip.class.getResourceAsStream("objects.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected int x, y;
	
	public ObjectRenderer(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void simpleDraw(Graphics gfx);
}
