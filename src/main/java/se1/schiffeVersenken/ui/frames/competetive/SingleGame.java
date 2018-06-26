package se1.schiffeVersenken.ui.frames.competetive;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.*;

import se1.schiffeVersenken.botBattle.PlayerInfo;

public class SingleGame extends JPanel{
	
	JLabel lblPoints = null;
	JProgressBar progressBar = new JProgressBar();
	
	int pointsP1 = 0;
	int pointsP2 = 0;
	
	private boolean isSwitched = false;
	private int totalGames = 0;
	
	private PlayerInfo p1;
	private PlayerInfo p2;
	
	JLabel lblPlayer1;
	JLabel lblPlayer2;

	
	public SingleGame(PlayerInfo p1, PlayerInfo p2, int pointsP1, int pointsP2, int totalGames) {
		this.totalGames = totalGames;
		
		this.p1 = p1;
		this.p2 = p2;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		add(horizontalBox_1);
		
		lblPlayer1 = new JLabel(p1.name);
		lblPlayer2 = new JLabel(p2.name);
		
		Box verticalBox = Box.createVerticalBox();
		horizontalBox_1.add(verticalBox);
		
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);
		
		lblPlayer1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPlayer1.setHorizontalAlignment(SwingConstants.LEFT);
		horizontalBox.add(lblPlayer1);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);
		
		lblPoints = new JLabel(String.format("%2d | %2d", pointsP1, pointsP2));
		lblPoints.setFont(new Font("Tahoma", Font.BOLD, 16));
		horizontalBox.add(lblPoints);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue_1);
		
		lblPlayer2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlayer2.setFont(new Font("Tahoma", Font.BOLD, 16));
		horizontalBox.add(lblPlayer2);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_1);
		
		progressBar.setMaximum(totalGames);
		verticalBox.add(progressBar);
	}
	
	public boolean pointP1() {
		if (isSwitched)
			this.pointsP2++;
		else
			this.pointsP1++;
		lblPoints.setText(String.format("%2d | %2d", pointsP1, pointsP2));
		progressBar.setValue(progressBar.getValue() + 1);
		
		return endGame();
	}
	
	public boolean pointP2() {
		if (isSwitched)
			this.pointsP1++;
		else
			this.pointsP2++;
		lblPoints.setText(String.format("%2d | %2d", pointsP1, pointsP2));
		progressBar.setValue(progressBar.getValue() + 1);

		return endGame();
	}
	
	public boolean endGame() {
		if (pointsP1 + pointsP2 == totalGames / 2)
			isSwitched = true;
		boolean gameEnd = progressBar.getValue() == progressBar.getMaximum();
		if(gameEnd) {
			if(pointsP1 > pointsP2) {
				lblPlayer1.setForeground(Color.GREEN.darker());
				lblPlayer2.setForeground(Color.RED);
			}else if(pointsP2 > pointsP1) {
				lblPlayer2.setForeground(Color.GREEN.darker());
				lblPlayer1.setForeground(Color.RED);
			}else {
				lblPlayer1.setForeground(Color.BLUE.darker());
				lblPlayer2.setForeground(Color.BLUE.darker());
			}
		}
		return gameEnd;
	}

	public PlayerInfo getP1() {
		return isSwitched ? p2 : p1;
	}

	public PlayerInfo getP2() {
		return isSwitched ? p1 : p2;
	}
	
	
}
