package Commands;

public class Extend extends TimeRestrictedCommand {
	
	public static final String NAME = "Extend";
	
	public Extend(int argsLength) {
		super(argsLength);
	}

	@Override
	protected boolean isFinished() {
		return super.isFinished();
	}
	
	@Override
	protected void initialize () {
		System.out.println("extend");
		super.initialize();
		
	}

}
