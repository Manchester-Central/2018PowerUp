package command.group;

import Commands.Extend;
import Commands.Lift;
import Commands.Output;
import Commands.Retract;
import SystemComponents.CubeManipulator;
import SystemComponents.LinearLift;
import edu.wpi.first.wpilibj.command.CommandGroup;

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
