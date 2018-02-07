package command.group;

import Commands.Extend;
import Commands.Intake;
import Commands.Lift;
import Commands.Retract;
import SystemComponents.CubeManipulator;
import SystemComponents.LinearLift;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeCube extends CommandGroup {
	
	private final double FLOOR_POSITION = 0;
	private final double INTAKE_POSITION = 300;
	
	public IntakeCube(CubeManipulator cubeManipulator, LinearLift lift) {
		
		Lift liftCommand = new Lift (1, lift);
		String[] args = { String.valueOf(FLOOR_POSITION) };
		liftCommand.setArgs(args);
		addSequential (liftCommand);
		
		addSequential (new Extend (0, cubeManipulator));
		addSequential (new Intake (0, cubeManipulator));
		
		liftCommand = new Lift (1, lift);
		String[] args2 = { String.valueOf(INTAKE_POSITION) };
		liftCommand.setArgs(args2);
		addSequential (liftCommand);
		
		addSequential (new Retract (0, cubeManipulator));
		
	}


}
