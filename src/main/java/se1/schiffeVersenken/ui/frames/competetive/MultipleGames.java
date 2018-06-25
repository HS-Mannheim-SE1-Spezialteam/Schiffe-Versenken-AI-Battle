package se1.schiffeVersenken.ui.frames.competetive;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.BoxLayout;

public class MultipleGames extends JFrame{
	
	public MultipleGames(SingleGame[] games) {
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		Box verticalBox = Box.createVerticalBox();
		getContentPane().add(verticalBox);
		for(SingleGame game : games) {
			System.out.println(game);
			verticalBox.add(game);
			verticalBox.add(new JSeparator());
		}
	}
}
