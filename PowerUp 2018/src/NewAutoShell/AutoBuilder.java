package NewAutoShell;

import java.util.Map;

import SystemComponents.Climber;
import SystemComponents.CubeManipulator;
import SystemComponents.DriveBase;
import SystemComponents.LinearLift;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoBuilder {
	
	Preferences prefs;
	int stage = 1;
	
	DriveBase drive;
//	LinearLift lift;
//	CubeManipulator cubeManipulator;
//	Climber climber;
	
	public AutoBuilder (DriveBase drive /*, LinearLift lift, CubeManipulator cubeManipulator, Climber climber */) {

		this.drive = drive;
		prefs = Preferences.getInstance();
//		this.lift = lift;
//		this.cubeManipulator = cubeManipulator;
//		this.climber = climber;
	}
	
	//public String getAutoStageString(int i) {
	//	return prefs.getString("Auto Stage " + i, "none");
	//}
	
	
	// Takes the commands from the Preference Table Line and creates a command group to be run
	public void createCommandGroup (CommandGroup commandGroup) {
		
		if (prefs.getKeys() == null || prefs.getKeys().isEmpty()) {
			System.out.println("No input given");
			return;
		}

		for (String key : prefs.getKeys()) {
			
			String input = prefs.getString(key, "");
			PreferenceTableLine line = new PreferenceTableLine(input, drive /*, lift, cubeManipulator, climber */);
			
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
