package autonomous.commands;

import autonomous.builder.ChaosCommand;
import system.components.DriveBase;

public class Drive extends ChaosCommand {
	
	DriveBase driveBase;
	public static final String NAME = "Drive";
	
	public Drive (int argsLength, DriveBase driveBase) {
		super(argsLength, NAME);
		this.driveBase = driveBase;
	}
	
	@Override
	protected boolean isFinished() {
		return doneDriving (driveBase);
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		driveBase.resetEncoders();
		System.out.println("Target: " + Double.parseDouble(args[0]));
		//driveBase.setGains(0.015, 0.1);
	}
	
	@Override 
	protected void execute() {
		
		driveBase.tankCorrectedDrive(Double.parseDouble(args[0]), Double.parseDouble(args[0]));
		
		driveBase.encoderData();
	
	}
	
	@Override
	protected void end () {
		driveBase.resetEncoders();
		driveBase.end();
		super.end();
	}

}
