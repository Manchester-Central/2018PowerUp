package Commands;

import org.usfirst.frc.team131.robot.DriveBase;

import NewAutoShell.ChaosCommand;
import NewAutoShell.GameData;

public class SwitchDrive extends ChaosCommand {

	DriveBase drive;
	GameData data;
	public static final String NAME = "SwitchDrive";
	
	public SwitchDrive(int argsLength, DriveBase drive) {
		super(argsLength, NAME);
		this.drive = drive;
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		drive.resetEncoders();
		data = new GameData ();
		
	}

	@Override
	protected boolean isFinished() {
		return doneDriving (drive);
	}
	

	@Override 
	protected void execute() {
		//driveBase.encoderData();
		if (data.scaleIsLeft()) {
			drive.tankCorrectedDrive(Double.parseDouble(args[0]), Double.parseDouble(args[0]));
		} else {
		
			drive.tankCorrectedDrive(Double.parseDouble(args[1]), Double.parseDouble(args[1]));
			
		}
	}
	
	@Override
	protected void end () {
		drive.resetEncoders();
		drive.end();
		super.end();
	}


}
