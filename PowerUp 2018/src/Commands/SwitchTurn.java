package Commands;

import NewAutoShell.ChaosCommand;
import NewAutoShell.GameData;
import SystemComponents.DriveBase;

public class SwitchTurn extends ChaosCommand {

	// CounterClockwise is positive turn
	
	DriveBase drive;
	public static final String NAME = "SwitchTurnRight";
	
	public SwitchTurn(int argsLength, DriveBase drive) {
		super(argsLength, NAME);
		this.drive = drive;
	}
	
	@Override
	protected void initialize () {
		
		super.initialize();
		drive.resetEncoders();
		GameData data = new GameData ();
		if (data.closeSwitchIsLeft()) {
			drive.turnToAngleRight(Double.parseDouble(args[0]));
		} else {
		
			drive.turnToAngleRight(Double.parseDouble(args[1]));
			
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
