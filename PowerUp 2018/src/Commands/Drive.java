package Commands;

import NewAutoShell.ChaosCommand;
import SystemComponents.DriveBase;

public class Drive extends ChaosCommand {
	
	DriveBase driveBase;
	public static final String NAME = "Drive";
	
	public Drive (DriveBase driveBase, int argsLength) {
		super (argsLength);
		this.driveBase = driveBase;
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		boolean shouldFinish = Math.abs(driveBase.getRightEncoderVelocity()) <= 0.1 && Math.abs(driveBase.getLeftEncoderVelocity()) <= 0.1
				&& Math.abs(driveBase.getLeftTalonEncoderValue()) > 100 && Math.abs(driveBase.getRightTalonEncoderValue()) > 100;
		System.out.println("shouldFinish: " + shouldFinish);
		return shouldFinish;
	}
	
	@Override
	protected void initialize () {
		
		driveBase.setTalonsToPosition(Double.valueOf(args[0]));
		System.out.println("started");
	
	}
	
	@Override 
	protected void execute() {
		//driveBase.encoderData();
		driveBase.velocityData();
	}
	
	@Override
	protected void end () {
		driveBase.end();
	}

}
