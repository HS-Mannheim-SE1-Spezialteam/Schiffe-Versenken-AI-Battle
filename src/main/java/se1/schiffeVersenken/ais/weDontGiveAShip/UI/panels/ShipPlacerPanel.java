package com.weDontGiveAShip.UI.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.weDontGiveAShip.main.Main;

import se1.schiffeVersenken.interfaces.GameSettings.ShipBorderConditions;
import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.util.Direction;
import se1.schiffeVersenken.interfaces.util.Position;

public class ShipPlacerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int[] shipAmount;
	private ArrayList<Ship> ships;

	private ArrayList<Integer> previousShipLength;
	private ArrayList<Integer> previousShipAmount;

	private int currentShipLength;
	private int currentShipAmount;
	private Direction currentShipDirection = Direction.HORIZONTAL;

	private FieldPanel fieldPanel;
	private boolean everyShipIsPlaced;

	JButton commitButton;
	JButton switchDirectionButton;
	JLabel shipLengthLabel;
	JLabel shipAmountLabel;
	JButton undoButton;

	public ShipPlacerPanel() {

		setLayout(new BorderLayout());

		// currentShipPanel
		JPanel currentShipPanel = new JPanel();
		currentShipPanel.setLayout(new FlowLayout());

		shipLengthLabel = new JLabel("XXX");
		shipAmountLabel = new JLabel("XXX");

		switchDirectionButton = new JButton(currentShipDirection.toString());
		switchDirectionButton.setSize(30, 30);
		switchDirectionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentShipDirection == Direction.HORIZONTAL)
					currentShipDirection = Direction.VERTICAL;

				else
					currentShipDirection = Direction.HORIZONTAL;

				switchDirectionButton.setText(currentShipDirection.toString());
			}

		});

		commitButton = new JButton("Commit");
		commitButton.setSize(30, 30);
		commitButton.setEnabled(false);
		
		commitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Ship[] s = new Ship[ships.size()];
				ships.toArray(s);
				
				Main.gui.openMatchPanel(s);
			}

		});

		undoButton = new JButton("Undo");
		undoButton.setSize(30, 30);
		undoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ships.size() > 0) {
					undo();
					shipLengthLabel.setText("LENGTH: " + currentShipLength);
					shipAmountLabel.setText("AMOUNT: " + currentShipAmount);
				}
			}

		});

		currentShipPanel.add(undoButton);
		currentShipPanel.add(switchDirectionButton);
		currentShipPanel.add(shipLengthLabel);
		currentShipPanel.add(shipAmountLabel);
		currentShipPanel.add(commitButton);
		add(currentShipPanel, BorderLayout.NORTH);

		shipAmount = Main.getSettings().getNumberOfShips();
		currentShipLength = 0;
		currentShipAmount = shipAmount[0];

		nextShip();

		shipLengthLabel.setText("LENGTH: " + currentShipLength);
		shipAmountLabel.setText("AMOUNT: " + currentShipAmount);

		// create ships
		ships = new ArrayList<>();
		previousShipAmount = new ArrayList<>();
		previousShipLength = new ArrayList<>();

		add(fieldPanel = new FieldPanel(10, true) {
			private static final long serialVersionUID = 1L;

			@Override
			public void setColor(int x, int y, Color color) {
				super.setColor(x, y, color);
			}

			@Override
			public void onClick(int x, int y) {
				if (!everyShipIsPlaced) {

					boolean occupied = false;
					ShipBorderConditions touchingConditions = Main.getSettings().getShipBorderConditions();
					for (Ship s : ships) {

						for (Position p : s.getOccupiedSpaces()) {

							for (int i = 0; i < currentShipLength; i++) {

								Position clickedPosition = new Position(x, y);
								Position shipPosition = clickedPosition.add(currentShipDirection.positive.multiply(i));

								if (p.equals(shipPosition)) {
									occupied = true;
								}

							}

						}
						for (Position p : s.getEmptySpacesSurrounding(touchingConditions)) {

							for (int i = 0; i < currentShipLength; i++) {

								Position clickedPosition = new Position(x, y);
								Position shipPosition = clickedPosition.add(currentShipDirection.positive.multiply(i));

								if (p.equals(shipPosition)) {
									occupied = true;
								}

							}

						}
					}

					boolean outOfBounds = false;
					for (int i = 0; i < currentShipLength; i++) {

						Position clickedPosition = new Position(x, y);
						Position p = clickedPosition.add(currentShipDirection.positive.multiply(i));

						if (p.x >= 10 || p.x < 0 || p.y >= 10 || p.y < 0)
							outOfBounds = true;
					}

					if (!occupied && !outOfBounds) {
						for (int i = 0; i < currentShipLength; i++) {
							Position clicked = new Position(x, y);
							Position p = clicked.add(currentShipDirection.positive.multiply(i));
							setColor(p.x, p.y, Main.SHIP_COLOR);

						}
						super.onClick(x, y);
						
						ships.add(new Ship(new Position(x, y), currentShipDirection, currentShipLength));
						previousShipAmount.add(currentShipAmount);
						previousShipLength.add(currentShipLength);

						if (!nextShip()) {
							commitButton.setEnabled(true);
							everyShipIsPlaced = true;
							shipLengthLabel.setText("LENGTH: ~");
							shipAmountLabel.setText("AMOUNT: ~");
							
						} else {
							shipLengthLabel.setText("LENGTH: " + currentShipLength);
							shipAmountLabel.setText("AMOUNT: " + currentShipAmount);
						}
					}
				}
			}

		}, BorderLayout.CENTER);

	}

	private boolean nextShip() {
		currentShipAmount--;
		if (currentShipAmount <= 0) {

			currentShipLength++;

			if (currentShipLength - 1 < shipAmount.length) {
				currentShipAmount = shipAmount[currentShipLength - 1];
				if (currentShipAmount == 0) {
					if (!nextShip())
						return false;
				}
				return true;
			} else
				return false;
		}
		return true;
	}

	private void undo() {
		currentShipAmount = previousShipAmount.remove(previousShipAmount.size() - 1);
		currentShipLength = previousShipLength.remove(previousShipLength.size() - 1);
		Ship s = ships.remove(ships.size() - 1);
		for (Position p : s.getOccupiedSpaces()) {
			fieldPanel.setColor(p.x, p.y, Main.WATER_COLOR);
		}

		everyShipIsPlaced = false;
		commitButton.setEnabled(false);

	}
	
	public Ship[] getShips(){
		return ships.toArray(new Ship[ships.size()]);
	}

}
