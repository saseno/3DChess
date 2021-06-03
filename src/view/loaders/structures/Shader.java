package view.loaders.structures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.jogamp.opengl.GL2;

/**
 * Shader class that represents an opengl shader, ie a
 * vertex and fragment shader.
 * 
 * Currently this is not used
 * 
 * @author Nicholas
 *
 */
public class Shader {

	private int shaderprogram;
	private int vertexShader;
	private int fragmentShader;
	
	public Shader(GL2 gl, File vs, File fs) throws IOException {
		createShader(gl, vs, fs);
	}
	
	private void createShader(GL2 gl, File vs, File fs) throws IOException {
		vertexShader = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
		fragmentShader = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
		
		//Start reading vertex code
		BufferedReader brv = new BufferedReader(new FileReader(vs));
		String src = "";
		String line;
		while ((line = brv.readLine()) != null) 
		  src += line + "\n";
		//End reading vertex code
		
		gl.glShaderSource(vertexShader, 1, new String[] { src }, null);
		gl.glCompileShader(vertexShader);

		//Start reading fragment code
		BufferedReader brf = new BufferedReader(new FileReader(fs));
		src = "";
		while ((line = brf.readLine()) != null) 
		  src += line + "\n";	
		//End reading fragment code
		
		gl.glShaderSource(fragmentShader, 1, new String[] { src }, null);
		gl.glCompileShader(fragmentShader);
		
		//create shader program
		shaderprogram = gl.glCreateProgram();
		gl.glAttachShader(shaderprogram, vertexShader);
		gl.glAttachShader(shaderprogram, fragmentShader);
		gl.glLinkProgram(shaderprogram);
		gl.glValidateProgram(shaderprogram);
		
		brv.close();
		brf.close();
		
		useShader(gl);
	}
	
	public void useShader(GL2 gl) {
		gl.glUseProgram(shaderprogram);
	}
	
}
