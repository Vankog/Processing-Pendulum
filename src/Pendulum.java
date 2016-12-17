import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by Vankog on 17.12.2016.
 */
public class Pendulum extends PApplet {
	final int sizeX = 640;
	final int sizeY = 480;

	PVector origin;
	PVector bob;
	float len;

	float angle = PI / 2;
	float angVel = 0.01f;
	float angAcc = 0f;
	float amp = 0.01f;
	float period = 200f;

	float gravity = 0.01f;

	public static void main(String[] args) {
		PApplet.main("Pendulum", args);
	}

	//setup screen
	public void settings() {
		size(sizeX, sizeY);
	}

	public void setup() {
		len = width / 2 - 30;
		origin = new PVector(0, 0);
		bob = new PVector(0, len);

		//SOHCAHTOA
		// sin(angle) = bob.y/len;
		// -> asin(bob.y/len);
		//angle = asin(bob.y/len);
	}

	public void mouseClicked() {
	}

	public void draw() {
		background(0);
		translate(width / 2, 0);

		stroke(255);
		fill(255);

		// oscillation -> amp × sin(theta) -> theta could be: frame / period × 2Pi:
		//float x = amp * sin(frameCount/period * TWO_PI);
		// simplified theta:
		//float x = amp * sin(angle);

		angAcc += getAngForceFromGravity(angle, gravity);
		angVel += angAcc;
		angle -= angVel;

		//reset acceleration for next loop
		angAcc = 0;

		text("angle+: " + angle, 0, 70, 10);

		// sin(angle) = bob.x/len; -> angle from y-axis
		// -> bob.x = sin(angle) * len;
		bob.x = sin(angle) * len;
		bob.y = cos(angle) * len;

		// sin(angle) = bob.x/len;
		// -> asin(bob.x/len);


		line(origin.x, origin.y, bob.x, bob.y);
		ellipse(bob.x, bob.y, 32, 32);
	}

	static float getAngForceFromGravity(float angle, float gravity) {
		// ang force must be perpendicular to pendulum -> right triangle between gravity and pendulum with angle to gravity = angle of pendulum
		// sin(angle) = F(angular) / F(force)
		// -> F(ang) = sin(angle) × F(force)
		float angForce = gravity * sin(angle);
		return angForce;
	}
}
