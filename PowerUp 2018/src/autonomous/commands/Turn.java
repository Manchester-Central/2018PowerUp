package autonomous.commands;

import autonomous.builder.ChaosCommand;
import system.components.DriveBase;

public class Turn extends ChaosCommand {

	
	
	DriveBase drive;
	public static final String NAME = "TurnRight";
	
	public Turn(int argsLength, DriveBase drive) {
		super(argsLength, NAME);
		this.drive = drive;
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		//drive.setGains(0.0125, 0.1);
	}
	
	@Override
	protected boolean isFinished() {
		return doneDriving (drive);
	}
	

	@Override 
	protected void execute() {

		drive.turnToAngleRight(Double.parseDouble(args[0]));
		
	}
	
	@Override
	protected void end () {
		drive.resetEncoders();
		drive.end();
		super.end();
	}


}
