package ChaosLib;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.Preferences;
//import edu.wpi.first.wpilibj.networktables.NetworkTable;
import system.components.DriveBase;

public class AutoShell {
	
	
	Map<String, IState> states;
	Map<String, ICondition> conditions;
	UserDefinedChanges userFunction;
	//char[] characterBreaks;
	
	//NetworkTable table;
	Preferences prefs;
	int stage = 1;
	
	IState currentState;
	ICondition currentCondition;
	String currentCondtionVariable;
	String currentStateVariable;
	
	public AutoShell (Map<String, IState> uStates, Map<String, ICondition> uConditions) {
		states = uStates;
		conditions = uConditions;
	}
		
	
	public AutoShell (Map<String, IState> uStates, Map<String, ICondition> uConditions, UserDefinedChanges uFunc) {
		this(uStates, uConditions);
		userFunction = uFunc;
		//characterBreaks = charBreaks;
	}
	
	public String getAutoStageString(int i) {
		return prefs.getString("Auto Stage " + i, "none;none=hi");
	}
	
	public void run() {
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
			} */
			
			String[] currentStageArgs = currentStageString.split("(;) | (=)");
			
			if (currentStageArgs.length != 4){
				stage++;
				return;
			}
			
			currentState = states.get(currentStageArgs[0]);
			currentStateVariable = currentStageArgs[1];
			currentCondition = conditions.get(currentStageArgs[2]);
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
	}
}
