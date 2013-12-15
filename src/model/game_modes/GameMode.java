package model.game_modes;

import model.ChessMove;
import model.board.Board;
import controller.GameLoop;
import controller.Player;

/**
 * Provides a basic interface to define a gamemode for a chess
 * game (such as standard, crazyhouse, suicide). These methods
 * are called by the GameLoop class.
 * 
 * 
 * @author Nicholas
 *
 */
public interface GameMode {

	/**
	 * Creates a board object with the players passed in. This
	 * method will return a complete setup based on the GameMode
	 * 
	 * @param player1
	 * @param player2
	 * @return Board that has been setup with the rules of the GameMode
	 */
	Board initPieces(Player player1, Player player2);

	/**
	 * Used to determine if a move is "valid" in the context of the 
	 * GameMode. This method inspects the board and returns true if the
	 * board is in a valid state (for a StandardGame, this would check to see
	 * if the king is in check)
	 * 
	 * @param board
	 * @param victim
	 * @return true if the board is in a valid state
	 */
	boolean boardValid(Board board, Player victim, ChessMove lastMove);

	/**
	 * Called when there are no moves left for the current player. At this
	 * point the game will be a stalemate or a player has lost.
	 * 
	 * @param board
	 * @param victim
	 * @return true if the player has lost, false if it is a stalemate
	 */
	boolean hasPlayerLost(Board board, Player victim);

	/**
	 * Called after a move has been executed. This method should display
	 * and warning, such as check in a Standard game
	 * 
	 * @param gameController
	 * @param lastMove
	 */
	void postMoveAction(final GameLoop gameController, ChessMove lastMove);
	
}
