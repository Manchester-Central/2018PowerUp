package commands;

import auto.builder.ChaosCommand;
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
		System.out.println("started");
	}
	
	@Override 
	protected void execute() {
		
		driveBase.tankCorrectedDrive(Double.parseDouble(args[0]), Double.parseDouble(args[0]));
		
		driveBase.velocityData();
		driveBase.talonSpeedToVictors();
	}
	
	@Override
	protected void end () {
		driveBase.resetEncoders();
		driveBase.end();
		super.end();
	}

}
