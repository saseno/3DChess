package model.board;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL2;

import model.ChessMove;
import model.ChessPiece;
import model.game_modes.GameMode;
import model.pieces.Pawn;
import view.loaders.AssetLoader;
import view.loaders.structures.Model;
import controller.Player;

/**
 * Rectangular board which can be given any dimensions. This class inherits 
 * from Board, and overrides all the methods necessary, using a simple multidimensional 
 * array to represent the data. The would be the Board to use for a standard
 * game of chess (with size (8, 8), or by using the default constructor). 
 * 
 * @author Nicholas
 *
 */
public class RectangularBoard extends Board {

	private final static float RENDER_SIZE = 18;
	
	/**
	 * Board that is represented as a 2D array
	 */
	private ChessPiece[][] boardPieces;
	
	private int width;
	private int length;
	
	private String typeString;
	
	/**
	 * Constructs a basic board of regulation size (8,8)
	 * 
	 * @param gameMode
	 */
	public RectangularBoard(GameMode gameMode) {
		this(8, 8, gameMode);
	}
	
	public RectangularBoard(int _width, int _length, GameMode gameMode) {
		super(gameMode);
		boardPieces = new ChessPiece[_width][_length];
		width = _width;
		length = _length;
		typeString = "Rectangular(" + width + "," + length + ")";
	}
	
	private void addSquareToBuffer(FloatBuffer buffer, int b_count, float x, float y, float z, float texX, float texZ) {
		buffer.put(b_count, x);
		buffer.put(b_count + 1, y);
		buffer.put(b_count + 2, z);
		buffer.put(b_count + 4, 1); //START NORMAL
		buffer.put(b_count + 6, texX); //START TEX
		buffer.put(b_count + 7, texZ);
		
		buffer.put(b_count + 8, x);
		buffer.put(b_count + 9, y);
		buffer.put(b_count + 10, z + RENDER_SIZE);
		buffer.put(b_count + 12, 1); //START NORMAL
		buffer.put(b_count + 14, texX); //START TEX
		buffer.put(b_count + 15, texZ + 1.0f);
		
		buffer.put(b_count + 16, x + RENDER_SIZE);
		buffer.put(b_count + 17, y);
		buffer.put(b_count + 18, z + RENDER_SIZE);
		buffer.put(b_count + 20, 1); //START NORMAL
		buffer.put(b_count + 22, texX + 0.5f); //START TEX
		buffer.put(b_count + 23, texZ + 1.0f);
		
		buffer.put(b_count + 24, x + RENDER_SIZE);
		buffer.put(b_count + 25, y);
		buffer.put(b_count + 26, z);
		buffer.put(b_count + 28, 1); //START NORMAL
		buffer.put(b_count + 30, texX + 0.5f); //START TEX
		buffer.put(b_count + 31, texZ);
	}
	
	public Model generateTileModel() {
		int size = width * length * 8 * 4;
		FloatBuffer buffer = ByteBuffer.allocateDirect(size * (Float.SIZE / 8)).
                order(ByteOrder.nativeOrder()).asFloatBuffer();
		buffer.rewind();
		addSquareToBuffer(buffer, 0, -RENDER_SIZE / 2, 0.04f, -RENDER_SIZE / 2, 0, 0);
		Model model = new Model(null, GL2.GL_QUADS, buffer, size / 8, "Green");
		return model;
	}
	
	@Override
	public Model generateBoardModel() {
		//Generates the model board with using 24 byte vertices
		//in the form (vertex, normal, texCoord)
		int size = width * length * 8 * 4;
		FloatBuffer buffer = ByteBuffer.allocateDirect(size * (Float.SIZE / 8)).
                order(ByteOrder.nativeOrder()).asFloatBuffer();
		
		int b_count = 0;
		
		//start position is halfway below 0 (so its centered)
		float startX = -width / 2.0f * RENDER_SIZE;
		//the texture coords (will be 0 or .5 for X)
		float texX = 0.5f, texZ = 0;
		for (float x = 0;x < width;x ++) {
			//start position is halfway below 0 (so its centered)
			float startZ = -length / 2.0f * RENDER_SIZE;
			for (int z = 0;z < length;z ++) {
				addSquareToBuffer(buffer, b_count, startX, 0, startZ, texX, texZ);
				
				b_count += 8 * 4;
				startZ += RENDER_SIZE; //Step in z direction
				
				//we have reached the end of a column, change color
				texX += 0.5f;
				if (texX >= 0.999f)
					texX = 0;
			}
			//we have reached the end of a row, change color
			texX += 0.5f;
			if (texX >= 0.999f)
				texX = 0;
			
			startX += RENDER_SIZE; //Step in x direction
		}
		buffer.rewind();
		Model model = new Model(null, GL2.GL_QUADS, buffer, size / 8, "BlackWhite");
		return model;
	}
	
	@Override
	public String getType() {
		return typeString;
	}
	
	@Override
	public ChessPiece getTile(int x, int y) {
		return boardPieces[x][y];
	}
	
	@Override
	public ChessPiece setTile(ChessPiece piece, int x, int y) {
		ChessPiece prev = boardPieces[x][y]; //save old piece
		if (piece.getLocation().x >= 0 || piece.getLocation().y >= 0)
			boardPieces[piece.getLocation().x][piece.getLocation().y] = null;
		
		//if piece existed...
		if (prev != null)
			prev.setLocation(new Point(-1, -1)); //internally set the piece to invalid (-1, -1)
		
		
		boardPieces[x][y] = piece;
		piece.setLocation(new Point(x, y));
		return prev;
	}
	
	@Override
	public boolean isInBounds(int x, int y) {
		return !(x < 0 || y < 0 || x >= width || y >= length);
	}
	
	@Override
	public boolean hasEnemyPiece(int x, int y, Player player) {
		return isInBounds(x, y) && boardPieces[x][y] != null && 
				boardPieces[x][y].getPlayer() == player.getOtherPlayer();
	}
	
	@Override
	public boolean isMovableTile(int x, int y, Player player, boolean canCapture) {
		return isInBounds(x, y) && (boardPieces[x][y] == null ||
				(hasEnemyPiece(x, y, player) && canCapture));
	}

	/**
	 * Returns 4 spots, (x-1, y), (x-1, y), (x, y-1), (x, y+1)
	 */
	@Override
	public ArrayList<Point> getAdjacentRankFileTiles(int x, int y) {
		ArrayList<Point> adj = new ArrayList<Point>();
		adj.add(new Point(x - 1, y));
		adj.add(new Point(x + 1, y));
		adj.add(new Point(x, y - 1));
		adj.add(new Point(x, y + 1));
		return adj;
	}

	/**
	 * Returns 4 spots, (x-1, y-1), (x-1, y+1), (x+1, y-1), (x+1, y+1)
	 */
	@Override
	public ArrayList<Point> getAdjacentDiagonalTiles(int x, int y) {
		ArrayList<Point> adj = new ArrayList<Point>();
		adj.add(new Point(x - 1, y - 1));
		adj.add(new Point(x - 1, y + 1));
		adj.add(new Point(x + 1, y - 1));
		adj.add(new Point(x + 1, y + 1));
		return adj;
	}

	/**
	 * Returns 8 spots, all of which form a L shape
	 * 
	 */
	@Override
	public ArrayList<Point> getKnightMoves(int x, int y) {
		ArrayList<Point> moves = new ArrayList<Point>();
		moves.add(new Point(x - 2, y + 1));
		moves.add(new Point(x - 1, y + 2));
		moves.add(new Point(x + 2, y + 1));
		moves.add(new Point(x + 1, y + 2));
		moves.add(new Point(x + 2, y - 1));
		moves.add(new Point(x + 1, y - 2));
		moves.add(new Point(x - 2, y - 1));
		moves.add(new Point(x - 1, y - 2));	
		return moves;
	}

	@Override
	public ArrayList<Point> getPawnMoves(int x, int y, Pawn pawn) {
		ArrayList<Point> moves = new ArrayList<Point>();
		int dir = pawn.getPlayer().getDirection();
		moves.add(new Point(x, y + dir));
		if (!pawn.getHasMoved())
			moves.add(new Point(x, y + dir * 2));
			
		return moves;	
	}

	@Override
	public ArrayList<Point> getPawnAttacks(int x, int y, Pawn pawn) {
		ArrayList<Point> moves = new ArrayList<Point>();
		int dir = pawn.getPlayer().getDirection();
		moves.add(new Point(x + 1, y + dir));
		moves.add(new Point(x - 1, y + dir));		
		return moves;
	}
	
	public Point getBoardPosition(Point2D.Float renderPoint) {
		Point retVal = new Point();
		retVal.x = (int) (renderPoint.x / RENDER_SIZE + width / 2.0f);
		retVal.y = (int) (renderPoint.y / RENDER_SIZE + length / 2.0f);
		return retVal;
	}
	
	@Override
	public Point2D.Float getRenderPosition(Point loc) {
		Point2D.Float retVal = new Point2D.Float();
		retVal.x = (loc.x - width / 2.0f + 1 / 2.0f) * RENDER_SIZE;
		retVal.y = (loc.y - length / 2.0f + 1 / 2.0f) * RENDER_SIZE;
		return retVal;
	}
	
	@Override
	public void render(GL2 gl, ChessPiece selectedPiece) {		
		AssetLoader.getInstance().getModel(getType()).render(gl);
		for (int x = 0;x < width;x ++) {
			for (int z = 0;z < length;z ++) {
				if (boardPieces[x][z] != null) {
					boardPieces[x][z].render(gl);
				}
			}
		}
		if (selectedPiece != null) {
			ArrayList<ChessMove> moves = selectedPiece.getValidMoves();
			for (ChessMove move : moves) {
				Point2D.Float rl = getRenderPosition(move.getMoveLocation());
				gl.glPushMatrix();
					gl.glTranslatef(rl.x, 0, rl.y);
					AssetLoader.getInstance().getModel(getType() + ":Tile").render(gl);
				gl.glPopMatrix();
			}
		}
	}

}
