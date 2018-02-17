package NewAutoShell;

import SystemComponents.DriveBase;
import edu.wpi.first.wpilibj.command.Command;

public abstract class ChaosCommand extends Command {
	
	protected String[] args;
	private final int argsLength;
	
	public void setArgs (String[] args) {
		this.args = args;
		System.out.println(String.join(" ", args));
	}
	
	public int getArgsLength () {
		return argsLength;
	}
	
	public int getCurrentArgsSize () {
		return args.length;
	}
	
	public ChaosCommand(int argsLength) {
        this.argsLength = argsLength;
    }
	/**
	 * 
	 * @param driveBase drive
	 * @return - returns whether both talons have stopped and are within the target
	 */
	protected boolean doneDriving (DriveBase drive) {
		boolean shouldFinish = Math.abs(drive.getRightEncoderVelocity()) <= 0.1 
				&& Math.abs(drive.getLeftEncoderVelocity()) <= 0.1
				&& Math.abs(Math.abs(drive.getLeftTalonEncoderValue()) - Math.abs(drive.getLeftTarget())) < 100 
				&& Math.abs(Math.abs(drive.getRightTalonEncoderValue()) - Math.abs(drive.getRightTarget())) < 100;
		System.out.println("shouldFinish: " + shouldFinish);
		return shouldFinish;
	}

    /**
     *  Called just before this Command runs the first time
     */
	@Override
    protected void initialize() {
    	
    }
    
    
    

}
