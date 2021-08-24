package edu.elon.kinect;

public class BodyMovement extends PositionTracking {
	
	private float lastPosition;
	
	public BodyMovement() {
		super();
		lastPosition = pos.x;
	}

	@Override
	public void draw() {
		context.update();
	

		for (int user : users) {
			context.getCoM(user, pos);
			
			if (Math.abs(pos.x - lastPosition) > 150) {
				lastPosition = pos.x;
				controller.turnOnAllRandom();
				System.out.println(lastPosition);
			}
		}
	}
}
