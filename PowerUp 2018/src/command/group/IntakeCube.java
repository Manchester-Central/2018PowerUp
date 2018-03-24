package command.group;

import commands.Extend;
import commands.Intake;
import commands.Lift;
import commands.Pinch;
import commands.Release;
import commands.Retract;
import edu.wpi.first.wpilibj.command.CommandGroup;
import system.components.CubeManipulator;
import system.components.LinearLift;

public class IntakeCube extends CommandGroup {
	
	public static final String NAME = "IntakeCube";
	
	public IntakeCube(CubeManipulator cubeManipulator, LinearLift lift) {
		
		
		addSequential(new Release (0, cubeManipulator));
		
		Lift liftCommand = new Lift (1, lift);
		String[] args = { String.valueOf(LinearLift.EXTEND_POSITION_INCHES) };
		liftCommand.setArgs(args);
		addSequential (liftCommand);
		
		addSequential (new Extend (0, cubeManipulator));
		
		liftCommand = new Lift (1, lift);
		String[] args2 = { String.valueOf(LinearLift.FLOOR_POSITION_INCHES) };
		liftCommand.setArgs(args2);
		addSequential (liftCommand);
		
		
		addSequential (new Intake (0, cubeManipulator));

		addSequential (new Pinch (0, cubeManipulator));
		
		liftCommand = new Lift (1, lift);
		String[] args3 = { String.valueOf(LinearLift.EXTEND_POSITION_INCHES) };
		liftCommand.setArgs(args3);
		addSequential (liftCommand);
		
		addSequential (new Retract (0, cubeManipulator));
		
	}


}
