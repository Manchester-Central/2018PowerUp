package NewAutoShell;

import java.util.Map;

import StuffToLookAt.DriveForward;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NewAutoShell {
	
	//Map<String, IState> states;
	//Map<String, ICondition> conditions;
	Map<String, ChaosCommand> commands;
	//UserDefinedChanges userFunction;
	//char[] characterBreaks;
	
	//NetworkTable table;
	Preferences prefs;
	int stage = 1;
	
	
	//IState currentState;
	//ICondition currentCondition;
	//String currentCondtionVariable;
	//String currentStateVariable;
	
	public NewAutoShell (Map<String, ChaosCommand> commands) {
		this.commands = commands;
		SmartDashboard.putData("asdf",new DriveForward());
	}
		
	
	//public NewAutoShell (Map<String, Command> commands, UserDefinedChanges uFunc) {
	//	this(commands);
		//userFunction = uFunc;
		//characterBreaks = charBreaks;
	//}
	
	public String getAutoStageString(int i) {
		return prefs.getString("Auto Stage " + i, "none");
	}
	
	public void clearAll() {
		//if ()
//		for (String key : prefs.getKeys()) {
//			prefs.remove(key);
//		}
		
	}
	
	
	
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
	
	/*public void run() {
		if (currentState == null){
			String currentStageString = getAutoStageString(stage);

			if (currentStageString == null){
				stage++;
				return;
			}
			/*
			// the characters that separate the input 
			String charBreakString = "";
			
			// adds each character in characterBreaks to charBreaks
			if (characterBreaks.length > 0) {
				
				for (char x : characterBreaks) {
					
					charBreakString = charBreakString + " | (" + x + ")"; 
				}
				charBreakString = charBreakString.substring(3); 
			} 
			
			String[] currentStageArgs = currentStageString.split("(;) | (=)");
			
			if (currentStageArgs.length != 4){
				stage++;
				return;
			}
			
			//currentState = states.get(currentStageArgs[0]);
			currentStateVariable = currentStageArgs[1];
			//currentCondition = conditions.get(currentStageArgs[2]);
			currentCondtionVariable = currentStageArgs[3];
			
			if (currentState == null || currentCondition == null || currentCondtionVariable == null || currentStateVariable == null){
				stage++;
				currentState = null;
				currentCondition = null;
				currentCondtionVariable = null;
				return;
			}
			currentCondition.init(currentCondtionVariable);
			
			userFunction.init();
		
		}
		
		userFunction.execute();	
		boolean theCheckCondition = currentCondition.check();
		boolean theStopCondition = currentState.checkStop(currentStateVariable);
		
		if (theStopCondition) {
			currentState.stop();
		} else {
			currentState.run();
		}
		
		if (theCheckCondition){
			currentState.stop();
			stage++;
			currentState = null;
			currentCondition = null;
			currentCondtionVariable = null;
			currentStateVariable = null;
		}
	} */
}
