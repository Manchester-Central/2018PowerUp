package Commands;

public class Intake extends TimeRestrictedCommand {
	
	public static final String NAME = "Intake";
	
	public Intake(int argsLength) {
		super(argsLength);
		
	}

	@Override
	protected boolean isFinished() {
		return super.isFinished();
	}
	
	@Override
	protected void execute () {
		
		
	}
	
	@Override
	protected void initialize () {
		
		super.initialize();
		System.out.println("intake");
		
	}

}
