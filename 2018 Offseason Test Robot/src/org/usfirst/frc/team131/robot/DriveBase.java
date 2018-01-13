package org.usfirst.frc.team131.robot;




import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
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
	
	
	public DriveBase ()
	{
		leftBackVictor = new Victor(PortConstants.LEFT_BACK_TALON);
		leftMidVictor = new Victor(PortConstants.LEFT_MID_TALON);
		leftFrontVictor = new Victor(PortConstants.LEFT_FRONT_TALON);
		rightBackVictor = new Victor(PortConstants.RIGHT_BACK_TALON);
		rightMidVictor = new Victor(PortConstants.RIGHT_MID_TALON);
		rightFrontVictor = new Victor(PortConstants.RIGHT_FRONT_TALON);
		
		rightTalonSRX = new WPI_TalonSRX(PortConstants.RIGHT_CAN_TALON);
		leftTalonSRX = new WPI_TalonSRX(PortConstants.LEFT_CAN_TALON);
		
		gearShifter = new DoubleSolenoid(PortConstants.GEAR_SHIFTER_A_SLOW, PortConstants.GEAR_SHIFTER_B_SLOW);
		 
	}
	
	public void setSpeed(double leftSpeed, double rightSpeed)
	{
		leftBackVictor.set(leftSpeed);
		leftMidVictor.set(leftSpeed);
		leftFrontVictor.set(leftSpeed);
		leftTalonSRX.set(leftSpeed);
		
		rightBackVictor.set(rightSpeed);
		rightMidVictor.set(rightSpeed);
		rightFrontVictor.set(rightSpeed);
		rightTalonSRX.set(rightSpeed);
		
	}
	
	
//	public void gearShift(Value setGear)
//	{
//		// Forward is low Gear
//		// Reverse is high Gear
//		gearShifter.set(setGear);
//	}
	
	
	
	
	
	
	
}
