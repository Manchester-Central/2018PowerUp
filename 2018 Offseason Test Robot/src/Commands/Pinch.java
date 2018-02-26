package Commands;

public class Pinch extends TimeRestrictedCommand {
	
	public static final String NAME = "Pinch";
	
	public Pinch(int argsLength) {
		super(argsLength);
		
	}

	@Override
	protected boolean isFinished() {
		
		return super.isFinished();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		System.out.println("pinch");
		
	}

}

