package NewAutoShell;

import java.util.Map;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoBuilder {
	
	Map<String, ChaosCommand> commands;
	
	Preferences prefs;
	int stage = 1;
	
	public AutoBuilder (Map<String, ChaosCommand> commands) {
		this.commands = commands;

	}
	
	public String getAutoStageString(int i) {
		return prefs.getString("Auto Stage " + i, "none");
	}
	
	
	// Takes the commands from the Preference Table Line and creates a command group to be run
	public void createCommandGroup (CommandGroup commandGroup) {
		
		if (prefs.getKeys() == null || prefs.getKeys().isEmpty()) {
			System.out.println("No input given");
			return;
		}
		
		for (String key : prefs.getKeys()) {
			
			String input = prefs.getString(key, "");
			
			PreferenceTableLine line = new PreferenceTableLine(input, commands);
			
			if (line.getCommand() == null) {
				System.out.println(String.format("Key %s has a bad value: %s", key, input));
				continue;
			}
			
			if (line.getIsParallel()) {
				commandGroup.addParallel(line.getCommand());
			} else {
				commandGroup.addSequential(line.getCommand());
			}
				
		}
		
		
	}
	
	
}
