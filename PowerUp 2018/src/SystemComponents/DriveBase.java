package SystemComponents;

import org.usfirst.frc.team131.robot.PortConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Victor;
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
	private static final double WHEEL_CIRCUMFERENCE_INCHES = 4 * Math.PI;

	private static final double ROBOT_TURN_RADIUS_INCHES = 13.0;
	private static final double ROBOT_CIRCUMFERENCE = ROBOT_TURN_RADIUS_INCHES * 2 * Math.PI;
	
	
	public DriveBase () {
		
		leftTalonTarget = 0D;
		rightTalonTarget = 0D;
		
		leftBackVictor = new WPI_VictorSPX(PortConstants.LEFT_BACK_VICTOR);
		leftMidVictor = new WPI_VictorSPX(PortConstants.LEFT_MID_VICTOR);
		leftFrontVictor = new WPI_VictorSPX(PortConstants.LEFT_FRONT_VICTOR);
		
		rightBackVictor = new WPI_VictorSPX(PortConstants.RIGHT_BACK_VICTOR);
		rightMidVictor = new WPI_VictorSPX(PortConstants.RIGHT_MID_VICTOR);
		rightFrontVictor = new WPI_VictorSPX(PortConstants.RIGHT_FRONT_VICTOR);
		
		rightTalonSRX = new WPI_TalonSRX(PortConstants.RIGHT_CAN_TALON);
		leftTalonSRX = new WPI_TalonSRX(PortConstants.LEFT_CAN_TALON);
		
		gearShifter = new DoubleSolenoid(PortConstants.GEAR_SHIFTER_A_SLOW, PortConstants.GEAR_SHIFTER_B_FAST);
		
		leftTalonSRX.setInverted(false);
		rightTalonSRX.setInverted(true); 
		
		leftTalonSRX.setSensorPhase(true);
		leftTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		leftTalonSRX.configPeakCurrentLimit(30, 0);
		leftTalonSRX.configPeakOutputForward(0.5 , 0);
		leftTalonSRX.configPeakOutputReverse(-0.5, 0);

		rightTalonSRX.setSensorPhase(true);
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
	public double getLeftEncoderVelocity () {
		
		return leftTalonSRX.getSelectedSensorVelocity(0);
		
	}
	public void resetEncoders () {
		rightTalonSRX.setSelectedSensorPosition(0, 0, 0);
		leftTalonSRX.setSelectedSensorPosition(0, 0, 0);
	}
	
	public double getLeftTalonEncoderValue () {
		
		return leftTalonSRX.getSelectedSensorPosition(0);
		
	}
	
	/**
	 * Uses in-build PID controls to drive straight to a target
	 * @param target = distance to drive
	 */
	public void setTalonsToPosition(double target) {

		resetEncoders();
		rightTalonSRX.set(ControlMode.Position, inchesToTicks(target));
		
		leftTalonSRX.set(ControlMode.Position, inchesToTicks(target));
		updateTargetValues (target, target);
		
		System.out.println ("ticks: " + inchesToTicks(target));
		
		talonSpeedToVictors();
		
	}
	/**
	 * 
	 * @param angle - Angle in Degrees to turn. Positive angle = counterclockwise
	 */
	public void turnToAngle (double angle) {
		
		double inches = (angle / 360) * ROBOT_CIRCUMFERENCE;
		
		resetEncoders();
		rightTalonSRX.set(ControlMode.Position, inchesToTicks(inches));
		
		leftTalonSRX.set(ControlMode.Position, inchesToTicks(-inches));
		updateTargetValues (-inches, inches);
		talonSpeedToVictors();
		
	}
	
	private void updateTargetValues (double left, double right) {
		leftTalonTarget = inchesToTicks(left);
		rightTalonTarget = inchesToTicks(right);
	}
	
	/**
	 * Makes the drive Victors follow the speed of the Talons++
	 */
	private void talonSpeedToVictors() {
		double leftTalonSpeed = leftTalonSRX.get();
		double rightTalonSpeed = rightTalonSRX.get();
		
		
		leftBackVictor.set(leftTalonSpeed);
		leftMidVictor.set(leftTalonSpeed);
		leftFrontVictor.set(leftTalonSpeed);
		
		rightBackVictor.set(rightTalonSpeed);
		rightMidVictor.set(rightTalonSpeed);
		rightFrontVictor.set(rightTalonSpeed);
	}
	
	public void encoderData() {
		
		System.out.println("Left Encoder: " + ticksToInches(getLeftTalonEncoderValue())
		+ " right Encoder: " +ticksToInches(getRightTalonEncoderValue()) + "-- "
		+ ticksToInches(rightTalonSRX.getClosedLoopError(0)));
	}
	
	public void velocityData () {
		System.out.println("left velocity: " + getRightEncoderVelocity() + " right velocity: " +getLeftEncoderVelocity());
	}
	
	/**
	 * Stops the drive and resets the encoders for the next drive command
	 */
	public void end () {
		rightTalonSRX.stopMotor();
		leftTalonSRX.stopMotor();
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
		
		boolean inLowShift = gearShifter.get().equals(Value.kForward);
		
		SmartDashboard.putBoolean("Gear is shifted: ", inLowShift);
		
	}
	
}
