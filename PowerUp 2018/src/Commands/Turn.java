package Commands;

import NewAutoShell.ChaosCommand;
import SystemComponents.DriveBase;

public class Turn extends ChaosCommand {

	DriveBase drive;
	
	public Turn(int argsLength, DriveBase drive) {
		super(argsLength);
		this.drive = drive;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void initialize () {
		drive.turnToAngle(Double.parseDouble(args[0]));
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
