package autonomous.builder;

import java.util.regex.Pattern;

import autonomous.command.group.IntakeCube;
import autonomous.command.group.OutputScale;
import autonomous.command.group.OutputSwitch;
import autonomous.commands.Drive;
import autonomous.commands.Extend;
import autonomous.commands.Intake;
import autonomous.commands.Lift;
import autonomous.commands.Output;
import autonomous.commands.Pinch;
import autonomous.commands.Relax;
import autonomous.commands.Release;
import autonomous.commands.Retract;
import autonomous.commands.TestChaosCommand;
import autonomous.commands.Turn;
import autonomous.commands.Wait;
import autonomous.scalecommands.ScaleDrive;
import autonomous.scalecommands.ScaleExtend;
import autonomous.scalecommands.ScaleIntake;
import autonomous.scalecommands.ScaleLift;
import autonomous.scalecommands.ScaleOutput;
import autonomous.scalecommands.ScalePinch;
import autonomous.scalecommands.ScaleRelax;
import autonomous.scalecommands.ScaleRelease;
import autonomous.scalecommands.ScaleRetract;
import autonomous.scalecommands.ScaleTurn;
import autonomous.scalecommands.ScaleWait;
import autonomous.switchcommands.SwitchDrive;
import autonomous.switchcommands.SwitchExtend;
import autonomous.switchcommands.SwitchIntake;
import autonomous.switchcommands.SwitchLift;
import autonomous.switchcommands.SwitchOutput;
import autonomous.switchcommands.SwitchPinch;
import autonomous.switchcommands.SwitchRelax;
import autonomous.switchcommands.SwitchRelease;
import autonomous.switchcommands.SwitchRetract;
import autonomous.switchcommands.SwitchTurn;
import autonomous.switchcommands.SwitchWait;
import edu.wpi.first.wpilibj.command.CommandGroup;
import system.components.CubeManipulator;
import system.components.DriveBase;
import system.components.LinearLift;

public class PreferenceTableLine {
	
	private ChaosCommand command;
	private CommandGroup commandGroup;
	private boolean isParallel;
	
	private String[] commands = {
			
			TestChaosCommand.NAME,
			
			Drive.NAME,
			Turn.NAME,
			Retract.NAME,
			Output.NAME,
			Lift.NAME,
			Intake.NAME,
			Extend.NAME,
			Wait.NAME,
			Release.NAME,
			Pinch.NAME,
			Relax.NAME,

			SwitchDrive.NAME,
			SwitchTurn.NAME,
			SwitchRelease.NAME,
			SwitchLift.NAME,
			SwitchExtend.NAME,
			SwitchOutput.NAME,
			SwitchRelax.NAME,
			SwitchPinch.NAME,
			SwitchRetract.NAME,
			SwitchWait.NAME,
			SwitchIntake.NAME,

			ScaleDrive.NAME,
			ScaleTurn.NAME,
			ScaleRelease.NAME,
			ScaleLift.NAME,
			ScaleExtend.NAME,
			ScaleOutput.NAME,
			ScaleRelax.NAME,
			ScalePinch.NAME,
			ScaleRetract.NAME,
			ScaleWait.NAME,
			ScaleIntake.NAME
			
			};
	
	private String[] commandGroups = {IntakeCube.NAME, OutputSwitch.NAME, OutputScale.NAME};
	
	/**
	 * 
	 * @param input 
	 * @param drive 
	 * @param lift 
	 * @param cubeManipulator 
	 */
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
				
				// Put CommandGroups Here
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
			case Wait.NAME:
				command = new Wait (1);
				break;
			case Release.NAME:
				command = new Release (0, cubeManipulator);
				break;
			case Relax.NAME:
				command = new Relax (0, cubeManipulator);
				break;
			case Pinch.NAME:
				command = new Pinch (0, cubeManipulator);
				break;
			case SwitchRelease.NAME:
				command = new SwitchRelease (2, cubeManipulator);
				break;
			case SwitchRelax.NAME:
				command = new SwitchRelax (2, cubeManipulator);
				break;
			case SwitchLift.NAME:
				command = new SwitchLift (2, lift);
				break;
			case SwitchOutput.NAME:
				command = new SwitchOutput (2, cubeManipulator);
				break;
			case SwitchExtend.NAME:
				command = new SwitchExtend(2, cubeManipulator);
			case ScaleExtend.NAME:
				command = new ScaleExtend (2, cubeManipulator);
				break;
			case ScaleLift.NAME:
				command = new ScaleLift (2, lift);
				break;
			case ScaleOutput.NAME:
				command = new ScaleOutput (2, cubeManipulator);
				break;
			case ScaleRelease.NAME:
				command = new ScaleRelease (2, cubeManipulator);
				break;
			case ScaleRelax.NAME:
				command = new ScaleRelax (2, cubeManipulator);
				break;
			case SwitchPinch.NAME:
				command = new SwitchPinch (2, cubeManipulator);
				break;
			case SwitchRetract.NAME:
				command = new SwitchRetract (2, cubeManipulator);
				break;
			case SwitchWait.NAME:
				command = new SwitchWait (2);
				break;
			case SwitchIntake.NAME:
				command = new SwitchIntake (2, cubeManipulator);
				break;
			case ScalePinch.NAME:
				command = new ScalePinch (2, cubeManipulator);
				break;
			case ScaleRetract.NAME:
				command = new ScaleRetract (2, cubeManipulator);
				break;
			case ScaleWait.NAME:
				command = new ScaleWait (2);
				break;
			case ScaleIntake.NAME:
				command = new ScaleIntake (2, cubeManipulator);
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
