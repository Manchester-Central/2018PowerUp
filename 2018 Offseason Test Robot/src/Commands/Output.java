package Commands;

public class Output extends TimeRestrictedCommand {
	
	public static final String NAME = "Output";

	
	public Output(int argsLength) {
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
		System.out.println("output");
	}

}
