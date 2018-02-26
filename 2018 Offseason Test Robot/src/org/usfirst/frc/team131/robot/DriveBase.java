package org.usfirst.frc.team131.robot;




import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
	
	private double leftTalonTarget;
	private double rightTalonTarget;
	
	private static final double ENCODER_TICKS_PER_REVOLUTION = 4150.0;
	private static final double WHEEL_CIRCUMFERENCE_INCHES = 4 * Math.PI;

	private static final double ROBOT_TURN_RADIUS_INCHES = 13.0;
	private static final double ROBOT_CIRCUMFERENCE = ROBOT_TURN_RADIUS_INCHES * 2 * Math.PI;

	
	
	public DriveBase () {
		

		leftTalonTarget = 0.0;
		rightTalonTarget = 0.0;
		
		leftBackVictor = new Victor(PortConstants.LEFT_BACK_TALON);
		leftMidVictor = new Victor(PortConstants.LEFT_MID_TALON);
		leftFrontVictor = new Victor(PortConstants.LEFT_FRONT_TALON);
		
		rightBackVictor = new Victor(PortConstants.RIGHT_BACK_TALON);
		rightMidVictor = new Victor(PortConstants.RIGHT_MID_TALON);
		rightFrontVictor = new Victor(PortConstants.RIGHT_FRONT_TALON);
		
		rightTalonSRX = new WPI_TalonSRX(PortConstants.RIGHT_CAN_TALON);
		leftTalonSRX = new WPI_TalonSRX(PortConstants.LEFT_CAN_TALON);
		
		gearShifter = new DoubleSolenoid(PortConstants.GEAR_SHIFTER_A_SLOW, PortConstants.GEAR_SHIFTER_B_SLOW);
		
		leftTalonSRX.setInverted(false);
		rightTalonSRX.setInverted(true);
		
		leftTalonSRX.enableCurrentLimit(true);
		rightTalonSRX.enableCurrentLimit(true);
		
		leftTalonSRX.configContinuousCurrentLimit(30, 0);
		rightTalonSRX.configContinuousCurrentLimit(30, 0);
		
		leftTalonSRX.configPeakCurrentDuration(50, 0);
		rightTalonSRX.configPeakCurrentDuration(50, 0);

		rightTalonSRX.configPeakCurrentLimit(30, 0);
		leftTalonSRX.configPeakCurrentLimit(30, 0);
		
		rightTalonSRX.configClosedloopRamp (1, 0);
		leftTalonSRX.configClosedloopRamp (1, 0);
		
		
	}
	
	public void setSpeed(double leftSpeed, double rightSpeed) {
		
		//leftTalonSRX.set(-leftSpeed);
		rightTalonSRX.set(rightSpeed);
		
		talonSpeedToVictors();
		
		
	}
	
	public static double inchesToTicks(double inches) {
		
		return inches * ENCODER_TICKS_PER_REVOLUTION / WHEEL_CIRCUMFERENCE_INCHES;
		
	}

	public static double ticksToInches(double ticks) {
		
		return ticks * WHEEL_CIRCUMFERENCE_INCHES / ENCODER_TICKS_PER_REVOLUTION;
		
	}
	
	/**
	 * Shifts the drive train to high or low gear
	 * @param lowGear - true(forward) when you want to shift low, false(reverse) when you want to shift high
	 */
	public void gearShift(boolean lowGear) {

		if (lowGear) {
		gearShifter.set(Value.kForward);
		} else {
		gearShifter.set(Value.kReverse);
		}
	}
	
	private void updateTargetValues (double left, double right) {
		leftTalonTarget = inchesToTicks(left);
		rightTalonTarget = inchesToTicks(right);
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
	
	public double getLeftEncoderVelocity () {
		
		return leftTalonSRX.getSelectedSensorVelocity(0);
		
	}
	
	public void driveToInches(double inches) {
		
		if(getLeftTalonEncoderValue() >= inchesToTicks(inches) && getRightTalonEncoderValue() >= inchesToTicks(inches)) {
			
			setSpeed( 0.0 , 0.0);	
		
		} else {
			
			setSpeed( 0.5 , 0.5 );
		
		}
		
	}
	
	public double getLeftTalonCurrent() {
		return leftTalonSRX.getOutputCurrent();
	}
	
	public double getRightTalonCurrent() {
		return rightTalonSRX.getOutputCurrent();
	}
	
	/**
	 * Configures PID commands and sets a distance to drive to
	 * @param target - the distance to drive to
	 */
	public void setRightTalonToPosition(double target) {
		leftTalonSRX.setSensorPhase(true);
		leftTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		leftTalonSRX.configPeakOutputForward(0.5 , 0);
		leftTalonSRX.config_kF(0, 0, 0);
		leftTalonSRX.config_kP(0, 0.21, 0);
		leftTalonSRX.config_kI(0, 0.000005, 0);
		leftTalonSRX.config_kD(0, 70, 0);

		rightTalonSRX.setSensorPhase(true);
		rightTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightTalonSRX.configPeakOutputForward(0.455 , 0);
		rightTalonSRX.config_kF(0, 0, 0);
		rightTalonSRX.config_kP(0, 0.21, 0);
		rightTalonSRX.config_kI(0, 0.000005, 0);
		rightTalonSRX.config_kD(0, 70, 0);
		resetEncoders();
		rightTalonSRX.set(ControlMode.Position, inchesToTicks(target));
		
		
		leftTalonSRX.set(ControlMode.Position, inchesToTicks(target));
		
		talonSpeedToVictors();
		updateTargetValues (target, target);
		
	}
	
	public void end() {
		rightTalonSRX.stopMotor();
		leftTalonSRX.stopMotor();
		resetEncoders();
	}
	
	public void velocityData () {
		System.out.println("left velocity: " + getRightEncoderVelocity() + " right velocity: " +getLeftEncoderVelocity());
	}
	

	public double getRightTarget () {
		return rightTalonTarget;
	}
	
	public double getLeftTarget () {
		return leftTalonTarget;
	}
	
	public void turnToAngle (double angle) {
		
		double inches = (angle / 360) * ROBOT_CIRCUMFERENCE;
		
		resetEncoders();
		rightTalonSRX.set(ControlMode.Position, inchesToTicks(inches));
		
		leftTalonSRX.set(ControlMode.Position, inchesToTicks(-inches));
		updateTargetValues (-inches, inches);
		talonSpeedToVictors();
		
	}
	
	private void talonSpeedToVictors( ) {
		//double leftTalonSpeed = leftTalonSRX.get();
		double rightTalonSpeed = rightTalonSRX.get();
		
		
		//leftBackVictor.set(leftTalonSpeed);
		//leftMidVictor.set(leftTalonSpeed);
		//leftFrontVictor.set(leftTalonSpeed);
		
		
		//rightBackVictor.set(rightTalonSpeed);
		//rightMidVictor.set(rightTalonSpeed);
		rightFrontVictor.set(rightTalonSpeed);
	}
	
	public void encoderData() {
		
		System.out.println("Left Encoder: " + ticksToInches(getLeftTalonEncoderValue())
		+ " right Encoder: " +ticksToInches(getRightTalonEncoderValue()) + "-- "
		+ ticksToInches(rightTalonSRX.getClosedLoopError(0)));
	}
	
}
