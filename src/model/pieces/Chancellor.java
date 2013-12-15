package model.pieces;

import java.awt.Point;
import java.util.ArrayList;

import model.ChessMove;
import model.ChessPiece;
import model.board.Board;
import controller.Player;

/**
 * Represents a Chancellor. A Chancellor is a hybrid between
 * a rook and a knight.
 * 
 * @author Nicholas
 *
 */
public class Chancellor extends ChessPiece {

	public Chancellor(int x, int y, Board _board, Player _player) {
		super(x, y, _board, _player);
	}

	/**
	 * Returns all the diagonal moves that the Chancellor can capture/move
	 * and all the knigh-type moves this piece can capture/move to
	 */
	@Override
	public ArrayList<ChessMove> getPossibleMoves() {
		ArrayList<ChessMove> moves = getRankFileMoves(true, true);
		for (Point loc : board.getKnightMoves(location.x, location.y)) {
			ChessMove move = new ChessMove(loc, this);
			if (board.isMovableTile(loc, player, true))
				moves.add(move);
		}
		
		return moves;
	}

	@Override
	public String getType() {
		return "Chancellor";
	}

}
