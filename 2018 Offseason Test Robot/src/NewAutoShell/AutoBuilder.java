package NewAutoShell;

import org.usfirst.frc.team131.robot.DriveBase;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoBuilder {
	
	Preferences prefs;
	int stage = 1;
	
	DriveBase drive;
	
	
	public AutoBuilder (DriveBase drive) {

		this.drive = drive;
		prefs = Preferences.getInstance();
	}
	
	/**
	 * Takes the commands from the Preference Table Line and creates a command group to be run
	 * @param commandGroup
	 */
	public void createCommandGroup (CommandGroup commandGroup) {
		
		if (prefs.getKeys() == null || prefs.getKeys().isEmpty()) {
			System.out.println("No input given");
			return;
		}

		for (String key : prefs.getKeys()) {
			
			String input = prefs.getString(key, "");
			PreferenceTableLine line = new PreferenceTableLine(input, drive);
			
			if (line.hasCommands()) {
				System.out.println(String.format("Key %s has a bad value: %s", key, input));
				continue;
			}
			
			if (line.getCommand().getArgsLength() != line.getCommand().getCurrentArgsSize() || line.getCommandGroup() != null) {
				System.out.println(String.format("Key %s has a bad value: %s", key, input));
				continue;
			}
			
			if (line.getCommand() != null) {
				if (line.getIsParallel()) {
					commandGroup.addParallel(line.getCommand());
				} else {
					commandGroup.addSequential(line.getCommand());
				}
			} else {
				if (line.getIsParallel()) {
					commandGroup.addParallel(line.getCommandGroup());
				} else {
					commandGroup.addSequential(line.getCommandGroup());
				}
			}
			
				
		}
		
		
	}
	
	
}
