package controller;
//sfddf
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.ChessMove;
import model.board.Board;
import model.game_modes.GameMode;
import view.GameCamera;
import view.Renderer;
import view.loaders.AssetLoader;
import controller.util.Animation;

/**
 * This class will control the gameflow of the program. This class
 * implements the maintaining running loop for this application, and has basic functions
 * that control chess gameflow, such as execute move, cancel game, and setupNewGame
 * 
 * @author Nicholas
 *
 */
public class GameLoop implements Runnable {
	
	/**
	 * Reference to the canvas, used to redraw
	 */
	private GLCanvas canvas;
	private Renderer renderer;
	
	/**
	 * The players that in this game
	 */
	private Player player1;
	private Player player2;
	private Player currentPlayer;
	
	/**
	 * A reference to the board
	 */
	private Board board;
	
	/**
	 * The type of game played
	 */
	private GameMode gameMode;
	
	/**
	 * A list of a moves, used for undo implementation
	 */
	private Stack<ChessMove> allMoves;
	
	/**
	 * Flags used to determine if the game is over
	 */
	private Player winner;
	private boolean running;
	
	/**
	 * Variables used for animations
	 */
	private float lastRenderTime;
	private CopyOnWriteArrayList<Animation> animations;
	
	/**
	 * Constructs a new gameloop, with the given canvas.
	 * The renderer is set later.
	 * 
	 * @param _canvas The opengl window created
	 */
	public GameLoop(GLCanvas _canvas, JFrame frame) {
		canvas = _canvas;
		player1 = new Player(1, true, frame);
		player2 = new Player(-1, true, frame);
		player1.setOtherPlayer(player2);
		player2.setOtherPlayer(player1);
	}
	
	/**
	 * Sets up the game for the given gameMode. This
	 * method initializes the players and the board. This 
	 * method is called automatically, when the current game ends
	 * 
	 * @param _gameMode The game mode to be used
	 */
	public synchronized void setupNewGame(GameMode _gameMode) {
		//reset all animations and moves
		allMoves = new Stack<ChessMove>();
		animations = new CopyOnWriteArrayList<Animation>();
		
		//reset current player
		currentPlayer = player1;
		gameMode = _gameMode;
		player1.reset();
		player2.reset();
		board = gameMode.initPieces(player1, player2);
		
		//Load the board models, will be ignored if already created
		AssetLoader.getInstance().addModel(board.generateBoardModel(), board.getType());
		AssetLoader.getInstance().addModel(board.generateTileModel(), board.getType() + ":Tile");
		running = false;
		winner = null;
		
		//set camera in player1's direction
		if (renderer.getCamera() != null)
			renderer.getCamera().setHorizontalRotation(player1.getCameraDirection());
		
		//start a new game thread
		new Thread(this).start();
	}
	
	/**
	 * The method is what runs when start() is called on a thread. The
	 * method will continue running until running is set to "false", ie the
	 * game is over. When running is set to false, the winner will be displayed
	 * and new game will be set up.
	 */
	@Override
	public void run() {
		running = true;
		/* Every iteration this thread updates the animations and
		 * displays the board. Since frame rates can vary, we calculate
		 * the last running time to update the animations uniformly.
		 */
		while (running || !animations.isEmpty()) {
			long startTime = System.nanoTime();
			
			ArrayList<Animation> removeList = new ArrayList<Animation>();
			for (Animation animation : animations)
				if (animation.stepAnimation(lastRenderTime))
					removeList.add(animation);
			
			animations.removeAll(removeList);
			canvas.display();
				
			long endTime = System.nanoTime();	
			lastRenderTime = (float) ((endTime - startTime) / 1.0E9);
		}
		
		String winnerString = (winner == null) ? "Tie" : winner.getPlayerName();
		JOptionPane.showMessageDialog(canvas, 
				"Winner was: " + winnerString + "\nScore is:\n" + player1.getPlayerName() + ":" + player1.getWins() + 
				" " + player2.getPlayerName() + ":" + player2.getWins(),  
				"Game Over", 
				JOptionPane.INFORMATION_MESSAGE);
		
		setupNewGame(gameMode);
	}
		
	/**
	 * Executes a chess move, sets up an animation,
	 * and switches the currentPlayer. The ChessMove
	 * is also added the total move list, so it can be undone
	 * later if necessary. This move also checks to see if there
	 * is any moves left for the other to make, otherwise the game is ended
	 *  If their is no game running, this method
	 * does nothing.
	 * 
	 * @param move ChessMove to be executed, must be a validMove
	 */
	public synchronized void executeMove(ChessMove move) {
		if (!running) 
			return;
		
		//add move
		allMoves.push(move);
		//actually execute the move
		move.executeMove(true);
		currentPlayer = currentPlayer.getOtherPlayer();
		//check to see if there are any moves left
		boolean noMoves = board.noPossibleMoves(currentPlayer);
		if (noMoves) {
			//check the game mode to see if the player has lost
			if (gameMode.hasPlayerLost(board, currentPlayer)) 
				cancelGame(currentPlayer.getOtherPlayer());
			else //or just tied
				cancelGame(null);
			
			return;
		}
		gameMode.postMoveAction(this, move);
		
		//create animations based on move locations
		Point2D.Float start = board.getRenderPosition(move.getStartLocation());
		Point2D.Float end = board.getRenderPosition(move.getMoveLocation());
		
		animations.add(new Animation(move.getPiece(), "drawLocationX", start.x, end.x, 1));
		animations.add(new Animation(move.getPiece(), "drawLocationY", start.y, end.y, 1));
		//rotate the camera
		GameCamera c = renderer.getCamera();
		animations.add(new Animation(c, "horizontalRotation", c.getHorizontalRotation(), 
				currentPlayer.getCameraDirection(), 2));
	}
	
	/**
	 * Undoes a chess move, sets up an animation,
	 * and switches the currentPlayer.
	 *  If their is no game running, or there are moves to 
	 *  undo, this method does nothing.
	 * 
	 * @param move ChessMove to be executed, must be a validMove
	 */
	public synchronized void undoLastMove() {
		if (!running) 
			return;
		
		if (allMoves.empty())
			return;
		
		//get last move
		ChessMove lastMove = allMoves.pop();
		//undo it
		lastMove.undoMove();
		//swap players
		currentPlayer = currentPlayer.getOtherPlayer();
		if (!allMoves.isEmpty())
			gameMode.postMoveAction(this, allMoves.peek());
		
		//create animation
		Point2D.Float start = board.getRenderPosition(lastMove.getMoveLocation());
		Point2D.Float end = board.getRenderPosition(lastMove.getStartLocation());
		
		animations.add(new Animation(lastMove.getPiece(), "drawLocationX", start.x, end.x, 1));
		animations.add(new Animation(lastMove.getPiece(), "drawLocationY", start.y, end.y, 1));
		//rotate camera
		GameCamera c = renderer.getCamera();
		animations.add(new Animation(c, "horizontalRotation", c.getHorizontalRotation(), 
				currentPlayer.getCameraDirection(), 2));
	}
	
	/**
	 * Cancels the game, which will result in no moves,
	 * being able to be made, but the any animations will finish.
	 * 
	 * @param _winner The winner of the game, can be null in which case the 
	 * game was a tie
	 */
	public void cancelGame(Player _winner) {
		running = false;
		winner = _winner;
		if (_winner != null)
			_winner.addWin();
	}
	
	/*
	 * -------------------------------------------
	 * Getters and Setters start here
	 * -------------------------------------------
	 */
	
	public void addAnimation(Animation anim) {
		animations.add(anim);
	}
	
	public void setRenderer(Renderer _renderer) {
		renderer = _renderer;
	}
	
	public Board getBoard() {
		return board;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		 return player2;
	}
	
	public GLCanvas getCanvas() {
		return canvas;
	}
	
}
