package tests.core_tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import model.ChessMove;
import model.board.Board;
import model.board.RectangularBoard;
import model.game_modes.StandardGame;
import model.pieces.Bishop;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.pieces.Queen;
import model.pieces.Rook;

import org.junit.Before;
import org.junit.Test;

import controller.Player;


/**
 * Checks various check/checkmate scenarios
 * 
 * 
 * @author Nicholas
 *
 */
public class TestCheckSenerios {

	private Board chessBoard;
	private Player player1;
	private Player player2;
	
	@Before
	public void setup() {
		player1 = new Player(1, false, null);
		player2 = new Player(-1, false, null);
		player1.setOtherPlayer(player2);
		player2.setOtherPlayer(player1);;
	}
	
	/**
	 *  Tests to see if a bishop at 4,4 causes check to a king at 2,2 
	 */
	@Test
    public void testBasicCheck() {
		setup();
        chessBoard = new RectangularBoard(new StandardGame());
        Bishop b = new Bishop(4, 4, chessBoard, player1);
        King k = new King(2, 2, chessBoard, player2);
        assertTrue(chessBoard.locationPressured(player2.getKing().getLocation(), player2));
        assertEquals(6, chessBoard.getAllMoves(player2).size());
    }
	
	/**
	 * Same scenario as testBasicCheck() but with a friendly pawn in
	 * between so no check should be available
	 */
	@Test
	public void testBasicNoCheck() {
		setup();
        chessBoard = new RectangularBoard(new StandardGame());
        Bishop b = new Bishop(4, 4, chessBoard, player1);
        Pawn p = new Pawn(3, 3, chessBoard, player2);
        King k = new King(2, 2, chessBoard, player2);
        assertFalse(chessBoard.locationPressured(player2.getKing().getLocation(), player2));
        assertEquals(7, chessBoard.getAllMoves(player2).size());
	}
	
	/**
	 * Tests a complicated check situation where the king can only move 
	 * in 2 possible locations. A queen is then added, to opening another option
	 * to avoid check by moving the Queen in front of the bishop
	 */
	@Test
	public void testComplicatedCheck() {
		setup();
        chessBoard = new RectangularBoard(new StandardGame());
        Bishop b = new Bishop(4, 4, chessBoard, player1);
        Rook r1 = new Rook(4, 1, chessBoard, player1);
        Rook r2 = new Rook(1, 4, chessBoard, player1);
        King K = new King(2, 2, chessBoard, player2);
        assertTrue(chessBoard.getAllMoves(player2).contains(new ChessMove(new Point(2, 3), K)));
        assertTrue(chessBoard.getAllMoves(player2).contains(new ChessMove(new Point(3, 2), K)));
        assertEquals(2, chessBoard.getAllMoves(player2).size());
        /*  
        4 . r2. . b
        3 . . . . .
        2 . . K . .
        1 . . . . r1
   		0 . . . . .
        */
        
        //now there should also be 2 moves but with a  different option, Queen(3,2) to 3,3 
        Queen Q = new Queen(3, 2, chessBoard, player2);
        assertTrue(chessBoard.getAllMoves(player2).contains(new ChessMove(new Point(2, 3), K)));
        assertTrue(chessBoard.getAllMoves(player2).contains(new ChessMove(new Point(3, 3), Q)));
        assertEquals(2, chessBoard.getAllMoves(player2).size());
        /*  
        4 . r2. . b
        3 . . . . .
        2 . . K Q .
        1 . . . . r1
   		0 . . . . .
        */
	}
	
	/**
	 * Tests a complicated checkmate situation where player2 is checkmated
	 * by player1's knight.
	 */
	@Test
	public void testComplicatedMate() {
		setup();
        chessBoard = new RectangularBoard(new StandardGame());
        Bishop b = new Bishop(4, 4, chessBoard, player1);
        Knight h = new Knight(5, 4, chessBoard, player1);
        King k = new King(3, 3, chessBoard, player1);
        Rook R = new Rook(5, 7, chessBoard, player2);
        King K = new King(6, 7, chessBoard, player2);
        Pawn P1 = new Pawn(5, 6, chessBoard, player2);
        Pawn P2 = new Pawn(7, 6, chessBoard, player2);
        Pawn P3 = new Pawn(6, 5, chessBoard, player2);
        
        
        /*  
        7 . . R K .     (player 2) Capital's direction is down
        6 . . P . P     NOTE: ORGINAL VIEW
        5 . . . P .
        4 . b h . .
   		3 k . . . .
   		  3 4 5 6 7
        */
        
        assertFalse(chessBoard.locationPressured(player2.getKing().getLocation(), player2));
        
        //now move the horse
        h.movePieceToTile(new Point(7, 5), true);
        
        assertTrue(chessBoard.locationPressured(player2.getKing().getLocation(), player2));
        assertTrue(chessBoard.noPossibleMoves(player2));
        
        /*  
        7 . . R K .     Capital's direction is down
        6 . . P . P     NOTE: FINAL OUTCOME, horse was at 5, 4
        5 . . . P h
        4 . b . . .
   		3 . . . . .
   		  3 4 5 6 7
        */
	}
	
}
