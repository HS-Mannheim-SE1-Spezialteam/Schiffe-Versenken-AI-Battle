package se1.schiffeVersenken.ui.frames.game;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import se1.schiffeVersenken.ui.elements.JShip;
import se1.schiffeVersenken.ui.elements.ObjectRenderer;

public class GamePanel extends JPanel{
	
	private BufferedImage background = null;
	private BufferedImage objects = null;

	protected ObjectRenderer[] ships;
	protected List<ObjectRenderer> shots = new ArrayList<ObjectRenderer>();
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(1200, 576));
		try {
			this.background = ImageIO.read(this.getClass().getResourceAsStream("playground.png"));
			this.objects = ImageIO.read(this.getClass().getResourceAsStream("objects.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	@Override
//	public void repaint(Graphics gfx) {
//		
//	}
	
	@Override
	public void paint(Graphics gfx){
		super.paint(gfx);
		
		if(this.background != null){
			gfx.drawImage(this.background, 0, 0, this);
		}

		if(ships != null)
		for(ObjectRenderer ship : this.ships) {
			ship.simpleDraw(gfx);
		}
		
		if(shots != null)
		for(ObjectRenderer ship : this.shots) {
			ship.simpleDraw(gfx);
		}
	}
}