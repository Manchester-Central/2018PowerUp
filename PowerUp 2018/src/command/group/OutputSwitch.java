package command.group;

import commands.Extend;
import commands.Lift;
import commands.Output;
import commands.Retract;
import edu.wpi.first.wpilibj.command.CommandGroup;
import system.components.CubeManipulator;
import system.components.LinearLift;

public class OutputSwitch extends CommandGroup {
	
	public static final String NAME = "OutputSwitch";
	
	public OutputSwitch(LinearLift lift, CubeManipulator roller) {
		
		Lift liftCommand = new Lift (1, lift);
		String[] args = { String.valueOf(LinearLift.SWITCH_POSITION_INCHES) };
		liftCommand.setArgs(args);
		addSequential (liftCommand);
		
		addSequential (new Extend (0, roller));
		addSequential (new Output (0, roller));
		
		addSequential (new Retract (0, roller));
		
	}


}
