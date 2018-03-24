package autonomous.scalecommands;

import autonomous.builder.ChaosCommand;
import autonomous.builder.GameData;
import system.components.DriveBase;

public class ScaleDrive extends ChaosCommand {

	DriveBase drive;
	public static final String NAME = "ScaleDrive";
	GameData data;
	
	public ScaleDrive(int argsLength, DriveBase drive) {
		super(argsLength, NAME);
		this.drive = drive;
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		data = new GameData ();
		
	}

	@Override
	protected boolean isFinished() {
		return doneDriving (drive);
	}
	

	@Override 
	protected void execute() {
		
		if (data.scaleIsLeft()) {
			drive.tankCorrectedDrive(Double.parseDouble(args[0]), Double.parseDouble(args[0]));
		} else {
		
			drive.tankCorrectedDrive(Double.parseDouble(args[1]), Double.parseDouble(args[1]));
			
		}
		
		drive.velocityData();
	}
	
	@Override
	protected void end () {
		drive.resetEncoders();
		drive.end();
		super.end();
	}

}
