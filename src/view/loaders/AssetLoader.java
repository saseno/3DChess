package view.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Map;
import java.util.TreeMap;

import view.loaders.structures.Model;
import view.loaders.structures.Shader;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * Utility class used for loading the required resources for this project including 
 * textures, shaders, and models. This class uses the Singleton design pattern
 * @author Nicholas
 *
 */
public class AssetLoader {

	/**
	 * The only AssetLoader object
	 */
	private static AssetLoader loader;
	
	/**
	 * Holds all the textures, with a unique name
	 */
	private Map<String, Texture> textures;
	
	/**
	 * Holds all the shaders, with a unique name
	 */
	private Map<String, Shader> shaders;
	
	/**
	 * Holds all the Models, with a unique name
	 */
	private Map<String, Model> models;
	
	/**
	 * The current texture that is bound
	 */
	private String currentTexture;
	
	private AssetLoader() {
		textures = new TreeMap<String, Texture>();
		shaders = new TreeMap<String, Shader>();
		models = new TreeMap<String, Model>();
	}
	
	/**
	 * Returns the only created object of the AssetLoader class,
	 * and if no object exists yet create one.
	 * 
	 * @return a reference to the only AssetLoader class
	 */
	public static AssetLoader getInstance() {
		if (loader == null)
			loader = new AssetLoader();
		
		return loader;
	}
	
	
	/**
	 * Loads a basic model file, with the format (vertex, normal, textureCoord)\n"
	 * 
	 * @param gl
	 * @param file the directory of all the models
	 */
	public void loadChessModels(GL2 gl) {
		try {
			String[] modelNames = {
					"Bishop.mdl", "Chancellor.mdl", "King.mdl", "Knight.mdl",
					"LameQueen.mdl", "Pawn.mdl", "Queen.mdl", "Rook.mdl"
				};
			ClassLoader cl = getClass().getClassLoader();
			for (String file : modelNames) { //Loop through all the files 
				
				//create a file reader
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(cl.getResourceAsStream("view/Models/" + file)));
				
				//the first line is the size
				int size = Integer.parseInt(reader.readLine());
				//create the buffer
				FloatBuffer buffer = ByteBuffer.allocateDirect(size * 8 * (Float.SIZE / 4)).
		                order(ByteOrder.nativeOrder()).asFloatBuffer();
				
				String currentLine;
				while ((currentLine = reader.readLine()) != null) {//read each line
					for (String val : currentLine.split(" ")) //load the line
						buffer.put(Float.parseFloat(val)); //insert into the buffer
				}
			
				buffer.rewind();
				Model model = new Model(gl, GL2.GL_QUADS, buffer, size, null);
				models.put(file.split("\\.")[0], model);
				reader.close();
			}
		} catch (IOException ex) { ex.printStackTrace(); }
	}
	
	/**
	 * Adds an in application built model, which is used by the Board classes,
	 * to add their dynamically created models 
	 * 
	 * @param model
	 * @param name
	 */
	public void addModel(Model model, String name) {
		if (models.containsKey(name))
			return;
		
		models.put(name, model);
	}

	/**
	 * @param name
	 * @return returns the model for the name
	 */
	public Model getModel(String name) {
		return models.get(name);
	}
	
	/**
	 * Load all the textures in the file supplied
	 * 
	 * @param gl
	 * @param dir the file containing the textures
	 * @throws IOException 
	 * @throws GLException 
	 */
	public void loadChessTextures(GL2 gl) throws GLException, IOException {
		ClassLoader cl = getClass().getClassLoader();
		String[] texturesNames = { "Black.png", "BlackWhite.png", "Green.png", "White.png" };
		for (String file : texturesNames)  { //loop through all images
			Texture tex = TextureIO.newTexture(cl.getResourceAsStream("view/Textures/" + file), true, TextureIO.PNG);
			tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		    tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
		    tex.enable(gl);
		    tex.bind(gl);
			textures.put(file.split("\\.")[0], tex);
		}
	}
	
	/**
	 * Bind the texture supplied by the name, if it is not the
	 * current one bound.
	 * 
	 * @param gl
	 * @param name
	 */
	public void bindTexture(GL2 gl, String name) {
		if (currentTexture != null && currentTexture.equals(name))
			return;
		
		textures.get(name).bind(gl);
		currentTexture = name;
	}
	
	/**
	 * Loads a shader file 
	 * 
	 * @param gl
	 * @param dataSource
	 * @throws IOException 
	 */
	public void loadShader(GL2 gl, String path, String name) throws IOException {
			File fs = new File(path + File.pathSeparator + name + ".fs");
			File vs = new File(path + File.pathSeparator + name + ".vs");
			Shader shader = new Shader(gl, vs, fs);
			shaders.put(name, shader);
	}
	
}
