package tests;

import model.game_modes.LosersGameMode;
import view.GameFrame;
import controller.GameLoop;

public class LosersGameTest {

	public static void main(String[] args) {
		GameFrame chessFrame = new GameFrame();
		GameLoop game = chessFrame.getGameController();
		game.setupNewGame(new LosersGameMode());
	}
	
}
