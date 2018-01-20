package NewAutoShell;

import java.util.Map;
import java.util.regex.Pattern;

public class PreferenceTableLine {
	
	private ChaosCommand command;
	private boolean isParallel;
	
	public PreferenceTableLine (String input, Map<String, ChaosCommand> commands) {
		
		if(!match(input)) {
			return;
		}
		
		
		String[] splitLine = input.split(";");
		String commandInput = splitLine[0];
		if  (!commands.containsKey(commandInput)) { 
			return;
		}
		String[] commandArgs = splitLine[1].substring(0, splitLine[1].length() - 1).split("&");
		
		isParallel = splitLine[1].endsWith(",");
		
		command = commands.get(commandInput);
		
		command.setArgs(commandArgs);
	}
	
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
