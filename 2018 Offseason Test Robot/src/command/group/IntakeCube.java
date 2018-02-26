package command.group;

import Commands.Extend;
import Commands.Intake;
import Commands.Lift;
import Commands.Pinch;
import Commands.Release;
import Commands.Retract;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeCube extends CommandGroup {
	
	public static final String NAME = "IntakeCube";
	
	public IntakeCube() {
		
		
		addSequential(new Release (0));
		
		Lift liftCommand = new Lift (1);
		String[] args = { "Intake Position" };
		liftCommand.setArgs(args);
		addSequential (liftCommand);
		
		addSequential (new Extend (0));
		
		liftCommand = new Lift (1);
		String[] args2 = { "Floor Position" };
		liftCommand.setArgs(args2);
		addSequential (liftCommand);
		
		
		addSequential (new Intake (0));

		addSequential (new Pinch (0));
		
		liftCommand = new Lift (1);
		String[] args3 = { "Intake Position" };
		liftCommand.setArgs(args3);
		addSequential (liftCommand);
		
		addSequential (new Retract (0));
		
	}


}
