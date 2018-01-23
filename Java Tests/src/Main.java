import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import Commands.TestChaosCommand;
import NewAutoShell.ChaosCommand;
import NewAutoShell.PreferenceTableLine;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		String jeff = "test;&yabadabadoo&fdsd&.";
		
		match(jeff);
		match("turn;87&-87.");
		match("");
		match("turn87.");
		match("turn;87;87.");
		match("turn;87;87,");
		match("turn;87");
		
		
	}

	public static void match(String check) {
		CommandGroup autoSequence = new CommandGroup();
		Map<String, ChaosCommand> commands = new HashMap<String, ChaosCommand>();
		commands.put("test", new TestChaosCommand());
		PreferenceTableLine line = new PreferenceTableLine(check, commands);
		System.out.print(line.getCommand() != null);
		System.out.println(line.getIsParallel());
		
	}
	private static void endsWith(String check, String endMatch)
	{
		System.out.println(check.endsWith(endMatch));
		System.out.println(Pattern.matches(".+[" + endMatch + "]", check) );
	}
}
