package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import controller.GameLoop;
import controller.Player;

/**
 * Menu bar for the Chess game. Contains one drop down menu
 * with 3 options: restart, forfeit black, forfeit white.
 * All options trigger a confirm dialog as well.
 * 
 * @author Nicholas
 *
 */
public class GameMainMenu extends JMenuBar implements ActionListener {

	private GameLoop gameController;
	
	private GameFrame gameFrame;
	
	private JMenuItem restart;
	private JMenuItem forfietBlack;
	private JMenuItem forfietWhite;
	
	public GameMainMenu(GameFrame frame, GameLoop controller) {
		gameFrame = frame;
		gameController = controller;
		JMenu mainMenu = new JMenu("Game");
		mainMenu.add(restart = new JMenuItem("Restart"));
		restart.addActionListener(this);
		mainMenu.add(forfietBlack = new JMenuItem("Forfiet Black"));
		forfietBlack.addActionListener(this);
		mainMenu.add(forfietWhite = new JMenuItem("Forfiet White"));
		forfietWhite.addActionListener(this);
		add(mainMenu);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == restart) { //if restart was selected
			Player p1 = gameController.getPlayer1();
			int result = JOptionPane.showConfirmDialog(gameFrame, 
					"Are you sure you wish to restart, " + p1.getPlayerName() + "?",
					"Restart?",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.OK_OPTION) { //player 1 agreed
				Player p2 = gameController.getPlayer2();
				result = JOptionPane.showConfirmDialog(gameFrame, 
					"Are you sure you wish to restart, " + p2.getPlayerName() + "?",
					"Restart?",
					JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) //player 2 agreed
					gameController.cancelGame(null);
			}		
		} else if (arg0.getSource() == forfietWhite || arg0.getSource() == forfietBlack) {
			int result = JOptionPane.showConfirmDialog(gameFrame, 
							"Are you sure you wish to forfiet?",
							"Forfiet?",
							JOptionPane.YES_NO_OPTION);
			
			if (result == JOptionPane.OK_OPTION) {
				if (arg0.getSource() == forfietBlack)
					gameController.cancelGame(gameController.getPlayer1());
				else 
					gameController.cancelGame(gameController.getPlayer2());
			}
		}
	}
	
}
