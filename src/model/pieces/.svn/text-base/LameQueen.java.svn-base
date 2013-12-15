package model.pieces;

import java.util.ArrayList;

import model.ChessMove;
import model.ChessPiece;
import model.board.Board;
import controller.Player;

/**
 * Represents a LameQueen. A LameQueen can move in the 
 * same directions as a queen (diagonal and rank-file) but
 * can only take pieces on the diagonal.
 * 
 * @author Nicholas
 *
 */
public class LameQueen extends ChessPiece {

	public LameQueen(int x, int y, Board _board, Player _player) {
		super(x, y, _board, _player);
	}

	/**
	 * Returns all the diagonal moves that the LameQueen can capture/move
	 * and the rank-file moves that the queen can move
	 */
	@Override
	public ArrayList<ChessMove> getPossibleMoves() {
		//add diagonal
		ArrayList<ChessMove> moves = this.getDiagonalMoves(true, true);
		//add rank-file
		moves.addAll(getRankFileMoves(true, false));
		return moves;
	}

	@Override
	public String getType() {
		return "LameQueen";
	}

}
