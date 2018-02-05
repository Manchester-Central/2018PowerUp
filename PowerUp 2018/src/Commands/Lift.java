package Commands;

import org.usfirst.frc.team131.robot.Controller;

import NewAutoShell.ChaosCommand;
import SystemComponents.LinearLift;

public class Lift extends ChaosCommand {

	LinearLift linearLift;
	
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
		
		//linearLift.setToPosition(Controller.DPadDirection.);
		
	}
	

	@Override 
	protected void execute() {
		
	}
	
	@Override
	protected void end () {

	}
}
