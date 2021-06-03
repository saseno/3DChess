package model;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import model.board.Board;
import view.loaders.AssetLoader;
import controller.Player;
import controller.util.Animation.Animatable;

/**
 * Basic template for any ChessPiece, which provides the basic
 * methods to move the Piece.
 * 
 * @author Nicholas
 *
 */
public abstract class ChessPiece implements Animatable {

	private float drawLocationX;
	private float drawLocationY;
	
	/**
	 * The board location of the piece
	 */
	protected Point location;
	
	/**
	 * If the piece has been used yet
	 */
	protected boolean hasMoved;
	
	/**
	 * The player owning this piece
	 */
	protected Player player;
	
	/**
	 * Reference to the board
	 */
	protected Board board;
	
	/**
	 * Initializes the basic variables of a chess piece and
	 * adds it to the board
	 *  
	 * @param x
	 * @param y
	 * @param _board
	 * @param _player
	 */
	public ChessPiece(int x, int y, Board _board, Player _player) {
		location = new Point(x, y);
		Point2D.Float drawLoc = _board.getRenderPosition(location);
		drawLocationX = drawLoc.x;
		drawLocationY = drawLoc.y;
		
		board = _board;
		player = _player;
		board.setTile(this, x, y);
		player.addPiece(this);
		hasMoved = false;
	}
	
	/**
	 * Used by pieces such as the rook, queen and king to get the moves
	 * in the rank-file directions of the boards. Will only return all the moves 
	 * up to (and including) an enemy piece and as long as the piece remains valid
	 * 
	 * @param cont true returns the list of all moves in the flat directions
	 * @param canCapture true if this piece can capture
	 * @return an array of the valid moves possible in the flat direction
	 */
	protected ArrayList<ChessMove> getRankFileMoves(boolean cont, boolean canCapture) {
		ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
		ArrayList<Point> adj = board.getAdjacentRankFileTiles(location.x, location.y);
		Point currentPos;
		for (int i = 0;i < adj.size();i ++) {
			currentPos = adj.get(i);
			while (board.isMovableTile(currentPos, player, canCapture)) {
				ChessMove move = new ChessMove(currentPos, this);
				moves.add(move);
				
				if (board.hasEnemyPiece(currentPos, player))
					break;
				
				if (!cont) break;
				currentPos = board.getAdjacentRankFileTiles(currentPos).get(i);
			}
		}
		return moves;
	}
	
	/**
	 * Used by pieces such as the bishop, queen and king to get the moves
	 * in the diagonal directions of the boards. Will only return all the moves 
	 * up to (and including) an enemy piece and as long as the piece remains valid
	 * 
	 * @param cont true returns the list of all moves in the diagonal directions
	 * @param canCapture true if this piece can capture
	 * @return an array of the valid moves possible in the diagonal direction
	 */
	protected ArrayList<ChessMove> getDiagonalMoves(boolean cont, boolean canCapture) {
		ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
		ArrayList<Point> adj = board.getAdjacentDiagonalTiles(location.x, location.y);
		Point currentPos;
		for (int i = 0;i < adj.size();i ++) {
			currentPos = adj.get(i);
			while (board.isMovableTile(currentPos, player, canCapture)) {
				ChessMove move = new ChessMove(currentPos, this);
				moves.add(move);
				
				if (board.hasEnemyPiece(currentPos, player))
					break;
				
				if (!cont) break;
				currentPos = board.getAdjacentDiagonalTiles(currentPos).get(i);
			}
		}
		return moves;
	}
	
	/**
	 * Moves this piece to the position, while internally checking
	 * if the moves is valid (by comparing the given location to possible moves)
	 * 
	 * @throws IllegalArgumentException if this position cannot move to the given location
	 * @param loc the location to move to 
	 * @param checkValid 
	 * @return the ChessPiece that was at the position original
	 */
	public ChessPiece movePieceToTile(Point loc, boolean checkValid) {
		if (!checkValid || getValidMoves().contains(new ChessMove(loc, this))) {
			if (checkValid)
				hasMoved = true;
			
			Point2D.Float drawLoc = board.getRenderPosition(loc);
			drawLocationX = drawLoc.x;
			drawLocationY = drawLoc.y;
			return board.setTile(this, loc.x, loc.y);
		}
		
		throw new IllegalArgumentException("Bad Position");
	}
	
	/**
	 * Overridden by all chess piece classes, which returns an Array of chess
	 * moves that this peice can make 
	 * 
	 * @return An array of all the possible moves this piece can make
	 */
	public abstract ArrayList<ChessMove> getPossibleMoves();
	
	/**
	 * Subset of getPossibleMoves() which returns all the moves that 
	 * result in a capture
	 * 
	 * @return An array of all the possible moves this piece can make
	 *  that result in it taking a piece.
	 */
	public ArrayList<ChessMove> getCaptureMoves() {
		ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
		for (ChessMove move : getPossibleMoves()) //loop through all moves...
			if (board.hasEnemyPiece(move.getMoveLocation(), player))
				moves.add(move); //add it
		
		return moves;
	}
	
	/**
	 * Subset of getPossibleMoves() which contains all the moves which 
	 * are valid to the type of game mode. For example, getPossibleMoves()
	 * for a king would return the 8 squares around it, while getValidMoves()
	 * would only return the moves that don't put this king in check (for a standard game)
	 * 
	 * @return An array of valid chess moves
	 */
	public ArrayList<ChessMove> getValidMoves() {
		ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
		for (ChessMove move : getPossibleMoves())
			if (board.isValidMove(move))
				moves.add(move);
		
		return moves;
	}
	
	/**
	 * Used to determine a pieces type without using reflection (ie instanceof)
	 * 
	 * @return A string that contains the name of the piece, ie the Pawn
	 * class would return "Pawn"
	 */
	public abstract String getType();
	
	public void setHasMoved(boolean hadMoved) {
		hasMoved = hadMoved;
	}
	
	/**
	 * @return true if this piece has moved
	 */
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	/**
	 * @return the owning player
	 */
	public Player getPlayer() { 
		return player;
	}
	
	/**
	 * Sets this piece's board location to _location. NOTE: Doe's NOT change hasMoved
	 * 
	 * @param _location
	 */
	public void setLocation(Point _location) {
		location.setLocation(_location);
	}

	/**
	 * @return Point representing the board location of this object
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Renders this piece to the current openGL context. The drawX and
	 * drawY are provided by the board, while the model is retrieved from 
	 * the AssetLoader class. The model used is just the name of the piece
	 * (ie getType())
	 * 
	 * @param gl
	 * @param drawX
	 * @param drawZ
	 */
	public void render(GL2 gl) {
		AssetLoader.getInstance().bindTexture(gl, player.getColorTexture());
		
		gl.glPushMatrix();
			gl.glTranslatef(drawLocationX, 0, drawLocationY);
			AssetLoader.getInstance().getModel(getType()).render(gl);
		gl.glPopMatrix();
	}
	
	/**
	 * So that this piece can be animatable, it must overload this method.
	 * Two fields can be animated in the class, drawLocationX, and drawLocationY
	 * which are the positions this piece will be rendered on the screen.
	 * 
	 */
	@Override
	public void setValue(String fieldName, float value) {
		if (fieldName.equals("drawLocationX"))
			drawLocationX = value;
		else if (fieldName.equals("drawLocationY"))
			drawLocationY = value;
	}
	
}
