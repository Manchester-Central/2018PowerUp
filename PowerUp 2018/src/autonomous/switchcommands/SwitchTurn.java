package autonomous.switchcommands;

import autonomous.builder.ChaosCommand;
import autonomous.builder.GameData;
import system.components.DriveBase;

public class SwitchTurn extends ChaosCommand {

	// CounterClockwise is positive turn
	
	DriveBase drive;
	GameData data;
	public static final String NAME = "SwitchTurnRight";
	
	public SwitchTurn(int argsLength, DriveBase drive) {
		super(argsLength, NAME);
		this.drive = drive;
	}
	
	@Override
	protected void initialize () {
		
		super.initialize();
		drive.resetEncoders();
		data = new GameData ();
		//drive.setGains(0.0125, 0.1);
		
		if (data.closeSwitchIsLeft()) {
			
			System.out.println("Target: " + Double.parseDouble(args[0]));
			
		} else {
		
			System.out.println("Target: " +Double.parseDouble(args[1]));
			
		}
	}

	@Override
	protected boolean isFinished() {
		return doneDriving (drive);
	}
	
	@Override 
	protected void execute() {

		if (data.closeSwitchIsLeft()) {
			
			drive.turnToAngleRight(Double.parseDouble(args[0]));
			
		} else {
		
			drive.turnToAngleRight(Double.parseDouble(args[1]));
			
		}
		
	}
	
	@Override
	protected void end () {
		drive.resetEncoders();
		drive.end();
		super.end();
	}

}
