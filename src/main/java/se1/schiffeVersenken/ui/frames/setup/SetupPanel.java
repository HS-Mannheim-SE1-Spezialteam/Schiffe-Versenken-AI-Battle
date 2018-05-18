package se1.schiffeVersenken.ui.frames.setup;

import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;

import se1.schiffeVersenken.interfaces.util.Direction;
import se1.schiffeVersenken.ui.elements.JShip;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetupPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public SetupPanel() {
		this.setPreferredSize(new Dimension(600, 268));
		
		JLabel lblPlacementMode = new JLabel("Placement Mode:");

		ButtonGroup gameModeSelection = new ButtonGroup();
		
		JRadioButton rbtnNoTouch = new JRadioButton("No touch");
		
		JRadioButton rbtnNoTouchCorners = new JRadioButton("No Touch Corners");
		rbtnNoTouchCorners.setSelected(true);
		
		JRadioButton rbtnTouch = new JRadioButton("Allow Touch");
		
		gameModeSelection.add(rbtnNoTouch);
		gameModeSelection.add(rbtnNoTouchCorners);
		gameModeSelection.add(rbtnTouch);
		
		JSpinner spinner = new JSpinner();
		
		JLabel lblShips = new JLabel("Ships:");
		
		JButton btnBesttigen = new JButton("Starten");
		
		JShip ship = new JShip(1, Direction.HORIZONTAL);
		
		JShip ship_1 = new JShip(2, Direction.HORIZONTAL);
		
		JSpinner spinner_1 = new JSpinner();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnBesttigen, GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(75)
									.addComponent(lblShips, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(22)
									.addComponent(lblPlacementMode)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(rbtnNoTouch)
								.addComponent(rbtnTouch)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(ship, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(ship_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
								.addComponent(rbtnNoTouchCorners))
							.addGap(280)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPlacementMode)
								.addComponent(rbtnTouch))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rbtnNoTouch)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rbtnNoTouchCorners)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(lblShips))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(ship_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(ship, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(96)
							.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
					.addComponent(btnBesttigen, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		setLayout(groupLayout);
	}
}
