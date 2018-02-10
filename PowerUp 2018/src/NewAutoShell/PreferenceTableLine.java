package NewAutoShell;

import java.util.regex.Pattern;

import Commands.Drive;
import Commands.Extend;
import Commands.Intake;
import Commands.Lift;
import Commands.Output;
import Commands.Retract;
import Commands.ScaleDrive;
import Commands.ScaleTurn;
import Commands.SwitchDrive;
import Commands.SwitchTurn;
import Commands.TestChaosCommand;
import Commands.Turn;
import SystemComponents.CubeManipulator;
import SystemComponents.DriveBase;
import SystemComponents.LinearLift;
import command.group.IntakeCube;
import command.group.OutputScale;
import command.group.OutputSwitch;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class PreferenceTableLine {
	
	private ChaosCommand command;
	private CommandGroup commandGroup;
	private boolean isParallel;
	
	private String[] commands = {TestChaosCommand.NAME , SwitchDrive.NAME , ScaleDrive.NAME 
			, Drive.NAME, SwitchTurn.NAME, ScaleTurn.NAME, Turn.NAME, Retract.NAME, Output.NAME,
			Lift.NAME, Intake.NAME, Extend.NAME};
	
	private String[] commandGroups = {IntakeCube.NAME, OutputSwitch.NAME, OutputScale.NAME};
	
	// Reads each input line and builds the command for each
	public PreferenceTableLine (String input, DriveBase drive, LinearLift lift, CubeManipulator cubeManipulator) {
		
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
				
				switch (commandInput) {
				case IntakeCube.NAME:
					commandGroup = new IntakeCube(cubeManipulator, lift);
					break;
				case OutputSwitch.NAME:
					commandGroup = new OutputSwitch(lift, cubeManipulator);
					break;
				case OutputScale.NAME:
					commandGroup = new OutputScale(lift, cubeManipulator);
					break;
				default:
					commandInput = null;
					break;
				}
				
			}
		} else {
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
			case Output.NAME:
				command = new Output (0, cubeManipulator);
				break;
			case Lift.NAME:
				command = new Lift (1, lift);
				break;
			case Retract.NAME:
				command = new Retract (0, cubeManipulator);
				break;
			case Extend.NAME:
				command = new Extend (0, cubeManipulator);
				break;
			case Intake.NAME:
				command = new Intake (0, cubeManipulator);
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
	}
	
	
	// Returns whether input string matches syntax
	private boolean match(String check) {
		return Pattern.matches("[^;]+;[^;]+[,\\.]", check);
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
