package org.usfirst.frc.team131.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controller {
	public static final int LEFT_TRIGGER = 7;
	public static final int RIGHT_TRIGGER = 8;

	public static final int LEFT_BUMPER = 5;
	public static final int RIGHT_BUMPER = 6;

	public static final int SELECT_BUTTON = 9;
	public static final int START_BUTTON = 10;

	public static final int LEFT_X = 1;
	public static final int DOWN_A = 2;
	public static final int RIGHT_B = 3;
	public static final int UP_Y = 4;

	
	public static enum DPadDirection {
		LEFT, RIGHT, UP, DOWN, UP_RIGHT, DOWN_RIGHT, UP_LEFT, DOWN_LEFT, NONE
	}

	private Joystick stick;
	private boolean[] buttonsPreviouslyPressed;
	
	public Controller(int port) {
		
		stick = new Joystick(port);
		

		boolean[] initialState = {false, false, false, false, false, false, false, false, false, false};
		buttonsPreviouslyPressed = initialState;
		
		
	}

	
	public double getLeftX() {
		return stick.getRawAxis(0);
	}

	
	public double getLeftY() {
		return stick.getRawAxis(1);
	}

	
	public double getRightX() {
		return stick.getRawAxis(2);
	}

	
	public double getRightY() {
		return stick.getRawAxis(3);
	}

	
	public boolean buttonPressed(int buttonNum) {
		return stick.getRawButton(buttonNum);
	}

	
	public DPadDirection getDPad() {
		int pov = stick.getPOV();
		switch (pov) {
		case 0:
			return DPadDirection.UP;
		case 90:
			return DPadDirection.RIGHT;
		case 180:
			return DPadDirection.DOWN;
		case 270:
			return DPadDirection.LEFT;
		default:
			return DPadDirection.NONE;

		}
	}
	
	public int getRawDPadInput () {
		return stick.getPOV();
	}
	
	@Deprecated
	public boolean getButtonTapped (int buttonCode) {
		
		return !buttonsPreviouslyPressed[buttonCode - 1] && buttonPressed(buttonCode);
		
	}
	
	@Deprecated
	public boolean getButtonReleased (int buttonCode) {
		
		return buttonsPreviouslyPressed[buttonCode - 1] && !buttonPressed(buttonCode);
		
		
	}
	
	@Deprecated
	public void updateButtonsPressed () {
		
		for (int x = 0; x < buttonsPreviouslyPressed.length; x++) {
			
			buttonsPreviouslyPressed[x] = getButtonTapped(x);
			
		}
		
	}

}
