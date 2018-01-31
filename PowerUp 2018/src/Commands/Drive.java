package Commands;

import NewAutoShell.ChaosCommand;
import SystemComponents.DriveBase;

public class Drive extends ChaosCommand {
	
	DriveBase driveBase;
	
	public Drive (DriveBase driveBase) {
		this.driveBase = driveBase;
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Math.abs(driveBase.getLeftTalonEncoderValue() - Double.valueOf(args[0])) == 0.1 && Math.abs(driveBase.getRightTalonEncoderValue() - Double.valueOf(args[0])) == 0.1;
	}
	
	@Override
	protected void initialize () {
		
		driveBase.setRightTalonToPosition(Double.valueOf(args[0]));
		
		
		
	}

}
