package model.pieces;

import java.util.ArrayList;

import model.ChessMove;
import model.ChessPiece;
import model.board.Board;
import controller.Player;

/**
 * Represents a bishop
 * 
 * @author Nicholas
 *
 */
public class Bishop extends ChessPiece {

	public Bishop(int x, int y, Board _board, Player _player) {
		super(x, y, _board, _player);
	}

	/**
	 * Returns all the diagonal moves that the bishop can capture/move
	 */
	@Override
	public ArrayList<ChessMove> getPossibleMoves() {
		//return all diagonal moves, including capturables
		return getDiagonalMoves(true, true);
	}

	@Override
	public String getType() {
		return "Bishop";
	}

}
