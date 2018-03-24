package commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClearPrefs extends Command {

	Preferences prefs;
	
    public ClearPrefs() {
       prefs = Preferences.getInstance();
       setRunWhenDisabled(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	for (String key : prefs.getKeys()) {
			prefs.remove(key);
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	for (String key : prefs.getKeys()) {
			prefs.remove(key);
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
