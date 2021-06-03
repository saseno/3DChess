package tests;

import java.awt.Point;

import com.jogamp.opengl.awt.GLCanvas;

import model.ChessMove;
import model.game_modes.StandardGame;
import view.GameFrame;
import controller.GameLoop;

/**
 * Simulates a (short) chess game that results in black checkmated.
 * Demonstrates the GUI.
 * 
 * @author Nicholas
 *
 */
public class StandardGameSimulationTest {
	
	//Convenience method for doing a simulated move, (startPoint, endPoint, game, cancas, timeafter)
	private static void doMove(int xs, int ys, int xe, int ye, GameLoop game, GLCanvas canvas, int seconds) {
		try {
			Thread.sleep(1000 * seconds);
			ChessMove move = new ChessMove(new Point(xe, ye), game.getBoard().getTile(xs, ys));
			game.executeMove(move);
			canvas.display();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		GameFrame chessFrame = new GameFrame();
		GLCanvas glcanvas = chessFrame.getCanvas();
		GameLoop game = chessFrame.getGameController();
		game.setupNewGame(new StandardGame());
		
		final int SECONDS = 2;
		System.out.println(game.getBoard().getAllMoves(game.getPlayer1()).size() + " moves left for white");
		doMove(3, 1, 3, 3, game, glcanvas, SECONDS); //move white pawn
		System.out.println(game.getBoard().getAllMoves(game.getPlayer2()).size() + " moves left for black");
		doMove(3, 6, 3, 4, game, glcanvas, SECONDS); //move black pawn
		System.out.println(game.getBoard().getAllMoves(game.getPlayer1()).size() + " moves left for white");
		
		doMove(4, 0, 2, 2, game, glcanvas, SECONDS); //move white queen
		System.out.println(game.getBoard().getAllMoves(game.getPlayer2()).size() + " moves left for black");
		doMove(7, 6, 7, 5, game, glcanvas, SECONDS); //move black pawn
		System.out.println(game.getBoard().getAllMoves(game.getPlayer1()).size() + " moves left for white");
		
		doMove(2, 0, 5, 3, game, glcanvas, SECONDS); //move white bishop
		System.out.println(game.getBoard().getAllMoves(game.getPlayer2()).size() + " moves left for black");
		doMove(7, 5, 7, 4, game, glcanvas, SECONDS); //move black pawn again...
		System.out.println(game.getBoard().getAllMoves(game.getPlayer1()).size() + " moves left for white");
		
		doMove(2, 2, 2, 6, game, glcanvas, SECONDS); //move white queen for checkmate
		System.out.println(game.getBoard().getAllMoves(game.getPlayer2()).size() + " moves left for black");
		
	}
	
	
}
