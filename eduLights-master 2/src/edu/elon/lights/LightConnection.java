package edu.elon.lights;

import java.util.ArrayList;
import java.util.List;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHHueParsingError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import edu.elon.lights.data.LightsProperties;

public class LightConnection {

	private PHHueSDK sdk;
	private PHBridge bridge;
	
	public LightConnection() {
		this.sdk = PHHueSDK.getInstance();
		bridge = sdk.getSelectedBridge();
	}
	
	public LightController getLightController() {
		LightController controller = new LightController(bridge, sdk);
		return controller;
	}
	
	public List<PHLight> getLights() {
		bridge = sdk.getSelectedBridge();
		PHBridgeResourcesCache cache = bridge.getResourceCache();
		return cache.getAllLights();
	}
	
	public void findBridges() {
		sdk = PHHueSDK.getInstance();
        PHBridgeSearchManager sm = (PHBridgeSearchManager) sdk.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);
	}
	
	public boolean connect() {
//		String username = LightsProperties.getUsername();
//		String ipAddress = LightsProperties.getLastConnectedIP();
		
		String username = "g0mhLbTmUabDf9a8lJdfSvjJXjSX7g113xXKCQ83";
		String ipAddress = "192.168.1.125";
		
		if (username == null || ipAddress == null) {
			System.out.println("Missing last username or ip address");
			return false;
		}
		
		PHAccessPoint accessPoint = new PHAccessPoint();
		accessPoint.setIpAddress(ipAddress);
		accessPoint.setUsername(username);
		sdk.connect(accessPoint);
		return true;
	}
	
	private PHSDKListener listener = new PHSDKListener() {

		@Override
		public void onAccessPointsFound(List<PHAccessPoint> accessPointsList) {
			// TODO Auto-generated method stub
//			PHHueSDK phHueSDK = PHHueSDK.getInstance();
//            phHueSDK.connect(accessPointsList.get(0));
		}

		@Override
		public void onAuthenticationRequired(PHAccessPoint accessPoint) {
			// TODO Auto-generated method stub
			System.out.println("Press pushlink.");
			sdk.startPushlinkAuthentication(accessPoint);
			
		}

		@Override
		public void onBridgeConnected(PHBridge bridge, String username) {
			System.out.println("BRIDGE CONNECTED");
			sdk.setSelectedBridge(bridge);
			sdk.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL);
			String lastIp = bridge.getResourceCache().getBridgeConfiguration().getIpAddress();
			LightsProperties.storeUsername(username);
			LightsProperties.storeLastIPAddress(lastIp);
			LightsProperties.saveProperties();
			System.out.println("LAST IP ADDRESS: " + lastIp);
			System.out.println("LAST USER NAME: " + username);
			
		}

		@Override
		public void onCacheUpdated(List<Integer> arg0, PHBridge arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConnectionLost(PHAccessPoint arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConnectionResumed(PHBridge arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onError(int arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onParsingErrors(List<PHHueParsingError> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public PHSDKListener getListener() {
		return listener;
	}
	
	public void setListener(PHSDKListener listener) {
		this.listener = listener;
	}
	
}
