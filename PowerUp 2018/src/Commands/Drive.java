package Commands;

import NewAutoShell.ChaosCommand;
import SystemComponents.DriveBase;

public class Drive extends ChaosCommand {
	
	DriveBase driveBase;
	public static final String NAME = "Drive";
	
	public Drive (int argsLength, DriveBase driveBase) {
		super (argsLength);
		this.driveBase = driveBase;
	}
	
	@Override
	protected boolean isFinished() {
		return doneDriving (driveBase);
	}
	
	@Override
	protected void initialize () {
		driveBase.resetEncoders();
		driveBase.setTalonsToPosition(Double.valueOf(args[0]));
		System.out.println("started");
	
	}
	
	@Override 
	protected void execute() {
		//driveBase.encoderData();
		driveBase.velocityData();
	}
	
	@Override
	protected void end () {
		driveBase.resetEncoders();
		driveBase.end();
	}

}
