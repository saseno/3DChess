package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.ChessPiece;
import model.pieces.King;

/**
 * Player class responsible for holding it's own chess pieces
 * and the general direction that the pawns travel. The player also holds
 * a reference to the opposing player for easy access.
 * 
 * 
 * @author Nicholas
 *
 */
public class Player {

	/**
	 * The way the camera should face for this player
	*/
	private float cameraDirection;
	
	/**
	 * Represent the y direction this player is moving (ie on Standard 1 or -1)
	 */
	private int direction;
	
	private String playerName;
	
	/**
	 * List of all piece owned by the player (includes captured pieces)
	 */
	private ArrayList<ChessPiece> chessPieces;
	
	/**
	 * List of all pieces that have been captured by this player
	 */
	private ArrayList<ChessPiece> capturedPieces;
	
	/**
	 * Reference to the King for easy access
	 */
	private King king;
	
	private int winCount;
	
	/**
	 * The opponent player
	 */
	private Player otherPlayer;
	
	public Player(int _direction, boolean requestName) {
		direction = _direction;
		cameraDirection = (direction == 1) ? 3.1415f * 3 / 2 : 3.1415f / 2;
		
		chessPieces = new ArrayList<ChessPiece>();
		capturedPieces = new ArrayList<ChessPiece>();
		if (requestName) {
			String playernum = (direction == 1) ? "1" : "2";
			playerName = JOptionPane.showInputDialog("Enter Player " + playernum + "'s name");
		}
	}
	
	public void reset() {
		chessPieces = new ArrayList<ChessPiece>();
		capturedPieces = new ArrayList<ChessPiece>();
		king = null;
	}

	/**
	 * Adds the piece to player, if it is a king store
	 * it separately as well to provide easy access
	 * 
	 * @param chessPiece
	 */
	public void addPiece(ChessPiece chessPiece) {
		if (chessPiece.getType().equals("King"))
			king = (King) chessPiece;
		
		chessPieces.add(chessPiece);
	}
	
	/*
	 * -----------------------
	 * Setters and Getters 
	 * -----------------------
	 */
	
	public void pieceCaptured(ChessPiece capturedPiece) {
		capturedPieces.add(capturedPiece);
		chessPieces.remove(capturedPiece);
	}
	
	public void pieceUncaptured(ChessPiece uncapturedPiece) {
		chessPieces.add(uncapturedPiece);
		capturedPieces.remove(uncapturedPiece);
	}
	
	public void addWin() {
		winCount ++;
	}
	
	public int getWins() {
		return winCount;
	}
	
	public ArrayList<ChessPiece> getPieces() {
		return chessPieces;
	}

	public King getKing() {
		return king;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public float getCameraDirection() {
		return cameraDirection;
	}
	
	public void setOtherPlayer(Player _otherPlayer) {
		otherPlayer = _otherPlayer;
	}
	
	public Player getOtherPlayer() {
		return otherPlayer;
	}

	public String getPlayerName() {
		return playerName;
	}
	
	public String getColorTexture() {
		return (direction == 1) ? "White" : "Black";
	}
	
}
