package Commands;

import org.usfirst.frc.team131.robot.DriveBase;

import NewAutoShell.ChaosCommand;
import NewAutoShell.GameData;

public class ScaleTurn extends ChaosCommand {


	
	DriveBase drive;
	GameData data;
	public static final String NAME = "ScaleTurnRight";
	
	public ScaleTurn(int argsLength, DriveBase drive) {
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
		drive.velocityData();
		if (data.scaleIsLeft()) {
			drive.turnToAngleRight(Double.valueOf(args[0]));
		} else {
		
			drive.turnToAngleRight(Double.valueOf(args[1]));
			
		}
	}
	
	@Override
	protected void end () {
		drive.resetEncoders();
		drive.end();
		super.end();
	}

}
