package NewAutoShell;

import java.util.regex.Pattern;

import Commands.Drive;
import Commands.ScaleDrive;
import Commands.ScaleTurn;
import Commands.SwitchDrive;
import Commands.SwitchTurn;
import Commands.TestChaosCommand;
import Commands.Turn;
import SystemComponents.DriveBase;

public class PreferenceTableLine {
	
	private ChaosCommand command;
	private boolean isParallel;
	
	private String[] commands = {TestChaosCommand.NAME , SwitchDrive.NAME , ScaleDrive.NAME 
			, Drive.NAME, SwitchTurn.NAME, ScaleTurn.NAME, Turn.NAME };
	
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
			command = new SwitchDrive (2, drive);
			break;
		case ScaleDrive.NAME:
			command = new ScaleDrive (2, drive);
			break;
		case Turn.NAME:
			command = new Turn (1, drive);
			break;
		case SwitchTurn.NAME:
			command = new SwitchTurn (2, drive);
			break;
		case ScaleTurn.NAME:
			command = new ScaleTurn (2, drive);
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
