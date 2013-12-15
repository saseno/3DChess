package model.game_modes;

import model.ChessMove;
import model.ChessPiece;
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

public class LosersGameMode implements GameMode {

	@Override
	public Board initPieces(Player player1, Player player2) {
		// Create a standard 8,8 board
		RectangularBoard board = new RectangularBoard(this);

		// if the direction is -1 set the pawns to row 6 otherwise set them to 1
		int p1PawnLoc = player1.getDirection() == -1 ? 6 : 1;
		int p2PawnLoc = player2.getDirection() == -1 ? 6 : 1;
		for (int i = 0; i < 8; i++) {
			new Pawn(i, p1PawnLoc, board, player1);
			new Pawn(i, p2PawnLoc, board, player2);
		}

		// if the direction is -1 set the special pieces to row 7 otherwise set
		// them to 0
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

	@Override
	public boolean boardValid(Board board, Player victim, ChessMove lastMove) {
		if (lastMove.getCapturedPiece() != null)
			return true;
		
		lastMove.undoMove();
		for (ChessPiece p : victim.getPieces()) {
			if (!p.getCaptureMoves().isEmpty()) {
				lastMove.executeMove(false);
				return false;
			}
		}
			
		return true;
	}

	@Override
	public boolean hasPlayerLost(Board board, Player victim) {
		return victim.getPieces().isEmpty();
	}

	@Override
	public void postMoveAction(final GameLoop gameController, ChessMove lastMove) {
		// TODO Auto-generated method stub
		
	}

}
