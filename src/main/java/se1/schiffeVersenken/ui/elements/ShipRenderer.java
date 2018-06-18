package se1.schiffeVersenken.ui.elements;

import java.awt.Graphics;

import se1.schiffeVersenken.interfaces.util.Direction;

public class ShipRenderer extends ObjectRenderer{

	int size = 0;
	Direction dir = Direction.HORIZONTAL;
	
	public ShipRenderer(int x, int y, Direction dir, int length) {
		super(x, y);
		this.size = length;
		this.dir = dir;
	}

	@Override
	public void simpleDraw(Graphics gfx) {
		if(size == 1) {
			
		}else {
			for(int i = 0; i < size; i++) {
				
				int u = dir == Direction.HORIZONTAL ? 0 : 96;
				int v = i == 0 ? 0 : i + 1 == size ? 96 : 48;
				
				gfx.drawImage(objects,
						this.x + (dir == Direction.HORIZONTAL ? 48 * i : 0),
						this.y + (dir == Direction.VERTICAL ? 48 * i : 0),
						this.x + (dir == Direction.HORIZONTAL ? 48 + 48 * i : 48),
						this.y + (dir == Direction.VERTICAL ? 48 + 48 * i : 48),
						u,v, u + 48, v + 48, null);
			}
		}
	}

}
