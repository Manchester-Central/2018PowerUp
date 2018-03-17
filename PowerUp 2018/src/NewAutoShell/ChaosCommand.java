package NewAutoShell;

import SystemComponents.DriveBase;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class ChaosCommand extends Command {
	
	protected String[] args = new String[0];
	private final int argsLength;
	private String name;

	public ChaosCommand(int argsLength, String name) {
        this.argsLength = argsLength;
        this.name = name;
        
    }
	
	public void setArgs (String[] args) {
		this.args = args;
	}
	
	public int getArgsLength () {
		return argsLength;
	}
	
	public int getCurrentArgsSize () {
		return args.length;
	}
	
	/**
	 * 
	 * @param driveBase drive
	 * @return - returns whether one talon have stopped and are within the target
	 */
	protected boolean doneDriving (DriveBase drive) {
		boolean shouldFinish = (Math.abs(Math.abs(drive.getCorrectedLeftTalonEncoderValue()) - Math.abs(drive.getLeftTarget())) < 100 
				|| Math.abs(Math.abs(drive.getCorrectedRightTalonEncoderValue()) - Math.abs(drive.getRightTarget())) < 100);
		//System.out.println("shouldFinish: " + shouldFinish);
		return shouldFinish;
	}

	//TODO test if this works for turning
	protected boolean doneTurning (DriveBase drive) {
		boolean shouldFinish = ( Math.abs(drive.getLeftTarget()) - Math.abs(drive.getCorrectedLeftTalonEncoderValue()) < 100 
				&& Math.abs(drive.getRightTarget()) - Math.abs(drive.getCorrectedRightTalonEncoderValue())  < 100);
		//System.out.println("shouldFinish: " + shouldFinish);
		return shouldFinish;
	}
	
    /**
     *  Called just before this Command runs the first time
     */
	@Override
    protected void initialize() {
		System.out.println("state started: " + name);
		System.out.flush();
    }
	
	@Override
	protected void end () {
		System.out.println("state ended: " + name);
	}
    
    
    

}
