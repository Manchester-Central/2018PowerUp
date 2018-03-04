package SystemComponents;

import org.usfirst.frc.team131.robot.Controller;
import org.usfirst.frc.team131.robot.PortConstants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LinearLift {
	
	public static final double FLOOR_POSITION_INCHES = 3.0;
	public static final double EXTEND_POSITION_INCHES = 8.0;
	public static final double PORTAL_POSITION_INCHES = 20.0;
	public static final double VAULT_POSITION_INCHES = 9.0;//10.0;
	public static final double SWITCH_POSITION_INCHES = 36.0;
	public static final double HIGH_POSITION_INCHES = 96.0; // position for scale and climb
	
	public static final double DEADBAND_INCHES = 2.0; // the acceptable error 
	
	private static final double MAX_UP_SPEED = 0.4;
	private static final double MIN_UP_SPEED = 0.0;
	
	private static final double MAX_DOWN_SPEED = 0.3;
	private static final double MIN_DOWN_SPEED = 0.0;
	
	// denominator of the proportional set
	private static final double PROPORTIONAL_DISTANCE = 4;
	
	private final double OFFSET = 0;
	private final double RANGE = 105.375;
	
	private final double chaosRange = 105.375;
	private final double potRange = (0.28 / 1.2) / 0.821917;
	private final double potOffset = 0.0048;
	
	Victor lift1;
	Victor lift2;
	Victor lift3;
	Victor lift4;
	
	SpeedControllerGroup lifts;
	
	String setPosition;
	
	AnalogPotentiometer pot;
	
	double targetPosition;
	
	public LinearLift() {
		pot = new AnalogPotentiometer (new AnalogInput (PortConstants.CHOAS_POT_PORT));
		lift1 = new Victor (PortConstants.LINEAR_LIFT_1);
		lift2 = new Victor (PortConstants.LINEAR_LIFT_2);
		lift3 = new Victor (PortConstants.LINEAR_LIFT_3);
		lift4 = new Victor (PortConstants.LINEAR_LIFT_4);
		lifts = new SpeedControllerGroup(lift1, lift2, lift3, lift4);
		lifts.setInverted(false);
		targetPosition = FLOOR_POSITION_INCHES;
		setPosition = "current position";
	}
	
	/**
	 * 
	 * @param speed = min = -1.0, max = 1.0
	 */
	public void setSpeed (final double speed) {
//		if ((chaosPotGet() >= HIGH_POSITION_INCHES && speed > 0.0) || (chaosPotGet() <= FLOOR_POSITION_INCHES && speed < 0.0)) {
//			lifts.set(0.0);
//		} else {
			lifts.set(speed);
//		}
			
			
			
			//System.out.println (chaosPotGet());
	}
	/**
	 * 
	 * @param direction - Up = High (climb/scale), Left = Vault, Right = Switch, Down = Floor
	 */
	public void setToPosition (final Controller.DPadDirection direction) {
		
		switch (direction) {
		case DOWN:
			targetPosition = FLOOR_POSITION_INCHES;
			setPosition = "floor position";
			
			break;
		case RIGHT:
			targetPosition = SWITCH_POSITION_INCHES;
			setPosition = "switch position";
			break;
		case LEFT:
			targetPosition = VAULT_POSITION_INCHES;
			setPosition = "scale position";
			break;
		case UP:
			targetPosition = HIGH_POSITION_INCHES;
			setPosition = "high position";
			break;
		case NONE:
		default:
			targetPosition = chaosPotGet();
			setPosition = "current position";
			break;
		}
		
	}
	
	public void setToIntakePosition () {
		targetPosition = EXTEND_POSITION_INCHES;
	}
	
	public void setToFloorPosition () {
		targetPosition = FLOOR_POSITION_INCHES;
	}
	
	
	
	public boolean liftIsStopped () {
		return isAtTargetPosition();
	}
	
	public boolean isAtTargetPosition () {
		return Math.abs(Math.abs(targetPosition) - Math.abs(chaosPotGet())) <= DEADBAND_INCHES ;
	}
	
	public void setTargetPosition (double target) {
		targetPosition = target;
	}
	
	public double liftPosition () {
		return chaosPotGet();
	}
	
	public void MoveToPosition () {
		
		if (isAtTargetPosition ()) {
			setSpeed(0.0);
		} else {
			setSpeed(getProportionalSet());
		}
		
	}
	/**
	 * 
	 * @return - Takes %distance to target position and sets a speed based on that % of a set Max speed. Cannot go above 1 or below -1
	 */
	private double getProportionalSet () {
		
		return getProportionalSet(targetPosition, chaosPotGet());
		
	}
	
	public static double getProportionalSet (double targetPosition, double currentPosition) {
		
		// if at the correct position, return 0 to void NaN errors
		if (targetPosition == currentPosition) {
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
		
		if (proportionalSet > 0.0) {
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

		SmartDashboard.putNumber("Proportional Set: ", proportionalSet);
		
		return proportionalSet;
		
	}
	
	public double chaosPotGet () {
		
		double slope = (chaosRange / potRange);
		
		return slope * (pot.get() - potOffset);
	}
	
	public void putInfo () {
		
		SmartDashboard.putNumber("Lift position (in inches): ", chaosPotGet());
		SmartDashboard.putNumber("Lift position (raw): ", pot.get());
		SmartDashboard.putString("Set Position: ", setPosition);
		SmartDashboard.putNumber("Speed set: ", getProportionalSet());
		
		
		
		
	}
	
}
