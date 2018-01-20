package StuffToLookAt;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PullUpCommand extends Command {
	
	double time;
	
    public PullUpCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	time = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	time++;
    	System.out.println(time);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return time > 50;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
