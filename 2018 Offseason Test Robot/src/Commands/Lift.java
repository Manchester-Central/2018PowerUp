package Commands;


import NewAutoShell.ChaosCommand;

public class Lift extends ChaosCommand {

	public static final String NAME = "Lift";
	
	public Lift(int argsLength) {
		super(argsLength);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void initialize () {
		
		System.out.println("set lift to: " + args[0]);
	}
	

	@Override 
	protected void execute() {
	}
	
	@Override
	protected void end () {
	}
}
