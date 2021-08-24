package edu.elon.lights;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class LightController {
	
	private PHHueSDK sdk;
	
    private static final int MAX_HUE = 65535;
    public static final int GREEN = 25500;
	public static final int RED = 0;
	
	public enum LightColors {
		RED, YELLOW, BLUE, WHITE, DARK, ORANGE,
		PURPLE, PINK, GREEN, L_YELLOW ,D_YELLOW,
		L_BLUE, D_BLUE, D_ORANGE, L_ORANGE, L_PURPLE, D_PURPLE, RED_ORANGE,
		YELLOW_ORANGE, MAGENTA, L_GREEN, ADD, SUB, RESET
	}
	
	Map<LightColors, Color> colorTable = new HashMap<LightColors, Color>() {{
		put(LightColors.WHITE, Color.WHITE);
		put(LightColors.PINK, Color.PINK);
		put(LightColors.RED, Color.RED);
		
		put(LightColors.L_BLUE, new Color(18, 137, 188));
		put(LightColors.BLUE, new Color(45, 49, 255));
		put(LightColors.D_BLUE, new Color(10, 22, 150));
		

		put(LightColors.L_ORANGE, new Color(255, 200, 0));
		put(LightColors.ORANGE, new Color(255, 180, 0));
		put(LightColors.D_ORANGE, new Color(255, 120, 0));
		put(LightColors.RED_ORANGE, new Color(255, 80, 0));
		
		put(LightColors.L_GREEN, new Color(122, 255, 170));
		put(LightColors.GREEN, Color.GREEN);
		
		put(LightColors.L_YELLOW, new Color(250, 252, 108));
		put(LightColors.YELLOW, new Color(255, 228, 28));
		put(LightColors.D_YELLOW, new Color(229, 211, 17));
		put(LightColors.YELLOW_ORANGE, new Color(255, 195, 0));
		
		put(LightColors.L_PURPLE, new Color(232, 154, 237));
		put(LightColors.PURPLE, new Color(203, 84, 255));
		put(LightColors.D_PURPLE,new Color(174, 0, 255));
		put(LightColors.MAGENTA, Color.MAGENTA);
		
	}};
	
	
	public LightController(PHBridge bridge, PHHueSDK sdk) {
		this.sdk = sdk;
	}
	
	public void turnOnRandomHue(PHLight light) {
		PHBridge bridge = sdk.getSelectedBridge();
//        PHBridgeResourcesCache cache = bridge.getResourceCache();

//        List<PHLight> allLights = cache.getAllLights();
        Random rand = new Random();

      
        PHLightState lightState = new PHLightState();
        lightState.setOn(true);
        lightState.setHue(rand.nextInt(MAX_HUE));
        bridge.updateLightState(light, lightState); // If no bridge response is required then use this simpler form.
	}
	
	public void turnOffLight(PHLight light) {
		PHBridge bridge = sdk.getSelectedBridge();
		
		PHLightState lightState = new PHLightState();
		lightState.setOn(false);
		bridge.updateLightState(light, lightState);
	}
	
	public void turnOnAllLights(ArrayList<PHLight> lights) {
		for (PHLight light : lights) {
			turnOnRandomHue(light);
		}
	}	
	
	public void turnLightColor(PHLight light, Integer value) {
	  PHBridge bridge = sdk.getSelectedBridge();
      Random rand = new Random();

      PHLightState lightState = new PHLightState();
      lightState.setOn(true);
      lightState.setHue(value);
      bridge.updateLightState(light, lightState);
	}
	

	
	public void turnLightRGB(PHLight light,int r, int g, int b){
		PHBridge bridge = sdk.getSelectedBridge();
		float xy[] = PHUtilities.calculateXYFromRGB(r, g, b, light.getModelNumber());
		PHLightState lightState = new PHLightState();
		lightState.setOn(true);
		lightState.setX(xy[0]);
		lightState.setY(xy[1]);
		bridge.updateLightState(light, lightState);
	}
	
	public void turnLightColor(PHLight light, LightColors color) {
		if (color == LightColors.DARK) {
			turnOffLight(light);
		} else {
			Color c = colorTable.get(color);
			turnLightRGB(light, c.getRed(), c.getGreen(), c.getBlue());
		}
		
		
		
		
	}

}
