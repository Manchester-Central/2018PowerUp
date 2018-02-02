package Commands;

import NewAutoShell.ChaosCommand;
import SystemComponents.DriveBase;
import edu.wpi.first.wpilibj.DriverStation;

public class ScaleConditionalDrive extends ChaosCommand {

DriveBase drive;
	
	public ScaleConditionalDrive(int argsLength, DriveBase drive) {
		super(argsLength);
		this.drive = drive;
	}
	
	@Override
	protected void initialize () {
		
		if (DriverStation.getInstance().getGameSpecificMessage().equals("scaleisredorsomething")) {
			drive.setTalonsToPosition(Integer.getInteger(args[0]));
		} else {
		
			drive.setTalonsToPosition(Integer.getInteger(args[1]));
			
		}
		
	}

	@Override
	protected boolean isFinished() {
		
		return Math.abs(drive.getLeftTalonEncoderValue() - Double.valueOf(args[0])) <= 0.1 && Math.abs(drive.getRightTalonEncoderValue() - Double.valueOf(args[0])) <= 0.1;
	}

}
