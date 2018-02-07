package Commands;

import org.usfirst.frc.team131.robot.Controller;

import NewAutoShell.ChaosCommand;
import SystemComponents.LinearLift;

public class Lift extends ChaosCommand {

	LinearLift linearLift;
	public static final String NAME = "Lift";
	
	public Lift(int argsLength, LinearLift linearLift) {
		super(argsLength);
		this.linearLift = linearLift;
	}

	@Override
	protected boolean isFinished() {
		return linearLift.liftIsStopped();
	}

	@Override
	protected void initialize () {
		
		linearLift.setTargetPosition(Double.parseDouble(args[0]));
		
	}
	

	@Override 
	protected void execute() {
		linearLift.MoveToPosition();
	}
	
	@Override
	protected void end () {
		linearLift.setTargetPosition(linearLift.liftPosition());
	}
}
