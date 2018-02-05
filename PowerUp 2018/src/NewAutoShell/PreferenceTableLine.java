package NewAutoShell;

import java.util.regex.Pattern;

import Commands.Drive;
import Commands.ScaleDrive;
import Commands.SwitchDrive;
import Commands.TestChaosCommand;
import SystemComponents.Climber;
import SystemComponents.CubeManipulator;
import SystemComponents.DriveBase;
import SystemComponents.LinearLift;

public class PreferenceTableLine {
	
	private ChaosCommand command;
	private boolean isParallel;
	
	private String[] commands = {TestChaosCommand.NAME , SwitchDrive.NAME , ScaleDrive.NAME , Drive.NAME };
	
	// Reads each input line and builds the command for each
	public PreferenceTableLine (String input, DriveBase drive/*, LinearLift lift, CubeManipulator cubeManipulator, Climber climber */) {
		
		// Ignores lines with bad syntax
		if(!match(input)) {
			return;
		}
		
		
		String[] splitLine = input.split(";");
		String commandInput = splitLine[0];
		boolean isName = false;
		for (String name : commands) {
			if (name.equals(commandInput)) {
				isName = true;
			}
		}
		
		if (!isName) {
			return;
		}
		String[] commandArgs = splitLine[1].substring(0, splitLine[1].length() - 1).split("&");
		
		isParallel = splitLine[1].endsWith(",");
		
		switch (commandInput) {
		case TestChaosCommand.NAME:
			command = new TestChaosCommand (1);
			break;
		case Drive.NAME:
			command = new Drive (drive, 1);
			break;
		case SwitchDrive.NAME:
			command = new SwitchDrive (1, drive);
			break;
		case ScaleDrive.NAME:
			command = new ScaleDrive (1, drive);
			break;
		default:
			command = null;	
			break;
		}
		
		command.setArgs(commandArgs);
	}
	
	
	// Returns whether input string matches syntax
	private boolean match(String check) {
		return Pattern.matches("[^;]+;[^;]+[,\\.]", check);
	}
	
	
	public ChaosCommand getCommand () {
		return command;
	}
	
	public boolean getIsParallel () {
		return isParallel;
	}
	
	

}
