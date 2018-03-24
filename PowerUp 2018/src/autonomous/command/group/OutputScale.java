package autonomous.command.group;

import autonomous.commands.Extend;
import autonomous.commands.Lift;
import autonomous.commands.Output;
import autonomous.commands.Retract;
import edu.wpi.first.wpilibj.command.CommandGroup;
import system.components.CubeManipulator;
import system.components.LinearLift;

public class OutputScale extends CommandGroup {
	
	public static final String NAME = "OutputScale";
	
	public OutputScale(LinearLift lift, CubeManipulator roller) {
		
		Lift liftCommand = new Lift (1, lift);
		String[] args = { String.valueOf(LinearLift.HIGH_POSITION_INCHES) };
		liftCommand.setArgs(args);
		addSequential (liftCommand);
		
		addSequential (new Extend (0, roller));
		addSequential (new Output (0, roller));
		
		addSequential (new Retract (0, roller));
		
	}

}
