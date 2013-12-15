package model.board;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.media.opengl.GL2;

import model.ChessMove;
import model.ChessPiece;
import model.game_modes.GameMode;
import model.pieces.Pawn;
import view.loaders.structures.Model;
import controller.Player;

/**
 * Abstract board class that is responsible for all the operations
 * on a chess board. The board is responsible for determining if
 * moves are valid, as well as rendering the whole scene.
 * 
 * @author Nicholas
 *
 */
public abstract class Board {
	
	/**
	 * Reference to the gameMode
	 */
	protected GameMode gameMode;
	
	protected Board(GameMode _gameMode) {
		gameMode = _gameMode;
	}
	
	/**
	 * Generates the 3d model for the board, so that it may be added as 
	 * a model to AssetLoader class. NOTE: this method is called automatically by
	 * the GameLoop during setup and should NEVER be called again. The proper way
	 * to access this Model would be AssetLoader.getInstace().getModel(getType())
	 * 
	 * @return the generated board model
	 */
	public abstract Model generateBoardModel();
	
	/**
	 * Generates the 3d model for the board, so that it may be added as 
	 * a model to AssetLoader class. NOTE: this method is called automatically by
	 * the GameLoop during setup and should NEVER be called again. The proper way
	 * to access this Model would be AssetLoader.getInstace().getModel(getType())
	 * 
	 * @return the generated board model
	 */
	public abstract Model generateTileModel();
	
	/**
	 * String used to identify the type of board. Also used as a unique 
	 * model name
	 * 
	 * @return String used to identify the type of board
	 */
	public abstract String getType();
	
	/**
	 * Converts the given tile location into 3D point, that is scaled
	 * to the boards size 
	 * 
	 * @param loc tile location
	 * @return 3D representation of loc
	 */
	public abstract Point2D.Float getRenderPosition(Point loc);
	
	/**
	 * Converts the given 3d location into a tile location,
	 * which can be used to access items on the board
	 * 
	 * @param renderPoint 3d point
	 * @return tile point representation of loc
	 */
	public abstract Point getBoardPosition(Point2D.Float renderPoint);
	
	/**
	 * Returns the piece at the given coordinates, as interpreted by the Board object.
	 * 
	 * @param x
	 * @param y
	 * @return the ChessPiece at the position, null otherwise
	 */
	public abstract ChessPiece getTile(int x, int y);
	
	
	/**
	 * Sets the piece to the (x, y) position, and sets the internal
	 * boardLocation of the piece. If a piece already existed it is removed
	 * from the board, location set to (-1, -1) and returned
	 * 
	 * @param piece Piece to be moved
	 * @param x Board location x
	 * @param y Board location y
	 * @return the piece that was occupying this tile, null otherwise
	 */
	public abstract ChessPiece setTile(ChessPiece piece, int x, int y);
	
	
	/**
	 * @param x Board location x
	 * @param y Board location y
	 * @return true if the given location is within the boards dimensions, false
	 *  otherwise 
	 */
	public abstract boolean isInBounds(int x, int y);
	
	/**
	 * @param x Board location x
	 * @param y Board location y
	 * @param attacker The player making the move
	 * @return true if the tile at (x, y) contains a piece of the opposing player
	 */
	public abstract boolean hasEnemyPiece(int x, int y, Player attacker);
	
	/**
	 * @param x Board location x
	 * @param y Board location y
	 * @param attacker The player making the move
	 * @param canCapture If a piece is able to capture on the the tile
	 * @return true if the space is open (ie no piece is on the tile) or if the 
	 *   tile contains an enemy piece, and the attacker can capture it 
	 */
	public abstract boolean isMovableTile(int x, int y, Player attacker, boolean canCapture);
	
	/**
	 * This method on a rectangular board return 4 positions (left, right, top, bottom)
	 * and on a hexagonal 6. This method is best thought of as all the directions a rook
	 * would travel. NOTE: these locations may not be in bounds, and must be checked
	 * accordingly
	 * 
	 * @param x Board location x
	 * @param y Board location y
	 * @return an array of all the locations adjacent to the given location
	 */
	public abstract ArrayList<Point> getAdjacentRankFileTiles(int x, int y);
	
	/**
	 * This method on a rectangular board return 4 positions (top-left, top-right, 
	 * bottom-left, bottom-right) and on a hexagonal 5. This method is best thought of as all the 
	 * directions a bishop would travel. NOTE: these locations may not be in bounds, and must be checked
	 * accordingly.
	 * 
	 * @param x Board location x
	 * @param y Board location y
	 * @return an array of all the locations diagonal to the given location
	 */
	public abstract ArrayList<Point> getAdjacentDiagonalTiles(int x, int y);
	
	/**
	 * This method on a rectangular board returns 8 positions that form an L shape, and
	 * 12 positions on a hexagonal board. NOTE: these locations may not be in bounds,
	 * and must be checked accordingly
	 * 
	 * @param x Board location x
	 * @param y Board location y
	 * @return an array of all the positions a knight at (x, y) can travel on this board
	 */
	public abstract ArrayList<Point> getKnightMoves(int x, int y);
	
	/**
	 * This method on a rectangular board returns 2 positions if the pawn has not yet moved 
	 * (the 2 spaces directly in from of the pawn), or 1 move if the pawn has moved
	 * The Pawn class is responsible for determining if these locations are valid
	 * (ie only one location is valid if it already moved). NOTE: these locations may not be in bounds,
	 * and must be checked accordingly
	 * 
	 * @param x Board location x
	 * @param y Board location y
	 * @return an array of all the positions a pawn at (x, y) can travel on this board
	 */
	public abstract ArrayList<Point> getPawnMoves(int x, int y, Pawn pawn);
	
	/**
	 * This method on a rectangular board returns 2 positions, on a rectangular board
	 * these are the 2 diagonals in front, while on a hex board they are the top-left and
	 * top-right adjacent tiles.
	 * 
	 * @param x Board location x
	 * @param y Board location y
	 * @return an array of all the positions a pawn at (x, y) can attack on this board
	 */
	public abstract ArrayList<Point> getPawnAttacks(int x, int y, Pawn pawn);
	
	/**
	 * Same as {@link #isInBounds(int, int) isInBounds}
	 * @param loc Board location point
	 */
	public boolean isInBounds(Point loc) {
		return isInBounds(loc.x, loc.y);
	}
	
	/**
	 * Same as {@link #hasEnemyPiece(int, int) isAttackable}
	 * @param loc Board location point
	 */
	public boolean hasEnemyPiece(Point loc, Player attacker) {
		return hasEnemyPiece(loc.x, loc.y, attacker);
	}
	
	/**
	 * Same as {@link #isMovableTile(int, int, Player, boolean) isMovableTile}
	 * @param loc Board location point
	 */
	public boolean isMovableTile(Point loc, Player player, boolean canCapture) {
		return isMovableTile(loc.x, loc.y, player, canCapture);
	}

	/**
	 * Same as {@link #getAdjacentRankFileTiles(int, int) getAdjacentFlatTiles}
	 * @param loc Board location point
	 */
	public ArrayList<Point> getAdjacentRankFileTiles(Point loc) {
		return getAdjacentRankFileTiles(loc.x, loc.y);
	}
	
	/**
	 * Same as {@link #getAdjacentDiagonalTiles(int, int) getAdjacentDiagonalTiles}
	 * @param loc Board location point
	 */
	public ArrayList<Point> getAdjacentDiagonalTiles(Point loc) {
		return getAdjacentDiagonalTiles(loc.x, loc.y);
	}
	
	/**
	 * @param x Board location x
	 * @param y Board location y
	 * @param victim The player who's piece will be taken
	 * @return true if the victim's piece at (x, y) can be taken 
	 *  by any of the opponents pieces
	 */
	public boolean locationPressured(int x, int y, Player victim) {
		for (ChessPiece oppPiece : victim.getOtherPlayer().getPieces()) {
			for (ChessMove move : oppPiece.getCaptureMoves())
				if (move.getMoveLocation().equals(new Point(x, y)))
					return true;
		}
		return false;
	}
	
	/**
	 * Same as {@link #locationPressured(int, int, Player) locationCapturable}
	 * @param loc Board location point
	 * @param victim The player who's piece will be taken
	 */
	public boolean locationPressured(Point loc, Player victim) {
		return locationPressured(loc.x, loc.y, victim);
	}
	
	/**
	 * Determines if this move is valid in the context of the game mode. For
	 * example a move that puts a king in check is not valid. This method simulates
	 * the move, then asks the gameMode to determine if the board is in a valid
	 * state. The board is then reset to the original state and the method returns
	 * the game mode's response
	 * 
	 * @param move
	 * @return true if the move is valid in the context of the gameMode;
	 */
	public boolean isValidMove(ChessMove move) {
		/* Works by simulating the chess move an calling gameMode.boardValid()
		 * The board will then be set back to its original state
		 */
		move.executeMove(false);
		boolean retVal = gameMode.boardValid(this, move.getPiece().getPlayer(), move);
		move.undoMove();
		return retVal;
	}
	
	/**
	 * Returns all the valid moves for the player, see 
	 * {@link #isValidMove(ChessMove) isValidMove} for the definition 
	 * on a valid move
	 * 
	 * @param player
	 * @return an array of all valid moves
	 */
	public ArrayList<ChessMove> getAllMoves(Player player) {
		ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
		for (ChessPiece piece : player.getPieces()) 
			moves.addAll(piece.getValidMoves());
		
		return moves;
	}
	
	
	/**
	 * Checks to see if their are any valid moves that 
	 * can be made
	 * 
	 * @param victim
	 * @return true if there are any valid moves, false otherwise
	 */
	public boolean noPossibleMoves(Player victim) { 
		return getAllMoves(victim).isEmpty();
	}
	
	/**
	 * Renders the board with the given opengl context. This 
	 * methods renders the board's mesh, and all the pieces on the board,
	 * by repeated calls to ChessPiece.render(gl);
	 * 
	 * @param gl
	 * @param selectedPiece 
	 */
	public abstract void render(GL2 gl, ChessPiece selectedPiece);
	
}