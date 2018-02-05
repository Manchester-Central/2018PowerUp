package Commands;

import NewAutoShell.ChaosCommand;
import NewAutoShell.GameData;
import SystemComponents.DriveBase;
import edu.wpi.first.wpilibj.DriverStation;

public class ScaleDrive extends ChaosCommand {

	DriveBase drive;
	public static final String NAME = "ScaleConditionalDrive";
	
	public ScaleDrive(int argsLength, DriveBase drive) {
		super(argsLength);
		this.drive = drive;
	}
	
	@Override
	protected void initialize () {
		GameData data = new GameData ();
		if (data.scaleIsLeft()) {
			drive.setTalonsToPosition(Integer.getInteger(args[0]));
		} else {
		
			drive.setTalonsToPosition(Integer.getInteger(args[1]));
			
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
		drive.end();
	}

}
