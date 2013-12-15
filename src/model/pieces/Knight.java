package model.pieces;

import java.awt.Point;
import java.util.ArrayList;

import model.ChessMove;
import model.ChessPiece;
import model.board.Board;
import controller.Player;

/**
 * Represents a Knight
 * 
 * @author Nicholas
 *
 */
public class Knight extends ChessPiece {

	public Knight(int x, int y, Board _board, Player _player) {
		super(x, y, _board, _player);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns all the moves this knight can make in
	 * it's current position
	 */
	@Override
	public ArrayList<ChessMove> getPossibleMoves() {
		ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
		//loop through all possible moves given by board
		for (Point loc : board.getKnightMoves(location.x, location.y)) {
			ChessMove move = new ChessMove(loc, this);
			if (board.isMovableTile(loc, player, true)) //if its a valid move...
				moves.add(move);
		}
				
		return moves;
	}

	@Override
	public String getType() {
		return "Knight";
	}

}
