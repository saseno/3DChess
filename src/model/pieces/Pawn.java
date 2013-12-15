package model.pieces;

import java.awt.Point;
import java.util.ArrayList;

import model.ChessMove;
import model.ChessPiece;
import model.board.Board;
import controller.Player;

/**
 * Represents a Pawn. Pawn moving positions are taken
 * directly from the Board object which is responsible
 * for providing the moving directions of a pawn
 * 
 * @author Nicholas
 *
 */
public class Pawn extends ChessPiece {

	public Pawn(int x, int y, Board _board, Player _player) {
		super(x, y, _board, _player);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns the possible moves this pawn can move. 
	 * NOTE: the class that inherits from board is responsible
	 * for checking to see if the pawn has moved. 
	 */
	@Override
	public ArrayList<ChessMove> getPossibleMoves() {
		ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
		//cycle through the moves, should just be 2 or 1, depending if it has moved already
		for (Point loc : board.getPawnMoves(location.x, location.y, this)) {
			ChessMove move = new ChessMove(loc, this);
			if (board.isMovableTile(loc, player, false))
				moves.add(move);
			else 
				break;
		}
		
		for (Point loc : board.getPawnAttacks(location.x, location.y, this)) {
			//cycke throught the attack locations, should just be 2
			ChessMove move = new ChessMove(loc, this);
			if (board.hasEnemyPiece(loc, player))
				moves.add(move);
		}
			
		return moves;
	}

	@Override
	public String getType() {
		return "Pawn";
	}

}
