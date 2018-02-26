package Commands;

import org.usfirst.frc.team131.robot.DriveBase;

import NewAutoShell.ChaosCommand;
import NewAutoShell.GameData;

public class ScaleDrive extends ChaosCommand {

	DriveBase drive;
	public static final String NAME = "ScaleDrive";
	
	public ScaleDrive(int argsLength, DriveBase drive) {
		super(argsLength);
		this.drive = drive;
	}
	
	@Override
	protected void initialize () {
		GameData data = new GameData ();
		if (data.scaleIsLeft()) {
			drive.setRightTalonToPosition(Double.valueOf(args[0]));
		} else {
		
			drive.setRightTalonToPosition(Double.valueOf(args[1]));
			
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
	}

}
