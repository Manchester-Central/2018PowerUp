package SystemComponents;

import org.usfirst.frc.team131.robot.Controller;
import org.usfirst.frc.team131.robot.PortConstants;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;

public class LinearLift {
	
	public static final double FLOOR_POSITION = 0.0;
	public static final double INTAKE_POSITION = 8.0;
	public static final double SWITCH_POSITION = 36.0;
	public static final double SCALE_POSITION = 84.0;
	public static final double CLIMB_POSITION = 80.0;
	public static final double TOP_POSITION = 100.0;
	
//	private final double DEADBAND = 1;
//	
//	private final double SPEED = 0.3;
//	
//	private final double OFFSET = 0;
//	private final double RANGE = 84.0;
	
	Victor lift1;
	Victor lift2;
	Victor lift3;
	Victor lift4;
	
	SpeedControllerGroup lifts;
	
	//Encoder encoder;
	
	//AnalogPotentiometer pot;
	
	double targetPosition;
	
	public LinearLift() {
		//pot = new AnalogPotentiometer (new AnalogInput (PortConstants.CHOAS_POT_PORT), RANGE, OFFSET);
		lift1 = new Victor (PortConstants.LINEAR_LIFT_1);
		lift2 = new Victor (PortConstants.LINEAR_LIFT_2);
		lift3 = new Victor (PortConstants.LINEAR_LIFT_3);
		lift4 = new Victor (PortConstants.LINEAR_LIFT_4);
		lifts = new SpeedControllerGroup(lift1, lift2, lift3, lift4);
		lifts.setInverted(true);
		targetPosition = FLOOR_POSITION;
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
	}
	/**
	 * 
	 * @param direction - Up = Climb, Left = Scale, Right = Switch, Down = Floor
	 */
	public void setToPosition (final Controller.DPadDirection direction) {
		
		switch (direction) {
		case DOWN:
			targetPosition = FLOOR_POSITION;
			break;
		case RIGHT:
			targetPosition = SWITCH_POSITION;
			break;
		case LEFT:
			targetPosition = SCALE_POSITION;
			break;
		case UP:
			targetPosition = CLIMB_POSITION;
			break;
		case NONE:
		default:
			//targetPosition = pot.get();
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
		return false;//lift.get() == 0F && isAtTargetPosition() ;
	}
	
	public boolean isAtTargetPosition () {
		return false;//Math.abs(Math.abs(targetPosition) - Math.abs(pot.get())) <= DEADBAND ;
	}
	
	public void setTargetPosition (double target) {
		targetPosition = target;
	}
	
	public double liftPosition () {
		return 0.0;//pot.get();
	}
	
	public void MoveToPosition () {
		
		//if (Math.abs(Math.abs(targetPosition) - Math.abs(pot.get())) >= DEADBAND ) {
		//	setSpeed(getProportionalSet());
		//} else {
		//	setSpeed(0.0);
		//}
		
	}
	/**
	 * 
	 * @return - Takes %distance to target position and sets a speed based on that % of a set Max speed. Cannot go above 1 or below -1
	 */
//	private double getProportionalSet () {
//		
//		double proportionalSet = (targetPosition- pot.get()) / Math.abs(targetPosition);
//		
//		if (proportionalSet > 1.0)
//			proportionalSet = 1.0;
//		else if (proportionalSet < -1.0)
//			proportionalSet = -1.0;
//		
//		final double signModifier = (proportionalSet / Math.abs(proportionalSet));
//		proportionalSet = signModifier * SPEED * (1.0 - Math.abs(proportionalSet));
//		
//		return proportionalSet;
//		
//	}
	
}
