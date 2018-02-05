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
	
	protected boolean doneDriving (DriveBase drive) {
		boolean shouldFinish = Math.abs(drive.getRightEncoderVelocity()) <= 0.1 && Math.abs(drive.getLeftEncoderVelocity()) <= 0.1
				&& Math.abs(drive.getLeftTalonEncoderValue()) > 100 && Math.abs(drive.getRightTalonEncoderValue()) > 100;
		System.out.println("shouldFinish: " + shouldFinish);
		return shouldFinish;
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }
    
    
    

}
