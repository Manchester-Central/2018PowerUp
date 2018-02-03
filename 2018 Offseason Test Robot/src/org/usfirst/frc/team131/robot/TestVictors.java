package org.usfirst.frc.team131.robot;

import edu.wpi.first.wpilibj.Victor;

public class TestVictors {
	
	Victor six;
	Victor seven;
	Victor eight;
	Victor nine;
	
	public TestVictors () {
		six = new Victor (PortConstants.TEST_VICTOR_A);
		seven = new Victor (PortConstants.TEST_VICTOR_B);
		eight = new Victor (PortConstants.TEST_VICTOR_C);
		nine = new Victor (PortConstants.TEST_VICTOR_D);
	}
	
	public void setSpeed (double speed) {
		six.set(speed);
		seven.set(speed);
		eight.set(speed);
		nine.set(speed);
	}

}
