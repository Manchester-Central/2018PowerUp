package NewAutoShell;

import org.usfirst.frc.team131.robot.DriveBase;

import edu.wpi.first.wpilibj.command.Command;

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
		
		double talonValueRightAbs = Math.abs(DriveBase.ticksToInches(drive.getRightTalonEncoderValue()));
		double talonValueLeftAbs = Math.abs(DriveBase.ticksToInches(drive.getLeftTalonEncoderValue()));
		
		double rightTargetAbs = Math.abs(drive.getRightTarget());
		double leftTargetAbs = Math.abs(drive.getLeftTarget());
		
		boolean shouldFinish = (Math.abs(leftTargetAbs - talonValueLeftAbs) < 2.0 
				|| Math.abs(rightTargetAbs - talonValueRightAbs) < 2.0);
		
		System.out.println(rightTargetAbs + " " + rightTargetAbs + "\n " + shouldFinish);
		
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
