package org.usfirst.frc.team131.robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;

public class TestVictors {
	
	Victor six;
	Victor seven;
	Victor eight;
	Victor nine;
	
	SpeedControllerGroup tests;
	
	public TestVictors () {
		six = new Victor (PortConstants.TEST_VICTOR_A);
		seven = new Victor (PortConstants.TEST_VICTOR_B);
		eight = new Victor (PortConstants.TEST_VICTOR_C);
		nine = new Victor (PortConstants.TEST_VICTOR_D);
		
		tests = new SpeedControllerGroup(six, seven, eight, nine);
	}
	
	public void setSpeed (double speed) {
		tests.set(speed);
	}

}
