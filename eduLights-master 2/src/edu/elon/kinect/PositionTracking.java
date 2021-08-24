package edu.elon.kinect;

import java.util.ArrayList;

import com.philips.lighting.model.PHLight;

import SimpleOpenNI.*;
import controlP5.*;
import edu.elon.lights.LightCommand;
import g4p_controls.G4P;
import g4p_controls.GButton;
import g4p_controls.GCScheme;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import processing.core.*;
import java.awt.*;

public abstract class PositionTracking extends PApplet{

	protected SimpleOpenNI context;
	protected ArrayList<Integer> users = new ArrayList<>();
	protected PVector pos = new PVector();
//	private static final int MID_X = 0;
	protected LightCommand controller;
	protected boolean current = false;
	
	private ArrayList<PHLight> lights;
	
	public void setup() {
		size(750,500, JAVA2D);
		context = new SimpleOpenNI(this);
		if (context.isInit() == false) {
			System.out.println("Can't init SimpleOpenNI, check camera.");
			exit();
			return;
		}
		
		context.setMirror(false);
		context.enableDepth();
		context.enableUser();

		controller = new LightCommand();
	}
	
	public abstract void draw();
//		context.update();
//
//		for (int user : users) {
//			context.getCoM(user, pos);
//			if (pos.x > MID_X && current == false) {
//				System.out.println("Greater than midpoint");
//				
//				controller.turnOnRandomLeft();
//				controller.turnOffRightLight();
//				current = true;
//			}
//			if (pos.x < MID_X && current == true) {
//				controller.turnOffLeftLight();
//				controller.turnOnRandomRight();
//				current = false;
//			}
//		}
	
	
   public void onNewUser(SimpleOpenNI curContext, int userId) {
		users.add(userId);
		curContext.startTrackingSkeleton(userId);
		System.out.println("new user");
	}
   
}