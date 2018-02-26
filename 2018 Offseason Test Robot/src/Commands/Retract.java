package Commands;

public class Retract extends TimeRestrictedCommand {
	
	public static final String NAME = "Retract";
	
	public Retract(int argsLength) {
		super(argsLength);
		
	}

	@Override
	protected boolean isFinished() {
		
		return super.isFinished();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		System.out.println("retract");
		
	}

}
