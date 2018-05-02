package autonomous.commands;


import autonomous.builder.ChaosCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import system.components.LinearLift;

public class Lift extends ChaosCommand {

	LinearLift linearLift;
	public static final String NAME = "Lift";
	
	public Lift(int argsLength, LinearLift linearLift) {
		super(argsLength, NAME);
		this.linearLift = linearLift;
	}

	@Override
	protected boolean isFinished() {
		return linearLift.liftIsStopped();
	}

	@Override
	protected void initialize () {
		super.initialize();
		System.out.println("Lift State Started");
		linearLift.setTargetPosition(Double.parseDouble(args[0]));
		
	}
	
	@Override
	protected void end () {
		linearLift.setTargetPosition(linearLift.liftPosition());
		super.end();
	}
}
