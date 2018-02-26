package command.group;

import Commands.Extend;
import Commands.Lift;
import Commands.Output;
import Commands.Retract;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class OutputScale extends CommandGroup {
	
	public static final String NAME = "OutputScale";
	
	public OutputScale() {
		
		Lift liftCommand = new Lift (1);
		String[] args = { "Output Scale Position" };
		liftCommand.setArgs(args);
		addSequential (liftCommand);
		
		addSequential (new Extend (0));
		addSequential (new Output (0));
		
		addSequential (new Retract (0));
		
	}

}
