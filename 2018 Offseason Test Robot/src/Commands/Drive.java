package Commands;

import org.usfirst.frc.team131.robot.DriveBase;

import NewAutoShell.ChaosCommand;

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
		///System.out
	}
	
	@Override 
	protected void execute() {
		driveBase.encoderData();
		//driveBase.velocityData();
		driveBase.tankCorrectedDrive(Double.parseDouble(args[0]), Double.parseDouble(args[0]));
	}
	
	@Override
	protected void end () {
		driveBase.resetEncoders();
		driveBase.end();
		super.end();
	}

}
