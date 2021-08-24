package edu.elon.leap;

import edu.elon.lights.LightCommand;
import edu.elon.lights.LightController.LightColors;

public class Test {
	
	public static void main(String[] args) {
		LightCommand controller = new LightCommand();
		while (!controller.isConnected()) {
			//wait
		}
		
		System.out.println(LightColors.YELLOW.ordinal());
		
	}

}
