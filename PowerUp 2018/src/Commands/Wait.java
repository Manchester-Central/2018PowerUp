package Commands;

public class Wait extends TimeRestrictedCommand {
	
	public static final String NAME = "Wait";
	
	public Wait(int argsLength) {
		super(argsLength);
		maxTime = Long.parseLong(args[0]);
	}

}
