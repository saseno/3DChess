package model.pieces;

import java.util.ArrayList;

import model.ChessMove;
import model.ChessPiece;
import model.board.Board;
import controller.Player;

/**
 * Represents the King, which in a Standard game results in a
 * loss if captured. Depending on the game a king may not always be
 * important (such as in suicide) so the option to validate check
 * must be passed in, which is used to determine if the king can move into
 * check situation
 * 
 * @author Nicholas
 *
 */
public class King extends ChessPiece {
	
	public King(int x, int y, Board _board, Player _player) {
		super(x, y, _board, _player);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns all the diagonal/adjacent moves that the king can capture/move to
	 * 
	 * NOTE: the list of possible moves includes moves that will put this King in check,
	 * (if the game allows). This is intentional, if you want a list of valid moves (ie 
	 * moves that conform the the current GameMode's rules) call getValidMoves()
	 */
	@Override
	public ArrayList<ChessMove> getPossibleMoves() {
		//get immediate adjacent moves
		ArrayList<ChessMove> moves = getDiagonalMoves(false, true);
		//get immediate diagonal moves
		moves.addAll(getRankFileMoves(false, true));
		return moves;
	}

	@Override
	public String getType() {
		return "King";
	}

}
