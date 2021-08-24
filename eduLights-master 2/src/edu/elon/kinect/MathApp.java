package edu.elon.kinect;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import edu.elon.lights.LightController;

public class MathApp extends PositionTracking {
	
	private String mathProblem;
	private int answer;
	private static final String[] OPS = {"+", "-", "*"};
	private static final int NUM_OPS = 3;
	private static final int RANGE = 20;
	private static final int THRESHOLD = 200;
	private boolean solved = false;
	private boolean currSolving = false;
	private int leftRight;
	
	
	public MathApp() {
		super();
		mathProblem = "";
		answer = 0;
		
	}

	public void draw() {
		context.update();

		while (!controller.isConnected()) { 
			//wait
		}
		
		if (solved == true) {
			solved = false;
			try {
				System.out.println("ABOUT TO SLEEP");
				TimeUnit.SECONDS.sleep(7);
				System.out.println("SLEEP");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		controller.turnOffLeftLight();
		controller.turnOffRightLight();
		
		if (currSolving == false) {
			
			currSolving = true;
			printMathProblem();
			
			while (solved == false) {
				context.update();
				for (int user : users) {
					context.getCoM(user, pos);
					if (pos.x > THRESHOLD) {
						if (leftRight == 0) {
							controller.turnOnLeft(LightController.GREEN);
							solved = true;
							currSolving = false;
						
						} else {
							controller.turnOnLeft(LightController.RED);
						}
					} else if (pos.x < -THRESHOLD) {
						if (leftRight == 1) {
							controller.turnOnRight(LightController.GREEN);
							solved = true;
							currSolving = false;
						} else {
							controller.turnOnRight(LightController.RED);
						}
					}
				}
				
			}
			
			
		}
		
	}
	

	
	public void printMathProblem() {
		Random rand = new Random();
		leftRight = rand.nextInt(2);
		
		
		System.out.println(generateMathProblem());
		int newAnswer = answer + 3;
		if (leftRight == 0) {
			System.out.println("Walk to the left light if you think the answer is " + answer);
			System.out.println("Walk to the right light if you think the answer is " + newAnswer);
		} else {
			System.out.println("Walk to the left light if you think the answer is " + newAnswer);
			System.out.println("Walk to the right light if you think the answer is " + answer);
		}
		
	}
	
	public String generateMathProblem() {
		int num1;
		int num2;
		int opIndex;
		Random rand = new Random();
		
		//Both numbers random between 0 and 20
		num1 = rand.nextInt(RANGE + 1);
		num2 = rand.nextInt(RANGE + 1);
		opIndex = rand.nextInt(NUM_OPS);
		String op = OPS[opIndex];
		
		mathProblem = "What is " + num1 + " " + op + " " + num2 + "?";
		
		if (op.equals("+")) {
			answer = num1 + num2;
		} else if (op.equals("-")) {
			answer = num1 - num2;
		} else if (op.equals("*")) {
			answer = num1 * num2;
		}
		
		
		return mathProblem;
		
	}

}
