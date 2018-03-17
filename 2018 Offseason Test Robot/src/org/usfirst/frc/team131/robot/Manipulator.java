package org.usfirst.frc.team131.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Manipulator {
	
	DoubleSolenoid extender;
	//DoubleSolenoid pincher;
	DoubleSolenoid pincher1;
	DoubleSolenoid pincher2;
	
	public Manipulator() {
		extender = new DoubleSolenoid(PortConstants.CUBE_EXTENDER_A, PortConstants.CUBE_EXTENDER_B);
		
		//pincher = new DoubleSolenoid(PortConstants.CUBE_PINCHER_A, PortConstants.CUBE_PINCHER_B);
		pincher1 = new DoubleSolenoid (PortConstants.CUBE_GRABBER_A, PortConstants.CUBE_GRABBER_B);
		pincher2 = new DoubleSolenoid (PortConstants.CUBE_RELEASE_A, PortConstants.CUBE_RELEASE_B);
	
	}
	
	/**
	 * extends claw only if it is above the intake position
	 */
	public void extend () {
		//if (lift.liftPosition() >= LinearLift.EXTEND_POSITION_INCHES - LinearLift.DEADBAND_INCHES) {
			extender.set(Value.kForward);
		//}
	}
	
	/**
	 * retracts claw only if it is above the intake position
	 */
	public void retract () {
		//if (lift.liftPosition() >= LinearLift.EXTEND_POSITION_INCHES - LinearLift.DEADBAND_INCHES) {
			extender.set(Value.kReverse);
		//}
	}
	
	public void pinch () {
		//pincher.set(Value.kForward);
		pincher1.set(Value.kForward);
		pincher2.set(Value.kReverse);
	}
	
	public void release () {
		//pincher.set(Value.kReverse);
		pincher1.set(Value.kReverse);
		pincher2.set(Value.kForward);
	}
	
	public void relax () {
		pincher1.set(Value.kReverse);
		pincher2.set(Value.kReverse);
	}
	
	
	public boolean isPinched() {
		return pincher1.get() == Value.kForward && pincher2.get() == Value.kReverse;
		//return pincher.get().equals(Value.kForward);
		
	}
	
	public boolean isExtended () {
		return extender.get().equals(Value.kForward);
	}
			

	public boolean isRelaxed() {
		return pincher1.get() == Value.kReverse && pincher2.get() == Value.kReverse;
	}
	
}
