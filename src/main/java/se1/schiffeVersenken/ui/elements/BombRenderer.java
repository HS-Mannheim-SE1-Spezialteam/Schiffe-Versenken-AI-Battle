package se1.schiffeVersenken.ui.elements;

import java.awt.Graphics;

public class BombRenderer extends ObjectRenderer{

	private boolean ship = false;
	public BombRenderer(int x, int y, boolean ship) {
		super(x, y);
		this.ship = ship;		
	}

	@Override
	public void simpleDraw(Graphics gfx) {
		gfx.drawImage(objects,
				x, y, x + 48, y + 48,
				48, ship ? 48 : 0, 96, ship ? 96 : 48, null);
	}
}
