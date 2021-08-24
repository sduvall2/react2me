package edu.elon.kinect;

public class ClosestLight extends PositionTracking {

	private static final int MID_X = 0;
	
	public ClosestLight() {
		super();
	}
	
	public void draw() {
		context.update();

		for (int user : users) {
			context.getCoM(user, pos);
			if (pos.x > MID_X && current == false) {
				System.out.println("Greater than midpoint");
				
				controller.turnOnRandomLeft();
				controller.turnOffRightLight();
				current = true;
			}
			if (pos.x < MID_X && current == true) {
				controller.turnOffLeftLight();
				controller.turnOnRandomRight();
				current = false;
			}
		}
	}
	
}
