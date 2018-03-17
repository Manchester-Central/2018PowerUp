package NewAutoShell;

import java.util.Comparator;
import java.util.Vector;

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
		
		Vector<String> keys = prefs.getKeys();
		if (keys.contains(".type")) { 
			keys.remove(keys.indexOf(".type"));
		}
		
		
		keys.sort(new Comparator< String>() {
			@Override
			public int compare(String a, String b) {
				
				int first = Integer.parseInt(a.split(" ")[0]);
				int second = Integer.parseInt(b.split(" ")[0]);
				return first - second;
			}
		});
		
		for (String key : keys) {
			
			String input = prefs.getString(key, "");
			PreferenceTableLine line = new PreferenceTableLine(input, drive);
			
			if (line.hasCommands() == false) {
				System.out.println(String.format("Key %s has a bad value(1): %s", key, input));
				continue;
			}
			if (line.getCommand() == null) {
				System.out.println ("command null");
			}
			if (line.getCommandGroup() == null) {
				System.out.println ("command group null");
			}
			
			if (line.getCommand().getArgsLength() != line.getCommand().getCurrentArgsSize() || line.getCommandGroup() != null) {
				System.out.println(String.format("Key %s has a bad value(1): %s", key, input));
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
