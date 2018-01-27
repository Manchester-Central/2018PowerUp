package org.usfirst.frc.team131.robot;




import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class DriveBase {

	Victor leftBackVictor;
	Victor leftMidVictor;
	Victor leftFrontVictor;
	Victor rightBackVictor;
	Victor rightMidVictor;
	Victor rightFrontVictor;
	
	WPI_TalonSRX rightTalonSRX;
	WPI_TalonSRX leftTalonSRX;
	
	DoubleSolenoid gearShifter;
	
	Encoder rightEncoder;
	
	private static final double ENCODER_TICKS_PER_REVOLUTION = 4150.0;
	private static final double WHEEL_CIRCUMFERENCE_INCHES = 4 * Math.PI;

	
	
	public DriveBase () {
		
		leftBackVictor = new Victor(PortConstants.LEFT_BACK_TALON);
		leftMidVictor = new Victor(PortConstants.LEFT_MID_TALON);
		leftFrontVictor = new Victor(PortConstants.LEFT_FRONT_TALON);
		
		rightBackVictor = new Victor(PortConstants.RIGHT_BACK_TALON);
		rightMidVictor = new Victor(PortConstants.RIGHT_MID_TALON);
		rightFrontVictor = new Victor(PortConstants.RIGHT_FRONT_TALON);
		
		rightTalonSRX = new WPI_TalonSRX(PortConstants.RIGHT_CAN_TALON);
		leftTalonSRX = new WPI_TalonSRX(PortConstants.LEFT_CAN_TALON);
		
		gearShifter = new DoubleSolenoid(PortConstants.GEAR_SHIFTER_A_SLOW, PortConstants.GEAR_SHIFTER_B_SLOW);
		
		leftTalonSRX.setInverted(true);
		rightTalonSRX.setInverted(true); 
	}
	
	public void setSpeed(double leftSpeed, double rightSpeed) {
		
		leftTalonSRX.set(leftSpeed);
		rightTalonSRX.set(rightSpeed);
		
		
		double leftTalonSpeed = leftTalonSRX.get();
		double rightTalonSpeed = rightTalonSRX.get();
		
		
		leftBackVictor.set(leftTalonSpeed);
		leftMidVictor.set(leftTalonSpeed);
		leftFrontVictor.set(leftTalonSpeed);
		
		
		rightBackVictor.set(rightTalonSpeed);
		rightMidVictor.set(rightTalonSpeed);
		rightFrontVictor.set(rightTalonSpeed);
		
	}
	
	public static double inchesToTicks(double inches) {
		
		return inches * ENCODER_TICKS_PER_REVOLUTION / WHEEL_CIRCUMFERENCE_INCHES;
		
	}

	public static double ticksToInches(double ticks) {
		
		return ticks * WHEEL_CIRCUMFERENCE_INCHES / ENCODER_TICKS_PER_REVOLUTION;
		
	}
	
	public void gearShift(boolean lowGear) {
		
		// Forward is low Gear
		// Reverse is high Gear
		if (lowGear) {
		gearShifter.set(Value.kForward);
		} else {
		gearShifter.set(Value.kReverse);
		}
	}
	
	public double getRightTalonEncoderValue () {
		
		return rightTalonSRX.getSelectedSensorPosition(0);
		
	}
	public double getRightEncoderVelocity () {
		
		return rightTalonSRX.getSelectedSensorVelocity(0);
		
	}
	
	public void resetEncoders () {
		rightTalonSRX.setSelectedSensorPosition(0, 0, 0);
		leftTalonSRX.setSelectedSensorPosition(0, 0, 0);
	}
	
	public double getLeftTalonEncoderValue () {
		
		return leftTalonSRX.getSelectedSensorPosition(0);
		
	}
	
	public void driveToInches(double inches) {
		
		if(getLeftTalonEncoderValue() >= inchesToTicks(inches) && getRightTalonEncoderValue() >= inchesToTicks(inches)) {
			
			setSpeed( 0.0 , 0.0);	
		
		} else {
			
			setSpeed( 0.5 , 0.5 );
		
		}
		
	}
	
}
