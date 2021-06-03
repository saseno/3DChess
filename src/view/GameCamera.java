package view;

import java.awt.geom.Point2D;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.glu.GLU;

import controller.util.Animation.Animatable;

/**
 * This class represents a camera (or eye) in 3D space. The eye
 * is always looking at point 0,0,0 (the center of the board). An the 
 * location of the camera is defined in spherical coordinates, where there
 * is a horizontal rotation from [0, 2*PI] and a vertical rotation [0, PI/2]
 * 
 * @author Nicholas
 *
 */
public class GameCamera implements Animatable {

	private GLU glu;
	
	private int screenLength;
	
	private float viewDistance;
	
	private float horizontalRotation;
	private float verticalRotation;
	
	public GameCamera(int _screenLength) {
		screenLength = _screenLength;
		viewDistance = 130;
		verticalRotation = (float) (Math.PI / 4);
		glu = new GLU();
	}
	
	/**
	 * Generates the location of the camera from the current rotation's defined
	 * by using the spherical coordinates.
	 * 
	 * @return
	 */
	private Vector3f getLocation() {
		Vector3f loc = new Vector3f();
		loc.x = (float) (Math.cos(horizontalRotation) * Math.cos(verticalRotation) * viewDistance);
		loc.y = (float) (Math.sin(verticalRotation) * viewDistance);
		loc.z = (float) (Math.sin(horizontalRotation) * Math.cos(verticalRotation) * viewDistance);
		return loc;
	}
	
	/**
	 * Creates a ModelView matrix by using the utility method 
	 * gluLookAt(), supplying the eye location, the look at position(0, 0, 0),
	 * and loc.x and loc
	 * 
	 */
	public void lookat() {
		Vector3f loc =  getLocation();
		glu.gluLookAt(loc.x, loc.y, loc.z,
						0, 0, 0, 
						0, 1, 0);	
	}
	
	/**
	 * Converts a 2D point on the screen to a 3d point that intersects
	 * the board (ie the XZ plane)
	 * 
	 * @param point 2D point
	 * @param renderer 
	 * @return A point whose value represents the x and z intersection at the XZ plane
	 */
	public Point2D.Float getClick(Point2D.Float point, Renderer renderer) {
		//invert y position
		point.y = screenLength - point.y;
		float[] rayStart = new float[3]; //the start of our click
		glu.gluUnProject(point.x, point.y, 0, 
					renderer.getModelViewMatrix(), 0, 
					renderer.getProjectionMatrix(), 0, 
					renderer.getViewportDimensions(), 0, 
					rayStart, 0);
		float[] rayDir = new float[3]; //the start of our click at z=1
		glu.gluUnProject(point.x, point.y, 1,
					renderer.getModelViewMatrix(), 0, 
					renderer.getProjectionMatrix(), 0, 
					renderer.getViewportDimensions(), 0, 
					rayDir, 0);
		rayDir[0] -= rayStart[0];
		rayDir[1] -= rayStart[1];
		rayDir[2] -= rayStart[2];
		
		Point2D.Float retVal = new Point2D.Float();
	    retVal.x = (float) (rayStart[0] - rayDir[0] * rayStart[1] / rayDir[1]);
	    retVal.y = (float) (rayStart[2] - rayDir[2] * rayStart[1] / rayDir[1]);
		return retVal;
	}
	
	/**
	 * Method that is called during animation, the camera allows
	 * animation around it's horizontal rotation
	 */
	@Override
	public void setValue(String fieldName, float value) {
		if (fieldName.equals("horizontalRotation"))
			horizontalRotation = value;
	}
	
	/*
	 * ------------------------
	 * Setters and Getters
	 * ------------------------
	 */

	public void setScreenLength(int length) {
		screenLength = length;
	}
	
	public void setHorizontalRotation(float val) {
		horizontalRotation = val;
	}
	
	public float getHorizontalRotation() {
		return horizontalRotation;
	}
	
}
