package controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import model.ChessMove;
import model.ChessPiece;
import model.board.Board;
import view.GameCamera;
import view.Renderer;

/**
 * Handles any Keystrokes or Mouse-clicks that occur
 * within the chess frame. The methods interprets the clicks, and
 * passes along commands to the GameLoop or Renderer
 * 
 * @author Nicholas
 *
 */
public class InputHandler implements KeyListener, MouseListener {

	/**
	 * References to the GameLoop and Renderer
	 */
	private GameLoop gameController;
	private Renderer renderer;
	
	public InputHandler(GameLoop _gameController, Renderer _renderer) {
		gameController = _gameController;
		renderer = _renderer;
	}
	
	/**
	 * This method interprets a click within the chess window.
	 * It uses the camera, to find where that click translates into 
	 * 3D space (ie where it intersects the board). This method handles the cases
	 * where there is already of selected a piece, and what to do with that piece
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		//get camera
		GameCamera camera = renderer.getCamera();
		Point2D.Float fpoint = new Point2D.Float();
		fpoint.x = arg0.getPoint().x;
		fpoint.y = arg0.getPoint().y;
		Board board = gameController.getBoard();
		//get the location where the click location intersects the 3D board
		Point clickLoc = board.getBoardPosition(camera.getClick(fpoint, renderer));
		//get the chess peice at that tile
		ChessPiece piece = board.getTile(clickLoc.x, clickLoc.y);
		if (renderer.getSelectedPiece() != null) { //if a piece is not selected yet...
			//create a ChessMove object that would move the selected piece to the selected location
			ChessMove move = new ChessMove(clickLoc, renderer.getSelectedPiece());
			if (renderer.getSelectedPiece().getValidMoves().contains(move)) { //if this ChessMove is valid for the piece...
				gameController.executeMove(move);  //execute the move
				renderer.setSelectedPiece(null); //piece is no longer selected
			} else if (renderer.getSelectedPiece() == piece) { //if we selected the piece already selected...
				renderer.setSelectedPiece(null); //piece is no longer selected
			} else if (piece != null && piece.getPlayer() == gameController.getCurrentPlayer()) { 
				if (piece.getValidMoves().size() != 0) //if it has any valid moves...
					renderer.setSelectedPiece(piece); //set selected piece
			}
		//if piece is selected and is a piece of our currentPlayer
		} else if (piece != null && piece.getPlayer() == gameController.getCurrentPlayer()) { 
			if (piece.getValidMoves().size() != 0) //if it has any valid moves...
				renderer.setSelectedPiece(piece); //set selected piece
		} else {
			renderer.setSelectedPiece(null);
		}
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) { }

	@Override
	public void mouseReleased(MouseEvent arg0) { }

	/**
	 * This method captures any key presses, which currently just
	 * handles undo
	 */
	@Override
	public void keyPressed(KeyEvent arg0) { 
		if (arg0.getKeyCode() == KeyEvent.VK_Z && arg0.isControlDown()) {
			gameController.undoLastMove();
			renderer.setSelectedPiece(null);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) { }

	@Override
	public void keyTyped(KeyEvent arg0) { }

}
