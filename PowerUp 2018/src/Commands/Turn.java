package Commands;

import NewAutoShell.ChaosCommand;
import SystemComponents.DriveBase;

public class Turn extends ChaosCommand {

	DriveBase drive;
	public static final String NAME = "Turn";
	
	public Turn(int argsLength, DriveBase drive) {
		super(argsLength);
		this.drive = drive;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void initialize () {
		drive.turnToAngle(Double.parseDouble(args[0]));
	}
	
	@Override
	protected boolean isFinished() {
		return doneDriving (drive);
	}
	

	@Override 
	protected void execute() {
		//driveBase.encoderData();
		drive.velocityData();
	}
	
	@Override
	protected void end () {
		drive.resetEncoders();
		drive.end();
	}


}
