package edu.elon.lights;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHLight;

import edu.elon.lights.LightController.LightColors;
import edu.elon.lights.data.LightsProperties;

public class LightCommand {

	private PHHueSDK sdk;
	private LightController controller;
	private LightConnection connection;
	private List<PHLight> lights;
	private PHLight leftLight;
	private PHLight rightLight1;
	private PHLight rightLight2;
	
	public LightCommand() {
		
		
		sdk = PHHueSDK.create();
		LightsProperties.loadProperties();
		
		connection = new LightConnection();
		sdk.getNotificationManager().registerSDKListener(connection.getListener());
		connection.findBridges();
		connection.connect();
		
		
		
		
		controller = connection.getLightController();
	}
	
	public void turnOnRandomLeft() {
		List<PHLight> lights = connection.getLights();
				
		controller.turnOnRandomHue(lights.get(2));
	}
	
	public boolean isConnected () {
		List<PHLight> lights = null;
		try {
			lights = connection.getLights();
		} catch (NullPointerException e) {
			return false;
		}
		return connection.getLights() != null;
	}
	
	public void turnOnRandomRight() {
		List<PHLight> lights = connection.getLights();
		
		controller.turnOnRandomHue(lights.get(1));
	}
	
	public void turnOffLeftLight() {
		List<PHLight> lights = connection.getLights();
		
		controller.turnOffLight(lights.get(2));
	}
	
	public void turnOffRightLight() {
        List<PHLight> lights = connection.getLights();
		
		controller.turnOffLight(lights.get(1));
	}
	
	public void turnOnAllRandom() {
		ArrayList<PHLight> lights = (ArrayList<PHLight>) connection.getLights();
		controller.turnOnAllLights(lights);
	}
	
	public void turnOnLeft(Integer color) {
		List<PHLight> lights = connection.getLights();
		
		controller.turnLightColor(lights.get(2), color);
	}
	
	public void turnOnRight(Integer color) {
		List<PHLight> lights = connection.getLights();
		
		controller.turnLightColor(lights.get(1), color);
	}
	
	public void turnOnMainLamp(int r, int g, int b) {
		List<PHLight> lights = connection.getLights();
		
		controller.turnLightRGB(lights.get(0), r, g, b);
	}
	
	public void turnOnMainLamp(Color c) {
		List<PHLight> lights = connection.getLights();
		
		controller.turnLightRGB(lights.get(0), c.getRed(), c.getGreen(), c.getBlue());
	}
	
	public void turnOnMainLamp(LightColors color) {

		List<PHLight> lights = connection.getLights();
		controller.turnLightColor(lights.get(0), color);
	}
}
