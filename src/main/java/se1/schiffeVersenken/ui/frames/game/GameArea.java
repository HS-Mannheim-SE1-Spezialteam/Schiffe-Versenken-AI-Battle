package se1.schiffeVersenken.ui.frames.game;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import se1.schiffeVersenken.botBattle.Game;
import se1.schiffeVersenken.botBattle.PlayerInfo;
import se1.schiffeVersenken.botBattle.gameCallback.GameCallback;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.Tile;
import se1.schiffeVersenken.interfaces.util.Position;
import se1.schiffeVersenken.ui.elements.BombRenderer;
import se1.schiffeVersenken.ui.elements.JShip;
import se1.schiffeVersenken.ui.elements.ObjectRenderer;
import se1.schiffeVersenken.ui.elements.ShipRenderer;
import se1.schiffeVersenken.ui.frames.fast.WinnerScreen;
import javax.swing.JScrollPane;

public class GameArea extends JFrame implements GameCallback{
	
	private GamePanel gamePanel = new GamePanel();

	PlayerInfo side1;
	PlayerInfo side2;
	Thread creationThread;
	int speed;
	
	public GameArea(int speed){
		this.speed = speed;
		creationThread = Thread.currentThread();
		
		this.setTitle("Schiffe Versenken - Playground");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setContentPane(gamePanel);
		this.pack();
		getContentPane().setLayout(null);
		

		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {}

			@Override
			public void windowClosed(WindowEvent e) {

				System.out.println();
				creationThread.stop();
				
			}

			@Override
			public void windowClosing(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowOpened(WindowEvent e) {}
			
		});
	}

	@Override
	public void init(Game game) {
		side1 = game.side1.playerInfo;
		side2 = game.side2.playerInfo;
		
		Ship[] p1 = game.side1.ownWorld.getShips();
		Ship[] p2 = game.side2.ownWorld.getShips();
		
		ObjectRenderer[] ships = new ObjectRenderer[p1.length + p2.length];
		
		for(int i = 0; i < p1.length; i++) {
			Position p = p1[i].getPosition();
			ships[i] = new ShipRenderer(p.x * 48 + 48, p.y * 48 + 48, p1[i].getDirection(), p1[i].getLength());
		}
		
		for(int i = 0; i < p2.length; i++) {
			Position p = p2[i].getPosition();
			ships[p1.length + i] = new ShipRenderer(p.x * 48 + 672, p.y * 48 + 48, p2[i].getDirection(), p2[i].getLength());
		}
		
		this.gamePanel.ships = ships;
	}

	@Override
	public void onShot(int id, boolean isSide1, Position position, Tile tile, Ship ship) {
		synchronized (this.gamePanel.shots) {
			this.gamePanel.shots.add(new BombRenderer(position.x * 48 + (!isSide1 ? 48 : 672), position.y * 48 + 48, tile == Tile.SHIP || tile == Tile.SHIP_KILL));
			if(this.speed > 0){
				try {
					creationThread.sleep(this.speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.gamePanel.repaint();
		}
	}

	@Override
	public void onGameOver(boolean isSide1) {
		new WinnerScreen(isSide1 ? side1.name : side2.name).setVisible(true);	
	}
}