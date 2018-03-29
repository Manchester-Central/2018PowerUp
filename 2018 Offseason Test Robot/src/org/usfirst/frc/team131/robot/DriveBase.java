package org.usfirst.frc.team131.robot;




import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Victor;

public class DriveBase {
	// lift testing
	///////////////////////////////////////////////////////////////////
	
	private static final double MAX_UP_SPEED = 0.5;
	private static final double MIN_UP_SPEED = 0.2;
	
	private static final double MAX_DOWN_SPEED = 0.5;
	private static final double MIN_DOWN_SPEED = 0.2;

	
	
	private static final double PROPORTIONAL_DISTANCE = 24;
	
	private final long changeRate = 500;
	private final double maxAcceleration = 0.05;
	

	double currentSet;
	
	long lastTimeUpdate;
	
	
	///////////////////////////////////////////////////////////////////

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

	private static final double ROBOT_TURN_RADIUS_INCHES = 13.755;
	private static final double ROBOT_CIRCUMFERENCE = ROBOT_TURN_RADIUS_INCHES * 2 * Math.PI;

	private double minSpeed = 0.05;
	private double maxSpeed = 0.2;
	
	private double forwardGain;
	private double turnGain;
	
	public DriveBase () {
		

		leftTalonTarget = 0.0;
		rightTalonTarget = 0.0;
		
		leftBackVictor = new Victor(PortConstants.LEFT_BACK_TALON);
		leftMidVictor = new Victor(PortConstants.LEFT_MID_TALON);
		leftFrontVictor = new Victor(PortConstants.LEFT_FRONT_TALON);
		
		rightBackVictor = new Victor(PortConstants.RIGHT_BACK_TALON);
		rightMidVictor = new Victor(PortConstants.RIGHT_MID_TALON);
		rightFrontVictor = new Victor(PortConstants.RIGHT_FRONT_TALON);
		
		rightBackVictor.setInverted(true);
		rightMidVictor.setInverted(true);
		rightFrontVictor.setInverted(true);
		
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
		
		setGains (0.07, 0.07);
	}
	
	public void setSpeed(double leftSpeed, double rightSpeed) {
		
		leftTalonSRX.set(leftSpeed);
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
		leftTalonTarget = left;
		rightTalonTarget = right;
	}
	
	public void resetEncoders () {
		rightTalonSRX.setSelectedSensorPosition(0, 0, 0);
		leftTalonSRX.setSelectedSensorPosition(0, 0, 0);
	}
	
	public void driveToInches(double inches) {
		
		if(getLeftTalonEncoderValue() >= inchesToTicks(inches) && getRightTalonEncoderValue() >= inchesToTicks(inches)) {
			
			setSpeed( 0.0 , 0.0);	
		
		} else {
			
			setSpeed( 0.5 , 0.5 );
		
		}
		
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
	
	public double getProportionalSet (double targetPosition, double currentPosition) {
		

		// if at the correct position, return 0 to void NaN errors
			if (targetPosition == currentPosition) {
				currentSet = 0;
				return 0;
			}
			
			// the slope of x value distance away from target distance
			double proportionalSet = (targetPosition- currentPosition) / PROPORTIONAL_DISTANCE;
			
			double maxSpeed;
			double minSpeed;
			double signModifier;
			
			// keeps proportions within 1 and -1
			if (proportionalSet > 1.0) {
				proportionalSet = 1.0;
			} else if (proportionalSet < -1.0) {
				proportionalSet = -1.0;
			}
			
			boolean proportionalSetIsPositive = proportionalSet > 0.0;
			
			if (proportionalSetIsPositive) {
				maxSpeed = MAX_UP_SPEED;
				minSpeed = MIN_UP_SPEED;
				signModifier = 1.0;
			} else {
				maxSpeed = MAX_DOWN_SPEED;
				minSpeed = MIN_DOWN_SPEED;
				signModifier = -1.0;
			}
			
			// sets the value of proportional set
			proportionalSet = maxSpeed * proportionalSet;
			
			//makes sure value is within min speed
			if (Math.abs(proportionalSet) < minSpeed) 
				proportionalSet = signModifier * minSpeed;

			
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
					
				} else if (lastTimeUpdate + changeRate < System.currentTimeMillis()) {
					
					double addAcceleration = (proportionalSetIsPositive) ? maxAcceleration : -maxAcceleration;
					
					proportionalSet = currentSet + addAcceleration;
					
					lastTimeUpdate = System.currentTimeMillis();
				
				} else {
					
					proportionalSet = currentSet;
					
				}
				
			}
				
			System.out.printf("p-set: %.3f,\tposition: %.3f\n", proportionalSet, ticksToInches(getRightTalonEncoderValue()));
			
			currentSet = proportionalSet;
			
			return proportionalSet;
		
		
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

	public void tankCorrectedDrive (double leftTarget, double rightTarget)
	{
		double leftError  = leftTarget  - ticksToInches(getLeftTalonEncoderValue ());
		double rightError = rightTarget - ticksToInches(getRightTalonEncoderValue ());
		
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
		
		updateTargetValues (leftTarget, rightTarget);
		setSpeed (leftOutput, rightOutput);
	}

	public void end() {
		setSpeed(0.0, 0.0);
		resetEncoders();
	}
	
	public void velocityData () {
		System.out.println("left velocity: " + getRightEncoderVelocity() + " right velocity: " +getLeftEncoderVelocity());
	}
	

	public double getLeftEncoderVelocity () {
		
		return leftTalonSRX.getSelectedSensorVelocity(0);
		
	}

	public double getRightEncoderVelocity () {
		
		return rightTalonSRX.getSelectedSensorVelocity(0);
		
	}

	public double getLeftTalonEncoderValue () {
		
		return -leftTalonSRX.getSelectedSensorPosition(0);
		
	}

	public double getRightTalonEncoderValue () {
		
		return -rightTalonSRX.getSelectedSensorPosition(0);
		
	}

	public double getLeftTarget () {
		return leftTalonTarget;
	}

	public double getRightTarget () {
		return rightTalonTarget;
	}
	
	public double getLeftTalonCurrent() {
		return leftTalonSRX.getOutputCurrent();
	}

	public double getRightTalonCurrent() {
		return rightTalonSRX.getOutputCurrent();
	}

	public void turnToAngle (double angle) {
		
		double inches = (angle / 360) * ROBOT_CIRCUMFERENCE;
		
		resetEncoders();
		rightTalonSRX.set(ControlMode.Position, inchesToTicks(inches));
		
		leftTalonSRX.set(ControlMode.Position, inchesToTicks(-inches));
		updateTargetValues (-inches, inches);
		//talonSpeedToVictors();
		
	}
	
	private void talonSpeedToVictors( ) {
		double leftTalonSpeed = leftTalonSRX.get();
		double rightTalonSpeed = rightTalonSRX.get();
		
		
		leftBackVictor.set(leftTalonSpeed);
		leftMidVictor.set(leftTalonSpeed);
		leftFrontVictor.set(leftTalonSpeed);
		
		
		rightBackVictor.set(rightTalonSpeed);
		rightMidVictor.set(rightTalonSpeed);
		rightFrontVictor.set(rightTalonSpeed);
	}
	
	public void setGains(double forwardGain, double turnGain) {
		this.forwardGain = forwardGain;
		this.turnGain = turnGain;
	}
	
	public void encoderData() {
		
		System.out.println("Left Encoder: " + ticksToInches(getLeftTalonEncoderValue())
		+ " right Encoder: " +ticksToInches(getRightTalonEncoderValue()) + "-- "
		+ ticksToInches(rightTalonSRX.getClosedLoopError(0)));
	}
	
}
