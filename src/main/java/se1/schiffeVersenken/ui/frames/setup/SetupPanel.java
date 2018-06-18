package se1.schiffeVersenken.ui.frames.setup;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;

import se1.schiffeVersenken.interfaces.GameSettings.ShipBorderConditions;
import se1.schiffeVersenken.interfaces.util.Direction;
import se1.schiffeVersenken.ui.UIControll;
import se1.schiffeVersenken.ui.elements.JShip;
import javax.swing.Box;
import javax.swing.JSplitPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetupPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	protected JSpinner shipSize1 = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
	protected JSpinner shipSize2 = new JSpinner(new SpinnerNumberModel(5, 0, 10, 1));
	protected JSpinner shipSize3 = new JSpinner(new SpinnerNumberModel(3, 0, 10, 1));
	protected JSpinner shipSize4 = new JSpinner(new SpinnerNumberModel(2, 0, 10, 1));
	protected JSpinner shipSize5 = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1));
	protected JSpinner shipSize6 = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
	protected JSpinner shipSize7 = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
	protected JSpinner shipSize8 = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
	protected JSpinner shipSize9 = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
	protected JSpinner shipSize10 = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
	
	ButtonGroup gameModeSelection = new ButtonGroup();

	JRadioButton rbtnNoTouch = new JRadioButton("No touch");
	JRadioButton rbtnNoDirectTouch = new JRadioButton("No Direct Touch");
	JRadioButton rbtnTouch = new JRadioButton("Allow Touch");

	
	public SetupPanel() {
		this.setPreferredSize(new Dimension(843, 510));

		
		JButton btnLaunch = new JButton("Start");
		btnLaunch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] ships = new int[] {
					(Integer) shipSize1.getValue(),
					(Integer) shipSize2.getValue(),
					(Integer) shipSize3.getValue(),
					(Integer) shipSize4.getValue(),
					(Integer) shipSize5.getValue(),
					(Integer) shipSize6.getValue(),
					(Integer) shipSize7.getValue(),
					(Integer) shipSize8.getValue(),
					(Integer) shipSize9.getValue(),
					(Integer) shipSize10.getValue()
				};
				
				
				if(rbtnTouch.isSelected())
					UIControll.initGame(ShipBorderConditions.TOUCHING_ALLOWED, ships, false);
				else if(rbtnTouch.isSelected())
					UIControll.initGame(ShipBorderConditions.NO_DIRECT_TOUCH, ships, false);
				else if(rbtnNoTouch.isSelected())
					UIControll.initGame(ShipBorderConditions.NO_DIRECT_AND_DIAGONAL_TOUCH, ships, false);

			}
		});
		btnLaunch.setBounds(222, 455, 611, 44);
		setLayout(null);
		add(btnLaunch);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.25);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		splitPane.setBounds(10, 22, 823, 422);
		add(splitPane);
		
		JPanel placementSettings = new JPanel();
		splitPane.setLeftComponent(placementSettings);
		placementSettings.setLayout(null);
		
		rbtnNoDirectTouch.setBounds(6, 54, 192, 23);
		placementSettings.add(rbtnNoDirectTouch);
		gameModeSelection.add(rbtnNoDirectTouch);
		rbtnTouch.setSelected(true);
		
		rbtnTouch.setBounds(6, 28, 192, 23);
		placementSettings.add(rbtnTouch);
		gameModeSelection.add(rbtnTouch);
		
		rbtnNoTouch.setBounds(6, 80, 192, 23);
		placementSettings.add(rbtnNoTouch);
		gameModeSelection.add(rbtnNoTouch);
		
		JLabel lblPlacementMode = new JLabel("Placement Mode:");
		lblPlacementMode.setBounds(6, 9, 192, 14);
		placementSettings.add(lblPlacementMode);
		
		JPanel shipSettings = new JPanel();
		splitPane.setRightComponent(shipSettings);
		shipSettings.setLayout(null);
		
		JLabel lblShips = new JLabel("Ships:");
		lblShips.setBounds(10, 11, 558, 14);
		shipSettings.add(lblShips);
		
		JShip ship1 = new JShip(1, Direction.HORIZONTAL);
		ship1.setBounds(10, 36, 48, 48);
		shipSettings.add(ship1);
		
		shipSize1.setBounds(57, 36, 48, 48);
		shipSettings.add(shipSize1);
		
		JShip ship2 = new JShip(2, Direction.HORIZONTAL);
		ship2.setBounds(10, 95, 96, 48);
		shipSettings.add(ship2);
		
		shipSize2.setBounds(106, 95, 48, 48);
		shipSettings.add(shipSize2);
		
		JShip ship3 = new JShip(3, Direction.HORIZONTAL);
		ship3.setBounds(10, 158, 144, 48);
		shipSettings.add(ship3);
		
		shipSize3.setBounds(154, 158, 48, 48);
		shipSettings.add(shipSize3);
		
		JShip ship4 = new JShip(4, Direction.HORIZONTAL);
		ship4.setBounds(10, 217, 192, 48);
		shipSettings.add(ship4);
		
		shipSize4.setBounds(202, 217, 48, 48);
		shipSettings.add(shipSize4);
		
		JShip ship5 = new JShip(5, Direction.HORIZONTAL);
		ship5.setBounds(260, 217, 240, 48);
		shipSettings.add(ship5);
		
		shipSize5.setBounds(500, 217, 48, 48);
		shipSettings.add(shipSize5);
		
		JShip ship6 = new JShip(6, Direction.HORIZONTAL);
		ship6.setBounds(212, 158, 288, 48);
		shipSettings.add(ship6);
		
		shipSize6.setBounds(500, 158, 48, 48);
		shipSettings.add(shipSize6);
		
		JShip ship8 = new JShip(8, Direction.HORIZONTAL);
		ship8.setBounds(116, 36, 384, 48);
		shipSettings.add(ship8);
		
		shipSize8.setBounds(500, 36, 48, 48);
		shipSettings.add(shipSize8);
		
		JShip ship9 = new JShip(9, Direction.HORIZONTAL);
		ship9.setBounds(10, 276, 432, 48);
		shipSettings.add(ship9);
		
		shipSize9.setBounds(442, 276, 48, 48);
		shipSettings.add(shipSize9);
		
		JShip ship10 = new JShip(10, Direction.HORIZONTAL);
		ship10.setBounds(10, 335, 480, 48);
		shipSettings.add(ship10);
		
		shipSize10.setBounds(489, 335, 48, 48);
		shipSettings.add(shipSize10);
		
		JShip ship = new JShip(7, Direction.HORIZONTAL);
		ship.setBounds(164, 95, 336, 48);
		shipSettings.add(ship);
		
		shipSize7.setBounds(500, 95, 48, 48);
		shipSettings.add(shipSize7);
		
		JButton btnQickLaunch = new JButton("Quick Start");
		btnQickLaunch.setBounds(10, 455, 202, 44);
		add(btnQickLaunch);
	}
}