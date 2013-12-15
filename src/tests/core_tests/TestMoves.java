package tests.core_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;

import model.ChessMove;
import model.board.Board;
import model.board.RectangularBoard;
import model.game_modes.StandardGame;
import model.pieces.Bishop;
import model.pieces.Chancellor;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.LameQueen;
import model.pieces.Pawn;
import model.pieces.Queen;
import model.pieces.Rook;

import org.junit.Before;
import org.junit.Test;

import controller.Player;

/**
 * These tests test the functionality of the Board, and the various 
 * chess pieces.
 * 
 * @author Nicholas
 *
 */
public class TestMoves {

	private Board chessBoard;
	private Player player1;
	private Player player2;
	
	@Before
	public void setup() {
		player1 = new Player(1, false);
		player2 = new Player(-1, false);
		player1.setOtherPlayer(player2);
		player2.setOtherPlayer(player1);;
	}
	
	@Test
	public void testRectangularBoardMethods() {
		setup();
		chessBoard = new StandardGame().initPieces(player1, player2);
		//check adjacent 
		ArrayList<Point> adj = chessBoard.getAdjacentRankFileTiles(4, 4);
		assertTrue(adj.contains(new Point(3, 4)));
		assertTrue(adj.contains(new Point(4, 5)));
		assertTrue(adj.contains(new Point(5, 4)));
		assertTrue(adj.contains(new Point(4, 3)));
		ArrayList<Point> dia = chessBoard.getAdjacentDiagonalTiles(4, 4);
		assertTrue(dia.contains(new Point(3, 3)));
		assertTrue(dia.contains(new Point(3, 5)));
		assertTrue(dia.contains(new Point(5, 5)));
		assertTrue(dia.contains(new Point(5, 3)));
		
		//check bounds
		assertTrue(chessBoard.isInBounds(2, 2));
		assertTrue(!chessBoard.isInBounds(8, 7));
		assertTrue(!chessBoard.isInBounds(-1, 7));
		
		
		//check hasEnemyPiece() method
		assertTrue(chessBoard.hasEnemyPiece(1, 1, player2)); //Player1
		assertTrue(chessBoard.hasEnemyPiece(1, 1, player2)); //Player1 
		assertTrue(chessBoard.hasEnemyPiece(7, 7, player1)); //Player2 
		assertTrue(!chessBoard.hasEnemyPiece(4, 4, player2)); //Player2 
	}
	
	@Test
	public void testPawnMoves() {
		setup();
		chessBoard = new RectangularBoard(new StandardGame());
		King K = new King(5, 6, chessBoard, player1);
		Pawn P1 = new Pawn(1, 1, chessBoard, player1);
		Pawn P2 = new Pawn(2, 1, chessBoard, player1);
		Bishop b = new Bishop(2, 2, chessBoard, player2);
		King k = new King(4, 3, chessBoard, player2);
		
		/* 5 . . . . .    player1 = Capital
		 * 4 . . . . .    player2 = Lower
		 * 3 . . . . . 
		 * 2 . . b . . 
		 * 1 .P1 P2 . .
		 * 0 . . . . .
		 */
		assertEquals(3, P1.getPossibleMoves().size());
		assertTrue(P1.getPossibleMoves().contains(new ChessMove(new Point(2, 2), P1))); //P1 attack on diagonal
		assertTrue(P1.getPossibleMoves().contains(new ChessMove(new Point(1, 2), P1))); //P1 move 1
		assertTrue(P1.getPossibleMoves().contains(new ChessMove(new Point(1, 3), P1))); //P1 jump 2
		assertEquals(0, P2.getPossibleMoves().size()); //P2 shouldn't be able to move
	}
	
	@Test
	public void testKnightMoves() {
		setup();
		chessBoard = new RectangularBoard(new StandardGame());
		King Ki = new King(5, 6, chessBoard, player1);
		Knight K = new Knight(1, 1, chessBoard, player1);
		
		/* 5 . . . . .    player1 = Capital
		 * 4 . . . . .    player2 = Lower
		 * 3 x . x . . 
		 * 2 . . . x . 
		 * 1 . K . . .
		 * 0 . . . x .
		 *   0 1 2 3 4
		 */
		assertEquals(4, K.getPossibleMoves().size()); //4 possible knight moves
		assertTrue(K.getPossibleMoves().contains(new ChessMove(new Point(0, 3), K)));
		assertTrue(K.getPossibleMoves().contains(new ChessMove(new Point(3, 2), K)));
	}
	
	@Test
	public void testRookMoves() {
		setup();
		chessBoard = new RectangularBoard(new StandardGame());
		King K = new King(5, 6, chessBoard, player1);
		Rook R = new Rook(1, 1, chessBoard, player1);
		
		/* 5 . x . . .    player1 = Capital
		 * 4 . x . . .    player2 = Lower
		 * 3 . x . . . 
		 * 2 . x . . . 
		 * 1 x R x x x
		 * 0 . x . . .
		 *   0 1 2 3 4
		 */
		assertEquals(14, R.getPossibleMoves().size());
		assertTrue(R.getPossibleMoves().contains(new ChessMove(new Point(1, 0), R)));
		assertTrue(R.getPossibleMoves().contains(new ChessMove(new Point(1, 7), R)));
		assertTrue(!R.getPossibleMoves().contains(new ChessMove(new Point(1, 1), R))); //shouldn't contain self
		assertTrue(!R.getPossibleMoves().contains(new ChessMove(new Point(2, 2), R)));
	}
	
	@Test
	public void testBishopMoves() {
		setup();
		chessBoard = new RectangularBoard(new StandardGame());
		King K = new King(5, 6, chessBoard, player1);
		Bishop B = new Bishop(1, 1, chessBoard, player1);
		
		/* 5 . . . . .    player1 = Capital
		 * 4 . . . . x    player2 = Lower
		 * 3 . . . x . 
		 * 2 x . x . . 
		 * 1 . B . . .
		 * 0 x . x . .
		 *   0 1 2 3 4
		 */
		assertEquals(9, B.getPossibleMoves().size());
		assertTrue(B.getPossibleMoves().contains(new ChessMove(new Point(0, 0), B)));
		assertTrue(B.getPossibleMoves().contains(new ChessMove(new Point(0, 2), B)));
		assertTrue(!B.getPossibleMoves().contains(new ChessMove(new Point(1, 1), B))); 
		assertTrue(!B.getPossibleMoves().contains(new ChessMove(new Point(1, 0), B)));
	}
	
	@Test
	public void testQueenMoves() {
		setup();
		chessBoard = new RectangularBoard(new StandardGame());
		King K = new King(5, 6, chessBoard, player1);
		Queen Q = new Queen(1, 1, chessBoard, player1);
		
		/* 5 . x . . .    player1 = Capital
		 * 4 . x . . x    player2 = Lower
		 * 3 . x . x . 
		 * 2 x x x . . 
		 * 1 x Q x x x
		 * 0 x x x . .
		 *   0 1 2 3 4
		 */
		assertEquals(23, Q.getPossibleMoves().size());
		assertTrue(Q.getPossibleMoves().contains(new ChessMove(new Point(0, 0), Q)));
		assertTrue(Q.getPossibleMoves().contains(new ChessMove(new Point(0, 2), Q)));
		assertTrue(Q.getPossibleMoves().contains(new ChessMove(new Point(1, 0), Q)));
		assertTrue(Q.getPossibleMoves().contains(new ChessMove(new Point(1, 7), Q)));
		assertTrue(!Q.getPossibleMoves().contains(new ChessMove(new Point(1, 1), Q))); //shouldn't contain self
		assertTrue(!Q.getPossibleMoves().contains(new ChessMove(new Point(0, 4), Q)));
	}
	
	@Test
	public void testKingMoves() {
		setup();
		chessBoard = new RectangularBoard(new StandardGame());
		King K = new King(1, 1, chessBoard, player1);
		
		/* 5 . . . . .    player1 = Capital
		 * 4 . . . . .    player2 = Lower
		 * 3 . . . . . 
		 * 2 x x x . . 
		 * 1 x K x . .
		 * 0 x x x . .
		 *   0 1 2 3 4
		 */
		assertEquals(8, K.getPossibleMoves().size());
		assertTrue(K.getPossibleMoves().contains(new ChessMove(new Point(0, 0), K)));
		assertTrue(K.getPossibleMoves().contains(new ChessMove(new Point(0, 2), K)));
		assertTrue(K.getPossibleMoves().contains(new ChessMove(new Point(1, 0), K)));
		assertTrue(K.getPossibleMoves().contains(new ChessMove(new Point(2, 2), K)));
		assertTrue(!K.getPossibleMoves().contains(new ChessMove(new Point(1, 1), K))); //shouldn't contain self
		assertTrue(!K.getPossibleMoves().contains(new ChessMove(new Point(0, 4), K)));
	}
	
	@Test
	public void testLameQueenMoves() {
		setup();
		chessBoard = new RectangularBoard(new StandardGame());
		LameQueen L = new LameQueen(1, 1, chessBoard, player1);
		Rook r = new Rook(1, 4, chessBoard, player2);
		Bishop b = new Bishop(4, 4, chessBoard, player2);
		
		/* 5 . . . . .    player1 = Capital
		 * 4 . r . . bx    player2 = Lower
		 * 3 . x . x . 
		 * 2 x x x . . 
		 * 1 x L x x x
		 * 0 x x x . .
		 *   0 1 2 3 4
		 */
		assertEquals(16, L.getPossibleMoves().size());
		assertTrue(L.getPossibleMoves().contains(new ChessMove(new Point(4, 4), L)));//it should capture bishop
		assertFalse(L.getPossibleMoves().contains(new ChessMove(new Point(1, 4), L)));//shouldn't capture rook (ie on rank-file)
		assertTrue(L.getPossibleMoves().contains(new ChessMove(new Point(1, 2), L)));//check random spot
	}
	
	@Test
	public void testChancellorMoves() {
		setup();
		chessBoard = new RectangularBoard(new StandardGame());
		Chancellor C = new Chancellor(1, 1, chessBoard, player1);
		
		/* 5 . x . . .    player1 = Capital
		 * 4 . x . . .    player2 = Lower
		 * 3 x x x . . 
		 * 2 . x . x . 
		 * 1 x L x x x
		 * 0 . x . x .
		 *   0 1 2 3 4
		 */
		assertEquals(18, C.getPossibleMoves().size());
		assertTrue(C.getPossibleMoves().contains(new ChessMove(new Point(1, 5), C)));//rank-file test
		assertTrue(C.getPossibleMoves().contains(new ChessMove(new Point(2, 3), C)));//knight test
		assertFalse(C.getPossibleMoves().contains(new ChessMove(new Point(3, 3), C)));//test bad spot
	}
	
}
