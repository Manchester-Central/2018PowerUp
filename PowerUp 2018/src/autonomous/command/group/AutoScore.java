package autonomous.command.group;

import autonomous.commands.Extend;
import autonomous.commands.Lift;
import autonomous.commands.Output;
import autonomous.commands.Retract;
import autonomous.commands.Wait;
import edu.wpi.first.wpilibj.command.CommandGroup;
import system.components.CubeManipulator;
import system.components.LinearLift;

public class AutoScore extends CommandGroup {
	
	public static final String NAME = "AutoScore";
	
	public AutoScore(LinearLift lift, CubeManipulator roller) {
		
		Wait sequentialWait = new Wait(1);
		String[] waitTime = { String.valueOf(1000) };
		sequentialWait.setArgs(waitTime);
		
		addSequential (new Extend (0, roller));
		
		addSequential(sequentialWait);
		
		addSequential (new Output (0, roller));
		
		addSequential(sequentialWait);
		
		addSequential (new Retract (0, roller));
		
		addSequential(sequentialWait);
		
		Lift liftCommand = new Lift (1, lift);
		String[] args = { String.valueOf(LinearLift.FLOOR_POSITION_INCHES) };
		liftCommand.setArgs(args);
		addSequential (liftCommand);
		
	}
}
