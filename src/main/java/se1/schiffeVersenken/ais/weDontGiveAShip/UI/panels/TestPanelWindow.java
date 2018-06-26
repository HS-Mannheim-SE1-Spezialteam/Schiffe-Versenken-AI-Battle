package com.weDontGiveAShip.UI.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class TestPanelWindow extends JFrame {

	public TestPanelWindow() {
		super("TEST");

	}

	public static void main(String[] args) {

		TestPanelWindow tpw = new TestPanelWindow();
		tpw.setSize(1400, 700);
		tpw.setLocationRelativeTo(null);
		//MEDNUBAR 
		
		// PANELFIELDS
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new GridLayout(1, 2));
		fieldPanel.add(new FieldPanel(10, true));
		fieldPanel.add(new FieldPanel(10, false));
		tpw.add(fieldPanel, BorderLayout.CENTER);
		//

		tpw.setVisible(true);
	}
}
