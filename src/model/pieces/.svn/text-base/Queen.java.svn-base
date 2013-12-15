package model.pieces;

import java.util.ArrayList;

import model.ChessMove;
import model.ChessPiece;
import model.board.Board;
import controller.Player;

/**
 * Represents a Queen
 * 
 * @author Nicholas
 *
 */
public class Queen extends ChessPiece {

	public Queen(int x, int y, Board _board, Player _player) {
		super(x, y, _board, _player);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns all the diagonal and rank-file moves that the queen can capture/move
	 */
	@Override
	public ArrayList<ChessMove> getPossibleMoves() {
		ArrayList<ChessMove> moves = getRankFileMoves(true, true);
		moves.addAll(getDiagonalMoves(true, true));
		return moves;
	}

	@Override
	public String getType() {
		return "Queen";
	}

}
