package se1.schiffeVersenken.ui.elements;

import java.awt.Color;
import java.awt.Graphics;

public class BombRenderer extends ObjectRenderer{

	private boolean lightUp = true;
	private boolean ship = false;
	public BombRenderer(int x, int y, boolean ship) {
		super(x, y);
		this.ship = ship;		
	}

	@Override
	public void simpleDraw(Graphics gfx, float sizeMultiplier) {
		gfx.drawImage(objects,
				(int) (sizeMultiplier * x), (int) (sizeMultiplier * y), (int) (sizeMultiplier * (x + 48)), (int) (sizeMultiplier * (y + 48)),
				48, ship ? 48 : 0, 96, ship ? 96 : 48, null);
		if(lightUp) {
			gfx.setColor(new Color(255, 255, 255, 128));
			gfx.fillRect((int) (sizeMultiplier * x), (int) (sizeMultiplier * y), 48, 48);
			lightUp = false;
		}
	}
}
