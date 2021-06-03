package view;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import controller.GameLoop;
import controller.InputHandler;

/**
 * Define a frame for the chess game. This class
 * pieces together all the parts that are needed to 
 * make a chess game start, such as the canvas, renderer,
 * input manager, and gameloop. To actually begin the game
 * you must call, getGameController().setupNewGame() with
 * the game mode you wish to play
 * 
 * @author Nicholas
 *
 */
public class GameFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3248983927774474631L;
	
	private GameLoop gameController;
	private GLCanvas canvas;
	
	public GameFrame() {
		GLProfile profile = GLProfile.get(GLProfile.GL2);
    	GLCapabilities capabilities = new GLCapabilities(profile);
    	capabilities.setSampleBuffers(true);
    	capabilities.setNumSamples(4);
    	capabilities.setDepthBits(24);
    	//end opengl setup
    	
    	//create opengl view
    	canvas = new GLCanvas(capabilities);
    	
    	setTitle("Chess!");
    	setJMenuBar(new GameMainMenu(this, gameController));
		add(canvas);
		setSize(900, 600);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create gameloop
    	gameController = new GameLoop(canvas, this);
    	Renderer renderer = new Renderer(gameController);
    	canvas.addGLEventListener((GLEventListener) renderer);
    	
    	InputHandler in = new InputHandler(gameController, renderer);
		canvas.addKeyListener(in);
		canvas.addMouseListener(in);
	}
	
	/*
	 * -------------------
	 * Getters and Setters
	 * -------------------
	 */
	
	public GameLoop getGameController() {
		return gameController;
	}
	
	public GLCanvas getCanvas() {
		return canvas;
	}
	
}
