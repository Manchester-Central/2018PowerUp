package command.group;

import Commands.Extend;
import Commands.Intake;
import Commands.Lift;
import Commands.Retract;
import SystemComponents.CubeManipulator;
import SystemComponents.LinearLift;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeCube extends CommandGroup {
	
	public static final String NAME = "IntakeCube";
	
	public IntakeCube(CubeManipulator cubeManipulator, LinearLift lift) {
		
		Lift liftCommand = new Lift (1, lift);
		String[] args = { String.valueOf(LinearLift.INTAKE_POSITION) };
		liftCommand.setArgs(args);
		addSequential (liftCommand);
		
		addSequential (new Extend (0, cubeManipulator));
		
		liftCommand = new Lift (1, lift);
		String[] args2 = { String.valueOf(LinearLift.FLOOR_POSITION) };
		liftCommand.setArgs(args2);
		addSequential (liftCommand);
		
		addSequential (new Intake (0, cubeManipulator));
		
		liftCommand = new Lift (1, lift);
		String[] args3 = { String.valueOf(LinearLift.INTAKE_POSITION) };
		liftCommand.setArgs(args3);
		addSequential (liftCommand);
		
		addSequential (new Retract (0, cubeManipulator));
		
	}


}
