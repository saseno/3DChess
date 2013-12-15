package model.pieces;

import java.util.ArrayList;

import model.ChessMove;
import model.ChessPiece;
import model.board.Board;
import controller.Player;

/**
 * Represents a Rook
 * 
 * @author Nicholas
 *
 */
public class Rook extends ChessPiece {

	public Rook(int x, int y, Board _board, Player _player) {
		super(x, y, _board, _player);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns all the rank-file moves that the bishop can capture/move
	 */
	@Override
	public ArrayList<ChessMove> getPossibleMoves() {
		return getRankFileMoves(true, true);
	}

	@Override
	public String getType() {
		return "Rook";
	}

}
