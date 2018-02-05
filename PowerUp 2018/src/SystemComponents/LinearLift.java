package SystemComponents;

import org.usfirst.frc.team131.robot.Controller;
import org.usfirst.frc.team131.robot.PortConstants;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class LinearLift {
	
	private final int FLOOR_POSITION = 0;
	private final int SWITCH_POSITION = 500;
	private final int SCALE_POSITION = 1000;
	private final int CLIMB_POSITION = 1500;
	private final int TOP_POSITION = 1600;
	
	private final int DEADBAND = 100;
	
	private final double SPEED = 0.3;
	
	Victor lift;
	
	Encoder encoder;
	
	int targetPosition;
	int currentPosition;
	
	public LinearLift() {
		lift = new Victor (PortConstants.LINEAR_LIFT);
		encoder = new Encoder (PortConstants.LINEAR_LIFT_ENCODER_INPUT, PortConstants.LINEAR_LIFT_ENCODER_OUTPUT);
		targetPosition = FLOOR_POSITION;
		currentPosition = encoder.get();
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
		currentPosition = encoder.get();
		if (targetPosition < currentPosition - DEADBAND) {
			lift.set(SPEED);
		} else if (targetPosition > currentPosition + DEADBAND) {
			lift.set(-SPEED);
		} else {
			lift.set(0.0);
		}
	}
	
	public boolean liftIsStopped () {
		return lift.get() == 0 && Math.abs(Math.abs(currentPosition) - Math.abs(encoder.get())) <= DEADBAND ;
	}
	
	public double liftPosition () {
		return encoder.get();
	}
	
}
