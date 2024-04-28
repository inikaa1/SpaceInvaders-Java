package invaders.physics;

import invaders.prototype.Prototype;

/**
 * A utility class for storing position information
 */
public class Vector2D implements Prototype {

	private double x;
	private double y;

	public Vector2D(double x, double y){
		this.x = x;
		this.y = y;
	}

	public Prototype clone() {
		double x = this.x;
		double y = this.y;

		return new Vector2D(x, y);
	}

	public double getX(){
		return this.x;
	}

	public double getY(){
		return this.y;
	}

	public void setX(double x){
		this.x = x;
	}

	public void setY(double y){
		this.y = y;
	}
}
