package model.game_modes;

import javax.swing.JOptionPane;

import model.ChessMove;
import model.board.Board;
import model.board.RectangularBoard;
import model.pieces.Bishop;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.pieces.Queen;
import model.pieces.Rook;
import controller.GameLoop;
import controller.Player;

/**
 * Defines a StandardGame, where the board is rectangular and
 * the king cannot be in check.
 * 
 * @author Nicholas
 *
 */
public class StandardGame implements GameMode {

	@Override
	public Board initPieces(Player player1, Player player2) {
		//Create a standard 8,8 board
		RectangularBoard board = new RectangularBoard(this);
		
		//if the direction is -1 set the pawns to row 6 otherwise set them to 1
		int p1PawnLoc = player1.getDirection() == -1 ? 6 : 1;
		int p2PawnLoc = player2.getDirection() == -1 ? 6 : 1;
		for (int i = 0;i < 8;i ++) {
			new Pawn(i, p1PawnLoc, board, player1);
			new Pawn(i, p2PawnLoc, board, player2);
		}
		
		//if the direction is -1 set the special pieces to row 7 otherwise set them to 0
		int p1pieceLoc = player1.getDirection() == -1 ? 7 : 0;
		int p2pieceLoc = player2.getDirection() == -1 ? 7 : 0;
		new Rook(0, p1pieceLoc, board, player1);
		new Rook(7, p1pieceLoc, board, player1);
		new Rook(0, p2pieceLoc, board, player2);
		new Rook(7, p2pieceLoc, board, player2);
		new Knight(1, p1pieceLoc, board, player1);
		new Knight(6, p1pieceLoc, board, player1);
		new Knight(1, p2pieceLoc, board, player2);
		new Knight(6, p2pieceLoc, board, player2);
		new Bishop(2, p1pieceLoc, board, player1);
		new Bishop(5, p1pieceLoc, board, player1);
		new Bishop(2, p2pieceLoc, board, player2);
		new Bishop(5, p2pieceLoc, board, player2);
		new Queen(4, p1pieceLoc, board, player1);
		new Queen(4, p2pieceLoc, board, player2);
		new King(3, p1pieceLoc, board, player1);
		new King(3, p2pieceLoc, board, player2);
		
		return board;
	}	

	/**
	 * In a standard game this board is inspected to make sure the king
	 * is not in check
	 * @returns true if the king is not in check, false otherwise;
	 */
	@Override
	public boolean boardValid(Board board, Player mover, ChessMove lastMove) {
		return !board.locationPressured(mover.getKing().getLocation(), mover);
	}
	
	@Override
	public boolean hasPlayerLost(Board board, Player victim) {
		return board.locationPressured(victim.getKing().getLocation(), victim);
	}

	@Override
	public void postMoveAction(final GameLoop gameController, ChessMove lastMove) {
		final Player currentPlayer = gameController.getCurrentPlayer();
		if (gameController.getBoard().locationPressured(currentPlayer.getKing().getLocation(), currentPlayer)) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					String message = currentPlayer.getPlayerName() + " is in check";
					JOptionPane.showMessageDialog(gameController.getCanvas(), message);
				}
			});
			t.start();
		}	
	}
	
}
