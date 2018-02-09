package SystemComponents;

import org.usfirst.frc.team131.robot.Controller;
import org.usfirst.frc.team131.robot.PortConstants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Victor;

public class LinearLift {
	
	private final double FLOOR_POSITION = 0.0;
	private final double SWITCH_POSITION = 36.0;
	private final double SCALE_POSITION = 84.0;
	private final double CLIMB_POSITION = 80.0;
	private final double TOP_POSITION = 100.0;
	
	private final double DEADBAND = 1;
	
	private final double SPEED = 0.3;
	
	private final double OFFSET = 0;
	private final double RANGE = 84.0;
	
	Victor lift;
	
	//Encoder encoder;
	
	AnalogPotentiometer pot;
	
	double targetPosition;
	double currentPosition;
	
	public LinearLift() {
		pot = new AnalogPotentiometer (new AnalogInput (PortConstants.CHOAS_POT_PORT), RANGE, OFFSET);
		lift = new Victor (PortConstants.LINEAR_LIFT);
		//encoder = new Encoder (PortConstants.LINEAR_LIFT_ENCODER_INPUT, PortConstants.LINEAR_LIFT_ENCODER_OUTPUT);
		targetPosition = FLOOR_POSITION;
	}
	
	public void setSpeed (double speed) {
		
		lift.set(speed);
		
	}
	
	public void setToPosition (Controller.DPadDirection direction) {
		
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
			targetPosition = currentPosition;
			break;
		}
		
	}
	
	public void MoveToPosition () {
		
		if (Math.abs(Math.abs(targetPosition) - Math.abs(currentPosition)) >= DEADBAND ) {
			lift.set(findProportionalSet());
		} else {
			lift.set(0.0);
		}
		
	}
	
	public boolean liftIsStopped () {
		return lift.get() == 0F && Math.abs(Math.abs(currentPosition) - Math.abs(pot.get())) <= DEADBAND ;
	}
	
	public void setTargetPosition (double target) {
		targetPosition = target;
	}
	
	public double liftPosition () {
		return pot.get();
	}
	
	public double findProportionalSet () {
		
		double proportionalSet = (targetPosition- currentPosition) / Math.abs(targetPosition);
		
		if (proportionalSet > 1.0)
			proportionalSet = 1.0;
		else if (proportionalSet < -1.0)
			proportionalSet = -1.0;
		
		double signModifier = (proportionalSet / Math.abs(proportionalSet));
		proportionalSet = signModifier * SPEED * (1.0 - Math.abs(proportionalSet));
		
		return proportionalSet;
		
	}
	
}
