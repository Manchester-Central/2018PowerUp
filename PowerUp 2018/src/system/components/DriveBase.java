package system.components;

import org.usfirst.frc.team131.robot.PortConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBase {

	WPI_VictorSPX leftBackVictor;
	WPI_VictorSPX leftMidVictor;
	WPI_VictorSPX leftFrontVictor;
	WPI_VictorSPX rightBackVictor;
	WPI_VictorSPX rightMidVictor;
	WPI_VictorSPX rightFrontVictor;
	
	WPI_TalonSRX rightTalonSRX;
	WPI_TalonSRX leftTalonSRX;
	
	DoubleSolenoid gearShifter;
	
	private double leftTalonTarget;
	private double rightTalonTarget;
	
	
	
	private static final double ENCODER_TICKS_PER_REVOLUTION = 4150.0;
	private static final double WHEEL_CIRCUMFERENCE_INCHES = 5.8642 * Math.PI;

	private static final double ROBOT_TURN_RADIUS_INCHES = 12.25;
	private static final double ROBOT_CIRCUMFERENCE = ROBOT_TURN_RADIUS_INCHES * 2 * Math.PI;
	
	private double minSpeed = 0.25;
	private double maxSpeed = 1.0;
	private double maxSpeedChange = 0.015;
	
	private double forwardGain;
	private double turnGain;
	
	long rightLastTimeUpdate;
	long leftLastTimeUpdate;
	
	double leftPreviousSet;
	double rightPreviousSet;
	
	public DriveBase () {
		
		rightLastTimeUpdate = 0;
		leftPreviousSet = 0.0;
		rightPreviousSet = 0.0;
		
		leftTalonTarget = 0.0;
		rightTalonTarget = 0.0;
		
		leftBackVictor = new WPI_VictorSPX(PortConstants.LEFT_BACK_VICTOR);
		leftMidVictor = new WPI_VictorSPX(PortConstants.LEFT_MID_VICTOR);
		leftFrontVictor = new WPI_VictorSPX(PortConstants.LEFT_FRONT_VICTOR);
		
		rightBackVictor = new WPI_VictorSPX(PortConstants.RIGHT_BACK_VICTOR);
		rightMidVictor = new WPI_VictorSPX(PortConstants.RIGHT_MID_VICTOR);
		rightFrontVictor = new WPI_VictorSPX(PortConstants.RIGHT_FRONT_VICTOR);
		
		rightTalonSRX = new WPI_TalonSRX(PortConstants.RIGHT_CAN_TALON);
		leftTalonSRX = new WPI_TalonSRX(PortConstants.LEFT_CAN_TALON);
		
		gearShifter = new DoubleSolenoid(PortConstants.GEAR_SHIFTER_A_SLOW, PortConstants.GEAR_SHIFTER_B_FAST);
		
		leftTalonSRX.setInverted(true);
		rightTalonSRX.setInverted(true);
		
		//leftTalonSRX.setSensorPhase(true);
		//rightTalonSRX.setSensorPhase(true);
		
		leftBackVictor.setInverted(true);
		leftMidVictor.setInverted(true);
		leftFrontVictor.setInverted(true);
		rightBackVictor.setInverted(true);
		rightMidVictor.setInverted(true);
		rightFrontVictor.setInverted(true);
		
		//leftTalonSRX.setSensorPhase(true);
		leftTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		leftTalonSRX.configPeakCurrentLimit(30, 0);
		leftTalonSRX.configPeakOutputForward(0.5 , 0);
		leftTalonSRX.configPeakOutputReverse(-0.5, 0);

		//rightTalonSRX.setSensorPhase(true);
		rightTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightTalonSRX.configPeakCurrentLimit(30, 0);
		rightTalonSRX.configPeakOutputForward(0.5 , 0);
		rightTalonSRX.configPeakOutputReverse(-0.5, 0);
		

		leftTalonSRX.enableCurrentLimit(true);
		rightTalonSRX.enableCurrentLimit(true);
		
		leftTalonSRX.configContinuousCurrentLimit(30, 0);
		rightTalonSRX.configContinuousCurrentLimit(30, 0);
		
		leftTalonSRX.configPeakCurrentDuration(50, 0);
		rightTalonSRX.configPeakCurrentDuration(50, 0);
		
		rightTalonSRX.configClosedloopRamp (1, 0);
		leftTalonSRX.configClosedloopRamp (1, 0);
		
		leftTalonSRX.config_kF(0, 0, 0);
		leftTalonSRX.config_kP(0, 0.6, 0);
		leftTalonSRX.config_kI(0, 0, 0);
		leftTalonSRX.config_kD(0, 0, 0);

		rightTalonSRX.config_kF(0, 0, 0);
		rightTalonSRX.config_kP(0, 0.6, 0);
		rightTalonSRX.config_kI(0, 0, 0);
		rightTalonSRX.config_kD(0, 0, 0);
		resetEncoders();
		
		setGains (0.09, 0.3 /*@ 0.12*/);
	}
	
	public void setSpeed(double leftSpeed, double rightSpeed) {
		
		leftTalonSRX.set(leftSpeed);
		rightTalonSRX.set(rightSpeed);
		
		leftPreviousSet = leftSpeed;
		rightPreviousSet = rightSpeed;
		
		//talonSpeedToVictors();
		leftBackVictor.set(leftSpeed);
		leftMidVictor.set(leftSpeed);
		leftFrontVictor.set(leftSpeed);
		
		rightBackVictor.set(rightSpeed);
		rightMidVictor.set(rightSpeed);
		rightFrontVictor.set(rightSpeed);
		
	}
	
	public static double inchesToTicks(double inches) {
		
		return inches * ENCODER_TICKS_PER_REVOLUTION / WHEEL_CIRCUMFERENCE_INCHES;
		
	}

	public static double ticksToInches(double ticks) {
		
		return ticks * WHEEL_CIRCUMFERENCE_INCHES / ENCODER_TICKS_PER_REVOLUTION;
		
	}
	
	/**
	 * Forward is low gear
	 * Reverse is high gear
	 * @param lowGear - whether we want to shift to low gear
	 */
	public void shiftLow(boolean lowGear) {

		if (lowGear) {
		gearShifter.set(Value.kForward);
		} else {
		gearShifter.set(Value.kReverse);
		}
	}
	
	public double getCorrectedRightTalonTicks () {
		
		return -rightTalonSRX.getSelectedSensorPosition(0);
		
	}
	
	public double getCorrectedLeftTalonTicks () {
		
		return leftTalonSRX.getSelectedSensorPosition(0);
		
	}
	
	public double getLeftTalonInches () {
		
		return ticksToInches(getCorrectedLeftTalonTicks());
		
	}
	
	public double getRightTalonInches () {
		
		return ticksToInches(getCorrectedRightTalonTicks());
		
	}
	
	public double getRightEncoderVelocity () {
		
		return rightTalonSRX.getSelectedSensorVelocity(0);
		
	}
	public double getLeftEncoderVelocity () {
		
		return leftTalonSRX.getSelectedSensorVelocity(0);
		
	}
	public void resetEncoders () {
		rightTalonSRX.setSelectedSensorPosition(0, 0, 0);
		leftTalonSRX.setSelectedSensorPosition(0, 0, 0);
	}
	
	
	/**
	 * 
	 * @param angle - Angle in Degrees to turn. Positive angle = counterclockwise
	 */
	public void turnToAngleRight (double angle) {
		
		
	
		double inches = (angle / 360) * ROBOT_CIRCUMFERENCE;
		
				
		tankCorrectedDrive ((inches), (-inches));
		
		//System.out.println ("ticks: " + inchesToTicks(inches));
		
		updateTargetValues (-inches, inches);
		//talonSpeedToVictors();
		
	}
	
	public void arcDrive (double turnPointDistanceX, double angle) {
		
		double signModifier = angle / Math.abs(angle);
		
		double leftSideDistance = (angle / 360.0) * ((turnPointDistanceX + (signModifier * ROBOT_TURN_RADIUS_INCHES)) * Math.PI * 2.0);
		
		double rightSideDistance = (angle / 360.0) * ((turnPointDistanceX - (signModifier * ROBOT_TURN_RADIUS_INCHES)) * Math.PI * 2.0);
		
		tankCorrectedDrive (leftSideDistance, rightSideDistance);
		
	}

	public void tankCorrectedDrive (double leftTarget, double rightTarget)
	{
		double leftError  = leftTarget  - getLeftTalonInches();
		double rightError = rightTarget - getRightTalonInches();
		
		double leftScaled  = (leftTarget  != 0) ? (leftError  / leftTarget)  : 0;
		double rightScaled = (rightTarget != 0) ? (rightError / rightTarget) : 0;
		
		
		double turnError  = (leftScaled - rightScaled) *
				            (leftTarget + rightTarget) /
				             2.0;
		
		double leftOutput  = (leftError  * forwardGain) + (turnError * turnGain);
		double rightOutput = (rightError * forwardGain) - (turnError * turnGain);
		
		double absLeftOutput  = Math.abs(leftOutput);
		double absRightOutput = Math.abs(rightOutput);
		
		double speedDenominator = (absRightOutput > absLeftOutput) ? absRightOutput : absLeftOutput;
		
		if (speedDenominator != 0) {
			if (absLeftOutput < minSpeed || absRightOutput < minSpeed) {
				rightOutput *= (minSpeed / speedDenominator);
				leftOutput  *= (minSpeed / speedDenominator);
		
			
			} else if (absRightOutput > maxSpeed || absLeftOutput > maxSpeed) {
				rightOutput *= (maxSpeed / speedDenominator);
				leftOutput  *= (maxSpeed / speedDenominator);
			}
		}
		
		double absLeftTarget = Math.abs(leftTarget);
		double absRightTarget = Math.abs(rightTarget);
		
		double biggerValue = (absLeftTarget > absRightTarget) ? absLeftTarget : absRightTarget;
		
//		double absLeftOutput = Math.abs(leftOutput);
//		double absRightOutput = Math.abs(rightOutput);
//		
//		double biggerValue = (absLeftOutput > absRightOutput) ? absLeftOutput : absRightOutput;
		
//	test	rightOutput = throttleAcceleration (rightPreviousSet, rightOutput, maxSpeedChange * (absRightOutput / biggerValue), 1, "right");
//		leftOutput = throttleAcceleration ( leftPreviousSet,  leftOutput, maxSpeedChange * ( absLeftOutput / biggerValue), 1, "left");
		
		rightOutput = throttleAcceleration (rightPreviousSet, rightOutput, maxSpeedChange * (absRightTarget / biggerValue), 1, "right");
		leftOutput = throttleAcceleration ( leftPreviousSet,  leftOutput, maxSpeedChange * ( absLeftTarget / biggerValue), 1, "left");
		
		System.out.println("L E : " + leftError + "\t R E: " + rightError +
							"\t T E: " + turnError +
							"\t L O: " + leftOutput + "\t R O: " + rightOutput);
		
		updateTargetValues (leftTarget, rightTarget);
		setSpeed (leftOutput, rightOutput);
		
	}
	
	private double throttleAcceleration (double currentSet, double proportionalSet, double maxAcceleration, long changeRate, String direction) {
		
		boolean proportionalSetIsPositive = proportionalSet > 0.0;
		
		double velocityChange = (proportionalSetIsPositive) ? proportionalSet - currentSet : Math.abs(currentSet - proportionalSet);
		
		if (currentSet == 0.0) {
			velocityChange = Math.abs(currentSet - proportionalSet); 
		} else if (proportionalSet == 0.0) {
			velocityChange = -Math.abs(currentSet - proportionalSet); 
		}
		
		// if ΔV > max acceleration
		if (velocityChange > maxAcceleration) {
			
			if ((proportionalSet > 0 && currentSet < 0) || (proportionalSet < 0 && currentSet > 0)) {
				
				proportionalSet = 0.0;
				
			} else if (rightLastTimeUpdate + changeRate < System.currentTimeMillis() && direction.equals("right")) {
				
				double addAcceleration = (proportionalSetIsPositive) ? maxAcceleration : -maxAcceleration;
				
				proportionalSet = currentSet + addAcceleration;
				
				rightLastTimeUpdate = System.currentTimeMillis();
			
			}  else if (leftLastTimeUpdate + changeRate < System.currentTimeMillis() && direction.equals("left")) {
				
				double addAcceleration = (proportionalSetIsPositive) ? maxAcceleration : -maxAcceleration;
				
				proportionalSet = currentSet + addAcceleration;
				
				leftLastTimeUpdate = System.currentTimeMillis();
			
			} else {
				
				proportionalSet = currentSet;
				
			}
			
		}
		
		return proportionalSet;
		
	}
	
	private void updateTargetValues (double left, double right) {
		leftTalonTarget = left;
		rightTalonTarget = right;
	}
	

	
	public void setGains(double forwardGain, double turnGain) {
		this.forwardGain = forwardGain;
		this.turnGain = turnGain;
	}
	
	public void encoderData() {
		
//		System.out.println("Left Error: " + getLeftTalonInches()
//		+ " right Encoder: " + getRightTalonInches() );
	}
	
	
	/**
	 * Stops the drive and resets the encoders for the next drive command
	 */
	public void end () {
		setSpeed(0.0,0.0);
		resetEncoders();
	}
	
	public double getRightTarget () {
		return rightTalonTarget;
	}
	
	
	public double getLeftTarget () {
		return leftTalonTarget;
	}
	
	public void putInfo () {
		
		SmartDashboard.putNumber("Right Talon Speed: ", getRightEncoderVelocity());
		SmartDashboard.putNumber("Left Talon Speed: ", getLeftEncoderVelocity());
		SmartDashboard.putNumber("Right Talon Encoder: ", getRightTalonInches());
		SmartDashboard.putNumber("Left Talon Encoder: ", getLeftTalonInches());
		
		boolean inLowShift = gearShifter.get().equals(Value.kForward);
		
		SmartDashboard.putBoolean("Low Gear: ", inLowShift);
		
	}
	
}
