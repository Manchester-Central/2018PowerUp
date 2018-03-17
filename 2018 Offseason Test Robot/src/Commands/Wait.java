package Commands;

public class Wait extends TimeRestrictedCommand {
	
	public static final String NAME = "Wait";
	
	public Wait(int argsLength) {
		super(argsLength, NAME);
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		maxTime = Long.parseLong(args[0]);
		System.out.println("Waiting");
		
	}

}
