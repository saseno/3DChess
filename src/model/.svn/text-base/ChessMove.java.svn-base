package model;

import java.awt.Point;

/**
 * Basic data object for holding a possible chess move. A chess
 * move is made up of 2 pieces of data, the piece that is moving, 
 * and the location the piece will move to
 * 
 * 
 * @author Nicholas
 *
 */
public class ChessMove {

	/**
	 * Original location
	 */
	private Point originalLocation;
	
	/**
	 * If this piece had moved yet
	 */
	private boolean hadMoved;
	
	/**
	 * Location piece may move
	 */
	private Point moveLocation;
	
	/**
	 * Piece that is moving
	 */
	private ChessPiece piece;
	
	/**
	 * Piece that may have been captured by the move, can be null
	 */
	private ChessPiece capturedPiece;
	
	/**
	 * @param _moveLocation The location being moved to
	 * @param _piece The piece being moved
	 */
	public ChessMove(Point _moveLocation, ChessPiece _piece) {
		originalLocation = new Point(_piece.getLocation());
		moveLocation = _moveLocation;
		piece = _piece;
	}
	
	public boolean equals(Object obj) {
		ChessMove move = (ChessMove) obj;
		return move.moveLocation.equals(moveLocation) &&
				move.originalLocation.equals(originalLocation) &&
				move.piece == piece;
	}
	
	/**
	 * Executes the move if it has not been done yet. Calling this
	 * method assumes the move has been checked as valid, otherwise an
	 * exception will be thrown. After this move has been completed, 
	 * caputedPiece will be set with the piece that was originally on the tile
	 */
	public void executeMove(boolean checkValid) {
		if (piece.getLocation().equals(moveLocation)) //we have already done the move...
			return;
		
		hadMoved = piece.getHasMoved();
		capturedPiece = piece.movePieceToTile(moveLocation, checkValid);
		if (capturedPiece != null) 
			capturedPiece.getPlayer().pieceCaptured(capturedPiece);	
	}
	
	/**
	 * Undoes the move if has not been undone yet. The piece
	 * is moved back to its original position, and if there was
	 * a piece there already, it is replaced
	 * 
	 */
	public void undoMove() {
		if (piece.getLocation().equals(originalLocation)) //we have already undone
			return;
		
		piece.movePieceToTile(originalLocation, false);
		piece.setHasMoved(hadMoved);
		if (capturedPiece != null) {
			capturedPiece.getPlayer().pieceUncaptured(capturedPiece);	
			capturedPiece.movePieceToTile(moveLocation, false);
			capturedPiece = null;
		}
	}
	
	/* 
	 * ------------------------------
	 * Getters and Setters start here
	 * ------------------------------
	 */

	public Point getStartLocation() {
		return originalLocation;
	}
	
	public Point getMoveLocation() {
		return moveLocation;
	}
	
	public ChessPiece getPiece() {
		return piece;
	}

	public Object getCapturedPiece() {
		return capturedPiece;
	}
	
}
