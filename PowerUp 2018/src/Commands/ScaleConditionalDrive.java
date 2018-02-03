package Commands;

import NewAutoShell.ChaosCommand;
import NewAutoShell.GameData;
import SystemComponents.DriveBase;
import edu.wpi.first.wpilibj.DriverStation;

public class ScaleConditionalDrive extends ChaosCommand {

	DriveBase drive;
	public static final String NAME = "ScaleConditionalDrive";
	
	public ScaleConditionalDrive(int argsLength, DriveBase drive) {
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
		boolean shouldFinish = Math.abs(drive.getRightEncoderVelocity()) <= 0.1 && Math.abs(drive.getLeftEncoderVelocity()) <= 0.1
				&& Math.abs(drive.getLeftTalonEncoderValue()) > 100 && Math.abs(drive.getRightTalonEncoderValue()) > 100;
		System.out.println("shouldFinish: " + shouldFinish);
		return shouldFinish;
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
