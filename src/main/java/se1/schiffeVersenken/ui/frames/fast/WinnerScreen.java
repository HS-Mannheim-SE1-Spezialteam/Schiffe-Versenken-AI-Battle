package se1.schiffeVersenken.ui.frames.fast;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class WinnerScreen extends JFrame{
	private JTextField playerName;
	
	public WinnerScreen(String winnerName) {
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WinnerScreen.this.dispose();
			}
		});
		getContentPane().add(btnOk, BorderLayout.SOUTH);
		
		JLabel theWinner = new JLabel("The winner is:");
		theWinner.setFont(new Font("Tahoma", Font.BOLD, 27));
		theWinner.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(theWinner, BorderLayout.NORTH);
		
		playerName = new JTextField();
		playerName.setHorizontalAlignment(SwingConstants.CENTER);
		playerName.setEditable(false);
		playerName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 28));
		getContentPane().add(playerName, BorderLayout.CENTER);
		playerName.setColumns(10);
		playerName.setText(winnerName);
		this.setSize(new Dimension(400,200));
		
		this.setLocationRelativeTo(null);
	}
}
