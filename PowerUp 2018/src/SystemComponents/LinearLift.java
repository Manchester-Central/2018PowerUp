package SystemComponents;

import java.awt.Color;

import org.usfirst.frc.team131.robot.Controller;
import org.usfirst.frc.team131.robot.PortConstants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Victor;

public class LinearLift {
	
	public static final double FLOOR_POSITION = 0.0;
	public static final double INTAKE_POSITION = 8.0;
	public static final double SWITCH_POSITION = 36.0;
	public static final double SCALE_POSITION = 84.0;
	public static final double CLIMB_POSITION = 80.0;
	public static final double TOP_POSITION = 100.0;
	
	private final double DEADBAND = 1;
	
	private final double SPEED = 0.3;
	
	private final double OFFSET = 0;
	private final double RANGE = 84.0;
	
	Victor lift;
	
	//Encoder encoder;
	
	AnalogPotentiometer pot;
	
	double targetPosition;
	
	public LinearLift() {
		pot = new AnalogPotentiometer (new AnalogInput (PortConstants.CHOAS_POT_PORT), RANGE, OFFSET);
		lift = new Victor (PortConstants.LINEAR_LIFT);
		targetPosition = FLOOR_POSITION;
	}
	
	public void setSpeed (final double speed) {
		
		lift.set(speed);
		
	}
	
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
			targetPosition = pot.get();
			break;
		}
		
	}
	
	public void setToIntakePosition () {
		targetPosition = INTAKE_POSITION;
	}
	
	public void setToFloorPosition () {
		targetPosition = FLOOR_POSITION;
	}
	
	public void MoveToPosition () {
		
		if (Math.abs(Math.abs(targetPosition) - Math.abs(pot.get())) >= DEADBAND ) {
			lift.set(getProportionalSet());
		} else {
			lift.set(0.0);
		}
		
	}
	
	public boolean liftIsStopped () {
		return lift.get() == 0F && isAtTargetPosition() ;
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
	
	private double getProportionalSet () {
		
		double proportionalSet = (targetPosition- pot.get()) / Math.abs(targetPosition);
		
		if (proportionalSet > 1.0)
			proportionalSet = 1.0;
		else if (proportionalSet < -1.0)
			proportionalSet = -1.0;
		
		final double signModifier = (proportionalSet / Math.abs(proportionalSet));
		proportionalSet = signModifier * SPEED * (1.0 - Math.abs(proportionalSet));
		
		return proportionalSet;
		
	}
	
}
