package se1.schiffeVersenken.ui.frames.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import se1.schiffeVersenken.ui.UIControll;
import se1.schiffeVersenken.ui.elements.ObjectRenderer;

public class GamePanel extends JPanel{
	
	private BufferedImage background = null;

	protected ObjectRenderer[] ships;
	protected List<ObjectRenderer> shots = new ArrayList<ObjectRenderer>();
	
	public GamePanel() {		
		this.setPreferredSize(new Dimension((int) (1200 * UIControll.sizeMultiplier), (int) (576 * UIControll.sizeMultiplier)));
		try {
			this.background = ImageIO.read(this.getClass().getResourceAsStream("playground.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics gfx){
		super.paint(gfx);
		
		if(this.background != null){
			gfx.drawImage(this.background, 0, 0, this.getWidth() , this.getHeight(), this);
		}
		
		if(ships != null)
		synchronized (this.ships) {
			for(ObjectRenderer ship : this.ships) {
				ship.simpleDraw(gfx, UIControll.sizeMultiplier);
			}
		}
		
		if(shots != null)		
		synchronized (this.shots) {
			for(ObjectRenderer ship : this.shots) {
				ship.simpleDraw(gfx, UIControll.sizeMultiplier);
			}
		}
	}
}