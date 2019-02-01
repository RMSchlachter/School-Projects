import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.Color;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class riskGUI {
	
	

	private JFrame frame;
	private JTextField txtName;
	private JTextField numToBattle;
	private JTextField numMove;
	private Game risk = new Game();
    private Board board = new Board();
    private Territory selectedTerr;
    private JTextField troopstoPlace;
    private Deck deck = new Deck();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					riskGUI window = new riskGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public riskGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		JLabel VDay = new JLabel("New label");
		VDay.setVisible(false);
		JLabel lblMyTerr = new JLabel("my terr");

		java.net.URL MapUrl = getClass().getResource("/Map.jpg");
		ImageIcon map = new ImageIcon(MapUrl);
		
		JLayeredPane userboxCards = new JLayeredPane();
		JLayeredPane userboxAddPlayer = new JLayeredPane();
		userboxAddPlayer.setBackground(Color.BLUE);
		userboxAddPlayer.setBounds(7, 450, 320, 291);
		frame.getContentPane().add(userboxAddPlayer);
		JLayeredPane userboxSetup = new JLayeredPane();
		

		java.net.URL UserBoxUrl = getClass().getResource("/UserBox.jpg");
		
		JLabel lblAddPlayer = new JLabel("Add Player:");
		lblAddPlayer.setFont(new Font("American Typewriter", Font.BOLD, 14));
		lblAddPlayer.setBounds(38, 84, 89, 16);
		userboxAddPlayer.add(lblAddPlayer);
		
		txtName = new JTextField();
		txtName.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		txtName.setText("name");
		txtName.setBounds(144, 79, 130, 26);
		userboxAddPlayer.add(txtName);
		txtName.setColumns(10);
		
		JLabel infoBoxAddPlayer = new JLabel("Add a New Player!");
		
		JLabel lblPlayerName = new JLabel("Player Name");
		JLabel troopsLeft = new JLabel("20");
		
		JButton btnNewButton = new JButton("Continue");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(risk.getPlayers().size() > 2){
					userboxAddPlayer.setVisible(false);
					lblPlayerName.setText(risk.checkWhosTurn().getName());
					lblPlayerName.setForeground(risk.checkWhosTurn().getColor());
					risk.setInitialTroopCount();
					troopsLeft.setText(Integer.toString(risk.checkWhosTurn().troopsToPlace));
				}
				else {
					infoBoxAddPlayer.setText("<html>You have to play with <br/>at least than 3 people!</html>");
				}
			}
		});
		
		JLabel lblThisIsThe = new JLabel("Mitch");
		JLabel lblPlayers = new JLabel("Players:");
		lblPlayers.setBounds(33, 106, 247, 123);
		userboxAddPlayer.add(lblPlayers);
		
		
		JButton btnAddPlayer = new JButton("Add Player");
		btnAddPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					risk.addPlayer(txtName.getText());
					String playerList = "<html>";
					for(int i=0; i<risk.getPlayers().size(); i++) {
						playerList = playerList + risk.getPlayers().get(i).toString() + "<br/>";
					}
					playerList = playerList + "</html>";
					lblPlayers.setText(playerList);
				} catch (Exception e1) {
					
//					e1.printStackTrace();
					infoBoxAddPlayer.setText("<html>You can't play with <br/>more than 6 people!</html>");
				}
			}
		});
		btnAddPlayer.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		btnAddPlayer.setBounds(33, 245, 117, 29);
		userboxAddPlayer.add(btnAddPlayer);
		btnNewButton.setBounds(183, 244, 117, 29);
		userboxAddPlayer.add(btnNewButton);
		
		
		infoBoxAddPlayer.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		infoBoxAddPlayer.setBounds(173, 106, 127, 72);
		userboxAddPlayer.add(infoBoxAddPlayer);
		
		JButton btnTest = new JButton("test");
		
		btnTest.setBounds(183, 221, 117, 29);
		userboxAddPlayer.add(btnTest);
		
		JLabel userbox1 = new JLabel(new ImageIcon(UserBoxUrl));
		userbox1.setBounds(0, 0, 320, 291);
		userboxAddPlayer.add(userbox1);
		
		
		userboxSetup.setBounds(7, 450, 320, 291);
		frame.getContentPane().add(userboxSetup);
		userboxSetup.setBackground(Color.BLUE);
		
		
		lblPlayerName.setFont(new Font("American Typewriter", Font.BOLD, 20));
		lblPlayerName.setBounds(53, 80, 104, 29);
		userboxSetup.add(lblPlayerName);
		
		JLabel infobox1 = new JLabel("<html>Select an Unclaimed <br/>\nTerritory to Place <br/>\nInitial Troop</html>");
		infobox1.setBounds(150, 55, 159, 86);
		userboxSetup.add(infobox1);
		infobox1.setHorizontalAlignment(SwingConstants.LEFT);
		infobox1.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		
		JLabel lblremainingtroops = new JLabel("<html>Remaining<br/>Troops:</html>");
		lblremainingtroops.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblremainingtroops.setBounds(40, 155, 120, 52);
		userboxSetup.add(lblremainingtroops);
		
		JLabel lblSelectedTerritory = new JLabel("Selected Territory:");
		lblSelectedTerritory.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblSelectedTerritory.setBounds(150, 140, 120, 16);
		userboxSetup.add(lblSelectedTerritory);
		
		JLabel displayTerr = new JLabel("Select Terr");
		displayTerr.setForeground(new Color(0, 0, 0));
		displayTerr.setFont(new Font("American Typewriter", Font.BOLD, 15));
		displayTerr.setBounds(150, 160, 150, 16);
		userboxSetup.add(displayTerr);
		
		JButton btnPlaceTroop = new JButton("Place Troop");
	
		btnPlaceTroop.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		btnPlaceTroop.setBounds(183, 244, 117, 29);
		userboxSetup.add(btnPlaceTroop);
		
		
		troopsLeft.setForeground(new Color(220, 20, 60));
		troopsLeft.setHorizontalAlignment(SwingConstants.CENTER);
		troopsLeft.setFont(new Font("American Typewriter", Font.BOLD, 18));
		troopsLeft.setBounds(45, 200, 48, 36);
		userboxSetup.add(troopsLeft);
		
		JLabel userbox2 = new JLabel(new ImageIcon(UserBoxUrl));
		userbox2.setBounds(0, 0, 320, 291);
		userboxSetup.add(userbox2);
		
		JLayeredPane userboxReinforce = new JLayeredPane();
		userboxReinforce.setBounds(7, 450, 320, 291);
		frame.getContentPane().add(userboxReinforce);
		userboxReinforce.setBackground(Color.BLUE);
		
		lblThisIsThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblThisIsThe.setFont(new Font("American Typewriter", Font.BOLD, 20));
		lblThisIsThe.setBounds(38, 84, 117, 41);
		userboxReinforce.add(lblThisIsThe);
		
		JLabel label_5 = new JLabel("20");
		JLabel playerName = new JLabel("Player Name");
		JLayeredPane userboxBattle = new JLayeredPane();
		JLayeredPane userboxMove = new JLayeredPane();
		JLayeredPane userboxCombat = new JLayeredPane();
		
		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerName.setText(risk.checkWhosTurn().getName());
				playerName.setForeground(risk.checkWhosTurn().getColor());
				label_5.setText(Integer.toString(risk.checkWhosTurn().getTotalTroops()));
				userboxAddPlayer.setVisible(false);
				userboxSetup.setVisible(false);
				userboxReinforce.setVisible(false);
				userboxMove.setVisible(false);
				userboxBattle.setVisible(false);
				userboxCombat.setVisible(true);
				userboxCards.setVisible(false);
			}
		});
		btnDone.setBounds(223, 244, 77, 29);
		userboxReinforce.add(btnDone);
		
		JLabel playernamecard = new JLabel("Mitch");
		JButton gotocardsbtn = new JButton("Risk Cards");
		
		gotocardsbtn.setBounds(27, 244, 100, 29);
		userboxReinforce.add(gotocardsbtn);
		
		troopstoPlace = new JTextField();
		JLabel lblSelectAControlled = new JLabel("<html>Select a controlled<br/> territory to reinforce</html>");
		
		JButton placepiecebtn = new JButton("Set Troops");
	
		placepiecebtn.setBounds(123, 244, 100, 29);
		userboxReinforce.add(placepiecebtn);
		
		
		lblSelectAControlled.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblSelectAControlled.setBounds(145, 86, 136, 72);
		userboxReinforce.add(lblSelectAControlled);
		
		JLabel lblToPlace = new JLabel("# to place");
		lblToPlace.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblToPlace.setBounds(51, 181, 67, 16);
		userboxReinforce.add(lblToPlace);
		
		
		troopstoPlace.setBounds(59, 198, 36, 26);
		userboxReinforce.add(troopstoPlace);
		troopstoPlace.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("<html>Available<br/>Reinforcements</html>");
		lblNewLabel.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblNewLabel.setBounds(39, 113, 100, 42);
		userboxReinforce.add(lblNewLabel);
		
		JLabel lblSelectedTerritory_1 = new JLabel("Selected Territory");
		lblSelectedTerritory_1.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblSelectedTerritory_1.setBounds(139, 181, 127, 16);
		userboxReinforce.add(lblSelectedTerritory_1);
		
		JLabel reinforcenum = new JLabel("4");
		reinforcenum.setForeground(Color.RED);
		reinforcenum.setHorizontalAlignment(SwingConstants.CENTER);
		reinforcenum.setFont(new Font("American Typewriter", Font.PLAIN, 18));
		reinforcenum.setBounds(58, 151, 41, 29);
		userboxReinforce.add(reinforcenum);
		
		
		lblMyTerr.setHorizontalAlignment(SwingConstants.CENTER);
		lblMyTerr.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblMyTerr.setBounds(134, 203, 117, 16);
		userboxReinforce.add(lblMyTerr);
		
		JLabel userbox3 = new JLabel(new ImageIcon(UserBoxUrl));
		userbox3.setBounds(0, 0, 320, 291);
		userboxReinforce.add(userbox3);
		
		
		userboxCards.setBackground(Color.BLUE);
		userboxCards.setBounds(7, 450, 320, 291);
		frame.getContentPane().add(userboxCards);
		
		
		playernamecard.setHorizontalAlignment(SwingConstants.CENTER);
		playernamecard.setFont(new Font("American Typewriter", Font.BOLD, 20));
		playernamecard.setBounds(38, 84, 117, 41);
		userboxCards.add(playernamecard);
		
		JButton btnContinue_1 = new JButton("Continue");
		btnContinue_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reinforcenum.setText(Integer.toString(risk.checkWhosTurn().getReinforcements()));
				userboxAddPlayer.setVisible(false);
				userboxSetup.setVisible(false);
				userboxReinforce.setVisible(true);
				userboxMove.setVisible(false);
				userboxBattle.setVisible(false);
				userboxCombat.setVisible(false);
				userboxCards.setVisible(false);
			}
		});
		btnContinue_1.setBounds(189, 244, 92, 29);
		userboxCards.add(btnContinue_1);
		
		JButton btnBack = new JButton("Cancel");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userboxAddPlayer.setVisible(false);
				userboxSetup.setVisible(false);
				userboxReinforce.setVisible(true);
				userboxMove.setVisible(false);
				userboxBattle.setVisible(false);
				userboxCombat.setVisible(false);
				userboxCards.setVisible(false);
			}
		});
		btnBack.setBounds(27, 244, 68, 29);
		userboxCards.add(btnBack);
		
		JButton btnTurnInSet = new JButton("Turn in Set");
		
		btnTurnInSet.setBounds(92, 244, 100, 29);
		userboxCards.add(btnTurnInSet);
		
		JLabel lblselectASet = new JLabel("<html>Select a set of <br/>risk cards to turn in <br/>for extra reinforcements<html/>");
		lblselectASet.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblselectASet.setBounds(145, 86, 136, 72);
		userboxCards.add(lblselectASet);
		
		JLabel lblSet = new JLabel("Set # ");
		lblSet.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblSet.setBounds(48, 113, 36, 42);
		userboxCards.add(lblSet);
		
		JLabel label_11 = new JLabel("4");
		label_11.setHorizontalAlignment(SwingConstants.CENTER);
		label_11.setForeground(Color.RED);
		label_11.setFont(new Font("American Typewriter", Font.PLAIN, 18));
		label_11.setBounds(77, 118, 41, 29);
		userboxCards.add(label_11);
		
		JRadioButton cardbtn1 = new JRadioButton("");
		
		cardbtn1.setBounds(23, 221, 28, 23);
		userboxCards.add(cardbtn1);
		
		JRadioButton cardbtn2 = new JRadioButton("");
		cardbtn2.setBounds(64, 221, 28, 23);
		userboxCards.add(cardbtn2);
		
		JRadioButton cardbtn3 = new JRadioButton("");
		cardbtn3.setBounds(113, 221, 28, 23);
		userboxCards.add(cardbtn3);
		
		JRadioButton cardbtn4 = new JRadioButton("");
		cardbtn4.setBounds(158, 221, 28, 23);
		userboxCards.add(cardbtn4);
		
		JRadioButton cardbtn5 = new JRadioButton("");
		cardbtn5.setBounds(203, 221, 28, 23);
		userboxCards.add(cardbtn5);
		
		JRadioButton cardbtn6 = new JRadioButton("");
		cardbtn6.setBounds(248, 221, 28, 23);
		userboxCards.add(cardbtn6);
		
		
		
		JLabel card1 = new JLabel("");
		card1.setBounds(15, 155, 45, 63);
		userboxCards.add(card1);
		
		JLabel card2 = new JLabel("");
		card2.setBounds(60, 155, 45, 63);
		userboxCards.add(card2);
		
		JLabel card3 = new JLabel("");
		card3.setBounds(105, 155, 45, 63);
		userboxCards.add(card3);
		
		JLabel card4 = new JLabel("");
		card4.setBounds(150, 155, 45, 63);
		userboxCards.add(card4);
		
		JLabel card5 = new JLabel("");
		card5.setBounds(195, 155, 45, 63);
		userboxCards.add(card5);
		
		JLabel card6 = new JLabel("");
		card6.setBounds(240, 155, 45, 63);
		userboxCards.add(card6);
		
		JLabel label_13 = new JLabel(new ImageIcon(UserBoxUrl));
		label_13.setBounds(0, 0, 320, 291);
		userboxCards.add(label_13);
		
		gotocardsbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label_11.setText(Integer.toString(risk.checkWhosTurn().totalSets));
				playernamecard.setText(risk.checkWhosTurn().getName());
				playernamecard.setForeground(risk.checkWhosTurn().getColor());
				label_5.setText(Integer.toString(risk.checkWhosTurn().getTotalTroops()));
				userboxAddPlayer.setVisible(false);
				userboxSetup.setVisible(false);
				userboxReinforce.setVisible(false);
				userboxMove.setVisible(false);
				userboxBattle.setVisible(false);
				userboxCombat.setVisible(false);
				userboxCards.setVisible(true);
				
				JLabel[] myhand = {card1, card2, card3, card4, card5, card6};
				JRadioButton[] mybuttons = {cardbtn1, cardbtn2, cardbtn3, cardbtn4, cardbtn5, cardbtn6};
				
				for(int i=0; i < 6; i++) {
					if(i<risk.checkWhosTurn().cards.size()) {
						myhand[i].setIcon(risk.checkWhosTurn().cards.get(i).getCardIcon());//lay down and display the players cards
						myhand[i].setVisible(true);
						mybuttons[i].setVisible(true);
					}
					else {
						myhand[i].setVisible(false);//hides null spaces
						mybuttons[i].setVisible(false);
					}
				}
				
				
			}
		});
		
		userboxCombat.setBackground(Color.BLUE);
		userboxCombat.setBounds(7, 450, 320, 291);
		frame.getContentPane().add(userboxCombat);
		
		
		playerName.setFont(new Font("American Typewriter", Font.BOLD, 16));
		playerName.setBounds(22, 77, 111, 23);
		userboxCombat.add(playerName);
		
		JLabel combatInfoBox = new JLabel("<html>Select a controlled <br/>\nTerritory to <br/>\nattack with</html>");
		combatInfoBox.setHorizontalAlignment(SwingConstants.LEFT);
		combatInfoBox.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		combatInfoBox.setBounds(150, 55, 159, 86);
		userboxCombat.add(combatInfoBox);
		
		JLabel lblTotalTroops = new JLabel("Total Troops:");
		lblTotalTroops.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblTotalTroops.setBounds(30, 101, 85, 29);
		userboxCombat.add(lblTotalTroops);
		
		JLabel lblVs = new JLabel("VS");
		lblVs.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblVs.setBounds(155, 177, 24, 16);
		userboxCombat.add(lblVs);
		

		
		JComboBox comboBox = new JComboBox();
		JLabel label_2 = new JLabel("Player Name");
		JLabel themBattle = new JLabel("Them(2)");
		
		JLabel attackTerrDisplay = new JLabel("Central America (5)");
		attackTerrDisplay.setForeground(new Color(220, 20, 60));
		attackTerrDisplay.setFont(new Font("American Typewriter", Font.BOLD, 12));
		attackTerrDisplay.setBounds(22, 174, 131, 23);
		userboxCombat.add(attackTerrDisplay);
		JButton btnAttack = new JButton("Attack");
		btnAttack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedTerr.attackable(risk.getTerritory(board, comboBox.getSelectedItem().toString()))) {
					themBattle.setText(risk.getTerritory(board, comboBox.getSelectedItem().toString()).getName() + " (" + risk.getTerritory(board, comboBox.getSelectedItem().toString()).armySize() + ")");
					label_2.setText(risk.checkWhosTurn().getName());
					label_2.setForeground(risk.checkWhosTurn().getColor());
					userboxAddPlayer.setVisible(false);
					userboxSetup.setVisible(false);
					userboxReinforce.setVisible(false);
					userboxCombat.setVisible(false);
					userboxBattle.setVisible(true);
					userboxMove.setVisible(false);
				}else {
					combatInfoBox.setText("<html>This Territory is not<br/>attackable! Select a new<br/>territory to attack</html>");
				}
				
			}
		});
		btnAttack.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		btnAttack.setBounds(183, 244, 117, 29);
		userboxCombat.add(btnAttack);
		
		JLabel playerNameMove = new JLabel("Player Name");
		JLabel label_10 = new JLabel("20");
		
		JButton btnSkipPhase = new JButton("Done w/ Phase");
		btnSkipPhase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerNameMove.setText(risk.checkWhosTurn().getName());
				playerNameMove.setForeground(risk.checkWhosTurn().getColor());
				label_10.setText(Integer.toString(risk.checkWhosTurn().getTotalTroops()));
				userboxAddPlayer.setVisible(false);
				userboxSetup.setVisible(false);
				userboxReinforce.setVisible(false);
				userboxCombat.setVisible(false);
				userboxBattle.setVisible(false);
				userboxMove.setVisible(true);
			}
		});
		btnSkipPhase.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		btnSkipPhase.setBounds(30, 244, 117, 29);
		userboxCombat.add(btnSkipPhase);
		
		numToBattle = new JTextField();
		numToBattle.setText("3");
		numToBattle.setBounds(90, 134, 34, 29);
		userboxCombat.add(numToBattle);
		numToBattle.setColumns(10);
		

		
		JLabel lblattacking = new JLabel("<html>Attacking # <br/>of Troops</html>");
		lblattacking.setFont(new Font("American Typewriter", Font.PLAIN, 10));
		lblattacking.setBounds(32, 129, 61, 41);
		userboxCombat.add(lblattacking);
		
		JLabel lblThem = new JLabel("Them");
		lblThem.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblThem.setBounds(214, 153, 61, 16);
		userboxCombat.add(lblThem);
		
		
		comboBox.setFont(new Font("American Typewriter", Font.PLAIN, 12));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Colorado (4)", "Boston (2)", "Denver (1)"}));
		comboBox.setBounds(180, 165, 129, 41);
		userboxCombat.add(comboBox);
		
		
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setForeground(new Color(220, 20, 60));
		label_5.setFont(new Font("American Typewriter", Font.BOLD, 13));
		label_5.setBounds(105, 97, 48, 36);
		userboxCombat.add(label_5);
		
		JLabel userbox4 = new JLabel(new ImageIcon(UserBoxUrl));
		userbox4.setBounds(0, 0, 320, 291);
		userboxCombat.add(userbox4);
		
		
		userboxBattle.setBackground(Color.BLUE);
		userboxBattle.setBounds(7, 450, 320, 291);
		frame.getContentPane().add(userboxBattle);
		
		
		label_2.setFont(new Font("American Typewriter", Font.BOLD, 16));
		label_2.setBounds(22, 77, 111, 23);
		userboxBattle.add(label_2);
		
		JLabel lblBattleInfoBox = new JLabel("<html>Roll Die to <br/>\nstart the battle</html>");
		lblBattleInfoBox.setHorizontalAlignment(SwingConstants.LEFT);
		lblBattleInfoBox.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblBattleInfoBox.setBounds(150, 55, 159, 86);
		userboxBattle.add(lblBattleInfoBox);
		
		JLabel label_9 = new JLabel("VS");
		label_9.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		label_9.setBounds(151, 136, 24, 16);
		userboxBattle.add(label_9);
		
		JLabel battleTerr = new JLabel("Central America (5)");
		battleTerr.setForeground(new Color(220, 20, 60));
		battleTerr.setFont(new Font("American Typewriter", Font.BOLD, 12));
		battleTerr.setBounds(22, 132, 131, 23);
		userboxBattle.add(battleTerr);
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userboxAddPlayer.setVisible(false);
				userboxSetup.setVisible(false);
				userboxReinforce.setVisible(false);
				userboxCombat.setVisible(true);
			}
		});
		btnContinue.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		btnContinue.setBounds(183, 244, 117, 29);
		userboxBattle.add(btnContinue);
		
		JButton btnRollDie = new JButton("Roll Die");
		
		
		btnRollDie.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		btnRollDie.setBounds(30, 244, 117, 29);
		userboxBattle.add(btnRollDie);
		
		
		themBattle.setForeground(new Color(107, 142, 35));
		themBattle.setFont(new Font("American Typewriter", Font.BOLD, 13));
		themBattle.setBounds(178, 135, 111, 16);
		userboxBattle.add(themBattle);
		
		JLabel attackDie3 = new JLabel("");
		attackDie3.setBounds(6, 197, 40, 40);
		userboxBattle.add(attackDie3);
		
		JLabel attackDie2 = new JLabel("");
		attackDie2.setBounds(82, 157, 40, 40);
		userboxBattle.add(attackDie2);
		
		JLabel attackDie1 = new JLabel("");
		attackDie1.setBounds(89, 197, 40, 40);
		userboxBattle.add(attackDie1);
		
		JLabel defenseDie2 = new JLabel("");
		defenseDie2.setBounds(183, 158, 40, 40);
		userboxBattle.add(defenseDie2);
		
		JLabel defenseDie1 = new JLabel("");
		defenseDie1.setBounds(179, 203, 40, 40);
		userboxBattle.add(defenseDie1);
		
		JLabel label_15 = new JLabel(new ImageIcon(UserBoxUrl));
		label_15.setBounds(0, 0, 320, 291);
		userboxBattle.add(label_15);
		
		
		userboxMove.setBackground(Color.BLUE);
		userboxMove.setBounds(7, 450, 320, 291);
		frame.getContentPane().add(userboxMove);
		
		
		playerNameMove.setFont(new Font("American Typewriter", Font.BOLD, 16));
		playerNameMove.setBounds(22, 77, 111, 23);
		userboxMove.add(playerNameMove);
		
		JLabel infobox = new JLabel("<html>Select two controlled <br/>\nTerritories to <br/>\nmove troops between</html>");
		infobox.setHorizontalAlignment(SwingConstants.LEFT);
		infobox.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		infobox.setBounds(150, 55, 159, 86);
		userboxMove.add(infobox);
		
		JLabel label_3 = new JLabel("Total Troops:");
		label_3.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		label_3.setBounds(30, 101, 85, 29);
		userboxMove.add(label_3);
		
		JLabel lblto = new JLabel(">>>");
		lblto.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lblto.setBounds(155, 177, 24, 16);
		userboxMove.add(lblto);
		
		JLabel moveTerrDisplay = new JLabel("Central America (5)");
		moveTerrDisplay.setForeground(new Color(220, 20, 60));
		moveTerrDisplay.setFont(new Font("American Typewriter", Font.BOLD, 12));
		moveTerrDisplay.setBounds(22, 174, 131, 23);
		userboxMove.add(moveTerrDisplay);
		
		JButton buttonMove = new JButton("Move");

		buttonMove.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		buttonMove.setBounds(183, 244, 117, 29);
		userboxMove.add(buttonMove);
		
		JButton buttonDOne = new JButton("Done");
		buttonDOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(risk.winCheck()) {
					userboxAddPlayer.setVisible(false);
					userboxSetup.setVisible(false);
					userboxReinforce.setVisible(false);
					userboxCards.setVisible(false);
					userboxCombat.setVisible(false);
					userboxBattle.setVisible(false);
					userboxMove.setVisible(false);
					VDay.setVisible(true);
					VDay.setText(risk.checkWhosTurn().getName() + " WINS!");
				}else {
					for(int i=0; i<risk.getPlayers().size(); i++) {
						if(risk.getPlayers().get(i).totalTerritories == 0) {
							risk.removePlayer(risk.getPlayers().get(i).getName());
						}
					}
					risk.nextTurn();
					risk.checkWhosTurn().setReinforcements(board);
					reinforcenum.setText(Integer.toString(risk.checkWhosTurn().getReinforcements()));
					userboxAddPlayer.setVisible(false);
					userboxSetup.setVisible(false);
					userboxReinforce.setVisible(true);
					userboxCombat.setVisible(false);
					userboxBattle.setVisible(false);
					userboxMove.setVisible(true);
					lblThisIsThe.setText(risk.checkWhosTurn().getName());
					lblThisIsThe.setForeground(risk.checkWhosTurn().getColor());
				}
			}
		});
		buttonDOne.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		buttonDOne.setBounds(30, 244, 117, 29);
		userboxMove.add(buttonDOne);
		
		numMove = new JTextField();
		numMove.setText("3");
		numMove.setColumns(10);
		numMove.setBounds(90, 134, 34, 29);
		userboxMove.add(numMove);
		
		JLabel lblmovingof = new JLabel("<html>Moving # <br/>of Troops</html>");
		lblmovingof.setFont(new Font("American Typewriter", Font.PLAIN, 10));
		lblmovingof.setBounds(32, 129, 61, 41);
		userboxMove.add(lblmovingof);
		
		JLabel lbldestination = new JLabel("destination");
		lbldestination.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		lbldestination.setBounds(199, 153, 76, 16);
		userboxMove.add(lbldestination);
		
		JComboBox comboBoxMove = new JComboBox();
		comboBoxMove.setFont(new Font("American Typewriter", Font.PLAIN, 12));
		comboBoxMove.setBounds(180, 165, 129, 41);
		userboxMove.add(comboBoxMove);
		
		
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setForeground(new Color(220, 20, 60));
		label_10.setFont(new Font("American Typewriter", Font.BOLD, 13));
		label_10.setBounds(105, 97, 48, 36);
		userboxMove.add(label_10);
		
		JLabel userbox5 = new JLabel(new ImageIcon(UserBoxUrl));
		userbox5.setBounds(0, 0, 320, 291);
		userboxMove.add(userbox5);
		
		JLayeredPane mapButtons = new JLayeredPane();
		mapButtons.setBounds(0, 0, 1200, 800);
		frame.getContentPane().add(mapButtons);
		
		JButton alaska = new JButton("0");
		alaska.setBackground(new Color(255, 255, 255));
		alaska.setBorderPainted(false);
		alaska.setOpaque(true);
		alaska.setFont(new Font("American Typewriter", Font.BOLD, 14));
		alaska.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getNorthAmerica().getTerritory("alaska");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
				
			}
		});
		
		JButton centralAmerica = new JButton("0");
		centralAmerica.setOpaque(true);
		centralAmerica.setFont(new Font("American Typewriter", Font.BOLD, 14));
		centralAmerica.setBorderPainted(false);
		centralAmerica.setBackground(new Color(255, 255, 255));
		centralAmerica.setBounds(278, 385, 62, 22);
		centralAmerica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getNorthAmerica().getTerritory("central america");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		
		
		VDay.setFont(new Font("American Typewriter", Font.BOLD, 70));
		VDay.setHorizontalAlignment(SwingConstants.CENTER);
		VDay.setOpaque(true);
		VDay.setBackground(Color.WHITE);
		VDay.setBounds(0, 0, 1200, 800);
		mapButtons.add(VDay);
		mapButtons.add(centralAmerica);
		
		JButton brazil = new JButton("0");
		brazil.setOpaque(true);
		brazil.setFont(new Font("American Typewriter", Font.BOLD, 14));
		brazil.setBorderPainted(false);
		brazil.setBackground(new Color(255, 255, 255));
		brazil.setBounds(445, 504, 62, 22);
		brazil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getSouthAmerica().getTerritory("brazil");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(brazil);
		
		JButton argentina = new JButton("0");
		argentina.setOpaque(true);
		argentina.setFont(new Font("American Typewriter", Font.BOLD, 14));
		argentina.setBorderPainted(false);
		argentina.setBackground(new Color(225, 225, 225));
		argentina.setBounds(393, 607, 62, 22);
		argentina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getSouthAmerica().getTerritory("argentina");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(argentina);
		
		JButton newGuinea = new JButton("0");
		newGuinea.setOpaque(true);
		newGuinea.setFont(new Font("American Typewriter", Font.BOLD, 14));
		newGuinea.setBorderPainted(false);
		newGuinea.setBackground(new Color(225, 225, 225));
		newGuinea.setBounds(1082, 534, 62, 22);
		newGuinea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAustralia().getTerritory("new guinea");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(newGuinea);
		
		JButton peru = new JButton("0");
		peru.setOpaque(true);
		peru.setFont(new Font("American Typewriter", Font.BOLD, 14));
		peru.setBorderPainted(false);
		peru.setBackground(new Color(225, 225, 225));
		peru.setBounds(383, 539, 62, 22);
		peru.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getSouthAmerica().getTerritory("peru");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(peru);
		
		JButton eAustralia = new JButton("0");
		eAustralia.setOpaque(true);
		eAustralia.setFont(new Font("American Typewriter", Font.BOLD, 14));
		eAustralia.setBorderPainted(false);
		eAustralia.setBackground(new Color(225, 225, 225));
		eAustralia.setBounds(1082, 631, 76, 22);
		eAustralia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAustralia().getTerritory("eastern australia");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(eAustralia);
		
		JButton wAustralia = new JButton("0");
		wAustralia.setOpaque(true);
		wAustralia.setFont(new Font("American Typewriter", Font.BOLD, 14));
		wAustralia.setBorderPainted(false);
		wAustralia.setBackground(new Color(225, 225, 225));
		wAustralia.setBounds(1020, 658, 84, 22);
		wAustralia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAustralia().getTerritory("western australia");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(wAustralia);
		
		JButton indonesia = new JButton("0");
		indonesia.setOpaque(true);
		indonesia.setFont(new Font("American Typewriter", Font.BOLD, 14));
		indonesia.setBorderPainted(false);
		indonesia.setBackground(new Color(225, 225, 225));
		indonesia.setBounds(999, 565, 62, 22);
		indonesia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAustralia().getTerritory("indonesia");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(indonesia);
		
		JButton kamchatka = new JButton("0");
		kamchatka.setOpaque(true);
		kamchatka.setFont(new Font("American Typewriter", Font.BOLD, 14));
		kamchatka.setBorderPainted(false);
		kamchatka.setBackground(new Color(225, 225, 225));
		kamchatka.setBounds(1076, 141, 62, 22);
		kamchatka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAsia().getTerritory("kamchatka");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(kamchatka);
		
		JButton yakutsk = new JButton("0");
		yakutsk.setOpaque(true);
		yakutsk.setFont(new Font("American Typewriter", Font.BOLD, 14));
		yakutsk.setBorderPainted(false);
		yakutsk.setBackground(new Color(225, 225, 225));
		yakutsk.setBounds(997, 142, 62, 22);
		yakutsk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAsia().getTerritory("yakutsk");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(yakutsk);
		
		JButton japan = new JButton("0");
		japan.setOpaque(true);
		japan.setFont(new Font("American Typewriter", Font.BOLD, 14));
		japan.setBorderPainted(false);
		japan.setBackground(new Color(225, 225, 225));
		japan.setBounds(1096, 312, 62, 22);
		japan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAsia().getTerritory("japan");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(japan);
		
		JButton afghanistan = new JButton("0");
		afghanistan.setOpaque(true);
		afghanistan.setFont(new Font("American Typewriter", Font.BOLD, 14));
		afghanistan.setBorderPainted(false);
		afghanistan.setBackground(new Color(225, 225, 225));
		afghanistan.setBounds(825, 321, 69, 22);
		afghanistan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAsia().getTerritory("afghanistan");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(afghanistan);
		
		JButton siberia = new JButton("0");
		siberia.setOpaque(true);
		siberia.setFont(new Font("American Typewriter", Font.BOLD, 14));
		siberia.setBorderPainted(false);
		siberia.setBackground(new Color(225, 225, 225));
		siberia.setBounds(913, 181, 62, 22);
		siberia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAsia().getTerritory("siberia");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(siberia);
		
		JButton ural = new JButton("0");
		ural.setOpaque(true);
		ural.setFont(new Font("American Typewriter", Font.BOLD, 14));
		ural.setBorderPainted(false);
		ural.setBackground(new Color(225, 225, 225));
		ural.setBounds(855, 231, 62, 22);
		ural.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAsia().getTerritory("ural");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(ural);
		
		JButton irkutsk = new JButton("0");
		irkutsk.setOpaque(true);
		irkutsk.setFont(new Font("American Typewriter", Font.BOLD, 14));
		irkutsk.setBorderPainted(false);
		irkutsk.setBackground(new Color(225, 225, 225));
		irkutsk.setBounds(983, 236, 62, 22);
		irkutsk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAsia().getTerritory("irkutsk");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(irkutsk);
		
		JButton mongolia = new JButton("0");
		mongolia.setOpaque(true);
		mongolia.setFont(new Font("American Typewriter", Font.BOLD, 14));
		mongolia.setBorderPainted(false);
		mongolia.setBackground(new Color(225, 225, 225));
		mongolia.setBounds(973, 302, 62, 22);
		mongolia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAsia().getTerritory("mongolia");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(mongolia);
		
		JButton china = new JButton("0");
		china.setOpaque(true);
		china.setFont(new Font("American Typewriter", Font.BOLD, 14));
		china.setBorderPainted(false);
		china.setBackground(new Color(225, 225, 225));
		china.setBounds(973, 375, 62, 22);
		china.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAsia().getTerritory("china");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(china);
		
		JButton siam = new JButton("0");
		siam.setOpaque(true);
		siam.setFont(new Font("American Typewriter", Font.BOLD, 14));
		siam.setBorderPainted(false);
		siam.setBackground(new Color(225, 225, 225));
		siam.setBounds(972, 453, 62, 22);
		siam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAsia().getTerritory("siam");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(siam);
		
		JButton india = new JButton("0");
		india.setOpaque(true);
		india.setFont(new Font("American Typewriter", Font.BOLD, 14));
		india.setBorderPainted(false);
		india.setBackground(new Color(225, 225, 225));
		india.setBounds(891, 414, 62, 22);
		india.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAsia().getTerritory("india");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(india);
		
		JButton middleEast = new JButton("0");
		middleEast.setOpaque(true);
		middleEast.setFont(new Font("American Typewriter", Font.BOLD, 14));
		middleEast.setBorderPainted(false);
		middleEast.setBackground(new Color(225, 225, 225));
		middleEast.setBounds(767, 404, 69, 22);
		middleEast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAsia().getTerritory("middle east");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(middleEast);
		
		JButton venezuela = new JButton("0");
		venezuela.setBounds(366, 453, 62, 22);
		venezuela.setOpaque(true);
		venezuela.setFont(new Font("American Typewriter", Font.BOLD, 14));
		venezuela.setBorderPainted(false);
		venezuela.setBackground(new Color(225, 225, 225));
		venezuela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getSouthAmerica().getTerritory("venezuela");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(venezuela);
		
		JButton madagascar = new JButton("0");
		madagascar.setOpaque(true);
		madagascar.setFont(new Font("American Typewriter", Font.BOLD, 14));
		madagascar.setBorderPainted(false);
		madagascar.setBackground(new Color(225, 225, 225));
		madagascar.setBounds(783, 658, 62, 22);
		madagascar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAfrica().getTerritory("madagascar");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(madagascar);
		
		JButton sAfrica = new JButton("0");
		sAfrica.setOpaque(true);
		sAfrica.setFont(new Font("American Typewriter", Font.BOLD, 14));
		sAfrica.setBorderPainted(false);
		sAfrica.setBackground(new Color(225, 225, 225));
		sAfrica.setBounds(690, 652, 62, 22);
		sAfrica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAfrica().getTerritory("south africa");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(sAfrica);
		
		JButton eAfrica = new JButton("0");
		eAfrica.setOpaque(true);
		eAfrica.setFont(new Font("American Typewriter", Font.BOLD, 14));
		eAfrica.setBorderPainted(false);
		eAfrica.setBackground(new Color(225, 225, 225));
		eAfrica.setBounds(733, 527, 62, 22);
		eAfrica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAfrica().getTerritory("east africa");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(eAfrica);
		
		JButton egypt = new JButton("0");
		egypt.setOpaque(true);
		egypt.setFont(new Font("American Typewriter", Font.BOLD, 14));
		egypt.setBorderPainted(false);
		egypt.setBackground(new Color(225, 225, 225));
		egypt.setBounds(683, 451, 62, 22);
		egypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAfrica().getTerritory("egypt");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(egypt);
		
		JButton congo = new JButton("0");
		congo.setOpaque(true);
		congo.setFont(new Font("American Typewriter", Font.BOLD, 14));
		congo.setBorderPainted(false);
		congo.setBackground(new Color(225, 225, 225));
		congo.setBounds(677, 574, 69, 22);
		congo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAfrica().getTerritory("congo");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(congo);
		
		JButton wEurope = new JButton("0");
		wEurope.setOpaque(true);
		wEurope.setFont(new Font("American Typewriter", Font.BOLD, 14));
		wEurope.setBorderPainted(false);
		wEurope.setBackground(new Color(225, 225, 225));
		wEurope.setBounds(566, 385, 62, 22);
		wEurope.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getEurope().getTerritory("western europe");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(wEurope);
		
		JButton sEurope = new JButton("0");
		sEurope.setOpaque(true);
		sEurope.setFont(new Font("American Typewriter", Font.BOLD, 14));
		sEurope.setBorderPainted(false);
		sEurope.setBackground(new Color(225, 225, 225));
		sEurope.setBounds(658, 347, 62, 22);
		sEurope.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getEurope().getTerritory("southern europe");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(sEurope);
		
		JButton nAfrica = new JButton("0");
		nAfrica.setOpaque(true);
		nAfrica.setFont(new Font("American Typewriter", Font.BOLD, 14));
		nAfrica.setBorderPainted(false);
		nAfrica.setBackground(new Color(225, 225, 225));
		nAfrica.setBounds(606, 484, 69, 22);
		nAfrica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getAfrica().getTerritory("north africa");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(nAfrica);
		
		JButton nEurope = new JButton("0");
		nEurope.setOpaque(true);
		nEurope.setFont(new Font("American Typewriter", Font.BOLD, 14));
		nEurope.setBorderPainted(false);
		nEurope.setBackground(new Color(225, 225, 225));
		nEurope.setBounds(647, 296, 62, 22);
		nEurope.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getEurope().getTerritory("northern europe");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(nEurope);
		
		JButton ukraine = new JButton("0");
		ukraine.setOpaque(true);
		ukraine.setFont(new Font("American Typewriter", Font.BOLD, 14));
		ukraine.setBorderPainted(false);
		ukraine.setBackground(new Color(225, 225, 225));
		ukraine.setBounds(757, 240, 62, 22);
		ukraine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getEurope().getTerritory("ukraine");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(ukraine);
		
		JButton scandinavia = new JButton("0");
		scandinavia.setOpaque(true);
		scandinavia.setFont(new Font("American Typewriter", Font.BOLD, 14));
		scandinavia.setBorderPainted(false);
		scandinavia.setBackground(new Color(225, 225, 225));
		scandinavia.setBounds(666, 176, 62, 22);
		scandinavia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getEurope().getTerritory("scandinavia");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(scandinavia);
		
		JButton greatBritain = new JButton("0");
		greatBritain.setOpaque(true);
		greatBritain.setFont(new Font("American Typewriter", Font.BOLD, 14));
		greatBritain.setBorderPainted(false);
		greatBritain.setBackground(new Color(225, 225, 225));
		greatBritain.setBounds(551, 278, 62, 22);
		greatBritain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getEurope().getTerritory("great britain");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(greatBritain);
		
		JButton eUnitedStates = new JButton("0");
		eUnitedStates.setBounds(352, 320, 69, 22);
		eUnitedStates.setOpaque(true);
		eUnitedStates.setFont(new Font("American Typewriter", Font.BOLD, 14));
		eUnitedStates.setBorderPainted(false);
		eUnitedStates.setBackground(new Color(225, 225, 225));
		eUnitedStates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getNorthAmerica().getTerritory("eastern united states");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(eUnitedStates);
		
		JButton wUnitedStates = new JButton("0");
		wUnitedStates.setOpaque(true);
		wUnitedStates.setFont(new Font("American Typewriter", Font.BOLD, 14));
		wUnitedStates.setBorderPainted(false);
		wUnitedStates.setBackground(new Color(225, 225, 225));
		wUnitedStates.setBounds(264, 296, 69, 22);
		wUnitedStates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getNorthAmerica().getTerritory("western united states");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(wUnitedStates);
		
		JButton Greenland = new JButton("0");
		Greenland.setOpaque(true);
		Greenland.setFont(new Font("American Typewriter", Font.BOLD, 14));
		Greenland.setBorderPainted(false);
		Greenland.setBackground(new Color(225, 225, 225));
		Greenland.setBounds(476, 125, 62, 22);
		Greenland.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getNorthAmerica().getTerritory("greenland");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(Greenland);
		
		JButton quebec = new JButton("0");
		quebec.setOpaque(true);
		quebec.setFont(new Font("American Typewriter", Font.BOLD, 14));
		quebec.setBorderPainted(false);
		quebec.setBackground(new Color(225, 225, 225));
		quebec.setBounds(411, 237, 69, 22);
		quebec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getNorthAmerica().getTerritory("quebec");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(quebec);
		
		JButton ontario = new JButton("0");
		ontario.setOpaque(true);
		ontario.setFont(new Font("American Typewriter", Font.BOLD, 14));
		ontario.setBorderPainted(false);
		ontario.setBackground(new Color(225, 225, 225));
		ontario.setBounds(341, 243, 62, 22);
		ontario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getNorthAmerica().getTerritory("ontario");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(ontario);
		
		JButton alberta = new JButton("0");
		alberta.setOpaque(true);
		alberta.setFont(new Font("American Typewriter", Font.BOLD, 14));
		alberta.setBorderPainted(false);
		alberta.setBackground(new Color(225, 225, 225));
		alberta.setBounds(264, 222, 62, 22);
		alberta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getNorthAmerica().getTerritory("alberta");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(alberta);
		
		JButton nwTerritory = new JButton("0");
		nwTerritory.setOpaque(true);
		nwTerritory.setFont(new Font("American Typewriter", Font.BOLD, 14));
		nwTerritory.setBorderPainted(false);
		nwTerritory.setBackground(new Color(225, 225, 225));
		nwTerritory.setBounds(258, 163, 99, 22);
		nwTerritory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getNorthAmerica().getTerritory("northwest territory");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(nwTerritory);
		
		JButton iceland = new JButton("0");
		iceland.setOpaque(true);
		iceland.setFont(new Font("American Typewriter", Font.BOLD, 14));
		iceland.setBorderPainted(false);
		iceland.setBackground(new Color(225, 225, 225));
		iceland.setBounds(567, 198, 62, 22);
		iceland.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedTerr = board.getEurope().getTerritory("iceland");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				displayTerr.setText(selectedTerr.getName());
				displayTerr.setForeground(risk.checkWhosTurn().getColor());
				attackTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				attackTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				moveTerrDisplay.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				moveTerrDisplay.setForeground(risk.checkWhosTurn().getColor());
				battleTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				battleTerr.setForeground(risk.checkWhosTurn().getColor());
				lblMyTerr.setText(selectedTerr.getName() + " (" + selectedTerr.armySize() + ")");
				lblMyTerr.setForeground(risk.checkWhosTurn().getColor());
				String[] adjTerr = selectedTerr.adjacentTerritories.toArray(new String[selectedTerr.adjacentTerritories.size()]);
				comboBox.setModel(new DefaultComboBoxModel(adjTerr));
				comboBoxMove.setModel(new DefaultComboBoxModel(adjTerr));
			}
		});
		mapButtons.add(iceland);
		alaska.setBounds(168, 158, 62, 22);
		mapButtons.add(alaska);
		JLabel mapPic = new JLabel(map);
		mapPic.setBounds(0, 0, 1200, 800);
		mapButtons.add(mapPic);
		frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{userboxAddPlayer, mapPic}));
		
		
		btnPlaceTroop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(risk.allReinforcementsPlaced()) {
					userboxAddPlayer.setVisible(false);
					userboxSetup.setVisible(false);
					risk.checkWhosTurn().setReinforcements(board);
					reinforcenum.setText(Integer.toString(risk.checkWhosTurn().getReinforcements()));
					
				}
				else {
					if(!risk.allTerritoriesOccupied(board)) {
						if(selectedTerr.getOwner() == null) {
							risk.getTerritory(board, selectedTerr.getName()).addArmy();
							risk.getTerritory(board, selectedTerr.getName()).setOwner(risk.checkWhosTurn());
							
							alaska.setText(Integer.toString(risk.getTerritory(board, "alaska").armySize()));
							alaska.setBackground(risk.getTerritory(board, "alaska").getOwnerColor());
							alberta.setText(Integer.toString(risk.getTerritory(board, "alberta").armySize()));
							alberta.setBackground(risk.getTerritory(board, "alberta").getOwnerColor());
							centralAmerica.setText(Integer.toString(risk.getTerritory(board, "central america").armySize()));
							centralAmerica.setBackground(risk.getTerritory(board, "central america").getOwnerColor());
							eUnitedStates.setText(Integer.toString(risk.getTerritory(board, "eastern united states").armySize()));
							eUnitedStates.setBackground(risk.getTerritory(board, "eastern united states").getOwnerColor());
							Greenland.setText(Integer.toString(risk.getTerritory(board, "greenland").armySize()));
							Greenland.setBackground(risk.getTerritory(board, "greenland").getOwnerColor());
							nwTerritory.setText(Integer.toString(risk.getTerritory(board, "northwest territory").armySize()));
							nwTerritory.setBackground(risk.getTerritory(board, "northwest territory").getOwnerColor());
							ontario.setText(Integer.toString(risk.getTerritory(board, "ontario").armySize()));
							ontario.setBackground(risk.getTerritory(board, "ontario").getOwnerColor());
							quebec.setText(Integer.toString(risk.getTerritory(board, "quebec").armySize()));
							quebec.setBackground(risk.getTerritory(board, "quebec").getOwnerColor());
							wUnitedStates.setText(Integer.toString(risk.getTerritory(board, "western united states").armySize()));
							wUnitedStates.setBackground(risk.getTerritory(board, "western united states").getOwnerColor());
							venezuela.setText(Integer.toString(risk.getTerritory(board, "venezuela").armySize()));
							venezuela.setBackground(risk.getTerritory(board, "venezuela").getOwnerColor());
							brazil.setText(Integer.toString(risk.getTerritory(board, "brazil").armySize()));
							brazil.setBackground(risk.getTerritory(board, "brazil").getOwnerColor());
							peru.setText(Integer.toString(risk.getTerritory(board, "peru").armySize()));
							peru.setBackground(risk.getTerritory(board, "peru").getOwnerColor());
							argentina.setText(Integer.toString(risk.getTerritory(board, "argentina").armySize()));
							argentina.setBackground(risk.getTerritory(board, "argentina").getOwnerColor());
							greatBritain.setText(Integer.toString(risk.getTerritory(board, "great britain").armySize()));
							greatBritain.setBackground(risk.getTerritory(board, "great britain").getOwnerColor());
							iceland.setText(Integer.toString(risk.getTerritory(board, "iceland").armySize()));
							iceland.setBackground(risk.getTerritory(board, "iceland").getOwnerColor());
							nEurope.setText(Integer.toString(risk.getTerritory(board, "northern europe").armySize()));
							nEurope.setBackground(risk.getTerritory(board, "northern europe").getOwnerColor());
							scandinavia.setText(Integer.toString(risk.getTerritory(board, "scandinavia").armySize()));
							scandinavia.setBackground(risk.getTerritory(board, "scandinavia").getOwnerColor());
							sEurope.setText(Integer.toString(risk.getTerritory(board, "southern europe").armySize()));
							sEurope.setBackground(risk.getTerritory(board, "southern europe").getOwnerColor());
							ukraine.setText(Integer.toString(risk.getTerritory(board, "ukraine").armySize()));
							ukraine.setBackground(risk.getTerritory(board, "ukraine").getOwnerColor());
							wEurope.setText(Integer.toString(risk.getTerritory(board, "western europe").armySize()));
							wEurope.setBackground(risk.getTerritory(board, "western europe").getOwnerColor());
							congo.setText(Integer.toString(risk.getTerritory(board, "congo").armySize()));
							congo.setBackground(risk.getTerritory(board, "congo").getOwnerColor());
							eAfrica.setText(Integer.toString(risk.getTerritory(board, "east africa").armySize()));
							eAfrica.setBackground(risk.getTerritory(board, "east africa").getOwnerColor());
							egypt.setText(Integer.toString(risk.getTerritory(board, "egypt").armySize()));
							egypt.setBackground(risk.getTerritory(board, "egypt").getOwnerColor());
							madagascar.setText(Integer.toString(risk.getTerritory(board, "madagascar").armySize()));
							madagascar.setBackground(risk.getTerritory(board, "madagascar").getOwnerColor());
							nAfrica.setText(Integer.toString(risk.getTerritory(board, "north africa").armySize()));
							nAfrica.setBackground(risk.getTerritory(board, "north africa").getOwnerColor());
							sAfrica.setText(Integer.toString(risk.getTerritory(board, "south africa").armySize()));
							sAfrica.setBackground(risk.getTerritory(board, "south africa").getOwnerColor());
							afghanistan.setText(Integer.toString(risk.getTerritory(board, "afghanistan").armySize()));
							afghanistan.setBackground(risk.getTerritory(board, "afghanistan").getOwnerColor());
							china.setText(Integer.toString(risk.getTerritory(board, "china").armySize()));
							china.setBackground(risk.getTerritory(board, "china").getOwnerColor());
							india.setText(Integer.toString(risk.getTerritory(board, "india").armySize()));
							india.setBackground(risk.getTerritory(board, "india").getOwnerColor());
							irkutsk.setText(Integer.toString(risk.getTerritory(board, "irkutsk").armySize()));
							irkutsk.setBackground(risk.getTerritory(board, "irkutsk").getOwnerColor());
							japan.setText(Integer.toString(risk.getTerritory(board, "japan").armySize()));
							japan.setBackground(risk.getTerritory(board, "japan").getOwnerColor());
							kamchatka.setText(Integer.toString(risk.getTerritory(board, "kamchatka").armySize()));
							kamchatka.setBackground(risk.getTerritory(board, "kamchatka").getOwnerColor());
							middleEast.setText(Integer.toString(risk.getTerritory(board, "middle east").armySize()));
							middleEast.setBackground(risk.getTerritory(board, "middle east").getOwnerColor());
							mongolia.setText(Integer.toString(risk.getTerritory(board, "mongolia").armySize()));
							mongolia.setBackground(risk.getTerritory(board, "mongolia").getOwnerColor());
							siam.setText(Integer.toString(risk.getTerritory(board, "siam").armySize()));
							siam.setBackground(risk.getTerritory(board, "siam").getOwnerColor());
							siberia.setText(Integer.toString(risk.getTerritory(board, "siberia").armySize()));
							siberia.setBackground(risk.getTerritory(board, "siberia").getOwnerColor());
							ural.setText(Integer.toString(risk.getTerritory(board, "ural").armySize()));
							ural.setBackground(risk.getTerritory(board, "ural").getOwnerColor());
							yakutsk.setText(Integer.toString(risk.getTerritory(board, "yakutsk").armySize()));
							yakutsk.setBackground(risk.getTerritory(board, "yakutsk").getOwnerColor());
							eAustralia.setText(Integer.toString(risk.getTerritory(board, "eastern australia").armySize()));
							eAustralia.setBackground(risk.getTerritory(board, "eastern australia").getOwnerColor());
							wAustralia.setText(Integer.toString(risk.getTerritory(board, "western australia").armySize()));
							wAustralia.setBackground(risk.getTerritory(board, "western australia").getOwnerColor());
							indonesia.setText(Integer.toString(risk.getTerritory(board, "indonesia").armySize()));
							indonesia.setBackground(risk.getTerritory(board, "indonesia").getOwnerColor());
							newGuinea.setText(Integer.toString(risk.getTerritory(board, "new guinea").armySize()));
							newGuinea.setBackground(risk.getTerritory(board, "new guinea").getOwnerColor());
							
							
							risk.checkWhosTurn().removeTroopsToPlace(1);
							troopsLeft.setText(Integer.toString(risk.checkWhosTurn().troopsToPlace));
							risk.nextTurn();
							lblPlayerName.setForeground(risk.checkWhosTurn().getColor());
							lblPlayerName.setText(risk.checkWhosTurn().getName());
						}
							
						else {
							infobox1.setText("Select a unoccupied territory!");
						}
					}
					else {
						if(selectedTerr.getOwner() == risk.checkWhosTurn()) {
							risk.getTerritory(board, selectedTerr.getName()).addArmy();
							risk.getTerritory(board, selectedTerr.getName()).setOwner(risk.checkWhosTurn());
							
							alaska.setText(Integer.toString(risk.getTerritory(board, "alaska").armySize()));
							alaska.setBackground(risk.getTerritory(board, "alaska").getOwnerColor());
							alberta.setText(Integer.toString(risk.getTerritory(board, "alberta").armySize()));
							alberta.setBackground(risk.getTerritory(board, "alberta").getOwnerColor());
							centralAmerica.setText(Integer.toString(risk.getTerritory(board, "central america").armySize()));
							centralAmerica.setBackground(risk.getTerritory(board, "central america").getOwnerColor());
							eUnitedStates.setText(Integer.toString(risk.getTerritory(board, "eastern united states").armySize()));
							eUnitedStates.setBackground(risk.getTerritory(board, "eastern united states").getOwnerColor());
							Greenland.setText(Integer.toString(risk.getTerritory(board, "greenland").armySize()));
							Greenland.setBackground(risk.getTerritory(board, "greenland").getOwnerColor());
							nwTerritory.setText(Integer.toString(risk.getTerritory(board, "northwest territory").armySize()));
							nwTerritory.setBackground(risk.getTerritory(board, "northwest territory").getOwnerColor());
							ontario.setText(Integer.toString(risk.getTerritory(board, "ontario").armySize()));
							ontario.setBackground(risk.getTerritory(board, "ontario").getOwnerColor());
							quebec.setText(Integer.toString(risk.getTerritory(board, "quebec").armySize()));
							quebec.setBackground(risk.getTerritory(board, "quebec").getOwnerColor());
							wUnitedStates.setText(Integer.toString(risk.getTerritory(board, "western united states").armySize()));
							wUnitedStates.setBackground(risk.getTerritory(board, "western united states").getOwnerColor());
							venezuela.setText(Integer.toString(risk.getTerritory(board, "venezuela").armySize()));
							venezuela.setBackground(risk.getTerritory(board, "venezuela").getOwnerColor());
							brazil.setText(Integer.toString(risk.getTerritory(board, "brazil").armySize()));
							brazil.setBackground(risk.getTerritory(board, "brazil").getOwnerColor());
							peru.setText(Integer.toString(risk.getTerritory(board, "peru").armySize()));
							peru.setBackground(risk.getTerritory(board, "peru").getOwnerColor());
							argentina.setText(Integer.toString(risk.getTerritory(board, "argentina").armySize()));
							argentina.setBackground(risk.getTerritory(board, "argentina").getOwnerColor());
							greatBritain.setText(Integer.toString(risk.getTerritory(board, "great britain").armySize()));
							greatBritain.setBackground(risk.getTerritory(board, "great britain").getOwnerColor());
							iceland.setText(Integer.toString(risk.getTerritory(board, "iceland").armySize()));
							iceland.setBackground(risk.getTerritory(board, "iceland").getOwnerColor());
							nEurope.setText(Integer.toString(risk.getTerritory(board, "northern europe").armySize()));
							nEurope.setBackground(risk.getTerritory(board, "northern europe").getOwnerColor());
							scandinavia.setText(Integer.toString(risk.getTerritory(board, "scandinavia").armySize()));
							scandinavia.setBackground(risk.getTerritory(board, "scandinavia").getOwnerColor());
							sEurope.setText(Integer.toString(risk.getTerritory(board, "southern europe").armySize()));
							sEurope.setBackground(risk.getTerritory(board, "southern europe").getOwnerColor());
							ukraine.setText(Integer.toString(risk.getTerritory(board, "ukraine").armySize()));
							ukraine.setBackground(risk.getTerritory(board, "ukraine").getOwnerColor());
							wEurope.setText(Integer.toString(risk.getTerritory(board, "western europe").armySize()));
							wEurope.setBackground(risk.getTerritory(board, "western europe").getOwnerColor());
							congo.setText(Integer.toString(risk.getTerritory(board, "congo").armySize()));
							congo.setBackground(risk.getTerritory(board, "congo").getOwnerColor());
							eAfrica.setText(Integer.toString(risk.getTerritory(board, "east africa").armySize()));
							eAfrica.setBackground(risk.getTerritory(board, "east africa").getOwnerColor());
							egypt.setText(Integer.toString(risk.getTerritory(board, "egypt").armySize()));
							egypt.setBackground(risk.getTerritory(board, "egypt").getOwnerColor());
							madagascar.setText(Integer.toString(risk.getTerritory(board, "madagascar").armySize()));
							madagascar.setBackground(risk.getTerritory(board, "madagascar").getOwnerColor());
							nAfrica.setText(Integer.toString(risk.getTerritory(board, "north africa").armySize()));
							nAfrica.setBackground(risk.getTerritory(board, "north africa").getOwnerColor());
							sAfrica.setText(Integer.toString(risk.getTerritory(board, "south africa").armySize()));
							sAfrica.setBackground(risk.getTerritory(board, "south africa").getOwnerColor());
							afghanistan.setText(Integer.toString(risk.getTerritory(board, "afghanistan").armySize()));
							afghanistan.setBackground(risk.getTerritory(board, "afghanistan").getOwnerColor());
							china.setText(Integer.toString(risk.getTerritory(board, "china").armySize()));
							china.setBackground(risk.getTerritory(board, "china").getOwnerColor());
							india.setText(Integer.toString(risk.getTerritory(board, "india").armySize()));
							india.setBackground(risk.getTerritory(board, "india").getOwnerColor());
							irkutsk.setText(Integer.toString(risk.getTerritory(board, "irkutsk").armySize()));
							irkutsk.setBackground(risk.getTerritory(board, "irkutsk").getOwnerColor());
							japan.setText(Integer.toString(risk.getTerritory(board, "japan").armySize()));
							japan.setBackground(risk.getTerritory(board, "japan").getOwnerColor());
							kamchatka.setText(Integer.toString(risk.getTerritory(board, "kamchatka").armySize()));
							kamchatka.setBackground(risk.getTerritory(board, "kamchatka").getOwnerColor());
							middleEast.setText(Integer.toString(risk.getTerritory(board, "middle east").armySize()));
							middleEast.setBackground(risk.getTerritory(board, "middle east").getOwnerColor());
							mongolia.setText(Integer.toString(risk.getTerritory(board, "mongolia").armySize()));
							mongolia.setBackground(risk.getTerritory(board, "mongolia").getOwnerColor());
							siam.setText(Integer.toString(risk.getTerritory(board, "siam").armySize()));
							siam.setBackground(risk.getTerritory(board, "siam").getOwnerColor());
							siberia.setText(Integer.toString(risk.getTerritory(board, "siberia").armySize()));
							siberia.setBackground(risk.getTerritory(board, "siberia").getOwnerColor());
							ural.setText(Integer.toString(risk.getTerritory(board, "ural").armySize()));
							ural.setBackground(risk.getTerritory(board, "ural").getOwnerColor());
							yakutsk.setText(Integer.toString(risk.getTerritory(board, "yakutsk").armySize()));
							yakutsk.setBackground(risk.getTerritory(board, "yakutsk").getOwnerColor());
							eAustralia.setText(Integer.toString(risk.getTerritory(board, "eastern australia").armySize()));
							eAustralia.setBackground(risk.getTerritory(board, "eastern australia").getOwnerColor());
							wAustralia.setText(Integer.toString(risk.getTerritory(board, "western australia").armySize()));
							wAustralia.setBackground(risk.getTerritory(board, "western australia").getOwnerColor());
							indonesia.setText(Integer.toString(risk.getTerritory(board, "indonesia").armySize()));
							indonesia.setBackground(risk.getTerritory(board, "indonesia").getOwnerColor());
							newGuinea.setText(Integer.toString(risk.getTerritory(board, "new guinea").armySize()));
							newGuinea.setBackground(risk.getTerritory(board, "new guinea").getOwnerColor());
							
							risk.checkWhosTurn().removeTroopsToPlace(1);
							troopsLeft.setText(Integer.toString(risk.checkWhosTurn().troopsToPlace));
							risk.nextTurn();
							lblPlayerName.setForeground(risk.checkWhosTurn().getColor());
							lblPlayerName.setText(risk.checkWhosTurn().getName());
						} else {
							infobox1.setText("<html>Select a territory you <br/>contorol to reinforce!</html>");
						}
					}
				}
			}
				
		});
		
		btnRollDie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((Integer.parseInt(numToBattle.getText()) > selectedTerr.armySize()-1) || (Integer.parseInt(numToBattle.getText()) > 3) || (Integer.parseInt(numToBattle.getText()) < 1)) {
					lblBattleInfoBox.setText("<html>Must Leave 1 troop<br/>and send between 1-3</html>");
				}else {
				Combat battle = new Combat();
				List<Integer> attack = battle.attack(Integer.parseInt(numToBattle.getText()));
				List<Integer> defend = battle.defend(risk.getTerritory(board, comboBox.getSelectedItem().toString()).armySize());
				int[] casualties = battle.compare(attack, defend);
				risk.getTerritory(board, comboBox.getSelectedItem().toString()).removeArmy(casualties[1]);
				int attackCas = Integer.parseInt(numToBattle.getText()) - casualties[0]; 
				if (risk.getTerritory(board, comboBox.getSelectedItem().toString()).armySize() < 1) {
					risk.getTerritory(board, comboBox.getSelectedItem().toString()).addArmy(attackCas);
					risk.getTerritory(board, comboBox.getSelectedItem().toString()).getOwner().removeTotalTerritories();
					risk.getTerritory(board, comboBox.getSelectedItem().toString()).setOwner(risk.checkWhosTurn());
					
					risk.getTerritory(board, selectedTerr.getName()).removeArmy(Integer.parseInt(numToBattle.getText()));
					
				}else {
					risk.getTerritory(board, selectedTerr.getName()).removeArmy(casualties[0]);
				}
				lblBattleInfoBox.setText("<html>"+ selectedTerr.getName() + " had " + casualties[0] + " casualties<br/>" +comboBox.getSelectedItem().toString()+ " had "+ casualties[1] + " casualties</html>");
				
				alaska.setText(Integer.toString(risk.getTerritory(board, "alaska").armySize()));
				alaska.setBackground(risk.getTerritory(board, "alaska").getOwnerColor());
				alberta.setText(Integer.toString(risk.getTerritory(board, "alberta").armySize()));
				alberta.setBackground(risk.getTerritory(board, "alberta").getOwnerColor());
				centralAmerica.setText(Integer.toString(risk.getTerritory(board, "central america").armySize()));
				centralAmerica.setBackground(risk.getTerritory(board, "central america").getOwnerColor());
				eUnitedStates.setText(Integer.toString(risk.getTerritory(board, "eastern united states").armySize()));
				eUnitedStates.setBackground(risk.getTerritory(board, "eastern united states").getOwnerColor());
				Greenland.setText(Integer.toString(risk.getTerritory(board, "greenland").armySize()));
				Greenland.setBackground(risk.getTerritory(board, "greenland").getOwnerColor());
				nwTerritory.setText(Integer.toString(risk.getTerritory(board, "northwest territory").armySize()));
				nwTerritory.setBackground(risk.getTerritory(board, "northwest territory").getOwnerColor());
				ontario.setText(Integer.toString(risk.getTerritory(board, "ontario").armySize()));
				ontario.setBackground(risk.getTerritory(board, "ontario").getOwnerColor());
				quebec.setText(Integer.toString(risk.getTerritory(board, "quebec").armySize()));
				quebec.setBackground(risk.getTerritory(board, "quebec").getOwnerColor());
				wUnitedStates.setText(Integer.toString(risk.getTerritory(board, "western united states").armySize()));
				wUnitedStates.setBackground(risk.getTerritory(board, "western united states").getOwnerColor());
				venezuela.setText(Integer.toString(risk.getTerritory(board, "venezuela").armySize()));
				venezuela.setBackground(risk.getTerritory(board, "venezuela").getOwnerColor());
				brazil.setText(Integer.toString(risk.getTerritory(board, "brazil").armySize()));
				brazil.setBackground(risk.getTerritory(board, "brazil").getOwnerColor());
				peru.setText(Integer.toString(risk.getTerritory(board, "peru").armySize()));
				peru.setBackground(risk.getTerritory(board, "peru").getOwnerColor());
				argentina.setText(Integer.toString(risk.getTerritory(board, "argentina").armySize()));
				argentina.setBackground(risk.getTerritory(board, "argentina").getOwnerColor());
				greatBritain.setText(Integer.toString(risk.getTerritory(board, "great britain").armySize()));
				greatBritain.setBackground(risk.getTerritory(board, "great britain").getOwnerColor());
				iceland.setText(Integer.toString(risk.getTerritory(board, "iceland").armySize()));
				iceland.setBackground(risk.getTerritory(board, "iceland").getOwnerColor());
				nEurope.setText(Integer.toString(risk.getTerritory(board, "northern europe").armySize()));
				nEurope.setBackground(risk.getTerritory(board, "northern europe").getOwnerColor());
				scandinavia.setText(Integer.toString(risk.getTerritory(board, "scandinavia").armySize()));
				scandinavia.setBackground(risk.getTerritory(board, "scandinavia").getOwnerColor());
				sEurope.setText(Integer.toString(risk.getTerritory(board, "southern europe").armySize()));
				sEurope.setBackground(risk.getTerritory(board, "southern europe").getOwnerColor());
				ukraine.setText(Integer.toString(risk.getTerritory(board, "ukraine").armySize()));
				ukraine.setBackground(risk.getTerritory(board, "ukraine").getOwnerColor());
				wEurope.setText(Integer.toString(risk.getTerritory(board, "western europe").armySize()));
				wEurope.setBackground(risk.getTerritory(board, "western europe").getOwnerColor());
				congo.setText(Integer.toString(risk.getTerritory(board, "congo").armySize()));
				congo.setBackground(risk.getTerritory(board, "congo").getOwnerColor());
				eAfrica.setText(Integer.toString(risk.getTerritory(board, "east africa").armySize()));
				eAfrica.setBackground(risk.getTerritory(board, "east africa").getOwnerColor());
				egypt.setText(Integer.toString(risk.getTerritory(board, "egypt").armySize()));
				egypt.setBackground(risk.getTerritory(board, "egypt").getOwnerColor());
				madagascar.setText(Integer.toString(risk.getTerritory(board, "madagascar").armySize()));
				madagascar.setBackground(risk.getTerritory(board, "madagascar").getOwnerColor());
				nAfrica.setText(Integer.toString(risk.getTerritory(board, "north africa").armySize()));
				nAfrica.setBackground(risk.getTerritory(board, "north africa").getOwnerColor());
				sAfrica.setText(Integer.toString(risk.getTerritory(board, "south africa").armySize()));
				sAfrica.setBackground(risk.getTerritory(board, "south africa").getOwnerColor());
				afghanistan.setText(Integer.toString(risk.getTerritory(board, "afghanistan").armySize()));
				afghanistan.setBackground(risk.getTerritory(board, "afghanistan").getOwnerColor());
				china.setText(Integer.toString(risk.getTerritory(board, "china").armySize()));
				china.setBackground(risk.getTerritory(board, "china").getOwnerColor());
				india.setText(Integer.toString(risk.getTerritory(board, "india").armySize()));
				india.setBackground(risk.getTerritory(board, "india").getOwnerColor());
				irkutsk.setText(Integer.toString(risk.getTerritory(board, "irkutsk").armySize()));
				irkutsk.setBackground(risk.getTerritory(board, "irkutsk").getOwnerColor());
				japan.setText(Integer.toString(risk.getTerritory(board, "japan").armySize()));
				japan.setBackground(risk.getTerritory(board, "japan").getOwnerColor());
				kamchatka.setText(Integer.toString(risk.getTerritory(board, "kamchatka").armySize()));
				kamchatka.setBackground(risk.getTerritory(board, "kamchatka").getOwnerColor());
				middleEast.setText(Integer.toString(risk.getTerritory(board, "middle east").armySize()));
				middleEast.setBackground(risk.getTerritory(board, "middle east").getOwnerColor());
				mongolia.setText(Integer.toString(risk.getTerritory(board, "mongolia").armySize()));
				mongolia.setBackground(risk.getTerritory(board, "mongolia").getOwnerColor());
				siam.setText(Integer.toString(risk.getTerritory(board, "siam").armySize()));
				siam.setBackground(risk.getTerritory(board, "siam").getOwnerColor());
				siberia.setText(Integer.toString(risk.getTerritory(board, "siberia").armySize()));
				siberia.setBackground(risk.getTerritory(board, "siberia").getOwnerColor());
				ural.setText(Integer.toString(risk.getTerritory(board, "ural").armySize()));
				ural.setBackground(risk.getTerritory(board, "ural").getOwnerColor());
				yakutsk.setText(Integer.toString(risk.getTerritory(board, "yakutsk").armySize()));
				yakutsk.setBackground(risk.getTerritory(board, "yakutsk").getOwnerColor());
				eAustralia.setText(Integer.toString(risk.getTerritory(board, "eastern australia").armySize()));
				eAustralia.setBackground(risk.getTerritory(board, "eastern australia").getOwnerColor());
				wAustralia.setText(Integer.toString(risk.getTerritory(board, "western australia").armySize()));
				wAustralia.setBackground(risk.getTerritory(board, "western australia").getOwnerColor());
				indonesia.setText(Integer.toString(risk.getTerritory(board, "indonesia").armySize()));
				indonesia.setBackground(risk.getTerritory(board, "indonesia").getOwnerColor());
				newGuinea.setText(Integer.toString(risk.getTerritory(board, "new guinea").armySize()));
				newGuinea.setBackground(risk.getTerritory(board, "new guinea").getOwnerColor());
				
				ImageIcon attackHigh = new ImageIcon(battle.printURL(attack)[2]);
				ImageIcon attackMed = new ImageIcon(battle.printURL(attack)[1]);
				ImageIcon attackLow = new ImageIcon(battle.printURL(attack)[0]);
				ImageIcon defendHigh = new ImageIcon(battle.printURL(defend)[1]);
				ImageIcon defendLow = new ImageIcon(battle.printURL(defend)[0]);
				attackDie1.setIcon(attackMed);
				attackDie2.setIcon(attackLow);
				attackDie3.setIcon(attackHigh);
				defenseDie2.setIcon(defendLow);
				defenseDie1.setIcon(defendHigh);
				}
			}
		});
		
		buttonMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!selectedTerr.moveable(risk.getTerritory(board, comboBoxMove.getSelectedItem().toString()))) {
					lblBattleInfoBox.setText("<html>You cannot<br/>move troops here</html>");
				}
					
				else if((Integer.parseInt(numMove.getText()) > selectedTerr.armySize()-1) || (Integer.parseInt(numMove.getText()) < 1)) {
					lblBattleInfoBox.setText("<html>Must Leave 1 troop<br/>and send at least 1</html>");
				}else {
				risk.getTerritory(board, selectedTerr.getName()).removeArmy(Integer.parseInt(numMove.getText()));
				risk.getTerritory(board, comboBox.getSelectedItem().toString()).addArmy(Integer.parseInt(numMove.getText()));
				lblBattleInfoBox.setText("<html>Moving "+ Integer.parseInt(numMove.getText()) + " troops from<br/>" + selectedTerr.getName() + "<br/>to " + comboBoxMove.getSelectedItem().toString()+"</html>");
				
				alaska.setText(Integer.toString(risk.getTerritory(board, "alaska").armySize()));
				alaska.setBackground(risk.getTerritory(board, "alaska").getOwnerColor());
				alberta.setText(Integer.toString(risk.getTerritory(board, "alberta").armySize()));
				alberta.setBackground(risk.getTerritory(board, "alberta").getOwnerColor());
				centralAmerica.setText(Integer.toString(risk.getTerritory(board, "central america").armySize()));
				centralAmerica.setBackground(risk.getTerritory(board, "central america").getOwnerColor());
				eUnitedStates.setText(Integer.toString(risk.getTerritory(board, "eastern united states").armySize()));
				eUnitedStates.setBackground(risk.getTerritory(board, "eastern united states").getOwnerColor());
				Greenland.setText(Integer.toString(risk.getTerritory(board, "greenland").armySize()));
				Greenland.setBackground(risk.getTerritory(board, "greenland").getOwnerColor());
				nwTerritory.setText(Integer.toString(risk.getTerritory(board, "northwest territory").armySize()));
				nwTerritory.setBackground(risk.getTerritory(board, "northwest territory").getOwnerColor());
				ontario.setText(Integer.toString(risk.getTerritory(board, "ontario").armySize()));
				ontario.setBackground(risk.getTerritory(board, "ontario").getOwnerColor());
				quebec.setText(Integer.toString(risk.getTerritory(board, "quebec").armySize()));
				quebec.setBackground(risk.getTerritory(board, "quebec").getOwnerColor());
				wUnitedStates.setText(Integer.toString(risk.getTerritory(board, "western united states").armySize()));
				wUnitedStates.setBackground(risk.getTerritory(board, "western united states").getOwnerColor());
				venezuela.setText(Integer.toString(risk.getTerritory(board, "venezuela").armySize()));
				venezuela.setBackground(risk.getTerritory(board, "venezuela").getOwnerColor());
				brazil.setText(Integer.toString(risk.getTerritory(board, "brazil").armySize()));
				brazil.setBackground(risk.getTerritory(board, "brazil").getOwnerColor());
				peru.setText(Integer.toString(risk.getTerritory(board, "peru").armySize()));
				peru.setBackground(risk.getTerritory(board, "peru").getOwnerColor());
				argentina.setText(Integer.toString(risk.getTerritory(board, "argentina").armySize()));
				argentina.setBackground(risk.getTerritory(board, "argentina").getOwnerColor());
				greatBritain.setText(Integer.toString(risk.getTerritory(board, "great britain").armySize()));
				greatBritain.setBackground(risk.getTerritory(board, "great britain").getOwnerColor());
				iceland.setText(Integer.toString(risk.getTerritory(board, "iceland").armySize()));
				iceland.setBackground(risk.getTerritory(board, "iceland").getOwnerColor());
				nEurope.setText(Integer.toString(risk.getTerritory(board, "northern europe").armySize()));
				nEurope.setBackground(risk.getTerritory(board, "northern europe").getOwnerColor());
				scandinavia.setText(Integer.toString(risk.getTerritory(board, "scandinavia").armySize()));
				scandinavia.setBackground(risk.getTerritory(board, "scandinavia").getOwnerColor());
				sEurope.setText(Integer.toString(risk.getTerritory(board, "southern europe").armySize()));
				sEurope.setBackground(risk.getTerritory(board, "southern europe").getOwnerColor());
				ukraine.setText(Integer.toString(risk.getTerritory(board, "ukraine").armySize()));
				ukraine.setBackground(risk.getTerritory(board, "ukraine").getOwnerColor());
				wEurope.setText(Integer.toString(risk.getTerritory(board, "western europe").armySize()));
				wEurope.setBackground(risk.getTerritory(board, "western europe").getOwnerColor());
				congo.setText(Integer.toString(risk.getTerritory(board, "congo").armySize()));
				congo.setBackground(risk.getTerritory(board, "congo").getOwnerColor());
				eAfrica.setText(Integer.toString(risk.getTerritory(board, "east africa").armySize()));
				eAfrica.setBackground(risk.getTerritory(board, "east africa").getOwnerColor());
				egypt.setText(Integer.toString(risk.getTerritory(board, "egypt").armySize()));
				egypt.setBackground(risk.getTerritory(board, "egypt").getOwnerColor());
				madagascar.setText(Integer.toString(risk.getTerritory(board, "madagascar").armySize()));
				madagascar.setBackground(risk.getTerritory(board, "madagascar").getOwnerColor());
				nAfrica.setText(Integer.toString(risk.getTerritory(board, "north africa").armySize()));
				nAfrica.setBackground(risk.getTerritory(board, "north africa").getOwnerColor());
				sAfrica.setText(Integer.toString(risk.getTerritory(board, "south africa").armySize()));
				sAfrica.setBackground(risk.getTerritory(board, "south africa").getOwnerColor());
				afghanistan.setText(Integer.toString(risk.getTerritory(board, "afghanistan").armySize()));
				afghanistan.setBackground(risk.getTerritory(board, "afghanistan").getOwnerColor());
				china.setText(Integer.toString(risk.getTerritory(board, "china").armySize()));
				china.setBackground(risk.getTerritory(board, "china").getOwnerColor());
				india.setText(Integer.toString(risk.getTerritory(board, "india").armySize()));
				india.setBackground(risk.getTerritory(board, "india").getOwnerColor());
				irkutsk.setText(Integer.toString(risk.getTerritory(board, "irkutsk").armySize()));
				irkutsk.setBackground(risk.getTerritory(board, "irkutsk").getOwnerColor());
				japan.setText(Integer.toString(risk.getTerritory(board, "japan").armySize()));
				japan.setBackground(risk.getTerritory(board, "japan").getOwnerColor());
				kamchatka.setText(Integer.toString(risk.getTerritory(board, "kamchatka").armySize()));
				kamchatka.setBackground(risk.getTerritory(board, "kamchatka").getOwnerColor());
				middleEast.setText(Integer.toString(risk.getTerritory(board, "middle east").armySize()));
				middleEast.setBackground(risk.getTerritory(board, "middle east").getOwnerColor());
				mongolia.setText(Integer.toString(risk.getTerritory(board, "mongolia").armySize()));
				mongolia.setBackground(risk.getTerritory(board, "mongolia").getOwnerColor());
				siam.setText(Integer.toString(risk.getTerritory(board, "siam").armySize()));
				siam.setBackground(risk.getTerritory(board, "siam").getOwnerColor());
				siberia.setText(Integer.toString(risk.getTerritory(board, "siberia").armySize()));
				siberia.setBackground(risk.getTerritory(board, "siberia").getOwnerColor());
				ural.setText(Integer.toString(risk.getTerritory(board, "ural").armySize()));
				ural.setBackground(risk.getTerritory(board, "ural").getOwnerColor());
				yakutsk.setText(Integer.toString(risk.getTerritory(board, "yakutsk").armySize()));
				yakutsk.setBackground(risk.getTerritory(board, "yakutsk").getOwnerColor());
				eAustralia.setText(Integer.toString(risk.getTerritory(board, "eastern australia").armySize()));
				eAustralia.setBackground(risk.getTerritory(board, "eastern australia").getOwnerColor());
				wAustralia.setText(Integer.toString(risk.getTerritory(board, "western australia").armySize()));
				wAustralia.setBackground(risk.getTerritory(board, "western australia").getOwnerColor());
				indonesia.setText(Integer.toString(risk.getTerritory(board, "indonesia").armySize()));
				indonesia.setBackground(risk.getTerritory(board, "indonesia").getOwnerColor());
				newGuinea.setText(Integer.toString(risk.getTerritory(board, "new guinea").armySize()));
				newGuinea.setBackground(risk.getTerritory(board, "new guinea").getOwnerColor());
				}
			}
			
		});
		
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userboxAddPlayer.setVisible(false);
				userboxSetup.setVisible(false);
				try {
					risk.addPlayer("mitch");
					risk.addPlayer("logan");
					risk.addPlayer("ryan");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				risk.checkWhosTurn().addCard(deck.drawCard());
				risk.checkWhosTurn().addCard(deck.drawCard());
				risk.checkWhosTurn().addCard(deck.drawCard());
				risk.checkWhosTurn().addCard(deck.drawCard());
				risk.checkWhosTurn().addCard(deck.drawCard());
				risk.checkWhosTurn().addCard(deck.drawCard());
				
				risk.getTerritory(board, "western australia").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "western australia").addArmy();
				risk.getTerritory(board, "new guinea").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "new guinea").addArmy();
				risk.getTerritory(board, "indonesia").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "indonesia").addArmy();
				risk.getTerritory(board, "eastern australia").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "eastern australia").addArmy();
				risk.getTerritory(board, "yakutsk").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "yakutsk").addArmy();
				risk.getTerritory(board, "ural").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "ural").addArmy();
				risk.getTerritory(board, "siberia").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "siberia").addArmy();
				risk.getTerritory(board, "siam").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "siam").addArmy();
				risk.getTerritory(board, "mongolia").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "mongolia").addArmy();
				risk.getTerritory(board, "middle east").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "middle east").addArmy();
				risk.getTerritory(board, "kamchatka").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "kamchatka").addArmy();
				risk.getTerritory(board, "japan").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "japan").addArmy();
				risk.getTerritory(board, "irkutsk").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "irkutsk").addArmy();
				risk.getTerritory(board, "afghanistan").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "afghanistan").addArmy();
				risk.getTerritory(board, "south africa").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "south africa").addArmy();
				risk.getTerritory(board, "north africa").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "north africa").addArmy();
				risk.getTerritory(board, "madagascar").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "madagascar").addArmy();
				risk.getTerritory(board, "egypt").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "egypt").addArmy();
				risk.getTerritory(board, "east africa").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "east africa").addArmy();
				risk.getTerritory(board, "congo").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "congo").addArmy();
				risk.getTerritory(board, "western europe").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "western europe").addArmy();
				risk.getTerritory(board, "ukraine").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "ukraine").addArmy();
				risk.getTerritory(board, "southern europe").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "southern europe").addArmy();
				risk.getTerritory(board, "scandinavia").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "scandinavia").addArmy();
				risk.getTerritory(board, "northern europe").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "northern europe").addArmy();
				risk.getTerritory(board, "iceland").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "iceland").addArmy();
				risk.getTerritory(board, "great britain").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "great britain").addArmy();
				risk.getTerritory(board, "venezuela").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "venezuela").addArmy();
				risk.getTerritory(board, "peru").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "peru").addArmy();
				risk.getTerritory(board, "argentina").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "argentina").addArmy();
				risk.getTerritory(board, "western united states").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "western united states").addArmy();
				risk.getTerritory(board, "quebec").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "quebec").addArmy();
				risk.getTerritory(board, "ontario").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "ontario").addArmy();
				risk.getTerritory(board, "northwest territory").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "northwest territory").addArmy();
				risk.getTerritory(board, "greenland").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "greenland").addArmy();
				risk.getTerritory(board, "eastern united states").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "eastern united states").addArmy();
				risk.getTerritory(board, "central america").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "central america").addArmy();
				risk.getTerritory(board, "alberta").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "alberta").addArmy();
				risk.getTerritory(board, "alaska").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "alaska").addArmy();
				risk.nextTurn();
				risk.getTerritory(board, "brazil").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "brazil").addArmy(5);
				risk.nextTurn();
				risk.getTerritory(board, "india").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "india").addArmy(5);
				risk.nextTurn();
				risk.getTerritory(board, "china").setOwner(risk.checkWhosTurn());
				risk.getTerritory(board, "china").addArmy(5);
				
				alaska.setText(Integer.toString(risk.getTerritory(board, "alaska").armySize()));
				alaska.setBackground(risk.getTerritory(board, "alaska").getOwnerColor());
				alberta.setText(Integer.toString(risk.getTerritory(board, "alberta").armySize()));
				alberta.setBackground(risk.getTerritory(board, "alberta").getOwnerColor());
				centralAmerica.setText(Integer.toString(risk.getTerritory(board, "central america").armySize()));
				centralAmerica.setBackground(risk.getTerritory(board, "central america").getOwnerColor());
				eUnitedStates.setText(Integer.toString(risk.getTerritory(board, "eastern united states").armySize()));
				eUnitedStates.setBackground(risk.getTerritory(board, "eastern united states").getOwnerColor());
				Greenland.setText(Integer.toString(risk.getTerritory(board, "greenland").armySize()));
				Greenland.setBackground(risk.getTerritory(board, "greenland").getOwnerColor());
				nwTerritory.setText(Integer.toString(risk.getTerritory(board, "northwest territory").armySize()));
				nwTerritory.setBackground(risk.getTerritory(board, "northwest territory").getOwnerColor());
				ontario.setText(Integer.toString(risk.getTerritory(board, "ontario").armySize()));
				ontario.setBackground(risk.getTerritory(board, "ontario").getOwnerColor());
				quebec.setText(Integer.toString(risk.getTerritory(board, "quebec").armySize()));
				quebec.setBackground(risk.getTerritory(board, "quebec").getOwnerColor());
				wUnitedStates.setText(Integer.toString(risk.getTerritory(board, "western united states").armySize()));
				wUnitedStates.setBackground(risk.getTerritory(board, "western united states").getOwnerColor());
				venezuela.setText(Integer.toString(risk.getTerritory(board, "venezuela").armySize()));
				venezuela.setBackground(risk.getTerritory(board, "venezuela").getOwnerColor());
				brazil.setText(Integer.toString(risk.getTerritory(board, "brazil").armySize()));
				brazil.setBackground(risk.getTerritory(board, "brazil").getOwnerColor());
				peru.setText(Integer.toString(risk.getTerritory(board, "peru").armySize()));
				peru.setBackground(risk.getTerritory(board, "peru").getOwnerColor());
				argentina.setText(Integer.toString(risk.getTerritory(board, "argentina").armySize()));
				argentina.setBackground(risk.getTerritory(board, "argentina").getOwnerColor());
				greatBritain.setText(Integer.toString(risk.getTerritory(board, "great britain").armySize()));
				greatBritain.setBackground(risk.getTerritory(board, "great britain").getOwnerColor());
				iceland.setText(Integer.toString(risk.getTerritory(board, "iceland").armySize()));
				iceland.setBackground(risk.getTerritory(board, "iceland").getOwnerColor());
				nEurope.setText(Integer.toString(risk.getTerritory(board, "northern europe").armySize()));
				nEurope.setBackground(risk.getTerritory(board, "northern europe").getOwnerColor());
				scandinavia.setText(Integer.toString(risk.getTerritory(board, "scandinavia").armySize()));
				scandinavia.setBackground(risk.getTerritory(board, "scandinavia").getOwnerColor());
				sEurope.setText(Integer.toString(risk.getTerritory(board, "southern europe").armySize()));
				sEurope.setBackground(risk.getTerritory(board, "southern europe").getOwnerColor());
				ukraine.setText(Integer.toString(risk.getTerritory(board, "ukraine").armySize()));
				ukraine.setBackground(risk.getTerritory(board, "ukraine").getOwnerColor());
				wEurope.setText(Integer.toString(risk.getTerritory(board, "western europe").armySize()));
				wEurope.setBackground(risk.getTerritory(board, "western europe").getOwnerColor());
				congo.setText(Integer.toString(risk.getTerritory(board, "congo").armySize()));
				congo.setBackground(risk.getTerritory(board, "congo").getOwnerColor());
				eAfrica.setText(Integer.toString(risk.getTerritory(board, "east africa").armySize()));
				eAfrica.setBackground(risk.getTerritory(board, "east africa").getOwnerColor());
				egypt.setText(Integer.toString(risk.getTerritory(board, "egypt").armySize()));
				egypt.setBackground(risk.getTerritory(board, "egypt").getOwnerColor());
				madagascar.setText(Integer.toString(risk.getTerritory(board, "madagascar").armySize()));
				madagascar.setBackground(risk.getTerritory(board, "madagascar").getOwnerColor());
				nAfrica.setText(Integer.toString(risk.getTerritory(board, "north africa").armySize()));
				nAfrica.setBackground(risk.getTerritory(board, "north africa").getOwnerColor());
				sAfrica.setText(Integer.toString(risk.getTerritory(board, "south africa").armySize()));
				sAfrica.setBackground(risk.getTerritory(board, "south africa").getOwnerColor());
				afghanistan.setText(Integer.toString(risk.getTerritory(board, "afghanistan").armySize()));
				afghanistan.setBackground(risk.getTerritory(board, "afghanistan").getOwnerColor());
				china.setText(Integer.toString(risk.getTerritory(board, "china").armySize()));
				china.setBackground(risk.getTerritory(board, "china").getOwnerColor());
				india.setText(Integer.toString(risk.getTerritory(board, "india").armySize()));
				india.setBackground(risk.getTerritory(board, "india").getOwnerColor());
				irkutsk.setText(Integer.toString(risk.getTerritory(board, "irkutsk").armySize()));
				irkutsk.setBackground(risk.getTerritory(board, "irkutsk").getOwnerColor());
				japan.setText(Integer.toString(risk.getTerritory(board, "japan").armySize()));
				japan.setBackground(risk.getTerritory(board, "japan").getOwnerColor());
				kamchatka.setText(Integer.toString(risk.getTerritory(board, "kamchatka").armySize()));
				kamchatka.setBackground(risk.getTerritory(board, "kamchatka").getOwnerColor());
				middleEast.setText(Integer.toString(risk.getTerritory(board, "middle east").armySize()));
				middleEast.setBackground(risk.getTerritory(board, "middle east").getOwnerColor());
				mongolia.setText(Integer.toString(risk.getTerritory(board, "mongolia").armySize()));
				mongolia.setBackground(risk.getTerritory(board, "mongolia").getOwnerColor());
				siam.setText(Integer.toString(risk.getTerritory(board, "siam").armySize()));
				siam.setBackground(risk.getTerritory(board, "siam").getOwnerColor());
				siberia.setText(Integer.toString(risk.getTerritory(board, "siberia").armySize()));
				siberia.setBackground(risk.getTerritory(board, "siberia").getOwnerColor());
				ural.setText(Integer.toString(risk.getTerritory(board, "ural").armySize()));
				ural.setBackground(risk.getTerritory(board, "ural").getOwnerColor());
				yakutsk.setText(Integer.toString(risk.getTerritory(board, "yakutsk").armySize()));
				yakutsk.setBackground(risk.getTerritory(board, "yakutsk").getOwnerColor());
				eAustralia.setText(Integer.toString(risk.getTerritory(board, "eastern australia").armySize()));
				eAustralia.setBackground(risk.getTerritory(board, "eastern australia").getOwnerColor());
				wAustralia.setText(Integer.toString(risk.getTerritory(board, "western australia").armySize()));
				wAustralia.setBackground(risk.getTerritory(board, "western australia").getOwnerColor());
				indonesia.setText(Integer.toString(risk.getTerritory(board, "indonesia").armySize()));
				indonesia.setBackground(risk.getTerritory(board, "indonesia").getOwnerColor());
				newGuinea.setText(Integer.toString(risk.getTerritory(board, "new guinea").armySize()));
				newGuinea.setBackground(risk.getTerritory(board, "new guinea").getOwnerColor());
				
				risk.checkWhosTurn().setReinforcements(board);
				reinforcenum.setText(Integer.toString(risk.checkWhosTurn().getReinforcements()));
			}
		});
		
		placepiecebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(risk.checkWhosTurn().getReinforcements()>0) {
					if(Integer.parseInt(troopstoPlace.getText())<=risk.checkWhosTurn().getReinforcements()){//check if player has the available reinforcements to match what was requested
						//add troops here
						selectedTerr.addArmy(Integer.parseInt(troopstoPlace.getText()));
						risk.checkWhosTurn().placeReinforcements(Integer.parseInt(troopstoPlace.getText()));
						reinforcenum.setText(Integer.toString(risk.checkWhosTurn().getReinforcements()));
						alaska.setText(Integer.toString(risk.getTerritory(board, "alaska").armySize()));
						alaska.setBackground(risk.getTerritory(board, "alaska").getOwnerColor());
						alberta.setText(Integer.toString(risk.getTerritory(board, "alberta").armySize()));
						alberta.setBackground(risk.getTerritory(board, "alberta").getOwnerColor());
						centralAmerica.setText(Integer.toString(risk.getTerritory(board, "central america").armySize()));
						centralAmerica.setBackground(risk.getTerritory(board, "central america").getOwnerColor());
						eUnitedStates.setText(Integer.toString(risk.getTerritory(board, "eastern united states").armySize()));
						eUnitedStates.setBackground(risk.getTerritory(board, "eastern united states").getOwnerColor());
						Greenland.setText(Integer.toString(risk.getTerritory(board, "greenland").armySize()));
						Greenland.setBackground(risk.getTerritory(board, "greenland").getOwnerColor());
						nwTerritory.setText(Integer.toString(risk.getTerritory(board, "northwest territory").armySize()));
						nwTerritory.setBackground(risk.getTerritory(board, "northwest territory").getOwnerColor());
						ontario.setText(Integer.toString(risk.getTerritory(board, "ontario").armySize()));
						ontario.setBackground(risk.getTerritory(board, "ontario").getOwnerColor());
						quebec.setText(Integer.toString(risk.getTerritory(board, "quebec").armySize()));
						quebec.setBackground(risk.getTerritory(board, "quebec").getOwnerColor());
						wUnitedStates.setText(Integer.toString(risk.getTerritory(board, "western united states").armySize()));
						wUnitedStates.setBackground(risk.getTerritory(board, "western united states").getOwnerColor());
						venezuela.setText(Integer.toString(risk.getTerritory(board, "venezuela").armySize()));
						venezuela.setBackground(risk.getTerritory(board, "venezuela").getOwnerColor());
						brazil.setText(Integer.toString(risk.getTerritory(board, "brazil").armySize()));
						brazil.setBackground(risk.getTerritory(board, "brazil").getOwnerColor());
						peru.setText(Integer.toString(risk.getTerritory(board, "peru").armySize()));
						peru.setBackground(risk.getTerritory(board, "peru").getOwnerColor());
						argentina.setText(Integer.toString(risk.getTerritory(board, "argentina").armySize()));
						argentina.setBackground(risk.getTerritory(board, "argentina").getOwnerColor());
						greatBritain.setText(Integer.toString(risk.getTerritory(board, "great britain").armySize()));
						greatBritain.setBackground(risk.getTerritory(board, "great britain").getOwnerColor());
						iceland.setText(Integer.toString(risk.getTerritory(board, "iceland").armySize()));
						iceland.setBackground(risk.getTerritory(board, "iceland").getOwnerColor());
						nEurope.setText(Integer.toString(risk.getTerritory(board, "northern europe").armySize()));
						nEurope.setBackground(risk.getTerritory(board, "northern europe").getOwnerColor());
						scandinavia.setText(Integer.toString(risk.getTerritory(board, "scandinavia").armySize()));
						scandinavia.setBackground(risk.getTerritory(board, "scandinavia").getOwnerColor());
						sEurope.setText(Integer.toString(risk.getTerritory(board, "southern europe").armySize()));
						sEurope.setBackground(risk.getTerritory(board, "southern europe").getOwnerColor());
						ukraine.setText(Integer.toString(risk.getTerritory(board, "ukraine").armySize()));
						ukraine.setBackground(risk.getTerritory(board, "ukraine").getOwnerColor());
						wEurope.setText(Integer.toString(risk.getTerritory(board, "western europe").armySize()));
						wEurope.setBackground(risk.getTerritory(board, "western europe").getOwnerColor());
						congo.setText(Integer.toString(risk.getTerritory(board, "congo").armySize()));
						congo.setBackground(risk.getTerritory(board, "congo").getOwnerColor());
						eAfrica.setText(Integer.toString(risk.getTerritory(board, "east africa").armySize()));
						eAfrica.setBackground(risk.getTerritory(board, "east africa").getOwnerColor());
						egypt.setText(Integer.toString(risk.getTerritory(board, "egypt").armySize()));
						egypt.setBackground(risk.getTerritory(board, "egypt").getOwnerColor());
						madagascar.setText(Integer.toString(risk.getTerritory(board, "madagascar").armySize()));
						madagascar.setBackground(risk.getTerritory(board, "madagascar").getOwnerColor());
						nAfrica.setText(Integer.toString(risk.getTerritory(board, "north africa").armySize()));
						nAfrica.setBackground(risk.getTerritory(board, "north africa").getOwnerColor());
						sAfrica.setText(Integer.toString(risk.getTerritory(board, "south africa").armySize()));
						sAfrica.setBackground(risk.getTerritory(board, "south africa").getOwnerColor());
						afghanistan.setText(Integer.toString(risk.getTerritory(board, "afghanistan").armySize()));
						afghanistan.setBackground(risk.getTerritory(board, "afghanistan").getOwnerColor());
						china.setText(Integer.toString(risk.getTerritory(board, "china").armySize()));
						china.setBackground(risk.getTerritory(board, "china").getOwnerColor());
						india.setText(Integer.toString(risk.getTerritory(board, "india").armySize()));
						india.setBackground(risk.getTerritory(board, "india").getOwnerColor());
						irkutsk.setText(Integer.toString(risk.getTerritory(board, "irkutsk").armySize()));
						irkutsk.setBackground(risk.getTerritory(board, "irkutsk").getOwnerColor());
						japan.setText(Integer.toString(risk.getTerritory(board, "japan").armySize()));
						japan.setBackground(risk.getTerritory(board, "japan").getOwnerColor());
						kamchatka.setText(Integer.toString(risk.getTerritory(board, "kamchatka").armySize()));
						kamchatka.setBackground(risk.getTerritory(board, "kamchatka").getOwnerColor());
						middleEast.setText(Integer.toString(risk.getTerritory(board, "middle east").armySize()));
						middleEast.setBackground(risk.getTerritory(board, "middle east").getOwnerColor());
						mongolia.setText(Integer.toString(risk.getTerritory(board, "mongolia").armySize()));
						mongolia.setBackground(risk.getTerritory(board, "mongolia").getOwnerColor());
						siam.setText(Integer.toString(risk.getTerritory(board, "siam").armySize()));
						siam.setBackground(risk.getTerritory(board, "siam").getOwnerColor());
						siberia.setText(Integer.toString(risk.getTerritory(board, "siberia").armySize()));
						siberia.setBackground(risk.getTerritory(board, "siberia").getOwnerColor());
						ural.setText(Integer.toString(risk.getTerritory(board, "ural").armySize()));
						ural.setBackground(risk.getTerritory(board, "ural").getOwnerColor());
						yakutsk.setText(Integer.toString(risk.getTerritory(board, "yakutsk").armySize()));
						yakutsk.setBackground(risk.getTerritory(board, "yakutsk").getOwnerColor());
						eAustralia.setText(Integer.toString(risk.getTerritory(board, "eastern australia").armySize()));
						eAustralia.setBackground(risk.getTerritory(board, "eastern australia").getOwnerColor());
						wAustralia.setText(Integer.toString(risk.getTerritory(board, "western australia").armySize()));
						wAustralia.setBackground(risk.getTerritory(board, "western australia").getOwnerColor());
						indonesia.setText(Integer.toString(risk.getTerritory(board, "indonesia").armySize()));
						indonesia.setBackground(risk.getTerritory(board, "indonesia").getOwnerColor());
						newGuinea.setText(Integer.toString(risk.getTerritory(board, "new guinea").armySize()));
						newGuinea.setBackground(risk.getTerritory(board, "new guinea").getOwnerColor());
						
					}
					else {
						lblSelectAControlled.setText("<html>Not enough reinforcements<br/> to match request!</html>");
					}
				}else {
					lblSelectAControlled.setText("<html>You have no more<br/> reinforcements left to place!</html>");
				}
			}
		});
		
		btnTurnInSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel[] myhand = {card1, card2, card3, card4, card5, card6};
				JRadioButton[] mybuttons = {cardbtn1, cardbtn2, cardbtn3, cardbtn4, cardbtn5, cardbtn6};
				ArrayList<Card> selectedCards = new ArrayList<Card>();
				for(int i=0; i < risk.checkWhosTurn().cards.size(); i++) {
					if(mybuttons[i].isSelected()) {
						selectedCards.add(risk.checkWhosTurn().cards.get(i)); 
					}
				}
				if(selectedCards == null) {
					lblselectASet.setText("No cards selected!");
				}else if(selectedCards.size() != 3) {
					lblselectASet.setText("3 cards must be selected!");
				}
				else {
					if(risk.checkWhosTurn().checkCardSets(selectedCards)) {
						risk.checkWhosTurn().playCardSet(selectedCards);
						label_11.setText(Integer.toString(risk.checkWhosTurn().totalSets));
						for(int i=0; i < 6; i++) {
							if(i<risk.checkWhosTurn().cards.size()) {
								myhand[i].setIcon(risk.checkWhosTurn().cards.get(i).getCardIcon());//lay down and display the players cards
								myhand[i].setVisible(true);
								mybuttons[i].setVisible(true);
							}
							else {
								myhand[i].setVisible(false);//hides null spaces
								mybuttons[i].setVisible(false);
							}
						}
					}else {
						lblselectASet.setText("The selected were not a set!");
					}
				}
			}
		});
	}
}
