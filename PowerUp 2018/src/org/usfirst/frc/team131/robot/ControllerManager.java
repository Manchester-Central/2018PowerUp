package org.usfirst.frc.team131.robot;

public class ControllerManager {
	
	public Controller driver;
	public Controller operator;
	
	public ControllerManager () {
		
		driver = new Controller (0);
		operator = new Controller (1);
		
	}
	
	public void updateControllerState () {
		
		driver.updateButtonsPressed();
		operator.updateButtonsPressed();
		
	}
	
}
