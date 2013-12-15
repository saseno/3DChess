package view.loaders.structures;

import java.nio.FloatBuffer;

import javax.media.opengl.GL2;

import view.loaders.AssetLoader;

/**
 * This class represents a 3d model, and is responsible for holding
 * a reference to an opengl buffer, and the name of the texture that should be bound before
 * rendering.
 * 
 * @author Nicholas
 *
 */
public class Model {

	/**
	 * Type of primitives that should be rendered ie. GL_TRIANGLES or GL_QUADS
	 */
	private int type;
	
	/**
	 * A reference to the opengl buffer stored on the GPU
	 */
	private int vboMesh;
	
	/**
	 * Holds the data for this buffer, if it has not yet been sent to the GPU
	 */
	private FloatBuffer buffer;
	private int size;
	
	private String texture;
	
	public Model(GL2 gl, int _type, FloatBuffer _buffer, int _size, String textureName) {
		type = _type;
		texture = textureName;
		size = _size;
		vboMesh = -1;
		if (gl == null) //if their is no opengl context yet...
			buffer = _buffer; //save the buffer
		else
			createBuffer(gl, _buffer); //pass the data to the GPU
	}
	
	/**
	 * Sends the data from the buffer to the gpu, creating an opengl
	 * VBO
	 * 
	 * @param gl
	 * @param buffer
	 */
	private void createBuffer(GL2 gl, FloatBuffer buffer) {
		if (vboMesh != -1)
			return;
		
		int[] vbo = new int[1];
		gl.glGenBuffers(1, vbo, 0);
		vboMesh = vbo[0];
		
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vboMesh); //Bind data to GPU
		gl.glBufferData(GL2.GL_ARRAY_BUFFER, 8 * size * 4, buffer, GL2.GL_STATIC_DRAW);
	}

	/**
	 * Binds this VBO, so that it can be rendered
	 * 
	 * @param gl
	 */
	private void bindData(GL2 gl) {
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vboMesh); //Bind this buffer
		gl.glVertexPointer(3, GL2.GL_FLOAT, 8 * (Float.SIZE / 8), 0);
		gl.glNormalPointer(GL2.GL_FLOAT, 8 * (Float.SIZE / 8), 3 * (Float.SIZE / 8));
		gl.glTexCoordPointer(2, GL2.GL_FLOAT, 8 * (Float.SIZE / 8), 6 * (Float.SIZE / 8));
	}
	
	/**
	 * Renders the model. If the vbo has not yet been sent to the GPU it, will be
	 * during the call. Also binds the texture (if specified) associated with this model
	 * 
	 * @param gl
	 */
	public void render(GL2 gl) {
		if (vboMesh == -1) { //if it has not yet been sent to the gpu...
			createBuffer(gl, buffer);
			buffer = null;
		}
		
		bindData(gl);
		if (texture != null)
			AssetLoader.getInstance().bindTexture(gl, texture);
		
		gl.glDrawArrays(type, 0, size);
	}
	
}
