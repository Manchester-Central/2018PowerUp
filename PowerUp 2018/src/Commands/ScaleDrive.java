package Commands;

import NewAutoShell.ChaosCommand;
import NewAutoShell.GameData;
import SystemComponents.DriveBase;

public class ScaleDrive extends ChaosCommand {

	DriveBase drive;
	public static final String NAME = "ScaleDrive";
	
	public ScaleDrive(int argsLength, DriveBase drive) {
		super(argsLength, NAME);
		this.drive = drive;
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.scaleIsLeft()) {
			drive.setTalonsToPosition(Double.valueOf(args[0]));
		} else {
		
			drive.setTalonsToPosition(Double.valueOf(args[1]));
			
		}
		
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
		super.end();
	}

}
