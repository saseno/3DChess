package controller.util;

/**
 * Defines a basic linear interpolation animation, which is updated
 * by a delta_t in the gameloop. This class works by calling
 * the setValue() method of the Animatable object passed in
 * 
 * @author Nicholas
 *
 */
public class Animation implements Comparable<Animation> {

	private Animatable object;
	private String fieldName;
	
	private float value;
	private float endValue;
	
	private float valueStep;
	
	/**
	 * Constructs an Animation object that will continually set the value
	 * of the object passed in.
	 * 
	 * @param _object Object on which the animation is being performed on
	 * @param _name The name of the field in the object that is being updated
	 * @param _startVal The starting value of the object's field
	 * @param _endVal The ending value of the object's field
	 * @param _time The time of the animation
	 */
	public Animation(Animatable _object, String _fieledName, float _startVal, float _endVal, float _time) {
		object = _object;
		fieldName = _fieledName;
		
		value = _startVal;
		endValue = _endVal;
		
		valueStep = (_endVal - _startVal) / _time;
		_object.setValue(_fieledName, _startVal);
	}
	
	/**
	 * Steps this animation by _deltaTime, which will result in the object's
	 * value being updated by +valueStep
	 * 
	 * @param _deltaTime The time step
	 * @return true if the animation has finished, false otherwise
	 */
	public boolean stepAnimation(float _deltaTime) {
		//update value
		value += valueStep * _deltaTime;
		
		//determine if the animation has passed the endValue
		float val_sign = Math.signum(valueStep);
		if (value * val_sign >= endValue * val_sign) {
			object.setValue(fieldName, endValue);
			return true;
		}
		
		//set the field of the object to value
		object.setValue(fieldName, value);
		return false;
	}
	
	@Override
	public int compareTo(Animation arg0) {
		return hashCode() - arg0.hashCode();
	}
	
	@Override
	public boolean equals(Object arg0) {
		return hashCode() == arg0.hashCode();
	}
	

	/**
	 * Interface that must be implemented by any object 
	 * that wants to be animated.
	 * 
	 * @author Nicholas
	 *
	 */
	public interface Animatable {
		
		void setValue(String fieldName, float value);
		
	}
	
}
