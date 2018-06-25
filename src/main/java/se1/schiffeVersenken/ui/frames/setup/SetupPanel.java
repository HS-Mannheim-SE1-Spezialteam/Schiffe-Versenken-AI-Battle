package se1.schiffeVersenken.ui.frames.setup;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerNumberModel;

import se1.schiffeVersenken.botBattle.PlayerInfo;
import se1.schiffeVersenken.interfaces.GameSettings.ShipBorderConditions;
import se1.schiffeVersenken.interfaces.util.Direction;
import se1.schiffeVersenken.ui.UIControll;
import se1.schiffeVersenken.ui.elements.JShip;
import se1.schiffeVersenken.ui.frames.competetive.CompetetiveController;
import se1.schiffeVersenken.ui.frames.competetive.MultipleGames;
import se1.schiffeVersenken.ui.frames.competetive.SingleGame;

import javax.swing.JComboBox;

public class SetupPanel extends JPanel{


	private PlayerInfo[] playerInformations;
	
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
	
	public JComboBox<String> cbPlayer1 = new JComboBox<String>();
	public JComboBox<String> cbPlayer2 = new JComboBox<String>();

	
	public SetupPanel(PlayerInfo[] playerInformations) {
		this.playerInformations = playerInformations;
		for(PlayerInfo info : playerInformations){
			cbPlayer1.addItem(info.name);
			cbPlayer2.addItem(info.name);
		}
		this.setPreferredSize(new Dimension(843, 510));

		JButton btnLaunch = new JButton("Start");
		btnLaunch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setup(false);
			};
		});
		
		JButton btnQickLaunch = new JButton("Quick Start");
		btnQickLaunch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setup(true);
			}
		});
		btnQickLaunch.setBounds(10, 455, 202, 44);
		add(btnQickLaunch);
		
		btnLaunch.setBounds(222, 455, 296, 44);
		setLayout(null);
		add(btnLaunch);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.25);
		splitPane.setBounds(10, 22, 823, 422);
		add(splitPane);
		
		JPanel placementSettings = new JPanel();
		splitPane.setLeftComponent(placementSettings);
		placementSettings.setLayout(null);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.5);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setBounds(0, 0, 204, 420);
		placementSettings.add(splitPane_1);
		
		JPanel panel = new JPanel();
		splitPane_1.setLeftComponent(panel);
		panel.setLayout(null);
		
		JLabel lblPlacementMode = new JLabel("Placement Mode:");
		lblPlacementMode.setBounds(7, 9, 189, 14);
		panel.add(lblPlacementMode);
		rbtnNoDirectTouch.setBounds(6, 54, 190, 23);
		panel.add(rbtnNoDirectTouch);
		gameModeSelection.add(rbtnNoDirectTouch);
		rbtnTouch.setBounds(7, 30, 189, 23);
		panel.add(rbtnTouch);
		rbtnTouch.setSelected(true);
		gameModeSelection.add(rbtnTouch);
		rbtnNoTouch.setBounds(7, 80, 189, 23);
		panel.add(rbtnNoTouch);
		gameModeSelection.add(rbtnNoTouch);
		
		JPanel panel_1 = new JPanel();
		splitPane_1.setRightComponent(panel_1);
		panel_1.setLayout(null);
		
		cbPlayer1.setBounds(10, 32, 182, 20);
		panel_1.add(cbPlayer1);
		
		JLabel lblPlayer1 = new JLabel("Player 1");
		lblPlayer1.setBounds(10, 11, 182, 14);
		panel_1.add(lblPlayer1);
		
		JLabel lblPlayer2 = new JLabel("Player 2");
		lblPlayer2.setBounds(10, 64, 182, 14);
		panel_1.add(lblPlayer2);
		
		cbPlayer2.setBounds(10, 85, 182, 20);
		panel_1.add(cbPlayer2);
		
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
		
		JButton btnCompetition = new JButton("Launch Competition");
		btnCompetition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CompetetiveController controller = new CompetetiveController(playerInformations);
				Thread trd = new Thread(() -> 
				controller.init(getBorderConditions(), gatherShips()));
				trd.start();
			}
		});
		btnCompetition.setBounds(528, 455, 305, 44);
		add(btnCompetition);

	}
	
	private int[] gatherShips() {
		return new int[] {
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
	}
	
	private ShipBorderConditions getBorderConditions() {
		return rbtnTouch.isSelected() ? ShipBorderConditions.TOUCHING_ALLOWED : rbtnNoDirectTouch.isSelected() ? ShipBorderConditions.NO_DIRECT_TOUCH : ShipBorderConditions.NO_DIRECT_AND_DIAGONAL_TOUCH;
	}
	
	private void setup(boolean fastMode){
		int[] ships = gatherShips();
			
		UIControll.initGame(getBorderConditions(), ships, playerInformations[cbPlayer1.getSelectedIndex()], playerInformations[cbPlayer2.getSelectedIndex()] , fastMode);
	}
}