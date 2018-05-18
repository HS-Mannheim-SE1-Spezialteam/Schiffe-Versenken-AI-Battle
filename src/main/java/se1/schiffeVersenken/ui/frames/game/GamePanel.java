package se1.schiffeVersenken.ui.frames.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	
	private BufferedImage background = null;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(1200, 576));
		try {
			this.background = ImageIO.read(this.getClass().getResourceAsStream("playground.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics gfx){
		if(this.background != null){
			gfx.drawImage(this.background, 0, 0, this);
		}
	}

}
