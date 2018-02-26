package Commands;

import org.usfirst.frc.team131.robot.DriveBase;

import NewAutoShell.ChaosCommand;
import NewAutoShell.GameData;

public class SwitchDrive extends ChaosCommand {

	DriveBase drive;
	public static final String NAME = "SwitchDrive";
	
	public SwitchDrive(int argsLength, DriveBase drive) {
		super(argsLength);
		this.drive = drive;
	}
	
	@Override
	protected void initialize () {
		drive.resetEncoders();
		GameData data = new GameData ();
		if (data.closeSwitchIsLeft()) {
			drive.setRightTalonToPosition(Double.parseDouble(args[0]));
		} else {
		
			drive.setRightTalonToPosition(Double.parseDouble(args[1]));
			
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
