package edu.elon.leap;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leapmotion.leap.*;

import edu.elon.lights.LightCommand;
import edu.elon.lights.LightController.LightColors;

public class LeapTracking extends PApplet {

	private LightCommand lightController = new LightCommand();
	LightColors newColor;
	float width = 300;
	float leftX = -150;
	float topY = 360;
	float height = 200;
	float rightX = 150;
	float bottomY = 160;
	
	LightColors colors[][] = { { LightColors.RED, LightColors.RESET }, { LightColors.YELLOW, LightColors.DARK },
			{ LightColors.BLUE, LightColors.WHITE }, { LightColors.ADD, LightColors.SUB } };

	LightColors[] values = LightColors.values();

	ArrayList<LightColors> possColors = new ArrayList<>();

	List<LightColors> possOps = new ArrayList<>(Arrays.asList(LightColors.SUB, LightColors.ADD));

	double lastTime;
	LightColors color = null;
	LightColors prevColor = null;
	LightColors colorToChange = null;
	LightColors currLightColor = LightColors.WHITE;

	private Controller controller = new Controller();

	private ArrayList<LightColors> sequence = new ArrayList<>();

	LightColors addTable[][] = {
			{ LightColors.RED, LightColors.ORANGE, LightColors.PURPLE, LightColors.PINK, LightColors.RED,
					LightColors.RED_ORANGE, LightColors.MAGENTA, LightColors.RED, LightColors.RED, LightColors.L_ORANGE,
					LightColors.D_ORANGE, LightColors.L_PURPLE, LightColors.D_PURPLE, LightColors.RED_ORANGE,
					LightColors.PINK, LightColors.PINK, LightColors.PURPLE, LightColors.RED, LightColors.ORANGE,
					LightColors.RED, LightColors.GREEN },
			{ LightColors.ORANGE, LightColors.YELLOW, LightColors.GREEN, LightColors.L_YELLOW, LightColors.D_YELLOW,
					LightColors.YELLOW_ORANGE, LightColors.L_PURPLE, LightColors.L_ORANGE, LightColors.L_GREEN,
					LightColors.YELLOW, LightColors.D_YELLOW, LightColors.L_GREEN, LightColors.BLUE,
					LightColors.D_YELLOW, LightColors.L_YELLOW, LightColors.L_PURPLE, LightColors.D_PURPLE,
					LightColors.ORANGE, LightColors.YELLOW, LightColors.D_ORANGE, LightColors.L_GREEN },
			{ LightColors.PURPLE, LightColors.GREEN, LightColors.BLUE, LightColors.L_BLUE, LightColors.D_BLUE,
					LightColors.D_ORANGE, LightColors.D_PURPLE, LightColors.L_PURPLE, LightColors.BLUE,
					LightColors.L_GREEN, LightColors.GREEN, LightColors.BLUE, LightColors.D_BLUE, LightColors.D_ORANGE,
					LightColors.L_ORANGE, LightColors.L_BLUE, LightColors.D_BLUE, LightColors.RED, LightColors.ORANGE,
					LightColors.L_PURPLE, LightColors.L_BLUE

			}, { LightColors.PINK, LightColors.L_YELLOW, LightColors.L_BLUE, LightColors.WHITE, LightColors.WHITE, 
				LightColors.L_ORANGE, LightColors.L_PURPLE, LightColors.WHITE, LightColors.L_GREEN, LightColors.WHITE, 
				LightColors.YELLOW, LightColors.WHITE, LightColors.BLUE, LightColors.ORANGE, LightColors.WHITE, 
				LightColors.WHITE, LightColors.PURPLE, LightColors.L_ORANGE, LightColors.YELLOW, LightColors.PINK, 
				LightColors.WHITE

			}, { LightColors.RED, LightColors.D_YELLOW, LightColors.D_BLUE, LightColors.DARK, LightColors.DARK, 
				LightColors.D_ORANGE, LightColors.D_PURPLE, LightColors.RED, LightColors.GREEN, LightColors.YELLOW, 
				LightColors.DARK, LightColors.BLUE, LightColors.DARK, LightColors.DARK, LightColors.ORANGE, 
				LightColors.PURPLE, LightColors.DARK, LightColors.ORANGE, LightColors.ORANGE, LightColors.RED,
				LightColors.GREEN } };

	LightColors[][] subTable = {
			{ LightColors.WHITE, LightColors.RED, LightColors.BLUE, LightColors.WHITE, LightColors.WHITE,
					LightColors.YELLOW, LightColors.BLUE, LightColors.WHITE, LightColors.GREEN, LightColors.L_YELLOW,
					LightColors.D_YELLOW, LightColors.L_BLUE, LightColors.D_BLUE, LightColors.D_YELLOW,
					LightColors.L_YELLOW, LightColors.L_BLUE, LightColors.D_BLUE, LightColors.ORANGE,
					LightColors.YELLOW, LightColors.PURPLE, LightColors.L_GREEN },
			{ LightColors.RED, LightColors.WHITE, LightColors.BLUE, LightColors.WHITE, LightColors.WHITE,
					LightColors.RED, LightColors.PURPLE, LightColors.PINK, LightColors.BLUE, LightColors.WHITE,
					LightColors.D_YELLOW, LightColors.L_BLUE, LightColors.D_BLUE, LightColors.RED, LightColors.PINK,
					LightColors.L_PURPLE, LightColors.D_PURPLE, LightColors.RED, LightColors.ORANGE,
					LightColors.MAGENTA, LightColors.L_BLUE, },
			{ LightColors.RED, LightColors.YELLOW, LightColors.WHITE, LightColors.WHITE, LightColors.WHITE,
					LightColors.ORANGE, LightColors.RED, LightColors.PINK, LightColors.YELLOW, LightColors.YELLOW,
					LightColors.D_YELLOW, LightColors.WHITE, LightColors.D_BLUE, LightColors.D_ORANGE,
					LightColors.L_ORANGE, LightColors.PINK, LightColors.RED, LightColors.RED_ORANGE,
					LightColors.YELLOW_ORANGE, LightColors.RED, LightColors.L_YELLOW },
			{ LightColors.RED, LightColors.D_YELLOW, LightColors.D_BLUE, LightColors.DARK, LightColors.WHITE,
					LightColors.D_ORANGE, LightColors.D_PURPLE, LightColors.RED, LightColors.GREEN, LightColors.YELLOW,
					LightColors.D_YELLOW, LightColors.BLUE, LightColors.D_BLUE, LightColors.D_ORANGE,
					LightColors.ORANGE, LightColors.PURPLE, LightColors.D_PURPLE, LightColors.RED_ORANGE,
					LightColors.YELLOW_ORANGE, LightColors.PURPLE, LightColors.GREEN, },
			{ LightColors.PINK, LightColors.L_YELLOW, LightColors.L_BLUE, LightColors.WHITE, LightColors.WHITE,
					LightColors.L_ORANGE, LightColors.L_PURPLE, LightColors.WHITE, LightColors.L_GREEN,
					LightColors.WHITE, LightColors.YELLOW, LightColors.WHITE, LightColors.BLUE, LightColors.ORANGE,
					LightColors.WHITE, LightColors.WHITE, LightColors.PURPLE, LightColors.ORANGE, LightColors.YELLOW,
					LightColors.PINK, LightColors.WHITE } };
	
	Map<LightColors, LightColors[][]> colorMaps = new HashMap<LightColors, LightColors[][]>(){{
		put(LightColors.ADD, addTable);
		put(LightColors.SUB, subTable);
	}};

	public void setup() {
		size(250, 450);

		possColors.addAll(Arrays.asList(LightColors.values()));

		possColors.remove(LightColors.ADD);
		possColors.remove(LightColors.SUB);
		possColors.remove(LightColors.RESET);
		System.out.println(possColors);
	}

	public void draw() {
		background(0);
		Frame frame = controller.frame();
		text(frame.hands().count() + " Hands", 50, 50);
		text(frame.fingers().count() + " Fingers", 50, 100);
		Finger pointable = frame.fingers().frontmost();
		float x = 0f;
		float y = pointable.stabilizedTipPosition().getY();
		if (pointable.isExtended()) {
			// System.out.println(pointable.stabilizedTipPosition());
			x = pointable.stabilizedTipPosition().getX();
		} else {
			lastTime = System.currentTimeMillis();
			if (color != null) {
				prevColor = color;
			}
			color = null;
		}

		if (x >= leftX && x <= rightX && y <= topY && y >= bottomY) {
			int colorX = Math.abs((int) ((x - leftX) / (width / 4)));
			int colorY = Math.abs((int) ((topY - y) / (height / 2)));
			newColor = colors[colorX][colorY];
			if (color != newColor) {
				prevColor = color;
				color = newColor;
				lastTime = System.currentTimeMillis();
			}
			if (System.currentTimeMillis() - lastTime > 400) {
				colorToChange = color;

				addToSequence(colorToChange);
			}
		} else if (x > rightX + 50) {
			System.out.println("performing..");
			performSequence();
		}

		text("LIGHT COLOR: " + currLightColor, 50, 200);
		text("Color: " + color, 50, 150);
		text("Color to change: " + colorToChange, 50, 250);
		text("Elapsed time on one color: " + (System.currentTimeMillis() - lastTime), 50, 300);
		text("Sequence " + sequence, 50, 350);
		text("newColor: " + newColor, 50, 400);

	}

	private void performSequence() {

		if (sequence.size() == 1) {
			// Reset
			if (sequence.get(0).equals(LightColors.RESET)) {
				currLightColor = LightColors.WHITE;
				sequence.clear();
			} else {
				// Set to current color
				currLightColor = sequence.get(0);
			}
		} else if (sequence.size() == 2) {
			currLightColor = sequence.get(0);
		} else if (sequence.size() == 3) {
			LightColors color1 = sequence.get(0);
			LightColors op = sequence.get(1);
			LightColors color2 = sequence.get(2);
			LightColors[][] table = colorMaps.get(op);

			newColor = table[color2.ordinal()][color1.ordinal()];
			if (newColor != null) {
				currLightColor = newColor;
			}
			sequence.clear();
			sequence.add(currLightColor);

		}
		lightController.turnOnMainLamp(currLightColor);
	}

	private void addToSequence(LightColors color) {
		if (sequence.isEmpty() && possOps.contains(color)) {
			// do nothing because sequence can't start with an operation
			return;
		} else if (color.equals(LightColors.RESET)) {
			sequence.clear();
			sequence.add(color);
		} else if (sequence.isEmpty() && !possOps.contains(color)) {
			sequence.add(color);
		} else if (sequence.get(sequence.size() - 1).equals(color)) {
			return;
		} else if ((possColors.contains(sequence.get(sequence.size() - 1)) && possColors.contains(color))
				|| (possOps.contains(sequence.get(sequence.size() - 1)) && possOps.contains(color))) {
			System.out.println("REPLACING..");
			sequence.set(sequence.size() - 1, color);
		} else if (sequence.size() == 1 && sequence.get(0) == LightColors.RESET) {
			if (possOps.contains(color))
				return;
			sequence.set(0, color);
		}

		else if (sequence.size() < 3) {
			System.out.println("NOT REPLACING...");
			sequence.add(color);
		}
	}

}
