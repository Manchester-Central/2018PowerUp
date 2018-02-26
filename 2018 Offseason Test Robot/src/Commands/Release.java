package Commands;

public class Release extends TimeRestrictedCommand {
	
	public static final String NAME = "Release";
	
	public Release(int argsLength) {
		super(argsLength);
		
	}

	@Override
	protected boolean isFinished() {
		
		return super.isFinished();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		System.out.println("release");
		
	}



}
