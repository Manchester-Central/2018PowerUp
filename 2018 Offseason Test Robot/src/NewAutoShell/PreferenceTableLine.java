package NewAutoShell;

import java.util.regex.Pattern;

import org.usfirst.frc.team131.robot.DriveBase;

import Commands.Drive;
import Commands.ScaleDrive;
import Commands.ScaleTurn;
import Commands.SwitchDrive;
import Commands.SwitchTurn;
import Commands.TestChaosCommand;
import Commands.Turn;
import Commands.Wait;
import command.group.IntakeCube;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class PreferenceTableLine {
	
	private ChaosCommand command;
	private CommandGroup commandGroup;
	private boolean isParallel;
	
	private String[] commands = {TestChaosCommand.NAME , SwitchDrive.NAME , ScaleDrive.NAME 
			, Drive.NAME, SwitchTurn.NAME, ScaleTurn.NAME, Turn.NAME, Wait.NAME};
	
	private String[] commandGroups = {IntakeCube.NAME};
	
	/**
	 * 
	 * @param input 
	 * @param drive 
	 * @param lift 
	 * @param cubeManipulator 
	 */
	public PreferenceTableLine (String input, DriveBase drive) {
		
		// Ignores lines with bad syntax
		if(!match(input)) {
			return;
		}
		
		
		String[] splitLine = input.split(";");
		String commandInput = splitLine[0];
		boolean isCommandName = false;
		boolean isCommandGroupName = false;
		for (String name : commands) {
			if (name.equals(commandInput)) {
				isCommandName = true;
			}
		}
		
		if (!isCommandName) {
			for(String name : commandGroups) {
				if(name.equals(commandInput)) {
					isCommandGroupName = true;
				}
			}
			
			if (!isCommandGroupName) {
				return;
			} else {
				
				// Put CommandGroups Here
				switch (commandInput) {
				case IntakeCube.NAME:
					commandGroup = new IntakeCube();
					break;
				default:
					commandInput = null;
					break;
				}
				
			}
		} else {
			String[] commandArgs = splitLine[1].substring(0, splitLine[1].length() - 1).split("&");
			System.out.println(commandArgs[0]);
			
			isParallel = splitLine[1].endsWith(",");
			
			// Put Commands Here
			switch (commandInput) {
			case TestChaosCommand.NAME:
				command = new TestChaosCommand (1);
				break;
			case Drive.NAME:
				command = new Drive (1, drive);
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
			case Wait.NAME:
				command = new Wait (1);
				break;
			default:
				command = null;	
				break;
			}
			System.out.println(commandInput);
			
			if (command == null) {
				return;
			}
			
			if(command.getArgsLength() != 0) {
				command.setArgs(commandArgs);
			}
		
			
		}
	}
	
	
	/**
	 * Syntax: command;value(s)(, or .)
	 * @param check - the line to check
	 * @return - whether the line matches the syntax
	 */
	private boolean match(String check) {
		return Pattern.matches("[^;]+;[^;]*[,\\.]", check);
	}
	
	
	public ChaosCommand getCommand () {
		return command;
	}
	
	public CommandGroup getCommandGroup () {
		return commandGroup;
	}
	
	public boolean hasCommands () {
		return command != null || commandGroup != null;
	}
	
	public boolean getIsParallel () {
		return isParallel;
	}
	
	

}
