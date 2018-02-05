package Commands;

import NewAutoShell.ChaosCommand;
import NewAutoShell.GameData;
import SystemComponents.DriveBase;

public class ScaleTurn extends ChaosCommand {

	DriveBase drive;
	
	public ScaleTurn(int argsLength, DriveBase drive) {
		super(argsLength);
		this.drive = drive;
		// TODO Auto-generated constructor stub
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
