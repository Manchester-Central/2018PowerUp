package SystemComponents;

import org.usfirst.frc.team131.robot.Controller;
import org.usfirst.frc.team131.robot.PortConstants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LinearLift {
	
	public static final double FLOOR_POSITION = 0.0;
	public static final double INTAKE_POSITION = 8.0;
	public static final double SWITCH_POSITION = 36.0;
	public static final double SCALE_POSITION = 84.0;
	public static final double CLIMB_POSITION = 80.0;
	public static final double TOP_POSITION = 100.0;
	
	public static final double DEADBAND = 1;
	
	private static final double MAX_SPEED = 0.5;
	private static final double MIN_SPEED = 0.2;
	
	// denominator of the proportional set
	private static final double PROPORTIONAL_DISTANCE = 12;
	
	private final double OFFSET = 0;
	private final double RANGE = 84.0;
	
	Victor lift1;
	Victor lift2;
	Victor lift3;
	Victor lift4;
	
	SpeedControllerGroup lifts;
	
	String setPosition;
	
	//Encoder encoder;
	
	AnalogPotentiometer pot;
	
	double targetPosition;
	
	public LinearLift() {
		pot = new AnalogPotentiometer (new AnalogInput (PortConstants.CHOAS_POT_PORT));
		lift1 = new Victor (PortConstants.LINEAR_LIFT_1);
		lift2 = new Victor (PortConstants.LINEAR_LIFT_2);
		lift3 = new Victor (PortConstants.LINEAR_LIFT_3);
		lift4 = new Victor (PortConstants.LINEAR_LIFT_4);
		lifts = new SpeedControllerGroup(lift1, lift2, lift3, lift4);
		lifts.setInverted(true);
		targetPosition = FLOOR_POSITION;
		setPosition = "current position";
	}
	
	/**
	 * 
	 * @param speed = min = -1.0, max = 1.0
	 */
	public void setSpeed (final double speed) {
//		if ((pot.get() >= TOP_POSITION && speed > 0.0) || (pot.get() <= FLOOR_POSITION && speed < 0.0)) {
//			lifts.set(0.0);
//		} else {
			lifts.set(speed);
//		}
		System.out.println(pot.get());
	}
	/**
	 * 
	 * @param direction - Up = Climb, Left = Scale, Right = Switch, Down = Floor
	 */
	public void setToPosition (final Controller.DPadDirection direction) {
		
		switch (direction) {
		case DOWN:
			targetPosition = FLOOR_POSITION;
			setPosition = "floor position";
			
			break;
		case RIGHT:
			targetPosition = SWITCH_POSITION;
			setPosition = "switch position";
			break;
		case LEFT:
			targetPosition = SCALE_POSITION;
			setPosition = "scale position";
			break;
		case UP:
			targetPosition = CLIMB_POSITION;
			setPosition = "climb position";
			break;
		case NONE:
		default:
			targetPosition = pot.get();
			setPosition = "current position";
			break;
		}
		
	}
	
	public void setToIntakePosition () {
		targetPosition = INTAKE_POSITION;
	}
	
	public void setToFloorPosition () {
		targetPosition = FLOOR_POSITION;
	}
	
	
	
	public boolean liftIsStopped () {
		return lifts.get() == 0F && isAtTargetPosition();
	}
	
	public boolean isAtTargetPosition () {
		return Math.abs(Math.abs(targetPosition) - Math.abs(pot.get())) <= DEADBAND ;
	}
	
	public void setTargetPosition (double target) {
		targetPosition = target;
	}
	
	public double liftPosition () {
		return pot.get();
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
		
		return getProportionalSet(targetPosition, pot.get());
		
	}
	
	public static double getProportionalSet (double targetPosition, double currentPosition) {
		
		// if at the correct position, return 0 to void NaN errors
		if (targetPosition == currentPosition)
			return 0;
		
		// the slope of x value distance awway from target distance
		double proportionalSet = (targetPosition- currentPosition) / PROPORTIONAL_DISTANCE;
		
		// keeps proportions within 1 and -1
		if (proportionalSet > 1.0)
			proportionalSet = 1.0;
		else if (proportionalSet < -1.0)
			proportionalSet = -1.0;
		
		// gets whether proportional set is negative or positive
		final double signModifier = (proportionalSet / Math.abs(proportionalSet));
		
		// sets the value of proportional set
		proportionalSet = signModifier * MAX_SPEED * (Math.abs(proportionalSet));
		
		//makes sure value is within min speed
		if (Math.abs(proportionalSet) < MIN_SPEED) 
			proportionalSet = signModifier * MIN_SPEED;
		
		return proportionalSet;
		
	}
	
	public void putInfo () {
		
		SmartDashboard.putNumber("Lift position (in inches): ", pot.get());
		SmartDashboard.putString("Set Position: ", setPosition);
		
		
	}
	
}
