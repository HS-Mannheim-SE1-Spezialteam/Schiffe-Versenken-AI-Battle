package se1.schiffeVersenken.ui.frames.fast;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class WinnerPane extends JPanel{
	private JTextField textField;
	public WinnerPane() {
		setLayout(new BorderLayout(0, 0));
		
		textField = new JTextField();
		add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
	}

}
