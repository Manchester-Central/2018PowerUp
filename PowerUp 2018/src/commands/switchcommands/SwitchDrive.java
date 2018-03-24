package commands.switchcommands;

import auto.builder.ChaosCommand;
import auto.builder.GameData;
import system.components.DriveBase;

public class SwitchDrive extends ChaosCommand {

	DriveBase drive;
	public static final String NAME = "SwitchDrive";
	GameData data;
	
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
		
		if (data.closeSwitchIsLeft()) {
			
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
